# JAX-RS_API
Implementación de un API en JAX-RS que permite abstraer funcionalidades de una cola de mensajes en Redis. Los métodos implementados cumplen el siguiente contrato:

## Ejecución de la API
El proyecto utiliza Maven para el manejo de librerías y WildFly 22.0.0 como servidor de aplicación. Para desplegar la aplicación ejecute:

```mvn clean
mvn install wildfly:run
```

## Métodos de la API
### PUSH
Agrega mensajes al final de la cola de mensaje
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
