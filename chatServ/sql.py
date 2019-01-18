import sys
import json
import sqlite3
 
conn = sqlite3.connect("db.sqlite")
cursor = conn.cursor()

if sys.argv[1] == "0":
    try:
        cursor.execute(sys.argv[2])
        print(200)
    except sqlite3.OperationalError:
        print(500)

elif sys.argv[1] == "1":
    try:
        cursor.execute(sys.argv[2])
        results = cursor.fetchall()
        print(json.dumps(results))
    except sqlite3.OperationalError:
        print(500)

conn.commit()
conn.close()