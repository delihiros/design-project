CREATE TABLE IF NOT EXISTS `department` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into department values(-1, '');
insert into department values(null, '人文学部');
insert into department values(null, '教育学部');
insert into department values(null, '経済学部');
insert into department values(null, '理学部');
insert into department values(null, '医学部');
insert into department values(null, '工学部');
insert into department values(null, '農学部');
insert into department values(null, '繊維学部');

