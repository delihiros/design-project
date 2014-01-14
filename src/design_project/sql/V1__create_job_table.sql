-- job
CREATE TABLE IF NOT EXISTS `job` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into job values(null, '学生');
insert into job values(null, '会社員');
insert into job values(null, '公務員');
insert into job values(null, 'その他');
