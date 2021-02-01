package api;

import javax.ws.rs.Consumes; 
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType; 
import javax.ws.rs.core.Response;

import redis.clients.jedis.Jedis; 

@Path("/count") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class Count {

	/**
	 * Método para consultar el tamaño de la cola 'queue' en el servidor Redis.
	 * @return El tamaño de la cola 'queue' o 0 si no existe.
	 */
	@GET  
    public Response sizeQueue() {  
		try {
			//Nueva conexión con el servidor Redis 
			Jedis jedis = new Jedis("localhost");
			
			//Obtener la cantidad de mensajes en la cola 'queue'
			Long sizeQueue=jedis.llen("queue");
			
			//Cerrar la conexión con el servidor Redis
			jedis.close();
			
			//Construye la respuesta en formato JSON
	        return Response.ok("{\"message\":"+sizeQueue+"}",MediaType.APPLICATION_JSON).build();   
		
		}
		catch(Exception e2){
			return Response.status(404).entity(e2).type(MediaType.APPLICATION_JSON).build();
		}
	} 
}
