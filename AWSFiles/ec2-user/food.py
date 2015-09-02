#!/usr/bin/python3
import collections
import collections.abc
import pathlib
import usda
import csv
import sqlite3

IGNORED_NUTRIENTS = {
    ("Energy", "kJ"),
}

class Nutrient(collections.namedtuple("Nutrient", ('unit','val'))):
    def __repr__(self):
        return str(self.val) + str(self.unit)

    def __str__(self):
        return str(self.val)

    def __add__(self, other):
        if other.unit == self.unit:
            return Nutrient(self.unit, self.val+other.val)
        else:
            raise TypeError("Cannot add units: {} and {}".format(self.unit, other.unit))

    def __mul__(self, other): # scalar multiplication
        return Nutrient(self.unit, self.val*other)

    def __truediv__(self, other): # scalar division
        return Nutrient(self.unit, self.val/other)

# TODO: Actually fix nutrient quantities to be exact 1g amounts
# TODO: Add a "current conv" so that the food is a 'qty' of a unit (ex: 100g or 2 slices or 5 cups cubed)
# TODO: Remove 'get' method and convert based on current conv
# TODO: Add a 'convert' method (or change resize?) so that the food can be changed from ex. 100g to 2cups
class Food(collections.abc.Mapping):
    """Object for organizing nutritional data relating to a food item

    Nutrients can be acessed in 2 ways: through direct indexing 
        Food["nutrient"]) 
    or through the .get method 
        Food.get("nutrient")
        Food.get("nutrient","conv_tbl")

    Attributes:
        id (str): NDB / local DB id
        name (str): Name of the food item
        group (str): Food/recipe group
        qty (numeric): Amount of food item
        conv_tbl (dict): Mapping of conv_tbl names (ex: "cup, shredded")
            to values used for converting nutrient data. Use the .get() method
            to convert directly
    """
    def __init__(self, ndbid, name, qty, grp=None, conv=None, **nutrients):
        """Initalizes the food object

        Args:
            ndbid (str): ID for the database (same for ndb and local)
            name (str): Name of the food object
            qty (numeric): Amount of food item, in grams

        Keyword Args:
            grp (str): Food group or recipe group
            conv (dict): conv_tbl table
            **nutrients (dict): mapping from string names to Nutrient objects
                describing the food
        """
        self.id = ndbid
        self.name = name
        self.group = grp
        self.qty = qty
        self.__nutrients = nutrients
        self.conv_tbl = conv

    @classmethod
    def from_ndb(cls, conn, ndbid):
        """Gets food data from the USDA and packages it into a Food object

        Args:
            conn (NDBconn): REST connection to the database
            ndbid (str): ID for the USDA NDB

        Returns:
            Food: The object created from the NDB with default qty of 100g
        """
        try:
            data = conn.report(ndbid)
        except ValueError:
            raise IndexError('Invalid food id')
        if 'errors' in data.keys():
            raise IndexError('Invalid food id')
        data = data['report']['food']
        name = data['name']
        group = data['fg']
        nutrients = {}
        for nutrient in data['nutrients']:
            nname = nutrient['name'].replace(":","_").replace("-","_").replace("(","").replace(")","").replace("+","").replace(',','').replace(' ','_')
            if (nname,nutrient['unit']) in IGNORED_NUTRIENTS:
                continue
            nutrients[nname.lower()] = Nutrient(
                    nutrient['unit'], 
                    nutrient['value']/100
            )
        convert = {}
        for conv_tbl in data['nutrients'][0]['measures']:
            convert[conv_tbl['label'].lower()] = conv_tbl['eqv']
        return cls(ndbid, name, 1, group, convert, **nutrients)

    def resize(self, new_qty):
        """Copies the old food with a new size

        While it is possible to resize a food in-flight, it may be more useful
        to treat Food objects as static, and create a new Food for a different
        size

        Args:
            new_qty (numeric): The new quantity, in grams

        Returns:
            Food: new Food object resized from the old object
        """
        return self.__class__(
                self.id,            # id
                self.name,          # name
                new_qty,            # qty
                self.group,         # group
                self.conv_tbl,    # convert
                **self.__nutrients  # nutrients
        )

    def __getitem__(self, key):
        n = self.__nutrients[key]
        return Nutrient(n.unit, n.val*self.qty)

    def __iter__(self):
        return iter(self.__nutrients)

    def __len__(self):
        return len(self.__nutrients) 

    def __add__(self, other):
        qty = self.qty + other.qty
        if self.id == other.id:
            # If they're the same object, don't iterate over everything
            return self.resize(qty)
        nutrients = {}
        for n in self.keys() & other.keys():
            nutrients[n] = (self[n]+other[n])/qty
        for n in self.keys() - other.keys():
            nutrients[n] = (self[n])/qty
        for n in other.keys() - self.keys():
            nutrients[n] = (other[n])/qty
        name = "{} + {}".format(self.name, other.name)
        group = None
        if self.group == other.group:
            group = self.group
        return self.__class__(
                0,          # id
                name,       # name
                qty,        # qty
                group,      # group
                None,       # convert
                **nutrients # nutrients
        )

    def get(self, nutrient, ctype=None):
        """Gets the given nutrient and converts it according to ctype

        Args:
            nutrient (str): The nutrient name to lookup
            ctype (str): The conv_tbl type (None for values in current qty)

        Returns:
            Nutrient: object containing the unit and value of the nutrient in
                the given amount of food

        Raises:
            KeyError: ctype or nutrient are invalid. Nutrient can be checked
                with Food.keys()
        """
        n = self[nutrient]
        convert = 1
        if ctype != None:
            convert = self.conv_tbl[ctype]/self.qty
        return Nutrient(n.unit, n.val*convert)

    def export(self, conn, path):
        """Exports food object to the database/cache

        CSV is in the format "type,val" with a separate line for each type of
        conv_tbl

        Args:
            conn (database): Connection to the database
            path (pathlib.Path): Directory for the CSV file to be written to
        """
        ### Insert into database
        # id, name, group, price, *nutrients
        vals = 3 + len(self.__nutrients)
        nutrients = self.__nutrients.items() # Done this way so keys and values are in alignment
        # Formatting a SQL query is usually very bad, but here we're just
        # adding the correct number of placeholders since it doesn't allow
        # variable injection prevention
        cols = ["id","name","group_name"] + [n[0] for n in nutrients]
        update_cols(conn, set(cols))
        cols = ['"{}"'.format(col) for col in cols]
        query = 'INSERT INTO ingredients ({0}) VALUES ({1})'.format(','.join(cols), ','.join(['?']*vals))
        args = [self.id, self.name, self.group] + [str(n[1]) for n in nutrients]
        c = conn.cursor()
        try:
            c.execute(query, args)
        except sqlite3.IntegrityError:
            print("Row already entered")
        c.close()

        ### Create CSV file
        csvpath = path/(self.id.replace("/","|") + '.csv')
        with csvpath.open("w") as f:
            c = csv.writer(f, quoting=csv.QUOTE_ALL)
            for conv, val in self.conv_tbl.items():
                c.writerow((conv,val))

def update_cols(conn, cols):
    c = conn.cursor()
    dbcols = {row[1] for row in c.execute("PRAGMA table_info(ingredients)")}
    newcols = cols - dbcols
    for col in newcols:
        c.execute('ALTER TABLE ingredients ADD COLUMN "' + col + '" varchar(30)')
    c.close()

class Recipe(Food):
    def __init__(self, name, *ingredients, grp=None):
        r = sum(ingredients, Food(0,'',0))
        super().__init__(0, name, r.qty, grp, **r)
        # tuple so it is immutable
        self.__ingredients = tuple(ingredients)

    @property
    def ingredients(self):
        # Since it's a tuple, the immutability should remain valid
        return self.__ingredients

    def export(self, path):
        """Exports recipe ingredients to csv

        CSV is in the format "id,qty" with separate lines for each ingredient

        Args:
            path (pathlib.Path): Directory for the CSV file to be written to
        """
        csvpath = path/(self.id + '.csv')
        with csvpath.open("w") as csv:
            for i in self.ingredients:
                csv.write(i.id + "," + str(i.qty) + '\n')

def test(ndbid):
    conn = usda.NDBconn(usda.DEV_KEY)
    return Food.from_ndb(conn, ndbid)

if __name__ == "__main__":
    conn = sqlite3.connect("ingredients.db")
    update_cols(conn, None)
    conn.close()
