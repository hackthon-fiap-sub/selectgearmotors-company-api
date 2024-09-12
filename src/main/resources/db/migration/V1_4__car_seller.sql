create table company.tb_car_seller (
    id bigserial not null,
    name varchar(255) not null,
    code varchar(255) not null,
    email varchar(255) not null,
    mobile varchar(255) not null,
    social_id VARCHAR(14) UNIQUE,
    social_id_dispatch_date date,
    document_id varchar(255),
    document_district varchar(255),
    document_dispatch_date date,
    birth_date date,
    company_id bigint not null,
    media_id bigint not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id),
    FOREIGN KEY (company_id) REFERENCES company.tb_company(id)
);

CREATE UNIQUE INDEX idx_company_social_id ON company.tb_car_seller(social_id);

