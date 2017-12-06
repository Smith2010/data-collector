DROP DATABASE IF EXISTS datacollector;
CREATE DATABASE datacollector DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

-- init users
CREATE USER 'winter'@'localhost' IDENTIFIED BY 'Cndnj37!@#';
CREATE USER 'summer'@'localhost' IDENTIFIED BY 'Ejdnj49!@#';

-- DB
USE mysql;

-- data collector Database
INSERT INTO db (HOST,Db,USER,Select_priv,Insert_priv,Update_priv,Delete_priv,Create_priv,Drop_priv,Index_priv,Alter_priv) VALUES('localhost','datacollector','summer','Y','Y','Y','Y','N','N','N','N');
INSERT INTO db (HOST,Db,USER,Select_priv,Insert_priv,Update_priv,Delete_priv,Create_priv,Drop_priv,Index_priv,Alter_priv) VALUES('localhost','datacollector','winter','Y','Y','Y','Y','Y','Y','Y','Y');
INSERT INTO db (HOST,Db,USER,Select_priv,Insert_priv,Update_priv,Delete_priv,Create_priv,Drop_priv,Index_priv,Alter_priv) VALUES('%','datacollector','summer','Y','Y','Y','Y','N','N','N','N');
INSERT INTO db (HOST,Db,USER,Select_priv,Insert_priv,Update_priv,Delete_priv,Create_priv,Drop_priv,Index_priv,Alter_priv) VALUES('%','datacollector','winter','Y','Y','Y','Y','Y','Y','Y','Y');

FLUSH PRIVILEGES;
