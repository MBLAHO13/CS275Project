import os
import sqlite3 

def changeName():
   conn = sqlite3.connect("ingredients.db")
   c = conn.cursor()
   c.execute("SELECT * from ingredients")
   ingredients = c.fetchall();
   for ingredient in ingredients:
      name = ingredient[1].replace('/','|')
      ingredient_id = "{0:0=5d}".format(ingredient[0])
      print(ingredient_id)
      os.rename('conversions/'+name+'.csv','conversions/'+ingredient_id+'.csv')
changeName()
