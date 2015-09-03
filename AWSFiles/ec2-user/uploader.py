#!/usr/bin/python3
import food
import usda
import pathlib
import sqlite3
import time

def get_id_list():
    ids = set()
    ndb = usda.NDBconn(usda.DEV_KEY)
    for i in range((8618 // 150)+1):
        json = ndb.search("",150,i*150)
        ids |= {item['ndbno'] for item in json['list']['item']}
    print(ids)

def ids_from_file(conn):
    c = conn.cursor()
    ids = {"{:05}".format(i[0]) for i in c.execute('SELECT id FROM ingredients')}
    with open("ids.txt") as idfile:
        for ndbid in idfile:
            fileid = ndbid.replace("\n","")
            if fileid not in ids:
                yield fileid
    c.close()

if __name__ == '__main__':
    conn = sqlite3.connect("ingredients.db")
    ndb = usda.NDBconn(usda.DEV_KEY)
    # select count(id) from ingredients
    for ndbid in ids_from_file(conn):
        print(ndbid)
        try:
            item = food.Food.from_ndb(ndb, ndbid)
        except IndexError as e:
            print(e)
        else:
            item.export(conn, pathlib.Path("./conversions"))
        print("ndb_req_remaining:",ndb.requests_remaining)
        if(ndb.requests_remaining < 100):
            time.sleep(60*60)
    conn.close()