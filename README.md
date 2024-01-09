# PriceApi

## Descripción General

API diseñada para el cálculo de precios según las cadenas de Inditex. Inmersa en una arquitectura de microservicios, esta aplicación está construida bajo los criterios técnicos y teóricos de la arquitectura hexagonal.

## Requisitos

# Requisitos

- **Java JDK**: Se requiere Java 17 para asegurar la compatibilidad y el correcto funcionamiento del proyecto.
- **Maven**: Para la gestión de dependencias y construcción del proyecto, se debe utilizar Maven. Asegúrate de que Maven esté correctamente instalado y configurado en tu entorno de desarrollo.

# Dependencias Principales

- **Spring Boot**: El proyecto se basa en la versión `3.2.1` de `spring-boot-starter-parent`, lo que proporciona una gran cantidad de configuraciones predeterminadas para Spring Boot.
- **Spring Boot Starter Data JPA**: Utilizado para integrar la capa de persistencia con JPA.
- **Spring Boot Starter Web**: Proporciona todas las dependencias necesarias para construir aplicaciones web, incluyendo RESTful aplicaciones usando Spring MVC.
- **H2 Database**: Base de datos en memoria utilizada para pruebas y desarrollo local.
- **Spring Boot Starter Test**: Contiene herramientas para pruebas unitarias y de integración.
- **SpringDoc OpenAPI**: Facilita la generación de documentación de la API a través de Spring MVC y OpenAPI 3.

# Herramientas de Mapeo y Lombok

- **MapStruct**: Se utiliza la versión `1.5.5.Final` para el mapeo entre objetos DTO y entidades de dominio.
- **Lombok**: Se utiliza para reducir el código repetitivo, especialmente para la generación de getters, setters, y otros métodos comunes.

# Testing

- **JUnit**: La versión `4.13.2` y `junit-jupiter-api` versión `5.10.1` se utilizan para pruebas unitarias.
- **Mockito**: Se utiliza la versión `5.8.0` para la creación de objetos mock en las pruebas.

# Construcción y Despliegue

- **Spring Boot Maven Plugin**: Se utiliza para construir y desplegar la aplicación Spring Boot.


## Configuración del Proyecto

### Base de Datos

- El proyecto utiliza H2, una base de datos en memoria, para el desarrollo y las pruebas. No es necesaria ninguna configuración adicional para H2 ya que Spring Boot configura automáticamente la base de datos en memoria.

### Variables de Entorno y Archivos de Propiedades

- Configura las variables de entorno específicas del proyecto en el archivo `application.yml` o `application-dev.yml` 
- en `src/main/resources`. Aquí puedes definir puertos, configuraciones de base de datos y otras propiedades específicas del entorno.


## Estructura del Proyecto

### Módulos Principales

- **Infraestructura (`infraestructure`)**:
  Este módulo contiene todo lo relacionado con la infraestructura del proyecto, como la configuración de bases de datos, comunicación con servicios externos y adaptadores de entrada/salida. Actúa como puente entre el dominio de la aplicación y el mundo externo.

- **Aplicación (`application`)**:
  El módulo de aplicación gestiona la lógica de negocio y el flujo de la aplicación. Aquí se encuentran los servicios que implementan los casos de uso del sistema, utilizando los puertos definidos en el dominio para interactuar con la infraestructura y otros componentes externos.

- **Dominio (`domain`)**:
  Este módulo es el núcleo del sistema, donde se definen las entidades y reglas de negocio. Representa los conceptos del dominio del problema y su lógica, independiente de la interfaz de usuario o la base de datos.

## Ejecución del Proyecto

Para poner en marcha el proyecto PriceApi, sigue los siguientes pasos. Estas instrucciones asumen que ya has configurado tu entorno de desarrollo con las dependencias necesarias, como se describe en secciones anteriores.

1. **Compilación del Proyecto**:
  - Abre una terminal y navega a la raíz del proyecto.
  - Ejecuta el comando Maven: `mvn clean package`. Este comando limpiará cualquier compilación anterior y creará un nuevo paquete del proyecto, incluyendo todas las dependencias necesarias.

2. **Navegación a la Carpeta de Infraestructura**:
  - Una vez completada la compilación, navega a la carpeta `infraestructure` dentro del proyecto. Este módulo contiene la configuración necesaria para ejecutar la aplicación.

3. **Ejecución de la Aplicación**:
  - Dentro de la carpeta `infraestructure`, ejecuta el comando: `mvn spring-boot:run`. Este comando inicia la aplicación utilizando Spring Boot.
  - Espera a que el proceso de inicio se complete. Puedes monitorear el progreso en la consola.

4. **Verificación de la Ejecución**:
  - Una vez que la aplicación esté en funcionamiento, verás un mensaje en la consola similar a: "Deploy app {nombre_app} - version {version_app}".
  - Este mensaje indica que la aplicación se ha desplegado correctamente y está ejecutándose. El nombre y la versión de la aplicación corresponden a los valores configurados en el archivo `application-dev.yml`.

5. **Acceso a Swagger UI**:
  - Además, puedes verificar que la aplicación se ha desplegado correctamente ingresando a la siguiente URL en tu navegador: `http://localhost:9091/swagger-ui/index.html#/`.
  - Ten en cuenta que el dominio y puerto deben coincidir con los configurados en tu archivo de propiedades.
  - En Swagger UI, podrás visualizar todos los endpoints disponibles y obtener información general del API.


## Pruebas

**Ejecución de Pruebas Unitarias**:
- Para ejecutar las pruebas unitarias, utiliza el comando `mvn test` en la terminal.
- Este comando ejecutará todas las pruebas unitarias definidas en el proyecto, mostrando en la consola el resultado de cada una.



## Creadores y Contribuidores

- **William Eduardo Rubiano Marín**
  - Rol: Creador y Desarrollador Principal
  - Email: [wrubianom1@gmail.com](mailto:wrubianom1@gmail.com)

---
