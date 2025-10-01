# Proyecto DOMI-Nation – Gestor de Salas de Ensayo 🎸🥁🎹

Aplicación web desarrollada en **Java (Spring Boot 3, JSP, Hibernate, MySQL)** para gestionar salas de ensayo.  
El proyecto fue refactorizado y modernizado para ejecutarse en **contenedores Docker** y ser fácilmente desplegable en cualquier entorno.

---

## 🚀 Tecnologías utilizadas

- **Backend**: Java 17 + Spring Boot 3 + Hibernate (JPA)
- **Frontend**: JSP + JSTL + Bootstrap
- **Base de datos**: MySQL 8
- **Servidor de aplicaciones**: Tomcat 10 (Jakarta EE)
- **Contenerización**: Docker + Docker Compose
- **Build Tool**: Maven

---

## 📦 Requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop)  
- Docker Compose v2  
- (Opcional) Maven 3.9+ para compilar localmente sin Docker

---

## ▶️ Cómo levantar el proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/KingMomoGiannini/Proyecto-Java-Web-DOMI-Nation---FINAL-PrograII-INSPT.git
   cd Proyecto-Java-Web-DOMI-Nation---FINAL-PrograII-INSPT
   ```

2. Construir y levantar los servicios:
   ```bash
   docker compose build --no-cache
   docker compose up -d
   ```

3. Acceder desde el navegador:
   ```
   http://localhost:8080/
   ```

---

## 🗄️ Base de datos

- Imagen: `mysql:8.0`  
- Base: `gestion_domination`  
- Usuario: `root`  
- Password: `1614`  
- Script inicial: [`gestion_domination.sql`](./DER%20y%20BDD/gestion_domination.sql)  

Conexión manual desde terminal:
```bash
docker exec -it domination-mysql mysql -uroot -p1614 gestion_domination
```

---

## 🧪 Comandos útiles

- Ver logs de la app:
  ```bash
  docker logs -f domination-app
  ```

- Ver logs de MySQL:
  ```bash
  docker logs -f domination-mysql
  ```

- Reiniciar contenedores:
  ```bash
  docker compose restart
  ```

- Apagar servicios:
  ```bash
  docker compose down
  ```

---

## 📌 Notas

- El WAR se despliega automáticamente en **Tomcat 10** dentro del contenedor.  
- Los JSP funcionan gracias a la integración con `tomcat-embed-jasper`.  
- Los datos de MySQL persisten en el volumen `domination-mysql-data`.  
- Configuración de Hibernate: `spring.jpa.hibernate.ddl-auto=validate` (para garantizar consistencia con el esquema SQL).

---

## 👨‍💻 Autor

Desarrollado por **Sebastián Giannini** (@KingMomoGiannini)  
Técnico en electrónica, robótica y estudiante de informática aplicada.  
Apasionado por el desarrollo de software y la optimización de procesos mediante soluciones tecnológicas innovadoras.
