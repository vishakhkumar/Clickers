

from flask import Flask,request
from flask_json import FlaskJSON, JsonError, json_response, as_json


class answerresponse(object):

  def init(self,StudentID=None,Answer=None):

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

 
  def toJson(self): 
    return json_response(studentID = self.studentID,answer=self.answer)
#    return {"StudentID": self.studentID,
#            "Answer"   :self.answer}


  def fromJSON(self,info):
    data = request.get_json(force=True)
    try:
        self.studentID = string(data['studentID'])
        self.answer = char(data['answer'])
    except (KeyError, TypeError, ValueError):
        raise JsonError(description='Invalid value.')




           
