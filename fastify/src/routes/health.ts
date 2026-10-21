import type { FastifyInstance, FastifyPluginAsync } from 'fastify'
import { request } from 'node:http';


const healthRoutes: FastifyPluginAsync = async (app: FastifyInstance) => {
  app.addHook('onRequest',
    async (request, reply) => {
      request.log.info("started working on health")
    })

  app.addHook('onResponse',
    async (request) => {
      request.log.info("Done processing")
    })

  app.get('/health', async () => ({ ok: true, database: app.config.databaseProvider }))
}

export default healthRoutes;
