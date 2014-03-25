# Chess gist schema

# --- !Ups

CREATE SEQUENCE chessgist_id_seq;
CREATE TABLE chessgist (
    id integer NOT NULL DEFAULT nextval('chessgist_id_seq'),
    label varchar(255),
    fen varchar(255)
);

# --- !Downs

DROP TABLE chessgist;
DROP SEQUENCE chessgist_id_seq;
