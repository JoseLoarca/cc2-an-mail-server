/*SCRIPT DE CREACION DE BASE DE DATOS*/

/*CON ESTE COMANDO CREA LA BASE*/
sqlite3 servidorcorreo.db

/*CREACION DE TABLAS */

/*TABLA SERVIDOR*/

CREATE TABLE servidor (
idservidor integer primary key AUTOINCREMENT,
nombre varchar(100) not null,
ip varchar(15),
estado integer);

/*TABLA USUARIO*/

CREATE TABLE usuario (
idusuario integer primary key AUTOINCREMENT,
nombre varchar(200),
correo varchar(200),
password varchar(255),
estado integer);

/*TABLA CONTACTO*/

CREATE TABLE contacto (
idcontacto integer primary key AUTOINCREMENT,
idusuariopersonal varchar(100),
idusuariocontacto varchar(100),
idservidor integer,
foreign key (idusuariopersonal) references usuario(idusuario)
foreign key (idusuariocontacto) references usuario(idusuario)
foreign key (idservidor) references servidor(idservidor));

/*TABLA CORREO*/

CREATE TABLE correo (
idcorreo integer primary key AUTOINCREMENT,
asunto varchar(1000),
cuerpo clob,
idusuarioremitente integer,
idservidor integer,
foreign key (idusuarioremitente) references usuario (idusuario)
foreign key (idservidor) references servidor (idservidor));

/*TABLA DESTINATARIO*/

CREATE TABLE destinatario(
iddestinatario integer primary key AUTOINCREMENT,
idusuarioreceptor integer,
idcorreo integer,
foreign key (idusuarioreceptor) references usuario (idusuario)
foreign key (idcorreo) references correo(idcorreo));


/*INSERTS DATA USUARIOS*/

insert into usuario (nombre,correo,password,estado)values('Manuel Santos','msantos10','123',0);

insert into usuario (nombre,correo,password,estado)values('Jair Flores','jflores','123',0);

insert into usuario (nombre,correo,password,estado)values('Harim Palma','hpalma','123',0);

insert into usuario (nombre,correo,password,estado)values('Jose Loarca','jloarca','123',0);

insert into usuario (nombre,correo,password,estado)values('Carla Bruno','cbruno','123',0);

 insert into usuario (nombre,correo,password,estado)values('Carlos Arias','carias','123',0);

insert into usuario (nombre,correo,password,estado)values('Lionel Messi','lmessi','123',0);

/*INSERTS DATA SERVIDORES*/

insert into servidor (nombre,ip,estado)values('Yimail','localhost','1');

/*INSERTS DATA CONTACTOS*/

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(1,2,1);

insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(1,3,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(1,4,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(1,5,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(2,6,1);

insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(2,3,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(2,4,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(2,5,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(3,6,1);

insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(3,2,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(3,4,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(3,1,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(4,6,1);

insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(4,2,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(4,5,1);

 insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)values(4,1,1);

/*INSERTS DATA CORREO*/

 insert into correo (asunto,cuerpo,idusuarioremitente,idservidor)values('PRUEBA','ESTO ES UNA PRUEBA DE ENVIO DE CORREO ELECTRONICO NO 1',1,1);

insert into correo (asunto,cuerpo,idusuarioremitente,idservidor)values('PRUEBA','ESTO ES UNA PRUEBA DE ENVIO DE CORREO ELECTRONICO NO 2',1,1);

insert into correo (asunto,cuerpo,idusuarioremitente,idservidor)values('PRUEBA','ESTO ES UNA PRUEBA DE ENVIO DE CORREO ELECTRONICO NO 1',2,1);

insert into correo (asunto,cuerpo,idusuarioremitente,idservidor)values('PRUEBA','ESTO ES UNA PRUEBA DE ENVIO DE CORREO ELECTRONICO NO 2',2,1);

insert into correo (asunto,cuerpo,idusuarioremitente,idservidor)values('PRUEBA','ESTO ES UNA PRUEBA DE ENVIO DE CORREO ELECTRONICO NO 1',3,1);

insert into correo (asunto,cuerpo,idusuarioremitente,idservidor)values('PRUEBA','ESTO ES UNA PRUEBA DE ENVIO DE CORREO ELECTRONICO NO 2',3,1);

insert into correo (asunto,cuerpo,idusuarioremitente,idservidor)values('PRUEBA','ESTO ES UNA PRUEBA DE ENVIO DE CORREO ELECTRONICO NO 1',4,1);

insert into correo (asunto,cuerpo,idusuarioremitente,idservidor)values('PRUEBA','ESTO ES UNA PRUEBA DE ENVIO DE CORREO ELECTRONICO NO 2',4,1);

/*INSERTS DATA DESTINATARIOS*/
insert into destinatario (idusuarioreceptor,idcorreo) values (1,1);
insert into destinatario (idusuarioreceptor,idcorreo) values (2,1);
insert into destinatario (idusuarioreceptor,idcorreo) values (3,1);
insert into destinatario (idusuarioreceptor,idcorreo) values (4,1);
insert into destinatario (idusuarioreceptor,idcorreo) values (1,2);
insert into destinatario (idusuarioreceptor,idcorreo) values (2,2);
insert into destinatario (idusuarioreceptor,idcorreo) values (3,2);
insert into destinatario (idusuarioreceptor,idcorreo) values (4,2);
insert into destinatario (idusuarioreceptor,idcorreo) values (1,3);
insert into destinatario (idusuarioreceptor,idcorreo) values (2,3);
insert into destinatario (idusuarioreceptor,idcorreo) values (3,3);
insert into destinatario (idusuarioreceptor,idcorreo) values (4,3);
insert into destinatario (idusuarioreceptor,idcorreo) values (1,4);
insert into destinatario (idusuarioreceptor,idcorreo) values (2,4);
insert into destinatario (idusuarioreceptor,idcorreo) values (3,4);
insert into destinatario (idusuarioreceptor,idcorreo) values (4,4);
insert into destinatario (idusuarioreceptor,idcorreo) values (2,7);
insert into destinatario (idusuarioreceptor,idcorreo) values (5,6);
insert into destinatario (idusuarioreceptor,idcorreo) values (1,5);
insert into destinatario (idusuarioreceptor,idcorreo) values (6,8);


