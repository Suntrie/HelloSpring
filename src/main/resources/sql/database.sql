-- Database creation & initial filling

CREATE DATABASE tutorial;


CREATE TABLE tutorial.katze
(
    `catId` UInt64,
    `catName` String

)
ENGINE = MergeTree()
PARTITION BY catId
ORDER BY (catId)
SAMPLE BY intHash32(catId);

INSERT INTO tutorial.katze (*) VALUES (1, 'gavochka');
INSERT INTO tutorial.katze (*) VALUES (2, 'marusya');

CREATE TABLE tutorial.error
(
    `id` UInt64,
    `deviceId` UInt64,
    `startTime`  DateTime,
    `endTime`  DateTime,
	`deviceType` String,
	`errorType` String
)
ENGINE = MergeTree()
PARTITION BY deviceId
ORDER BY (deviceId);

INSERT INTO tutorial.error (*) VALUES (1, 1,'2019-01-01 00:00:00', '2019-01-01 01:00:00', 'Roboter X', 'fault#1');  /*time in plain UTC*/
INSERT INTO tutorial.error (*) VALUES (2, 1,'2019-01-01 01:00:00', '2019-01-01 01:30:00', 'Roboter X', 'fault#2');
INSERT INTO tutorial.error (*) VALUES (3, 1,'2019-01-01 01:20:00', '2019-01-01 01:50:00', 'Roboter X', 'fault#3');
INSERT INTO tutorial.error (*) VALUES (4, 2,'2019-01-01 00:00:00', '2019-01-01 01:00:00', 'Doer Y', 'fault#1');
INSERT INTO tutorial.error (*) VALUES (5, 2, '2019-01-02 00:00:00', '2019-01-02 01:00:00', 'Doer Y', 'fault#1');


CREATE TABLE tutorial.recipes
(
    title String,
    ingredients Array(String),
    directions Array(String),
    link String,
    source LowCardinality(String),
    NER Array(String)
) ENGINE = MergeTree ORDER BY title;

