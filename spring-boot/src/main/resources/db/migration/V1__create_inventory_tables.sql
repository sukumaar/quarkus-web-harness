DO $$ BEGIN
  CREATE TYPE "MovementType" AS ENUM ('RECEIPT', 'SALE', 'ADJUSTMENT', 'TRANSFER_IN', 'TRANSFER_OUT', 'RETURN');
EXCEPTION
  WHEN duplicate_object THEN NULL;
END $$;
CREATE TABLE IF NOT EXISTS "Product" (
  "id" SERIAL PRIMARY KEY,
  "sku" TEXT NOT NULL UNIQUE,
  "name" TEXT NOT NULL,
  "description" TEXT,
  "unit" TEXT NOT NULL DEFAULT 'each',
  "active" BOOLEAN NOT NULL DEFAULT TRUE,
  "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updatedAt" TIMESTAMP(3) NOT NULL
);
CREATE TABLE IF NOT EXISTS "Location" (
  "id" SERIAL PRIMARY KEY,
  "name" TEXT NOT NULL,
  "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS "InventoryBalance" (
  "id" SERIAL PRIMARY KEY,
  "productId" INTEGER NOT NULL REFERENCES "Product"("id") ON DELETE RESTRICT ON UPDATE CASCADE,
  "locationId" INTEGER NOT NULL REFERENCES "Location"("id") ON DELETE RESTRICT ON UPDATE CASCADE,
  "quantity" DECIMAL(12, 3) NOT NULL DEFAULT 0,
  "updatedAt" TIMESTAMP(3) NOT NULL,
  CONSTRAINT "InventoryBalance_productId_locationId_key" UNIQUE ("productId", "locationId")
);
CREATE TABLE IF NOT EXISTS "StockMovement" (
  "id" SERIAL PRIMARY KEY,
  "productId" INTEGER NOT NULL REFERENCES "Product"("id") ON DELETE RESTRICT ON UPDATE CASCADE,
  "locationId" INTEGER NOT NULL REFERENCES "Location"("id") ON DELETE RESTRICT ON UPDATE CASCADE,
  "type" "MovementType" NOT NULL,
  "quantity" DECIMAL(12, 3) NOT NULL,
  "note" TEXT,
  "reference" TEXT,
  "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS "StockMovement_productId_locationId_createdAt_idx"
  ON "StockMovement" ("productId", "locationId", "createdAt");
