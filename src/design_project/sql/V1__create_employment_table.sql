-- employment_table
CREATE TABLE IF NOT EXISTS `employment` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`company_id` INT NOT NULL,
	`position` VARCHAR(32),
	`industry_id` INT,
	`comment` TEXT,
	`uptime` DATETIME,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
	FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
	FOREIGN KEY (`industry_id`) REFERENCES `industry_type` (`id`) )
ENGINE = InnoDB;

insert into employment values(null, 1, 1, '社長', 1, 'なし', '2013-12-21 00:00:00.000');

