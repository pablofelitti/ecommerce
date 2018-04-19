create table cart (id bigint not null, creation timestamp, email varchar(255), full_name varchar(255), status varchar(255), total decimal(19,2), primary key (id));
create table cart_cart_products (cart_id bigint not null, cart_products_id bigint not null);
create table cart_product (id bigint not null, quantity integer, unit_price decimal(19,2), product_id bigint, primary key (id));
create table product (id bigint not null, description varchar(255), stock bigint, unit_price decimal(19,2), primary key (id));

alter table cart_cart_products add constraint UK_6iyqfvapdsmcl4d7fenxqtl18 unique (cart_products_id);
alter table cart_cart_products add constraint FK84skyyjnt6ygna9u3dwuyj3gc foreign key (cart_products_id) references cart_product;
alter table cart_cart_products add constraint FKcxat3enqpvlarcm20bkrl7b9 foreign key (cart_id) references cart;
alter table cart_product add constraint FK2kdlr8hs2bwl14u8oop49vrxi foreign key (product_id) references product;