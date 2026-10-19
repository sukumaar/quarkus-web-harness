import 'dotenv/config'
import { CONST_DB_POSTGRES, CONST_DB_SQLITE } from './constants.ts'

const rawDB = process.env.DB_PROVIDER ?? CONST_DB_SQLITE

if (rawDB !== CONST_DB_SQLITE && rawDB !== CONST_DB_POSTGRES) {
  throw new Error(`DB_PROVIDER must be either ${CONST_DB_SQLITE} or ${CONST_DB_POSTGRES}`)
}

export const DB_PROVIDER = rawDB as typeof CONST_DB_POSTGRES | typeof CONST_DB_SQLITE

export type TYPE_DB_PROVIDER = typeof DB_PROVIDER
