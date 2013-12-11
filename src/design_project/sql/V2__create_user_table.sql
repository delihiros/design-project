-- user 
CREATE TABLE IF NOT EXISTS `user` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`university_id` INT,
	`status` INT NOT NULL,
	`name` VARCHAR(32) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`hash_method` VARCHAR(255) NOT NULL,
	`country` VARCHAR(32) NOT NULL,
	`address` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(16) NOT NULL,
	`job_id` INT NOT NULL,
	`industry_id` INT NOT NULL,
	`birthday` DATE NOT NULL,
	`sex` INT NOT NULL,
	`wish_curriculum_id` INT,
	`wish_course_id` INT,
	`wish_teacher` VARCHAR(32) NOT NULL,
	`entrance` BOOLEAN,
	`entrance_day` DATE,
	`class` INT,
	`department_id` INT,
	`graduate_course_id` INT,
	`laboratory_id` INT,
	`student_id` VARCHAR(16),
	`guarantor_name` VARCHAR(32),
	`guarantor_address` VARCHAR(255),
	`guarantor_phone` VARCHAR(16),
	`finish_course_day date,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`university_id`) REFERENCES `university` (`id`),
	FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
	FOREIGN KEY (`industry_id`) REFERENCES `industry_type` (`id`),
	FOREIGN KEY (`wish_curriculum_id`) REFERENCES `curriculum` (`id`),
	FOREIGN KEY (`wish_course_id`) REFERENCES `course` (`id`),
	FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
	FOREIGN KEY (`graduate_course_id`) REFERENCES `study` (`id`),
	FOREIGN KEY (`laboratory_id`) REFERENCES `lab` (`id`) )

ENGINE = InnoDB;

