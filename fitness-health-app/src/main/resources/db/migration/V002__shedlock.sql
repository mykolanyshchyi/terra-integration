create table fitness.shedlock
(
    name       nvarchar(64),
    lock_until datetime NULL,
    locked_at  datetime NULL,
    locked_by  nvarchar(255),
    constraint shedlock_pk primary key (name)
);

go
