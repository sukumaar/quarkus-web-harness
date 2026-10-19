import Fastify, { FastifyInstance } from 'fastify';
import appBlueprint from './app';
import { DB_PROVIDER } from './config';

const server: FastifyInstance = Fastify({
  logger: true
})

async function startServer() {
  try {
    await server.register(appBlueprint, {
      databaseProvider: DB_PROVIDER
    })
    const address = await server.listen({ port: 3000, host: '0.0.0.0' });
    console.log(`🚀 Fastify server is running smoothly at: ${address}`);
  }
  catch (err) {
    server.log.error(err);
    process.exit(1);
  }
}

startServer();