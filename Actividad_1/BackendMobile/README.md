CREACIÓN BASE DE DATOS:

docker pull mysql

docker run -d -p 3306:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=secret mysql --lower_case_table_names=1

docker exec -it mysql-db mysql -p

create database mobile_app;

create table mobile_user(full_name varchar(255), email varchar(255) PRIMARY KEY, password varchar(255));