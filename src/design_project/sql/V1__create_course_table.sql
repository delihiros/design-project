CREATE TABLE IF NOT EXISTS `course` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into course values(null, 'SE');
insert into course values(null, 'CE');