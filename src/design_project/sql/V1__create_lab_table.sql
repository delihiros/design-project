CREATE TABLE IF NOT EXISTS `lab` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into lab values(-1,'');
insert into lab values(null, '佐藤研究室');
insert into lab values(null, '鈴木研究室');
insert into lab values(null, '田中研究室');

