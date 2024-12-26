-- Drop existing constraints if they exist
ALTER TABLE user DROP COLUMN IF EXISTS first_name;
ALTER TABLE user DROP COLUMN IF EXISTS last_name;
ALTER TABLE user DROP COLUMN IF EXISTS role;
ALTER TABLE user DROP COLUMN IF EXISTS user_name;

-- Add new columns if they don't exist
ALTER TABLE user ADD COLUMN IF NOT EXISTS username VARCHAR(255);
ALTER TABLE user ADD COLUMN IF NOT EXISTS email VARCHAR(255);
ALTER TABLE user ADD COLUMN IF NOT EXISTS password VARCHAR(255);

-- Add unique constraints
ALTER TABLE user ADD UNIQUE (username);
ALTER TABLE user ADD UNIQUE (email);
