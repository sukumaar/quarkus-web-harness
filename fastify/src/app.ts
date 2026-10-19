import type { FastifyPluginAsync } from "fastify";
import type { TYPE_DB_PROVIDER } from "./config";
import { join } from "node:path";
import autoload from '@fastify/autoload'

export interface AppOption {
  databaseProvider: TYPE_DB_PROVIDER
}

const appBlueprint: FastifyPluginAsync<AppOption> = async (app, opts) => {
  app.decorate('config', {
    databaseProvider: opts.databaseProvider
  })
  await app.register(autoload, {
    dir: join(import.meta.dirname, 'routes'),
    ignoreFilter: file => file.startsWith('_')
  });
};

export default appBlueprint;