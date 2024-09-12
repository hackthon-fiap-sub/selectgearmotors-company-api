create table company.tb_media (
    id bigserial not null,
    name varchar(255) not null,
    path varchar(10000) not null,
    media_type varchar(60) not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id)
);