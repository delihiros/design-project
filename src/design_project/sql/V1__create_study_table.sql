CREATE TABLE IF NOT EXISTS `study` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into study values(null, '情報工学研究科');
insert into study values(null, '電気電子工学研究科');
insert into study values(null, '建築工学研究科');
insert into study values(null, '機械工学研究科');
insert into study values(null, '環境工学研究科');