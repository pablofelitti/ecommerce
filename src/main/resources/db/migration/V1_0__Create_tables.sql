create sequence hibernate_sequence start with 1 increment by 1;

create table cart (
    id bigint not null,
    creation_date timestamp,
    email varchar(255),
    full_name varchar(255),
    status varchar(255),
    total decimal(19,2),
    primary key (id)
);

create table cart_product (
    id bigint not null,
    quantity integer,
    unit_price decimal(19,2),
    product_id bigint,
    primary key (id)
);

create table product (
    id bigint not null,
    description varchar(255),
    stock bigint,
    unit_price decimal(19,2),
    primary key (id)
);

alter table cart_product add constraint FK_PRODUCT_ID foreign key (product_id) references product;