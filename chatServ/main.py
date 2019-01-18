import json
from flask import Flask
from flask import request
from subprocess import Popen, PIPE
from multiprocessing import Process, Queue
app = Flask(__name__)

def db_use(type_rec, rec_w):
  def execute(queue, result_n, rec):
    proc = Popen('python3 sql.py ' + str(result_n) + ' \"' + str(rec) + '\"', shell=True, stdout=PIPE)
    proc.wait()  # дождаться выполнения
    queue.put(proc.communicate()[0])
  queue = Queue()
  p = Process(target=execute, args=(queue, type_rec, rec_w))
  p.start()
  p.join()

  if type_rec == 0:
    return str(queue.get().decode("utf-8")) == ""
  else:
    return json.loads(queue.get().decode('utf-8'))


@app.route('/api')
def hello_world():

    login, messeg = request.args.get("login", ''), request.args.get("mess", '')

    if login != '' and messeg != '':
        db_use(0, "INSERT INTO messegs(id,login,messeg) VALUES (NULL,'"+login+"','"+messeg+"');")
        return 'ok'
    else :
        return 'error'

@app.route('/load')
def loadApi():
    wewe = db_use(1, "select * from messegs")
    weweq = []
    for qw in wewe:
        weweq.append({"messege":qw[1], "login":qw[2]})
    messobj = {
        "mess": weweq
    }
    return json.dumps(weweq)


if __name__ == '__main__':
    app.run(host="0.0.0.0",port="8080")
