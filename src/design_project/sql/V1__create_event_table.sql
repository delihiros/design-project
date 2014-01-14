-- event sql
CREATE TABLE IF NOT EXISTS `event` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `day` DATE NOT NULL ,
	`name` VARCHAR(255) NOT NULL,
	`place` VARCHAR(255) NOT NULL,
	`type_id` INT DEFAULT -1 NOT NULL,
  PRIMARY KEY (`id`),
	FOREIGN KEY (`type_id`) REFERENCES `event_type` (`id`) )
ENGINE = InnoDB;

insert into event values (-1,'','','',-1);
insert into event values(null, '2013-10-10', '説明会', '長野', 1);
insert into event values(null, '2013-10-21', '忘年会', '東京', 2);
