import 'fastify';
import { TYPE_DB_PROVIDER } from '../config';

declare module 'fastify' {
    interface FastifyInstance {
        config: {
            databaseProvider: TYPE_DB_PROVIDER
        }
    }
}
