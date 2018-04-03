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

create table canasta(
    id int auto_increment primary key,
    producto int,
    cantidad int,
    foreign key(producto) references producto(id)
);


/*--------------INSERT-----------------*/
insert into marca value(null,"Soprole");
insert into marca value(null,"Nestle");
insert into marca value(null,"Colun");
insert into marca value(null,"Ideal");

insert into producto value(null,"yoghurt",1,35,300);
insert into producto value(null,"gansito",4,30,250);
insert into producto value(null,"papas",4,55,500);
insert into canasta value(null,1,2);
insert into canasta value(null,2,5);
insert into canasta value(null,2,5);


/*-------------------------------------*/

/*--------SELECT-------------*/
select * from marca;
select * from producto;
select * from canasta;

select 
select producto.id,producto.nombre,marca.nombre,producto.stock,producto.precio from marca,producto where producto.marca = marca.id;


select  producto.precio, canasta.cantidad, (producto.precio * canasta.cantidad) from producto, canasta where canasta.producto = producto.id ;

SELECT p.nombre, SUM(p.precio *c.cantidad)
   FROM producto p LEFT JOIN canasta c ON c.id = p.id 
   
/*---------------------------*/
