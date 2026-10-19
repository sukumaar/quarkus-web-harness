# Fastify + Prisma starter

TypeScript service for Node.js 24. Prisma stores tasks in either SQLite or PostgreSQL. pg-boss processes tasks when PostgreSQL is selected; its queue cannot run on SQLite.

## Start with SQLite

```sh
cp .env.example .env
pnpm install --frozen-lockfile
pnpm run generate
pnpm run db:push
pnpm run dev
```

## Use PostgreSQL

Install and start PostgreSQL 18.6, create the `fastify_starter` database, then set `DB_PROVIDER=postgres` and `DATABASE_URL` in `.env`. Run:

```sh
pnpm run db:push
pnpm run dev
```

`db:push` creates the Prisma task table. pg-boss creates its own schema when the server starts. Run `pnpm run typecheck` after `pnpm run generate`.

Run `pnpm test` for the route tests. They use Fastify's in-process request injection and do not start a database or pg-boss.

Fastify writes structured JSON request and error logs to stdout. Set `LOG_LEVEL` in `.env` (for example, `debug`, `info`, `warn`, or `error`); tests disable logging. The PostgreSQL worker logs a task ID when it finishes a job.

## Try it

```sh
curl http://localhost:3000/health
curl -X POST http://localhost:3000/tasks -H 'content-type: application/json' -d '{"title":"Example"}'
curl http://localhost:3000/tasks
curl -X POST http://localhost:3000/tasks/1/process
```

The final request needs PostgreSQL. It queues a job that sets `processedAt` on the task.

Node.js has an LTS line; PostgreSQL has a five-year support window. SQLite, Fastify, Prisma, and pg-boss do not publish LTS lines. This starter uses Node.js 24.21.0, PostgreSQL 18.6, the current stable Prisma 7 line, and stable Fastify 5 and pg-boss 12 releases that meet the seven-day release-age requirement. The SQLite driver is supplied by `@prisma/adapter-better-sqlite3`.

`pnpm-workspace.yaml` makes pnpm select only package versions published at least seven days before installation, including transitive dependencies. Use pnpm 12.11.2 (available through Corepack with the Yapp Node installation). Direct dependencies use `~` to allow patch updates within their minor version; `pnpm-lock.yaml` fixes the resolved tree. The required native and Prisma install scripts are approved.

The seven-day rule currently excludes Fastify's October 13 security releases. Version 5.12.5 lacks [their fix](https://github.com/fastify/fastify/releases). On or after October 20, run `pnpm update fastify` to refresh the lockfile to an eligible patched version.

### Run
> pnpm tsx src/server.ts --log-level=debug

### Testing Example
```shell
pnpm exec node --test --test-name-pattern="configured database" test/app.test.ts
```
```shell
curl http://localhost:3000/health
```
