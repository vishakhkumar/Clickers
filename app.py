from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy

#implementation of answer responses via JSON queries

class answerresponse(object):
  def __init__(self, studentID = None, answer= None):
    # ask for forgiveness
    try: self.studentID 
    except AttributeError:
      self.studentID = None
    try: self.answer
    except AttributeError:
      self.answer = None
    # assign values, even if they don't exist.
    
    self.type = "Answer"
    self.studentID = studentID
    self.answer = answer
  
  def grade(self):
    if self.answer=='A':
      return True
    else:
      return False

app = Flask(__name__)

@app.route('/api/answer/<uuid>', methods=['GET', 'POST'])
def answer(uuid):
    content = request.json
    student = answerresponse(studentID =  content['studentID'],answer = content['answer'])
    print(content['studentID'])
    return jsonify({"grade":student.grade()})

@app.route('/api/add_message/<uuid>', methods=['GET', 'POST'])
def add_message(uuid):
    content = request.json
    print(content['mytext'])
    return jsonify({"uuid":uuid})

@app.route('/')
def sup():
  return "Sup, world!"

"""
if __name__ == '__main__':
    app.run(host= '0.0.0.0',debug=True)



def sqlstartup(app,numQ=1,numS=1):

 
  app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///students.sqlite3'

  db = SQLAlchemy(app)
  class students(db.Model):
    id = db.Column('student_id', db.Integer, primary_key = True)
    name = db.Column(db.String(100))
    city = db.Column(db.String(50))  
    addr = db.Column(db.String(200))
    pin = db.Column(db.String(10))
   
  def __init__(self, numQ, numS):
    
    
    self.city = city
    self.addr = addr
    self.pin = pin


db.create_all()
"""