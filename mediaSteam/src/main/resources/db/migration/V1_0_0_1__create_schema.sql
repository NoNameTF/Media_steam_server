use `db_ms`;

drop table if exists `db_ms`.`user`;
create table `db_ms`.`user` (
  `id`  bigint primary key auto_increment,
  `provider` varchar(10) not null,
  `name` varchar(20),
  `nickname` varchar(255),
  `email` varchar(255),
  `password` varchar(255),
  `picture` varchar(255),
  `role` varchar(20)
);