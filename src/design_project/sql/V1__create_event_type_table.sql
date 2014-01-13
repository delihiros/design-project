-- event_type table
CREATE TABLE IF NOT EXISTS `event_type` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(32) NOT NULL,
	PRIMARY KEY (`id`) )
ENGINE = InnoDB;

insert into event_type values(null, '説明会');
insert into event_type values(null, 'それ以外');
