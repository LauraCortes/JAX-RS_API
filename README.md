# JAX-RS_API
Implementación de un API en JAX-RS que permite abstraer funcionalidades de una cola de mensajes en Redis, utilizando la librería Jedis.

## Ejecución de la API
El proyecto utiliza Maven para el manejo de librerías y WildFly 22.0.0 como servidor de aplicación. Para desplegar la aplicación ejecute:

```
mvn clean
mvn install wildfly:run
```
Por último, no olvide ejecutar el servidor Redis en modo Standalone:
```
redis-server
```
### Autenticación a la API
Utilizando la librería JBoss RESTEasy se implementa un <i>"Security Interceptor"</i> básico. Para acceder a los servicios expuestos por la API agregue en la petición HTTP las siguientes credenciales básicas de autenticación:
```
User: admin
Password: admin
```

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
