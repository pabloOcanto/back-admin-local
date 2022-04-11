INSERT INTO user (id, dni,email,password,mobile_phone,full_name,status,rol) VALUES (null, 34404216,'pomalianni@gmail.com','12434',9221,'pablo ocanto','ENALBED','USER');
INSERT INTO user (id, dni,email,password,mobile_phone,full_name,status,rol) VALUES (null, 27803204,'cnlaffitte@gmail.com','12434',321,'carlos laffitte','ENALBED','USER');
INSERT INTO user (id, dni,email,password,mobile_phone,full_name,status,rol) VALUES (null, 24902133,'nicole@isur.com','12434',321,'nicole','ENALBED','ADMIN');


INSERT INTO notification (id,topic,title,area,message,date_created,user_created_id,status) VALUES (null, 'level-1','accidente ruta 3','Rio Negro,Rawson;Neuquen,San Martin de los Andes','ruta cortada por 3 hs','2022-02-19T16:23:00',1,'SENDED');


INSERT INTO topic (id, name,status) VALUES (null, 'level-1','ENABLED');
INSERT INTO topic (id, name,status) VALUES (null, 'level-2','ENABLED');
INSERT INTO topic (id, name,status) VALUES (null, 'level-3','DISABLED');

INSERT INTO city (id, lat,lon,city,state,status) VALUES (null, -0.116773, 51.510357,'Rio Negro','Rawson','ENABLED');
INSERT INTO city (id, lat,lon,city,state,status) VALUES (null, -0.116773, 51.510357,'Chubut','Puerto Madryn','ENABLED');
INSERT INTO city (id, lat,lon,city,state,status) VALUES (null, -0.116773, 51.510357,'Neuquén','San Martin de los Andes','ENABLED');

