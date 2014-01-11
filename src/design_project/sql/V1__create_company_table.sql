-- create company table
CREATE TABLE IF NOT EXISTS `company` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(64) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;
	
insert into company(`name`) values('com1'), ('com2');

