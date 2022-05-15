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

drop table if exists `db_ms`.`post`;
create table `db_ms`.`post` (
    `id`  bigint primary key auto_increment,
    `category_id` bigint,
    `title` varchar(255),
    `content` text,
    `is_blocked` bit,
    `is_removed` bit,
    `created_user_id`   bigint,
    `modified_user_id`   bigint,
    `created_at`    datetime,
    `updated_at`    datetime
);

drop table if exists `db_ms`.`post_comment`;
create table `db_ms`.`post_comment` (
  `id` bigint primary key auto_increment,
  `parent_id` bigint,
  `post_id` bigint,
  `content` varchar(1024),
  `is_blocked` bit,
  `is_removed` bit,
  `created_user_id`   bigint,
  `modified_user_id`   bigint,
  `created_at`    datetime,
  `updated_at`    datetime,

  key idx_parent_id (`parent_id`),
  key idx_post_id (`post_id`)
);

drop table if exists `db_ms`.`post_category`;
create table `db_ms`.`post_category` (
    `id` bigint primary key auto_increment,
    `name` varchar(255),
    `created_at` datetime,
    `updated_at` datetime
)