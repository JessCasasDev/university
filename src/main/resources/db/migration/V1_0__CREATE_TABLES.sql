DROP EXTENSION IF EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS ROLES (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    role VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS PERSON (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    document_number INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    constraint rol FOREIGN KEY(uuid) REFERENCES ROL(uuid)
);

CREATE TABLE IF NOT EXISTS STUDENTS (
    student_number INT NOT NULL UNIQUE,
    constraint person_id FOREIGN KEY(uuid) REFERENCES PERSON(uuid)
);

CREATE TABLE IF NOT EXISTS PROFESSORS (
    constraint person_id FOREIGN KEY(uuid) REFERENCES PERSON(uuid)
);

CREATE TABLE IF NOT EXISTS ASSIGNATURE (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    credits SMALLINT,
    CONSTRAINT professor_id FOREIGN KEY(uuid) REFERENCES PROFESSORS(uuid)
);

CREATE TABLE IF NOT EXISTS GRADES (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    grade DECIMAL(2,1) NOT NULL,
    CONSTRAINT assignature FOREIGN KEY(uuid) REFERENCES ASSIGNATURE(uuid),
    CONSTRAINT student FOREIGN KEY(student) REFERENCES STUDENT(id)
);

CREATE TABLE IF NOT EXISTS SCHEDULES (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    day VARCHAR(10) NOT NULL,
    initial_time TIME NOT NULL,
    end_time TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS CLASS_LIST (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    max_number_of_students SMALLINT NOT NULL,
    CONSTRAINT assignature FOREIGN KEY(UUID) REFERENCES ASSIGNATURE(UUID)
);

CREATE TABLE IF NOT EXISTS CLASS_LIST_STUDENTS(
    CONSTRAINT students_uuid FOREIGN KEY(UUID) REFERENCES STUDENT(UUID),
    CONSTRAINT class_lists_uuid FOREIGN KEY(UUID) REFERENCES CLASS_LIST(uuid)
);

CREATE TABLE IF NOT EXISTS PREREQUISITES(
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    CONSTRAINT assignature_id FOREIGN KEY(UUID) REFERENCES ASSIGNATURE(UUID),
    CONSTRAINT prerequisite FOREIGN KEY(UUID) REFERENCES ASSIGNATURE(UUID)
);

CREATE TABLE IF NOT EXISTS SCHEDULE_CLASS_LIST(
    CONSTRAINT schedule FOREIGN KEY(UUID) REFERENCES SCHEDULES(UUID),
    CONSTRAINT schedule FOREIGN KEY(UUID) REFERENCES CLASS_LIST(UUID)
);