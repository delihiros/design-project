-- certificate
CREATE TABLE IF NOT EXISTS `certificate` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`certificate_type` INT NOT NULL,
	`completion_year` DATE,
	`leaves` INT,
	`purpose` TEXT,
	`addressee` TEXT,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
ENGINE = InnoDB;

insert into certificate values(null, 1, 1, '2013-10-10', 1, '出して', 'ほにゃほにゃ');
