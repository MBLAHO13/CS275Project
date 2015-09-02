#!/usr/bin/python3
import requests # http://docs.python-requests.org/en/latest/

DEV_KEY = 'B10wdzhOsMwEyQeSUsmMnrOeYesAFpPWjTya8H2c'

class NDBconn:
    url = 'http://api.nal.usda.gov/ndb/'
    def __init__(self, key):
        """Initializes the connection with api key"""
        self.__key = key
        self.requests_remaining = 1000

    def report(self, ndbno):
        """Returns food data from NDB

        Uses the ndbno and stored api_key to retrieve data from 
        http://api.nal.usda.gov/ndb/reports/

        Args:
            ndbno (str): ID for the food to query

        Returns:
            Dict generated from NDB JSON output.
        """
        r = requests.get(self.__format_url(
                'reports', 
                ndbno=ndbno, 
                type='f'
            ))
        self.requests_remaining = int(r.headers['x-ratelimit-remaining'])
        return r.json()

    def search(self, query, rows=50, offset=0):
        """Returns search query from NDB

        Queries NDB using their native search support at
        http://api.nal.usda.gov/ndb/search/

        Args:
            query (str): Search term(s)
            rows (int): Maximum number of rows to return

        Returns:
            Dict generated from NDB JSON output.
        """
        return(
            requests.get(self.__format_url(
                'search', 
                q=query,
                max=rows,
                offset=offset,
                format='json',
            )).json()
        )

    def __format_url(self, base, **params):
        """Handles URL parameter formatting and adds in api_key"""
        params['api_key'] = self.__key
        return(
            self.url+
            base+
            '/?'+
            '&'.join('{}={}'.format(k,v) for k,v in params.items())
        )

if __name__ == '__main__':
    ndb = NDBconn(DEV_KEY)
    cheddar = ndb.report('01009')
    cheeses = ndb.search('cheese', rows=1000)
    for cheese in cheeses['list']['item']:
        print(cheese['name'])
