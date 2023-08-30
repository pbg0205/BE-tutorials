DROP TABLE IF EXISTS department, employee, product;

CREATE TABLE department
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_department PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    department_id BIGINT                NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

CREATE TABLE product
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255)          NOT NULL,
    quantity     INT                   NOT NULL,
    created_time timestamp              NULL,
    update_time  timestamp              NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE employee
    ADD CONSTRAINT FK_EMPLOYEE_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);
