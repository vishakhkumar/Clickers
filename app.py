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
db = SQLAlchemy(app)

# Sql constants
option = 5
numerical = 30
string = 120
question = 500


def characterLength(a):
    if a == "option":
        return 5
    elif a == "numerical":
        return 30
    elif a == "string":
        return 120
    elif a == "question":
        return 500


def columnname(i):
    return ("Q" + +str(i + 1))


def dbupdate(studentID, answer, question):
    student = db.query.filter_by(student_id=studentID).first()
    student.question = answer
    db.session.commit()


class Session(db.Model):
    # your students
    id = db.Column('student_id', db.Integer, primary_key=True)


def __init__(self, types, questions):
    for i in types:
        # serialize the name coming from columnname(i)
        exec(columnname(i) + " = db.column(db.String(" + characterLength(types[i]) + " )) ")


@app.route('/new', methods=['GET', 'POST'])
def new():
    if request.method == 'POST':
        student = students(request.form['name'], request.form['city'],
                           request.form['addr'], request.form['pin'])
        db.session.add(student)
        db.session.commit()
        flash('Record was successfully added')
        return redirect(url_for('show_all'))

db.create_all()
"""

if __name__ == '__main__':
    db.create_all()
    app.run(host='0.0.0.0', debug=True)
