CREATE DATABASE `sorc_db`
    DEFAULT CHARACTER SET utf8
    COLLATE 'utf8_general_ci';

COMMIT;

GRANT ALL PRIVILEGES ON `sorc_db`.* TO zorc@localhost IDENTIFIED BY 'zorc'
WITH GRANT OPTION;

COMMIT;