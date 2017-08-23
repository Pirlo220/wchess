DROP TABLE IF EXISTS users;

-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  id bigint NOT NULL,
  username character varying,
  email character varying,
  name character varying,
  surname character varying,
  password character varying,
  elo integer,
  CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO seguridad;

  
INSERT INTO users(id, username, email, name, surname, password) VALUES (1, 'test', 'test@gem.com', 'John', 'Doe', 'sdfsad5fsda5fsad5', 1200);
INSERT INTO users(id, username, email, name, surname, password) VALUES (2, 'test2', 'test2@gem.com', 'Jane', 'Doe', 'adfdddddd', 1650);