from flask import Flask,request,jsonify
from flask_json import FlaskJSON, JsonError, json_response, as_json

#implementation of answer responses via JSON queries

class answerresponse(object):
  def __init__(self,studentID=None,answer=None):
    # ask for forgiveness
    try: self.studentID 
    except AttributeError:
      self.studentID = None
    try: self.answer
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
        self.studentID = str(info['studentID'])
        self.answer = char(info['answer'])
    except (KeyError, TypeError, ValueError):
        processResponse = False
    return processResponse

app = Flask(__name__)


@app.route('/api/answer/<uuid>', methods=['GET', 'POST'])
def answer(uuid):
    content = request.json
    
    student = answerresponse(studentID =  content['studentID'],answer = content['answer'])
    return jsonify({"message":student.grade()})
    

@app.route('/api/add_message/<uuid>', methods=['GET', 'POST'])
def add_message(uuid):
    content = request.json
    print content['mytext']
    return jsonify({"uuid":uuid})

@app.route('/')
def sup():
  return "Sup, world!"

if __name__ == '__main__':
    app.run(host= '0.0.0.0',debug=True)








