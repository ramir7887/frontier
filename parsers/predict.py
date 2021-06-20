import json
from pprint import pprint
import urllib.request

import pandas as pd
from prophet import Prophet

from flask import Flask
from flask import request

app = Flask(__name__)


def predict_articles(response) -> float:
    data = { 'ds':[], 'y': []}
    for x, row in enumerate(response['data']):
        # print(f"{row['year']}; {row['count']}")
        data['ds'].append(f'2000-01-{x+1}') # hackathon style, fix in prod
        data['y'].append(row['count'])

    df = pd.DataFrame.from_dict(data)
    m = Prophet()
    m.fit(df)
    future = m.make_future_dataframe(periods=5)
    future.tail(5)
    forecast = m.predict(future)[len(response['data']):]
    print(forecast[['yhat', 'yhat_lower', 'yhat_upper']])
    res = json.loads(forecast[['yhat', 'yhat_lower', 'yhat_upper']].to_json(orient="index"))
    for i in res.copy().keys():
        res['20'+i] = res.pop(i)
    return res


def predict_money(response) -> float:
    data = { 'ds':[], 'y': []}
    for x, row in enumerate(response['money']):
        # print(f"{row['year']}; {row['count']}")
        data['ds'].append(f'2000-01-{x+1}') # hackathon style, fix in prod
        data['y'].append(row['amount'])

    df = pd.DataFrame.from_dict(data)
    m = Prophet()
    m.fit(df)
    future = m.make_future_dataframe(periods=5)
    future.tail(5)
    forecast = m.predict(future)[len(response['money']):]
    print(forecast[['yhat', 'yhat_lower', 'yhat_upper']])
    res = json.loads(forecast[['yhat', 'yhat_lower', 'yhat_upper']].to_json(orient="index"))
    for i in res.copy().keys():
        res[int(2017 + int(i))] = res.pop(i)
    return res


def get_data(query:str, start_year:int, end_year:int) -> dict:
    query = query.replace(' ', '%20').replace('+', '%20')

    url = f'https://frontier-analytics.kovalev.team/year-articles?query={query}&start_year={start_year}&end_year={end_year}'

    response = urllib.request.urlopen(url).read().decode('utf-8')
    return response


def send_data(query:str) -> dict:

    url = f'https://frontier-api.kovalev.team/subject/money'

    # response = urllib.request.urlopen(url).read().decode('utf-8')
    req = urllib.request.Request(url)
    req.add_header('Content-Type', 'application/json; charset=utf-8')
    jsondata = json.dumps(query)
    jsondataasbytes = jsondata.encode('utf-8')
    req.add_header('Content-Length', len(jsondataasbytes))
    response = urllib.request.urlopen(req, jsondataasbytes).read().decode('utf-8')
    return json.loads(response)[0]



@app.route("/articles/", methods=['POST'])
def articles():
    data = request.get_json(silent=True)
    if not data['name']:
        data['name'] = 'cnc'
    if data['start_year'] == data['end_year']:
        start_year = 2000
    data = json.loads(get_data(data['name'], data['start_year'], data['end_year']))
    # print(data['name'], data['start_year'], data['end_year'])
    return predict_articles(data)


@app.route("/money/", methods=['POST'])
def money():
    data = request.get_json(silent=True)
    res = send_data(data)
    print(res)
    return predict_money(res)
