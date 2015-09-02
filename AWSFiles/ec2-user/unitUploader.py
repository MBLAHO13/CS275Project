import requests, json

url = "http://api.nal.usda.gov/ndb/list?format=json&lt=n&max=200&sort=n&api_key=xRqfBLIxnQHgOZQJkJDHhBR2X95Vlhs002Y58Lfr";
n_list = requests.get(url).json()
nutrients = n_list["list"]["item"]
unitsCSV = open("units2.csv", 'w')
for nutrient in nutrients:
   n_id = nutrient["id"] 
   name = nutrient["name"]
   try:
      unit_url = "http://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=xRqfBLIxnQHgOZQJkJDHhBR2X95Vlhs002Y58Lfr&nutrients="+n_id
      f_list = requests.get(unit_url).json()
      unit = f_list["report"]["foods"][0]["nutrients"][0]["unit"]
      unitsCSV.write(name+","+unit+"\n")
   except: 
      print(n_id,"Bad id")
   
