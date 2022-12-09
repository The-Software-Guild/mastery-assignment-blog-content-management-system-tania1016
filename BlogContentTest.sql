DROP DATABASE IF EXISTS BlogContentTest;
CREATE DATABASE BlogContentTest;
USE BlogContentTest;
CREATE TABLE blog(
blog_id int primary key auto_increment,
`title` varchar(50) not null,
date Date,
body Text not null,
isApproved boolean);
CREATE TABLE tag(
tag_id int primary key auto_increment,
`tag_name` varchar(25));
CREATE TABLE blog_tag(
blog_id int not null,
tag_id int not null,
primary key(blog_id, tag_id),
foreign key (blog_id) references blog(blog_id),
foreign key (tag_id) references tag(tag_id));
CREATE TABLE user(
user_id int primary key auto_increment,
`role` varchar(25) not null,
`username` varchar(50) not null,
`password` varchar(50) not null);