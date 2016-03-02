DROP DATABASE IF EXISTS tweetguessdb;

CREATE DATABASE tweetguessdb CHARACTER SET utf8mb4;

GRANT ALL ON tweetguessdb.* TO 'tweetguess' IDENTIFIED BY 'tweetguess';