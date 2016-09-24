from flask import Flask, request, jsonify
from flask import flash
from flask import redirect
from flask import render_template
from flask import url_for
from flask_sqlalchemy import SQLAlchemy


# implementation of answer responses via JSON queries

class answerresponse(object):
    def __init__(self, studentID, answer, questionNo):
        # ask for forgiveness
        try:
            self.studentID
        except AttributeError:
            self.studentID = None

        try:
            self.answer
        except AttributeError:
            self.answer = None

        try:
            self.questionNo
        except AttributeError:
            self.questionNo = None

        # assign values, even if they don't exist.

        self.type = "Answer"
        self.studentID = studentID
        self.answer = answer
        self.questionNo = questionNo

    def grade(self):
        if self.answer == 'A':
            return True
        else:
            return False


app = Flask(__name__)


@app.route('/api/answer/<uuid>', methods=['GET', 'POST'])
def answer():
    content = request.json
    answerR = answerresponse(studentID=content['studentID'], answer=content['answer'], question=content['question'])
    if request.method == 'POST':
        student = students(request.form['name'], request.form['city'],
                           request.form['addr'], request.form['pin'])
        db.session.add(student)
        db.session.commit()
        flash('Record was successfully added')
        return redirect(url_for('show_all'))

    return jsonify(msg="Accepted")


app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///session.sqlite3'
app.config['SECRET_KEY'] = "random string"

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


if __name__ == '__main__':
    db.create_all()
    app.run(host='0.0.0.0', debug=True)
