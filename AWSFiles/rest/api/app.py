#!flask/bin/python
from flask import Flask, jsonify, request
import sqlite3
import requests, json

app = Flask(__name__)

def getIdResults(ingredient_id): 
   def dict_factory(cursor, row):
      d = {}
      for idx, col in enumerate(cursor.description):
         d[col[0]] = row[idx]
      return d
    
   #First checking in databse
   conn = sqlite3.connect('../../ingredients.db', detect_types=sqlite3.PARSE_COLNAMES)
   conn.row_factory = dict_factory
   c = conn.cursor()
   query = 'SELECT * from ingredients where id=(?);'
   c.execute(query, (int(ingredient_id),))
   query_result = c.fetchone()
   #if database doesn't have the id
   print(str(ingredient_id).zfill(5))
   if query_result is None: 
      string_id = str(ingredient_id).zfill(5)
      url = "http://api.nal.usda.gov/ndb/reports/?ndbno="+string_id+"&type=f&format=json&api_key=DEMO_KEY"
      url_results = request.get(url).json()
      #to make sure we don't get bad outputs
      if 'errors' in url_results:
         reponse ={'response': 'ingredient', 'error': 'Invalid key'}
      response = {
         'response' : 'ingredient',
         'id' : url_results['report']['food']['ndbno'],
         'name': url_results['report']['food']['name'],
         'group_name': url_results['report']['food']['fg'],
         'nutrients': []
                  }
      for nutrient in url_results['report']['food']['nutrients']:
         response['nutrients'].append({'name': nutrient['name'] ,'value':nutrient['value']})
      return response
                 
   response = {
    'response': 'ingredient',
    'id': query_result['id'],
    'name': query_result['name'],
    'group_name': query_result['group_name'],
    'nutrients': []
   }
   for key in query_result.keys():
      if key is not ('name' or 'id' or 'group_name'):
         if query_result[key] is not None: 
            response['nutrients'].append({'name':key,'value':query_result[key]})
   return response

def getNameResults(ingredient_name):
   def dict_factory(cursor, row):
      d = {}
      for idx, col in enumerate(cursor.description):
         d[col[0]] = row[idx]
      return d

   #First checking in databse
   conn = sqlite3.connect('../../ingredients.db', detect_types=sqlite3.PARSE_COLNAMES)
   conn.row_factory = dict_factory
   c = conn.cursor()
   query = 'SELECT * from ingredients where name=(?);'
   c.execute(query, (str(ingredient_name),))
   query_result = c.fetchone()
   #if database doesn't have the name
   if query_result is None: 
      url = "http://api.nal.usda.gov/ndb/search/?format=json&q="+ingredient_name+"&sort=n&max=100&offset=0&api_key=xRqfBLIxnQHgOZQJkJDHhBR2X95Vlhs002Y58Lfr"
      url_results = requests.get(url).json()
      response = {
              'response': 'search',
              'result':[] }
      for ingredient in url_results['list']['item']:
         response['result'].append({'name': ingredient['name'], 'id': ingredient['ndbno']})
      return response
   
   response = {
    'response': 'ingredient',
    'id': query_result['id'],
    'name': query_result['name'],
    'group_name': query_result['group_name'],
    'nutrients': []
   }
   for key in query_result.keys():
      if key is not ('name' or 'id' or 'group_name'):
         if query_result[key] is not None:
            response['nutrients'].append({'name':key,'value':query_result[key]})
   return response

@app.route('/', methods=['GET'])
def index():
    response = "Hello, World"
    return response

@app.route('/rest/api/id/', methods=['GET'])
def ingredient():
   ingredient_id = request.headers.get('request-value')
   response = getIdResults(ingredient_id)
   return jsonify(response)

@app.route('/rest/api/name/', methods=['GET'])
def search():
   ingredient_name = request.headers.get('request-value')
   response = getNameResults(ingredient_name)
   return jsonify(response)
if __name__ == '__main__':
    app.run(host='0.0.0.0')
    #print getNameResults("Cheese")
