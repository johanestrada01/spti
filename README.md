# PROYECTO FINAL - CICLO DE VIDA DEL DESARROLLO DE SOFTWARE

# CONDICIONES DE ENTREGA:
## Nombre del proyecto: Top Solution

- **Período Académico**
  - 7 Semestre


- **Nombre del Curso**
  - Ciclo de vida del desarrollo de Software


- **Nombre de los Integrantes**
  - Ana María Durán Burgos (Developer)
  - Juan David Contreras Becerra (Scrum Master)
  - Johan Alejandro Estrada Pastrán (Developer)
  - Samuel Rojas Yopasa (Developer)


- **Nombre del Profesor**
  - Iván Darío Lemus Moya (Product Owner)


## Descripción del producto.

El producto realizado es un cotizador web que permite seleccionar distintos productos por medio de una clasificación entre categorías,
la adición de estos a un carrito de compras al cual agregar y eliminar los servicios junto con un resumen pequeño del subtotal de la
compra, el costo de los impuestos y el valor total a pagar.

Al finalizar generará una factura con la compra la cual se podrá imprimir o pedir la ayuda de un asesor para continuar con el proceso de
cotización.

También cuenta con un módulo que permite agregar nuevos servicios que se pueden ofrecer en la página, además de actualizarlos, eliminarlos
y visualizarlos. Esta sección también deja visualizar el listado de cotizaciones que se han realizado en la página web junto con un resumen
de lo que tiene cada una de ellas.

Este cotizador se puede utilizar para cualquier propósito, esta vez se usó en los servicios que ofrece un taller automotriz.

## Descripción general.

- Módulo Público
  - Selección de vehículo para solicitar el servicio.
![Selección Marca](src/main/resources/static/images/readme/webpage/brands.png)
![Selección Modelo](src/main/resources/static/images/readme/webpage/models.png)
![Selección Año](src/main/resources/static/images/readme/webpage/years.png)
![Selección Cilindraje](src/main/resources/static/images/readme/webpage/cylinders.png)
  - Selección de servicios distribuidos por categorías.
![Servicios por categorías](src/main/resources/static/images/readme/webpage/servicesCategories.png)
  - Agregar y eliminar servicios de la cotización y un resumen de esta.
![Agregar Servicio](src/main/resources/static/images/readme/webpage/addService.png)
![Eliminar Servicio](src/main/resources/static/images/readme/webpage/deleteService.png)
![Resumen Cotización](src/main/resources/static/images/readme/webpage/quotation.png)
  - Impresión de la cotización realizada.
![Impresión Cotización](src/main/resources/static/images/readme/webpage/print.png)
  - Contacto con asesor para continuar con el proceso.
![Contacto Asesor](src/main/resources/static/images/readme/webpage/message.png)
- Módulo Privado
  - Login de acceso para el administrador.
![Login](src/main/resources/static/images/readme/webpage/login.png)
  - Visualización y detalle de las cotizaciones vigentes.
![Visualizar cotizaciones](src/main/resources/static/images/readme/webpage/quotations.png)
  - Módulo CRUD para los servicios.
![CRUD Servicios](src/main/resources/static/images/readme/webpage/read.png)
![CRUD Servicios](src/main/resources/static/images/readme/webpage/create.png)
![CRUD Servicios](src/main/resources/static/images/readme/webpage/update.png)
![CRUD Servicios](src/main/resources/static/images/readme/webpage/delete.png)

## Arquitectura y Diseño detallado:
 - Modelo E-R.

![Modelo Entidad Relación](src/main/resources/static/images/readme/model/ERModelo.png)

 - Diagrama de clases.

![Diagrama de clases](src/main/resources/static/images/readme/model/diagramaClases.png)

 - Descripción de la arquitectura (capas) y del Stack de tecnologías utilizado.


**Arquitectura**

La arquitectura que se utilizó fue la arquitectura MVC (Modelo - Vista - Controlador) y se dividió de la siguiente 
forma, el modelo se repartió en lo que son clases que representan a nuestra base de datos, los servicios que
interactúan con los modelos y repositorios, por último los repositorios que se comunican directamente con la base
de datos. A continuación se represetan su interacción por medio del siguiente diagrama.

![Arquitectura Capas](src/main/resources/static/images/readme/model/arquitectura.png)


**Stack de tecnologías**

Para este proyecto se usaron las siguientes tecnologías:
 - Spring Framework
 - Spring Data Jpa
 - Thymeleaf
 - Base de datos H2
 - Jacoco

## Descripción del proceso:
 - Breve descripción de la Metodología.

La metodología que usamos fue la metodología SCRUM en ella se dividen distintos roles a lo largo del equipo en donde el Product Owner
se reúne periódicamente con el cliente para conocer su opinión sobre lo que se lleva realizado del producto, el Scrum Master que 
apoya a los developers en caso de que a estos se les presente alguna dificultad y los desarrolladores que se encargan de dar funcionamiento
a la aplicación. Esta metodología divide las tareas a realizar para completar el proyecto final en historias de usuario de las que cada
desarrollador se puede encargar para poco a poco ir completando la aplicación antes de que finalice un Sprint, un periodo de tiempo en el que
se desarrolla una funcionalidad al juntar todas las historias de usuario realizadas.

La división de las tareas se puede encontrar adjunta en el Backlog, la repartición de los roles ya se mencionó anteriormente y se mantuvo una
comunicación constante entre el equipo por la medio de la realización de daily meetings para conocer el progreso de cada uno, lo que ya había
realizado y si este presentaba algún bloqueo.

 - Enlace al backlog de AzureDevops.


Enlace al [backlog](https://dev.azure.com/ivanlemus0422/cvds-2024-I-agile-dev/_backlogs/backlog/cvds-2024-I-agile-dev%20Team/Issues)


 - Reporte de pruebas y de cubrimiento de las mismas (JUnit test).

![Pruebas Unitarias](src/main/resources/static/images/readme/model/unitTests.png)

**Nota**: Las pruebas unitarias fallan si se realiza la inserción de datos en el archivo data.sql, por tanto 
se deben eliminar todas las inserciones excepto la inserción a la tabla configuration.
 
### Sprint 2:

![Sprint Backlog 2](src/main/resources/static/images/readme/sprint2/Sprint2I1.png)

![Sprint Backlog 2](src/main/resources/static/images/readme/sprint2/Sprint2I2.png)

![Sprint Backlog 2](src/main/resources/static/images/readme/sprint2/Sprint2I3.png)

![Sprint Backlog 2](src/main/resources/static/images/readme/sprint2/Sprint2I4.png)

![Sprint Backlog 2](src/main/resources/static/images/readme/sprint2/Sprint2I5.png)

![Sprint Backlog 2](src/main/resources/static/images/readme/sprint2/Sprint2I6.png)

### Sprint 3:

![Sprint Backlog 3](src/main/resources/static/images/readme/sprint3/Sprint3I1.png)

![Sprint Backlog 3](src/main/resources/static/images/readme/sprint3/Sprint3I2.png)

![Sprint Backlog 3](src/main/resources/static/images/readme/sprint3/Sprint3I3.png)

![Sprint Backlog 3](src/main/resources/static/images/readme/sprint3/Sprint3I4.png)