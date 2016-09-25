from flask import Flask, request, jsonify
from flask import flash
from flask import redirect
from flask import url_for
from flask_sqlalchemy import SQLAlchemy

# implementation of answer responses via JSON queries

global db


class AnswerResponse(object):
    def __init__(self, studentID, answer):
        # ask for forgiveness
        try:
            self.studentID
        except AttributeError:
            self.studentID = None
        try:
            self.answer
        except AttributeError:
            self.answer = None
        # assign values, even if they don't exist.

        self.type = "Answer"
        self.studentID = studentID
        self.answer = answer

    def grade(self):
        return self.answer == 'A'


# Professor question template
types = ["option", "numerical", "string", "option", "numerical"]

app = Flask(__name__)


@app.route('/initialize/<QuestionTemplate>')
def initialise(questionTemplate):
    questionTemplate = request.json
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

    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///session.sqlite3'
    db = SQLAlchemy(app)

    student = Session()
    student.init(questionTemplate['array'])
    db.session.add(student)
    db.session.commit()
    flash('Record was successfully added')

    return redirect(url_for('show_all'))


@app.route('/api/answer/<uuid>', methods=['GET', 'POST'])
def answer(uuid):
    content = request.json
    studentResp = AnswerResponse(studentID=content['studentID'], answer=content['answer'])

    student = db.query.filter_by(student_id=studentResp.studentID).first()
    student.question = answer
    db.session.commit()

    return jsonify({"msg": "Transmission successful"})


@app.route('/')
def sup():
    return "Sup, world!"


if __name__ == '__main__':
    db.create_all()
    app.run(host='0.0.0.0', debug=True)
