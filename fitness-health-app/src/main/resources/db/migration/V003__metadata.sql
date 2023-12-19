set
    quoted_identifier on

set nocount on

go


create table fitness.metadata
(
    id         bigint identity (1, 1) not null,
    start_time datetime               null,
    end_time   datetime               null,

    constraint metadata_pk primary key (id)
);

go

alter table fitness.fitness_health_data
    add metadata_id bigint null
        constraint fitness_health_data_metadata_fk
            FOREIGN KEY (metadata_id) references fitness.metadata (id);

go
