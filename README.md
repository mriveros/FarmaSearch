# FarmaSearch
Aplicación para la búsqueda de farmacias cercanas a ti, con administración web

**Introducción al diseño arquitectónico**

Inicialmente tras un análisis de los requisitos del sistema se va a necesitar
una serie de componentes software que van a estar distribuidos y con un estilo
arquitectónico centrado en datos (Blackboard). Con esto conseguimos que el
centro de la arquitectura sea una pizarra y que otros componentes tengan acceso
a él para actualizar, agregar, eliminar o consultar sus datos. Sin embargo cada
componente va a requerir un estilo arquitectónico distinto.

Vamos a tener 3 componentes de software principales, el primero de ellos es una
aplicación Android que tiene un estilo arquitectónico basado en capas ya que
tiene una presentación, una lógica de aplicación y una consulta a datos. El
segundo componente es una página web que está basada también en el mismo modelo
basado en capas. Por último tenemos otro componente software que hará las veces
de pizarra de datos como repositorio pasivo que será una aplicación en un
servidor tipo REST programado con jersey

A la hora de elegir el patrón arquitectónico, el software que se va a presentar
en este documento está orientado con un patrón arquitectónico de
cliente-servidor. En secciones posteriores se detallarán las necesidades que han
llevado a que este software tenga un patrón de arquitectura tipo
cliente-servidor.

**Motivación del diseño arquitectónico**

La elección del estilo arquitectónico basado en pizarra es debido a varios
factores:

-   Facilita la integración pues los componentes son independientes.

-   Se puede pasar datos entre componentes a través del almacén de datos.

-   Los componentes software de los que disponemos no necesitan comunicarse
    entre sí salvo con la pizarra.

La elección del patrón arquitectónico cliente-servidor es debido a que tenemos
un componente software REST programado en jersey que está alojado en un servidor
y hace las veces de proveedor de servicios que necesita el cliente, en este caso
son datos de una base de datos.

Los clientes a su vez realizan peticiones al servidor de manera continuada, como
clientes podemos identificar al componente software aplicación de Android y al
componente software página web.

**Diagrama de contexto inicial**

El diagrama de la figura 1 no sigue ningún tipo de notación UML ni otro lenguaje
específico, podría asemejarse a un diagrama de despliegue en el que se muestra
la estructura en tiempo de ejecución del sistema, es decir, la configuración del
hardware y cómo el software se distribuye en él. Se adjunta este diagrama para
hacer una representación gráfica de las conexiones y elementos que intervienen
en el sistema para tenerlos en cuenta a la hora de las conexiones y posibles
elementos.

![Diagrama general](http://coloredmoon.com/wp-content/uploads/2017/03/Diagrama-General-de-la-APP2.png "Diagrama General")

Figura 1: Diagrama de contexto inicial

**Release Notes**

**Fecha del Release**: 09/05/2016

**Requerimientos del sistema:**

>   Una máquina que haga las veces de servidor con capacidad de almacenar
>   Servlets y ejecutar el servidor Tomcat para ejecutar dichos Servlets. Dicho
>   servidor tiene que además poder almacenar y gestionar una base de datos
>   MYSQL y tener disponible un servidor Apache para poder visualizar la página
>   web de gestión de farmacias.

>   También se necesita de un dispositivo móvil para la aplicación desarrollada
>   en Android.

**Requisitos de Hardware:**

>   Para el software de Android se necesita un dispositivo móvil con la versión
>   de Android 4.4 como mínimo.

>   Para el servidor REST, una máquina con al menos 0.75GB RAM y cualquier
>   sistema operativo capaz de ejecutar los servicios Apache, Apache Tomcat v8,
>   MYSQL y el JDK superior al 1.8.

**Instalación:**

>   Primero debemos instalar los componentes software del servidor, tanto Apache
>   como Apache Tomcat v8 y MYSQL para ello en el sistema operativo Windows,
>   Linux y MAC podemos utilizar XAMPP que nos proveerá de todos estos servicios
>   en un solo instalador, en Linux Tomcat se tendrá instalar de manera
>   individual. En el siguiente enlace podéis encontrar el archivo de
>   instalación:

>   <https://www.apachefriends.org/es/download.html>

>   <http://www.liquidweb.com/kb/how-to-install-apache-tomcat-8-on-ubuntu-14-04/>

>   Una vez instalados los servicios tendremos que ejecutarlos con el panel de
>   control que nos proporciona XAMPP. Para subir nuestra aplicación servidora
>   (el archivo .war) tendremos que hacerlo a la carpeta {Ruta de instalación
>   XAMPP}\\ tomcat\\webapps\\.

>   Para crear la base de datos necesaria se incluye un archivo .sql para poder
>   crear la base de datos necesaria en el servidor:

>   farmasearch\_bbdd.sql

>   El cual tendrá que importarse usando phpMyAdmin o cualquier otro gestor de
>   MYSQL.

>   Para generar el archivo .war o modificar el proyecto REST se ha de
>   descomprimir el fichero adjunto como ChicaJimenezEmilio-2.zip e importarlo
>   con el IDE Eclipse, el proyecto Web Service se ha realizado con la versión
>   MARS 2 por si se encuentra algún tipo de problema al importarlo en otra
>   versión de Eclipse.

>   Se han de tener instalados los siguientes plugins para Eclipse:

-   Java 8 Support for m2e for Eclipse Kepler SR2

-   Maven Integration for Eclipse (Luna and newer) 1.5

-   Maven Integration for Eclipse (Luna) 1.5.0

>   Una vez importado y con los plugins instalados se ha de proceder a la
>   exportación del proyecto en formato .war. Para ello seleccionamos el
>   proyecto y con botón derecho le damos a Export y buscamos war en el
>   buscador.

>   Le damos a la opción war file y guardamos el fichero .war generado en la
>   carpeta del servidor antes citada. El servidor Tomcat se encargará de
>   desplegar nuestra aplicación.

>   Una vez instalado el servidor podemos descargarnos la aplicación de
>   GooglePlay buscando la aplicación:

>   FarmaSearch de ColoredMoon

>   Aquí podremos ver todas las farmacias añadidas en el panel de administración
>   web.

**Manual de usuario:**

>   Para usar el software de administración web del proyecto tenemos que acceder
>   vía navegador a la dirección:

<http://104.40.61.63:8080/ChicaJimenezEmilio>

>   Una vez hay tendremos que registrarnos con el formulario del que se dispone
>   y luego iniciar sesión con dicho usuario con el email y el password que
>   hayamos establecido. El usuario con el que se está registrando tiene los
>   privilegios de añadir tantas farmacias, departamentos y productos como
>   necesite, contemplando así que un farmacéutico puede ser propietario de
>   varias farmacias o inclusive de una franquicia, los productos que añada va
>   asignados a ese usuario y tendrá que añadir al inventario de cada farmacia
>   dichos productos por medio de la sección inventario.

>   Un mismo departamento pueden estar asignados a varias farmacias distintas
>   para seleccionar más de una de la lista tiene que utilizar CTRL+CLICK del
>   ratón izquierdo y para deseleccionar algunas puede hacerlo del mismo modo.

>   Para añadir una farmacia necesita saber la latitud y la longitud o usar el
>   mapa de Google que se proporciona y haciendo click sobre la calle donde se
>   encuentre su Farmacia y esto rellenará el campo de latitud y longitud
>   automáticamente.

>   Todo elemento producto, farmacia y departamento tiene asociado una imagen,
>   esta es opcional y no tiene porqué añadirla pero es aconsejable para que se
>   vea en la aplicación móvil.

>   El inventario va seccionado por farmacias, por lo que primero tendrá que
>   elegir la campos son obligatorios una vez haya seleccionado todo y haya
>   pulsado el botón con el símbolo + puede cambiar el stock y el precio de ese
>   producto en esa farmacia.

>   Por último la sección de reservas contiene todas las reservas realizadas por
>   todos los usuarios en su farmacia, para que pueda comprobar quien ha
>   reservado que producto y guardárselo al usuario.

>   Para el software Android puede seleccionar cualquier marcador en el mapa que
>   le llevará a la farmacia seleccionada y le mostrará los departamentos
>   asociados y puede elegir cualquiera de ellos y le mostrará los productos
>   asociados a ese departamento.

>   Tiene un buscador donde puede buscar los productos por nombre.

>   Tiene un menú lateral donde podrá consultar las reservas, su posición actual
>   y los productos por departamento y el contacto con nosotros.

>   También se ha añadido una sección de cesta donde podrá consultar los
>   productos que va añadiendo a la cesta.

**Diagramas de clases**

A continuación para mostrar la estructura de los componentes software vamos a
utilizar diagramas de clases UML divididos en dos componentes principales la
aplicación en Android y los diagramas del servidor en REST en jersey. Figura 2 y
3 respectivamente.
