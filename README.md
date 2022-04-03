# Backend Administracion

Administrar y proveer notificaciones 

Funcionalidades:
gestion de notificaciones
Gestion de usuarios


# Levantar el proyecto
- setear la variable de entorno spring.profiles.active con el valor dev

# Uso ambiente prod
- Es necesario levantar el postegres con docker

´´´
docker run -d --name basic-postgres --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=4y7sV96vA9wv46VR -e PGDATA=/var/lib/postgresql/data/pgdata -v /tmp:/var/lib/postgresql/data -p 5432:5432 -it postgres:14.1-alpine
´´´
