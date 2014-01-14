-- job
CREATE TABLE IF NOT EXISTS `job` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into job values(null, '仕事1');
insert into job values(null, '仕事2');
