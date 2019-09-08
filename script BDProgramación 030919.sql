--Fecha de creación	: 27 Agosto 2019
--Última modificación	: 28 Agosto 2019
--Autor			: Desarrollo App de escritorio

-- Database: "BDProgramacion"

-- DROP DATABASE "BDProgramacion";

/*CREATE DATABASE "BDProgramacion"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Spain.1252'
       LC_CTYPE = 'Spanish_Spain.1252'
       CONNECTION LIMIT = -1;
*/

CREATE TABLE USUARIO(
codUsuario serial not null primary key,
nomUsuario varchar(20) not null,
clave varchar(20) not null,
nombreCompleto varchar(80) not null,
cargo varchar(30) null,
estado boolean not null);

alter table USUARIO add pregunta varchar(50);
alter table USUARIO add respuesta varchar(50);

select * from USUARIO

insert into USUARIO (nomusuario, clave, nombrecompleto, cargo, estado) values
  ('admin','123456','Juan Perez Perez','Gerente General',true),
  ('invitado','usat2019','María Mendoza','Supervisor',false), 
  ('venta','chiclayo','Pedro Casas Merino','Jefe de ventas',true);

update USUARIO set pregunta='ciudad de nacimiento', respuesta='Lima' where codUsuario=1;
update USUARIO set pregunta='primer numero celular',respuesta='979105594' where codUsuario=2;
update USUARIO set pregunta='nombre de tu mascota',respuesta='Boby' where codUsuario=3;

CREATE TABLE MARCA(
codMarca serial not null primary key,
nomMarca varchar(30) not null);

CREATE TABLE CATEGORIA(
codCategoria serial not null primary key,
nomCategoria varchar(30) not null,
descripcion varchar(100) null,
vigencia boolean not null);

CREATE TABLE PRODUCTO(
codProducto serial not null primary key,
nomProducto varchar(30) not null,
descripcion varchar(100) not null,
precio decimal(8,2) not null,
stock int not null,
vigencia boolean not null,
codMarca int not null,
codCategoria int not null);

select * from marca;
select * from categoria;
select * from producto;

alter table PRODUCTO add constraint FK_PRO_MAR 
foreign key (codMarca) references MARCA;

alter table PRODUCTO add constraint FK_PRO_CAT 
foreign key (codCategoria) references CATEGORIA;

insert into MARCA(nomMarca) values('EPSON');

insert into CATEGORIA (nomCategoria, descripcion, vigencia)
values('Impresora','Dispositivos de impresión',true);

insert into PRODUCTO (nomProducto, descripcion, precio, stock, vigencia, codMarca, codCategoria)
values('Impresora Epson LX575','Impresora de tinta',850,12,true,1,1);

select * from MARCA;
select * from CATEGORIA;
select * from PRODUCTO;

update PRODUCTO set precio=950 where codProducto=1;

delete from PRODUCTO where codProducto=1;

--Inicio de sesión:
select * from usuario

--validar la vigencia del ususario
select estado from usuario where nomusuario='admin'

--validar usuario/contraseña
select * from usuario where nomusuario='admin' and clave='123456' and estado=true
select nombrecompleto from usuario where nomusuario='admin' and clave='123456' and estado=true

--Cambio de contraseña
update usuario set clave='nuevaClave' where nombrecompleto='nombre'

--Mostar Pregunta secreta
select pregunta from usuario where nomusuario='admin' 

--Validar Pregunta secreta
select nombrecompleto from usuario where nomusuario='admin' and respuesta='respuesta' and estado=true

--03/09/2019

--Modificaciones para la resolucion del lab 01
CREATE TABLE MOVIMIENTO(
numMovimiento serial not null primary key,
codUsuario int not null, 
fecha date not null,
estado Boolean not null);

alter table MOVIMIENTO add constraint FK_MOV_USU foreign key (codUsuario) references USUARIO

--para agregar la columna hora a la tabla movimiento
alter table movimiento add column hora time not null default current_time

alter table movimiento alter column fecha set data type default current_date
--para ver si hay registros en la tabla movimiento
select numMovimiento from movimiento  order by numMovimiento limit 1



