create table vehiclereservation.tb_reservation (
    id bigserial not null,
    vehicle_id UUID not null,
    buyer_id UUID not null,
    status_reservation VARCHAR(20) NOT NULL CHECK (status_reservation IN ('ACTIVE', 'SOLD', 'EXPIRED', 'CANCELLED')),
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id)
);