DROP TABLE IF EXISTS `messages`;
DROP TABLE IF EXISTS `users`;

SET character_set_client = utf8;
CREATE TABLE `users`(
	`id` INT(5) NOT NULL AUTO_INCREMENT,
	`login` nvarchar(250) NOT NULL,
	`password` nvarchar(250) NOT NULL,
	PRIMARY KEY ( id )
) ENGINE=INNODB;


CREATE TABLE `messages`(
	`id` INT(5) NOT NULL AUTO_INCREMENT,
	`user_id` INT(5) NOT NULL,
	`name` nvarchar(250) NOT NULL,
	`text` nvarchar(2000) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES users(`id`)
) ENGINE=INNODB;

LOCK TABLES `users` WRITE;
insert into `users` (`login`, `password`) values("test", "test");
insert into `users` (`login`, `password`) values("test1", "test1");
UNLOCK TABLES;

LOCK TABLES `messages` WRITE;
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 1");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 2");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 3");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 4");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 5");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 6");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 7");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 8");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 9");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 10");
insert into `messages` (`user_id`, `name`, `text`) values(1, "qwe", "test message 11");
UNLOCK TABLES;
