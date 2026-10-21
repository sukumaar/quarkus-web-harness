# Inventory API

Fastify and Prisma starter for an inventory management API. SQLite supports local development; PostgreSQL enables pg-boss background jobs.

```sh
cp .env.example .env
pnpm install --frozen-lockfile
pnpm run generate
pnpm run db:push
pnpm run dev
```

Set `DB_PROVIDER=postgres` and `DATABASE_URL` in `.env` to use PostgreSQL.
