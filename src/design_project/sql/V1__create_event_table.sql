-- event sql
CREATE TABLE IF NOT EXISTS `event` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `day` DATE NOT NULL ,
	`place` VARCHAR(64) NOT NULL,
	`type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
	FOREIGN KEY (`type_id`) REFERENCES `event_type` (`id`) )
ENGINE = InnoDB;

