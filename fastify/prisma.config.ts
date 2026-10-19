import 'dotenv/config'
import { defineConfig } from 'prisma/config'

const postgres = process.env.DB_PROVIDER === 'postgres'

export default defineConfig({
  schema: postgres ? 'prisma/postgres.prisma' : 'prisma/sqlite.prisma',
  datasource: {
    url: postgres
      ? process.env.DATABASE_URL ?? ''
      : process.env.SQLITE_URL ?? 'file:./dev.db'
  }
})
