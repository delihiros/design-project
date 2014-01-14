-- industry type table
CREATE TABLE IF NOT EXISTS `industry_type` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into industry_type values(-1, '');
insert into industry_type values(null, '農業');
insert into industry_type values(null, 'サービス業');
insert into industry_type values(null, 'その他');
