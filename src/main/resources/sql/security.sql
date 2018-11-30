--Create users table to give access to aplication
CREATE TABLE users (
  username VARCHAR(50)  NOT NULL PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  enabled  BOOLEAN      NOT NULL
)ENGINE = InnoDb;
CREATE TABLE authorities (
  username  VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users (username),
  UNIQUE INDEX authorities_idx_1 (username, authority)
)ENGINE = InnoDb;
ALTER TABLE users ADD id BIGINT NOT NULL AUTO_INCREMENT,ADD INDEX (id);
ALTER TABLE users AUTO_INCREMENT=1;
ALTER TABLE authorities ADD id BIGINT NOT NULL AUTO_INCREMENT,ADD INDEX (id);
ALTER TABLE authorities AUTO_INCREMENT=1;
