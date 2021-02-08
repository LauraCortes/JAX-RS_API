# JAX-RS_API
Implementación de un API en JAX-RS que permite abstraer funcionalidades de una cola de mensajes en Redis, utilizando la librería Jedis.

## Prerequisitos para la ejecución
Antes de iniciar con el despliegue de la API asegurese de tener instalados los siguientes componentes:
<ul><li>
Para compilar el proyecto y resolver las dependencias descargue <a href="https://maven.apache.org/download.cgi">Maven</a>. Para confirmar la instalación, ejecute en la línea de comandos <i>mvn -v</i>, debe retornar la versión de Maven que ha instalado.
</li>
  <li>Descargue e instale la versión estable de <a href="https://redis.io/download">Redis</a>, el broker de mensajes Open-Source que utilizará la API.</li>  
</ul>

## Ejecución de la API
Para desplegar el servidor Redis en modo Standalone, en una línea de comandos ejecute:
```
redis-server
```
Para confirmar que el servidor está en ejecución usted debe ver en la línea de comandos <i>Ready to accept connections</i>.<br>
Descargue el proyecto de este repositorio y sobre la carpeta raiz "/API" abra una línea de comandos. Primero, vamos a compilar el código fuente con Maven, ejecute:

```
mvn compile
```
Maven va a descargar todos los plugins y las dependencias que se definieron en el POM.xml. Al terminar, usted debe ver en la línea de comando el siguiente mensaje:
```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  41.697 s
[INFO] Finished at: 2021-02-08T09:05:35-05:00
[INFO] ------------------------------------------------------------------------
```
Luego, continuamos con la instalación del WAR que se va a desplegar en el servidor de aplicación, ejecute el comando:
```
mvn install
```
Observará que la aplicación se procesa en un proyecto WAR y debe tener el siguiente resultado:
```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.145 s
[INFO] Finished at: 2021-02-08T09:10:56-05:00
[INFO] ------------------------------------------------------------------------
```
Para ejecutar el servidor de aplicación y desplegar la aplicación compilada vamos a utilizar el plugin Wildfly Maven, se debió descargar durante la compilación del código fuente. Sobre la misma línea de comando ejecute:
```
mvn wildfly:run
```
Usted podrá observar que se inicia una instancia de Wildfly en standalone y que la aplicación se despliega en el servidor. El siguiente mensaje confirmará que los servicios REST están disponibles para ser consumidos:
```
09:20:32,308 INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 10) WFLYUT0021: Registered web context: '/api-0.0.1-SNAPSHOT' for server 'default-server'
09:20:32,396 INFO  [org.jboss.as.server] (management-handler-thread - 1) WFLYSRV0010: Deployed "api-0.0.1-SNAPSHOT.war" (runtime-name : "api-0.0.1-SNAPSHOT.war")
```

### Consumir servicios de la API
Para consumir los servicios de la API recomiendo utilizar la extensión de Google Chrome API Tester. Para acceder a los servicios, según el contrato de métodos definido más adelante, utilice el contexto web: HTTP://127.0.0.1:8080/api-0.0.1-SNAPSHOT/queue/[servicio]</br>
Al intentar acceder, seguramente obtuvo el mensaje <i>"Ingrese información de autenticación"</i>. Utilizando la librería JBoss RESTEasy se implementa un <i>"Security Interceptor"</i> básico. Utilice la opción "Add authorization" en los encabezados de la petición HTTP para incluir las siguientes credenciales básicas:
```
User: admin
Password: admin
```
En este punto ya puede consumir cualquiera de los servicios descritos en el contrato.
## Métodos de la API
Los métodos en la API cumplen con el siguiente contrato:
### PUSH
Agregar mensajes al final de la cola de mensaje
<table>
  <tr>
    <th>HTTP Method</th>
    <th>URI</th>
    <th>Cuerpo</th>
  </tr>
  <tr>
    <td>POST</td>
    <td>api-0.0.1-SNAPSHOT/queue/push</td>
    <td>{"message":[mensaje]}</td>
  </tr>
</table>

### POP
Obtener y eliminar el primer mensaje en la cola
<table>
  <tr>
    <th>HTTP Method</th>
    <th>URI</th>
  </tr>
  <tr>
    <td>POST</td>
    <td>api-0.0.1-SNAPSHOT/queue/pop</td>
  </tr>
</table>

### COUNT
Obtener la cantidad de mensajes en la cola
<table>
  <tr>
    <th>HTTP Method</th>
    <th>URI</th>
  </tr>
  <tr>
    <td>GET</td>
    <td>api-0.0.1-SNAPSHOT/queue/count</td>
  </tr>
</table>
