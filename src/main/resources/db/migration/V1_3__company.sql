create table company.tb_company (
    id bigserial not null,
    social_name varchar(255) not null,
    fantasy_name varchar(255) not null,
    code varchar(255) not null,
    email varchar(255) not null,
    mobile varchar(255) not null,
    description varchar(255) not null,
    media_id bigint not null,
    address varchar(255) not null,
    data_processing_consent boolean not null,
    company_id VARCHAR(14) UNIQUE,
    foundation_date date not null,
    company_type_id bigint not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id),
    FOREIGN KEY (company_type_id) REFERENCES company.tb_company_type(id)
);

CREATE UNIQUE INDEX idx_company_code ON company.tb_company(code);
CREATE UNIQUE INDEX idx_company_email ON company.tb_company(email);