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
/*FÜR UNIT TESTS:
INSERT INTO users (username, password) VALUES ('admin', 'istrator');
INSERT INTO tokens (username, token) VALUES ('admin', 'admin-mtcgToken');
INSERT INTO users (username, password, name, bio, image) VALUES ('test', 'test', 'testName', 'testBio', 'testImage');
INSERT INTO tokens(username, token) VALUES ('test', 'test-mtcgToken');
INSERT INTO users (username, password, coins) VALUES ('emptyUser', 'test', 0);
INSERT INTO tokens (username, token) VALUES ('emptyUser', 'emptyUser-mtcgToken');
INSERT INTO tokens (username, token) VALUES ('logoutUser', 'logoutUser-mtcgToken');

INSERT INTO cards (id, name, damage, owner) VALUES ('1', 'Dragon', '10.0', 'test');
INSERT INTO cards (id, name, damage, owner) VALUES ('2', 'Dragon', '10.0', 'test');
INSERT INTO cards (id, name, damage, owner) VALUES ('3', 'Dragon', '10.0', 'test');
INSERT INTO cards (id, name, damage, owner) VALUES ('4', 'Dragon', '10.0', 'test');
INSERT INTO cards (id, name, damage, owner) VALUES ('5', 'Dragon', '10.0', 'test');
INSERT INTO cards (id, name, damage, owner) VALUES ('50', 'Dragon', '10.0', 'test');
INSERT INTO cards (id, name, damage, owner) VALUES ('51', 'Dragon', '10.0', 'test');
INSERT INTO cards (id, name, damage, owner) VALUES ('52', 'Dragon', '5.0', 'admin');
INSERT INTO cards (id, name, damage, owner) VALUES ('53', 'Dragon', '20.0', 'admin');


INSERT INTO cards (id, name, damage) VALUES ('21', 'Dragon', '10.0');
INSERT INTO cards (id, name, damage) VALUES ('22', 'Dragon', '10.0');
INSERT INTO cards (id, name, damage) VALUES ('23', 'Dragon', '10.0');
INSERT INTO cards (id, name, damage) VALUES ('24', 'Dragon', '10.0');
INSERT INTO cards (id, name, damage) VALUES ('25', 'Dragon', '10.0');

INSERT INTO packages (card1, card2, card3, card4, card5) VALUES ('21','22','23','24','25');

INSERT INTO decks (owner, card1, card2, card3, card4) VALUES ('test', '2','3', '4', '5');
INSERT INTO decks (owner) VALUES ('emptyUser');
INSERT INTO decks (owner) VALUES ('admin');

INSERT INTO tradings (id, cardToTrade, type, minimumDamage) VALUES ('2', '50', 'monster', 10);
INSERT INTO tradings (id, cardToTrade, type, minimumDamage) VALUES ('3', '51', 'monster', 10);
INSERT INTO tradings (id, cardToTrade, type, minimumDamage) VALUES ('4', 'test', 'monster', 10);
 */

/*
SELECT * FROM users;
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