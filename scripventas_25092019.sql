create table tipo_cliente(
codtipo serial not null primary key,
nombre varchar(20) not null
)

insert into tipo_cliente(nombre) values ('Persona natural con RUC')
insert into tipo_cliente(nombre) values ('Persona natural')
insert into tipo_cliente(nombre) values ('Persona juridica')



create table cliente(
codcliente int not null primary key,
dni char(8) null,
ruc char(11) null, 
nombres varchar(30) not null, 
telefono varchar(13) null,
correo varchar (50) null,
direccion varchar(70) not null,
vigencia boolean not null,
codtipo int not null references tipo_cliente(codtipo)
);

create table venta(
numventa int not null primary key,
fecha date not null,
total decimal (10,2) not null,
subtotal decimal(10,2) null,
igv decimal(10,2) null,
tipocomprobante boolean not null,
estadopago boolean not null,
codcliente int not null references cliente(codcliente) 
);

create table detalle(
numventa int not null references venta(numventa),
codproducto int not null references producto(codproducto),
cantidad int not null,
precioventa decimal(8,2) not null,
descuento smallint not null,
subtotal decimal(10,2) not null,
constraint pk_detalle primary key (numventa,codproducto)
);

