<div align="center">
  <a href="https://github.com/dawcarlosp/client-management-java">
    <img src="https://skillicons.dev/icons?i=java,mysql" width="200"/>
  </a>
</div>

# 🏛️ Guía de Configuración: Client Management Java

¡Bienvenido/a! Este proyecto es una aplicación web Java desarrollada para ejecutarse en un servidor de aplicaciones **GlassFish**. Para que la aplicación funcione correctamente, es necesario configurar el Pool de Conexiones y el Recurso JDBC manualmente en el servidor.

---

## 🧱 Requisitos previos

Antes de empezar, asegúrate de tener:

- ☕ [JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- 🐟 [GlassFish 8](https://glassfish.org/download) instalado y en ejecución.
- 🐬 [MySQL Server](https://dev.mysql.com/downloads/mysql/) activo 
- 📂 El driver JDBC de MySQL (`mysql-connector-j`) en la carpeta `/lib` de tu dominio de GlassFish.
- 📂 Abrí MySQL Workbench → Open SQL Script → [control_clientes.sql](https://github.com/dawcarlosp/client-management-java/control_clientes.sql) → Execute 
- Otra forma, 💻 En la terminal
```bash 
mysql -u usuario -p < archivo.sql
```
---

## 📁 Paso 1. Clonar el repositorio

```bash
git clone https://github.com/dawcarlosp/client-management-java.git
```
--- 

## 📁Paso 2. Configurar GlassFish (Consola de Administración)

### 1️⃣ Crear el JDBC Connection Pool

#### Accede a la consola: [http://localhost:4848](http://localhost:4848)

#### Ve a Resources > JDBC > JDBC Connection Pools > New

#### Configura los valores básicos:

* **Pool Name:** ```MySQLPool```
* **Resource Type:** ```javax.sql.DataSource```
* **Database Driver Vendor:** ```MySQL```
* **Datasource Classname (Paso 2) :** ```com.mysql.cj.jdbc.MysqlDataSource```

#### En Additional Properties, define:

* **User:** 
* **Password:** 
* **serverName:** ```localhost```
* **portNumber:** ```3306```
* **databaseName:** ```control_clientes```
* **serverTimezone:** ```UTC```
* **allowPublicKeyRetrieval:** ```true```
* **useSSL:** ```false```