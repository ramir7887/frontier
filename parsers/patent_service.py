#! /usr/bin/env python
# -*- coding: utf-8 -*-

from flask import Flask, jsonify
import json
from flask import request
import requests
from flask_cors import CORS
import re

app = Flask(__name__)
CORS(app)
app.config['JSON_AS_ASCII'] = False


"""
Пример запроса
http://АДРЕС_СЕРВЕРА/get_patents_count?branch1=IOT&branch1=CNC&startDate=20200520&endDate=20201020&language=ENGLISH

"""

@app.route('/get_patents_count')
def get_data_from_pie():
    baseUrl = 'https://patents.google.com/xhr/query?url='

    branchLst = list()
    for arg in request.args.lists():
        if not str(arg[0]).find('branch'): 
            branchLst.append(arg[1][0])
    
    branch = ''
    for i in branchLst:
        branch += 'q%3D'
        branch += i
        branch += '%26'

    language = str(request.args.get("language", default='ENGLISH'))

    startDate = str(request.args.get("startDate", default=-1))
    endDate = str(request.args.get("endDate", default=-1))

    if startDate == -1:
        tmpReq = f'{branch}before%3Dpriority%3A{endDate}%26'
    elif endDate == -1:
        tmpReq = f'{branch}after%3Dpriority%3A{startDate}%26'
    else:
        tmpReq = f'{branch}before%3Dpriority%3A{endDate}%26after%3Dpriority%3A{startDate}%26'

    req = baseUrl + tmpReq + f'language%3D{language}%26type%3DPATENT&exp='

    response = json.loads(requests.get(req).text)

    return {"branches" : branchLst, "count" : response['results']['total_num_results']}


if __name__ == "__main__":
    app.run(host='127.0.0.1', port=8686, use_reloader=True)