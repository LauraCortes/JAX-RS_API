package api;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

@Path("/push") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON)
public class Push {

	/**
	 * Método para agregar nuevos objetos a la cola 'queue'
	 * @param message que se va a incluir en la cola
	 * @return El mensaje que se incluyó e la cola o la excepción al intentarlo.
	 */
	@RolesAllowed("ADMIN")
	@POST
	public Response addMessage(Mensaje message) {
		try 
		{
			
			//Nueva conexión con el servidor Redis
			Jedis jedis = new Jedis("localhost");
			
			//Agrega al final de la cola el mensaje que llega por parámetro
			jedis.rpush("queue",message.getMessage());
			
			//Cierra la conexión con el servidor Redis
			jedis.close();
			
			//Construye la respuesta en formato JSON
			return Response.ok("{\"message\":\""+message.getMessage()+"\"}",MediaType.APPLICATION_JSON).build();
		}
		catch(JedisDataException e)
		{
			return Response.status(404).entity("{\"message\":\"Los atributos no están correctamente definidos.\"").type(MediaType.APPLICATION_JSON).build();
		}
		catch(Exception e2){
			return Response.status(500).entity(e2).type(MediaType.APPLICATION_JSON).build();
		}
	}
}
