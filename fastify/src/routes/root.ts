import type { FastifyInstance, FastifyPluginAsync } from "fastify";

const rootRout: FastifyPluginAsync = async (app: FastifyInstance) => {
    app.get('/', async () => ({ ok: "hello world" }))
}

export default rootRout