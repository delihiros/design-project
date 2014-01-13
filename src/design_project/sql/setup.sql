drop database t;
create database t default character set utf8;

use t;

source V1__create_company_table.sql;
source V1__create_course_table.sql;
source V1__create_curriculum_table.sql;
source V1__create_department_table.sql;
source V1__create_industry_type_table.sql;
source V1__create_event_type_table.sql;
source V1__create_event_table.sql;
source V1__create_job_table.sql;
source V1__create_lab_table.sql;
source V1__create_study_table.sql;
source V1__create_university_table.sql;
source V1__create_user_table.sql;
source V1__create_employment_table.sql;
source V1__create_event_read_table.sql;
source V1__create_certificate_table.sql;
source V1__create_join_event_history_table.sql;


