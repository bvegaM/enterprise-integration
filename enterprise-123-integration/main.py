from fastapi import FastAPI
from starlette import status
from fastapi.responses import Response

from pydantic import BaseModel
from utils.constant import CLIENTS_LIST

app = FastAPI()


class Client(BaseModel):
    id: int
    firstName: str
    lastName: str
    phoneNumber: str
    address: str
    channel: str


@app.get('/')
async def root():
    return 'I am alive'


@app.get('/clients')
async def get_clients():
    return CLIENTS_LIST


@app.post('/clients', status_code=status.HTTP_201_CREATED)
async def save(client: Client):
    print('Python client process {}'.format(client.id))
    if client.id in CLIENTS_LIST:
        return Response(status_code=status.HTTP_400_BAD_REQUEST,
                        content='Client with id {} already exists'.format(client.id))
    CLIENTS_LIST[client.id] = client
    return Response(status_code=status.HTTP_201_CREATED,
                    content='Client with id {} created'.format(client.id))
