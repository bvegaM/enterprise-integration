import { HttpCode, HttpStatus, NotFoundException } from '@nestjs/common';
import { Body, Controller, Get, Post } from '@nestjs/common';
import { AppService } from './app.service';

export class Client {
  id: number;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  address: string;
  channel: string;
}
@Controller('/clients')
export class AppController {
  private clients: Client[] = [];
  constructor(private readonly appService: AppService) {}

  @Get()
  getAll() {
    return this.clients;
  }

  @Post()
  @HttpCode(HttpStatus.OK)
  save(@Body() client: Client) {
    console.log(`NestJs client process ${client.id}`);
    const clientExists = this.clients.findIndex((cli) => cli.id === client.id);
    if (clientExists != -1) {
      throw new NotFoundException({
        status: HttpStatus.NOT_FOUND,
        message: `Client id ${client.id} already exist`,
      });
    }
    this.clients.push(client);
    return {
      status: HttpStatus.OK,
      message: `Client with id ${client.id} created'`,
      body: client,
    };
  }
}
