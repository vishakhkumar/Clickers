from flask import Flask, request, jsonify
from flask_json import FlaskJSON, JsonError, json_response, as_json
from flask_sqlalchemy import SQLAlchemy

from datetime import datetime



# Professor question template
types = ["option", "numerical", "string", "option", "numerical"]

app = Flask(__name__,static_url_path='')
FlaskJSON(app)

app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///session.sqlite3'
db = SQLAlchemy(app)



@app.route('/')
def sup():
    return "Sup, world!"

@app.route('/peekaboo/<uuid>',methods=['GET','POST'])
def peekaboo(uuid):
    content = request.get_json()
    print("Hello!")
    print("sucess!") 
    return jsonify({"msg":"Transmission successful"})

@app.route('/get_time',methods=['POST'])
def get_time():
    print("poop")
    now = datetime.utcnow()
    return jsonify({"time":now})    


@app.route('/initialize/<uuid>', methods=['POST'])
def initialize(uuid):
    global db
    
    print("Sup!")
    questionTemplate = request.json
    # Sql constants
    option = 5
    numerical = 30
    string = 120
    question = 500

    def characterLength(a):
        if a == "option":      return 5
        elif a == "numerical": return 30
        elif a == "string":    return 120
        elif a == "question":  return 500

    def columnname(i):
        return "Q" + str(i + 1)

    class Session(db.Model):
        global db
        # your students id.
        id = db.Column('student_id', db.Integer, primary_key=True)

        def __init__(self, types):
            print("Database initialised")

        def init(self,types):
         for i in types:
             # serialize the name coming from columnname(i)
             eval(columnname(i) + " = db.column(db.String(" + characterLength(types[i]) + " )) ")


    student = Session()
    student.init(questionTemplate['array'])
    db.session.add(student)
    db.session.commit()
    flash('Record was successfully added')

    return redirect(url_for('show_all'))


@app.route('/api/answer/<uuid>', methods=['GET', 'POST'])
def answer(uuid):
    
    global db

    content = request.get_json()

    class AnswerResponse(object):
        def __init__(self, studentID, answer):
         self.type = "Answer"
         self.studentID = studentID
         self.answer = answer
        def grade(self):
         return self.answer == 'A'

    studentResp = AnswerResponse(studentID=content['studentID'], answer=content['answer'])

    """
    student = db.query.filter_by(student_id=studentResp.studentID).first()
    student.question = answer
    db.session.commit()
    """
    return jsonify({"msg": "Transmission successful"})


if __name__ == '__main__':
    app.run(debug=True)


