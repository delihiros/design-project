CREATE TABLE IF NOT EXISTS `curriculum` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into curriculum values(-1, '');
insert into curriculum values(null, '教養課程');
insert into curriculum values(null, '専門課程');
