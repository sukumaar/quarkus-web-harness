import type { FastifyInstance, FastifyPluginAsync } from 'fastify'

const healthRoutes: FastifyPluginAsync = async (app: FastifyInstance) => {
  app.get('/health', async () => ({ ok: true, database: app.config.databaseProvider }))
}

export default healthRoutes;
