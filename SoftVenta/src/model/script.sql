create database sofVenta;

use sofVenta;

create table marca(
    id int auto_increment primary key,
    nombre varchar(50)
);

create table producto(
    id int auto_increment primary key,
    nombre varchar(50),
    marca int,
    stock int,
    precio int,
    foreign key(marca) references marca(id)
);


/*--------------INSERT-----------------*/
insert into marca value(null,"Soprole");
insert into marca value(null,"Nestle");
insert into marca value(null,"Colun");
insert into marca value(null,"Ideal");

insert into producto value(null,"yoghurt",1,35,300);

/*-------------------------------------*/

/*--------SELECT-------------*/
select * from marca;
select * from producto;
/*---------------------------*/
