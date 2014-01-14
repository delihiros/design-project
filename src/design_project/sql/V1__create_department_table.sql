CREATE TABLE IF NOT EXISTS `department` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into department values(null, '学科1');
insert into department values(null, '学科2');
