

from flask import Flask,request
from flask_json import FlaskJSON, JsonError, json_response, as_json

#implementation of answer responses via JSON queries

class answerresponse(object):

  def __init__(self,StudentID=None,Answer=None):

    # ask for forgiveness
    try: self.studentID = 09090
    except AttributeError:
      self.studentID = None
    try: self.answer = 7878
    except AttributeError:
      self.answer = None

    # assign values, even if they don't exist.
    self.studentID = studentID
    self.answer = answer


  def toJSON(self):
    return json_response(studentID = self.studentID,answer=self.answer)


  def fromJSON(self,info):
    processResponse = True
    try:
        self.studentID = string(info['studentID'])
        self.answer = char(info['answer'])
    except (KeyError, TypeError, ValueError):
        processResponse = False
    return processResponse

app = Flask(__name__)

@app.route('/answer')
def answer():
  data = request.get_json(force=True)
  studentanswer = answerresponse()
  if studentanswer.fromJSON(data)=="Correct":
    return json_response(message="Thank you")
  else:
    raise JsonError(description='Invalid value.')







