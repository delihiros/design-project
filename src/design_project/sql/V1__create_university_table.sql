 -- university table
CREATE TABLE IF NOT EXISTS `university` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(64),
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into university values(null, '大学1');
insert into university values(null, '大学2');
