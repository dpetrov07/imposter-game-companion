-- Enables UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Category
CREATE TABLE category (
  id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

-- Game Session
CREATE TABLE game_session (
  id UUID PRIMARY KEY,
  status VARCHAR(255) NOT NULL
    CHECK (status IN ('CREATED', 'STARTED', 'FINISHED')),
  created_at TIMESTAMPTZ,
  last_activity_at TIMESTAMPTZ
);

-- Player
CREATE TABLE player (
  id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL
    CHECK (role IN ('NORMAL', 'IMPOSTER')),
  secret_word VARCHAR(255),
  game_session_id UUID NOT NULL,

  CONSTRAINT fk_player_game_session
    FOREIGN KEY (game_session_id)
    REFERENCES game_session(id)
    ON DELETE CASCADE
);

-- Word Pair
CREATE TABLE word_pair (
  id UUID PRIMARY KEY,
  word VARCHAR(255) NOT NULL,
  imposter_hint VARCHAR(255) NOT NULL,
  category_id UUID NOT NULL,

  CONSTRAINT fk_word_pair_category
    FOREIGN KEY (category_id)
    REFERENCES category(id)
    ON DELETE CASCADE
);