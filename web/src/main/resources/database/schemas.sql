CREATE TABLE gift_certificate
(
id serial PRIMARY KEY,
name varchar(255) NOT NULL,
description text NOT NULL,
price money NOT NULL,
duration varchar(30) NOT NULL,
create_date varchar(30) NOT NULL,
last_update_date varchar(30) NOT NULL
);

CREATE TABLE tag
(
id serial PRIMARY KEY,
name varchar(255) NOT NULL
);

CREATE TABLE gifts_and_tags
(
certificata_id bigint,
tag_id bigint,
PRIMARY KEY (certificata_id, tag_id),
FOREIGN KEY (certificata_id) REFERENCES gift_certificate (id),
FOREIGN KEY (tag_id) REFERENCES tag (id)
)