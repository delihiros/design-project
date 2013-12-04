-- event read
CREATE TABLE IF NOT EXISTS `event_read` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`event_id` INT NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
	FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) )
ENGINE = InnoDB;

