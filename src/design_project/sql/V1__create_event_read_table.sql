-- event read
CREATE TABLE IF NOT EXISTS `event_read` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT DEFAULT -1 NOT NULL,
	`event_id` INT DEFAULT -1 NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
	FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) )
ENGINE = InnoDB;


insert into event_read values(-1,-1,-1);
insert into event_read values(null, 1, 1);
insert into event_read values(null, 1, 2);
