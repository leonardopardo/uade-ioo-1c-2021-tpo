drop table if exists usuarios;
create table if not exists usuarios (
    id integer primary key autoincrement,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    username varchar(100) not null,
    password varchar(20) not null,
    edad date not null,
    role varchar(50) not null
);

insert into usuarios (id, nombre, apellido, username, password, edad, role) values
(1, 'Leonardo', 'Pardo', 'leonardo@mail.com', '123123', '1981-06-12', 'ADMINISTRADOR'),
(2, 'Nicolás', 'Pardo', 'nicolas@mail.com', '123123', '1981-06-12', 'ADMINISTRADOR'),
(3, 'Federico', 'Pardo', 'federico@mail.com', '123123', '1981-06-12', 'ADMINISTRADOR'),
(4, 'Admin', 'Admin', 'admin@mail.com', '123123', '1981-06-12', 'ADMINISTRADOR'),
(5, 'Operador', 'Operador', 'operador@mail.com', '123123', '1981-06-12', 'OPERADOR');