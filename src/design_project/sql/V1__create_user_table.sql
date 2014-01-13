CREATE TABLE IF NOT EXISTS `user` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`university_id` INT,
	`status` INT NOT NULL,
	`image` TEXT,
	`name` VARCHAR(32) NOT NULL,
	`user_id` TEXT UNIQUE NOT NULL,
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
	`wish_teacher` VARCHAR(32),
	`entrance` BOOLEAN,
	`entrance_day` DATE,
	`class` INT,
	`department_id` INT,
	`study_course_id` INT,
	`laboratory_id` INT,
	`student_id` VARCHAR(16),
	`guarantor_name` VARCHAR(32),
	`guarantor_address` VARCHAR(255),
	`guarantor_phone` VARCHAR(16),
	`finish_course_day` DATE,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`university_id`) REFERENCES `university` (`id`),
	FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
	FOREIGN KEY (`industry_id`) REFERENCES `industry_type` (`id`),
	FOREIGN KEY (`wish_curriculum_id`) REFERENCES `curriculum` (`id`),
	FOREIGN KEY (`wish_course_id`) REFERENCES `course` (`id`),
	FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
	FOREIGN KEY (`study_course_id`) REFERENCES `study` (`id`),
	FOREIGN KEY (`laboratory_id`) REFERENCES `lab` (`id`) )

ENGINE = InnoDB;

insert into `user` (`id`, `university_id`, `status`, `image`, `name`, `user_id` ,`password`, `hash_method`, `country`, `address`, `phone`, `job_id`, `industry_id`, `birthday`, `sex`)  values(null, 1, 0, null, 'admin', 'admin', 'admin', 'admin', '国3', '住所2', '0000000000', 1, 1, '1960-03-24', 1);

insert into `user` values(null, 1, 1, null, '名前', 'student', 'password', 'password_hash', '国1', '住所1', '0120333333',
	1, 1, '1993-03-09', 1, 1, 1, '先生1', true, '2010-04-01', 3, 1, 1, 1, '11t5000a', '保証人', '保証人住所', '0120111111', null);

insert into `user` (`id`, `university_id`, `status`, `image`, `name`, `user_id`, `password`, `hash_method`, `country`, `address`, `phone`, `job_id`, `industry_id`, `birthday`, `sex`) values(null, 1, 2, null, 'entry', 'entry', 'entry', 'entry', '国2', '住所1', '0001000000', 1, 1, '1995-07-04', 1);

insert into `user` values(null, 1, 3, null, 'graduated', 'graduated', 'graduated', 'graduated', '国2', '住所1', '0001000000',
	1, 1, '1990-07-04', 1, 1, 1, '先生2', true, '2007-04-01', null, 1, 1, 1, '07a0000a', 'ほしょうにん', 'じゅうしょ', '1234567890', '2012-03-31');


