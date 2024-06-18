create database tabling;

use tabling;

create table customer(
	customer_id int primary key auto_increment,
    customer_name varchar(50),
    phone varchar(50) not null unique,
    state enum('Y', 'N') default 'N',
    location_id int,
    foreign key(location_id) references location(location_id)
);

create table restaurant(
	restaurant_id int primary key auto_increment,
    restaurant_name varchar(100) not null,
    phone varchar(50),
    address varchar(255) not null,
    content text,
    open_time time,
    close_time time,
    rating decimal(2, 1),
    likes int default 0,
    rest_day varchar(5),
    location_id int,
    category_id int,
    foreign key(location_id) references location(location_id),
    foreign key(category_id) references category(category_id)
);

create table category (
    category_id int primary key AUTO_INCREMENT,
    category_name varchar(50) not null
);

create table food (
    food_id int primary key AUTO_INCREMENT,
    food_name VARCHAR(100) not null,
    category_id int,
    FOREIGN KEY(category_id) REFERENCES category(category_id)
);

create table reservation(
	reservation_id int primary key auto_increment,
    reservation_state enum('Y', 'N') default 'Y',
    reservation_time timestamp default current_timestamp,
    customer_id int,
    restaurant_id int,
    foreign key(customer_id) references customer(customer_id),
    foreign key(restaurant_id) references restaurant(restaurant_id)
);

create table location(
	location_id int primary key auto_increment,
    location_name varchar(50)
);

create table menu (
    restaurant_id int,
    food_id int,
    price int not null,
    PRIMARY KEY(restaurant_id, food_id),
    FOREIGN KEY(restaurant_id) REFERENCES restaurant(restaurant_id),
    FOREIGN KEY(food_id) REFERENCES food(food_id)
);

insert into location(location_name) values
('강서구'),
('사하구'),
('사상구'),
('북구'),
('서구'),
('중구'),
('동구'),
('부산진구'),
('영도구'),
('남구'),
('동래구'),
('연제구'),
('수영구'),
('금정구'),
('해운대구'),
('기장군');

select * from category;
select * from customer;
select * from food;
select * from location;
select * from menu;
select * from reservation;
select * from restaurant;
