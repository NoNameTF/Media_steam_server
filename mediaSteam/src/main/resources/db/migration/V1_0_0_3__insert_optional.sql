use `db_ms`;

insert into `db_ms`.`user`(`provider`, `name`, `nickname`, `email`, `password`, `picture`, `role`)
values ('local', '테스트', '테스트', 'test@test', md5('test'), '', 'USER');

insert into `db_ms`.`post`(`category_id`, `title`, `content`, `is_blocked`, `is_removed`, `created_user_id`, `created_at`)
values (null, '테스트', '테스트', 0, 0, 1, now());

insert into `db_ms`.`post`(`category_id`, `title`, `content`, `is_blocked`, `is_removed`, `created_user_id`, `created_at`)
values (null, '관리자에 의해 블락된 게시글 테스트', '테스트', 1, 0, 1, now());

insert into `db_ms`.`post`(`category_id`, `title`, `content`, `is_blocked`, `is_removed`, `created_user_id`, `created_at`)
values (null, '삭제된 게시글 테스트', '테스트', 0, 1, 1, now());