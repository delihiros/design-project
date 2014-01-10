-- industry type table
CREATE TABLE IF NOT EXISTS `industry_type` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(64) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into industry_type values(null, '業種1');
insert into industry_type values(null, '業種2');
