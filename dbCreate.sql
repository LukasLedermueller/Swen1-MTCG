/*create tables in db*/

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    uid      SERIAL,
    username varchar(64) not null unique,
    password varchar(64) not null,
    name varchar(64),
    bio varchar(64),
    image varchar(64),
    coins int default 20,
    elo int default 100,
    wins int default 0,
    losses int default 0,
    primary key (uid)
);
ALTER TABLE users add constraint coins_not_negative CHECK (coins >= 0);

DROP TABLE IF EXISTS tokens;
CREATE TABLE tokens
(
    id SERIAL,
    username varchar(64),
    token varchar(64) not null,
    primary key (id)
);

DROP TABLE IF EXISTS cards;
CREATE TABLE cards
(
    id varchar(64) unique not null,
    name varchar(64) not null,
    damage float not null,
    owner varchar(64) null,
    primary key (id)
);

DROP TABLE IF EXISTS decks;
CREATE TABLE decks
(
    owner varchar(64) unique not null,
    card1 varchar(64) unique null,
    card2 varchar(64) unique null,
    card3 varchar(64) unique null,
    card4 varchar(64) unique null,
    primary key (owner)
);

DROP TABLE IF EXISTS packages;
CREATE TABLE packages
(
    id SERIAL,
    card1 varchar(64) unique null,
    card2 varchar(64) unique null,
    card3 varchar(64) unique null,
    card4 varchar(64) unique null,
    card5 varchar(64) unique null,
    primary key (id)
);

DROP TABLE IF EXISTS tradings;
CREATE TABLE tradings
(
    id varchar(64) unique not null,
    cardToTrade varchar(64) not null,
    type varchar(64) not null,
    minimumDamage float not null,
    primary key (id)
);


/*SELECT * FROM users;
SELECT * FROM tokens;
SELECT * FROM cards;
SELECT * FROM decks;
SELECT * FROM packages;
SELECT * FROM tradings;

DELETE FROM users WHERE true;
DELETE FROM tokens WHERE true;
DELETE FROM cards WHERE true;
DELETE FROM decks WHERE true;
DELETE FROM packages WHERE true;
DELETE FROM tradings WHERE true;
*/