/*SCRIPT DE CREACION DE BASE DE DATOS*/

/*CON ESTE COMANDO CREA LA BASE*/
sqlite3 servidorcorreo.db

/*CREACION DE TABLAS */

/*TABLA SERVIDOR*/
CREATE TABLE servidor (
idservidor integer primary key not null,
nombre varchar(100) not null,
ip varchar(15),
estado varchar(10));
/*TABLA USUARIO*/
CREATE TABLE usuario (
idusuario varchar(100) primary key not null,
nombre varchar(200),
correo varchar(200),
password varchar(20),
estado integer);
/*TABLA CONTACTO*/
CREATE TABLE contacto (
idusuario varchar(100) primary key not null,
idusuariopersonal varchar(100),
idusuariocontacto varchar(100),
idservidor integer,
foreign key (idusuariopersonal) references usuario(idusuario)
foreign key (idusuariocontacto) references usuario(idusuario)
foreign key (idservidor) references servidor(idservidor));
/*TABLA CORREO*/
CREATE TABLE correo (
idcorreo integer primary key not null,
asunto varchar(1000),
cuerpo clob,
idusuarioremitente varchar(100),
idservidor integer,
foreign key (idusuarioremitente) references usuario (idusuario)
foreign key (idservidor) references servidor (idservidor));
/*TABLA DESTINATARIO*/
CREATE TABLE destinatario(
iddestinatario varchar(100) primary key not null,
idusuarioreceptor varchar(100),
idcorreo integer,
foreign key (idusuarioreceptor) references usuario (idusuario)
foreign key (idcorreo) references correo(idcorreo));

