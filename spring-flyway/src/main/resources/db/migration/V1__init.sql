USE `cooper_db`;

drop table if exists member;

create table member (
    id bigint auto_increment,
    name varchar(255),

    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
