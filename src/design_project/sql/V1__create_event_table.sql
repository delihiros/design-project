-- event sql
CREATE TABLE IF NOT EXISTS `event` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `day` DATE NOT NULL ,
	`place` VARCHAR(64) NOT NULL,
	`type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
	FOREIGN KEY (`type_id`) REFERENCES `event_type` (`id`) )
ENGINE = InnoDB;

insert into event values(null, '2013-10-10', '長野', 1);
insert into event values(null, '2013-10-21', '東京', 2);
