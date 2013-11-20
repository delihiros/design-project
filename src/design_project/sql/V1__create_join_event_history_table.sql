-- join event history
CREATE TABLE IF NOT EXISTS `join_event_history` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`event_id` INT NOT NULL,
	`user_id` INT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) )
ENGINE = InnoDB;

