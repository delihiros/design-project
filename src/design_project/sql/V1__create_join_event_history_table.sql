-- join event history
CREATE TABLE IF NOT EXISTS `join_event_history` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`event_id` INT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) )
ENGINE = InnoDB;

insert into join_event_history values(null, 1, 1);
insert into join_event_history values(null, 1, 2);
