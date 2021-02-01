package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import redis.clients.jedis.Jedis;

@Path("/pop") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class Pop {

	/**
	 * Método para obtener el primer mensaje de la cola 'queue'
	 * @return El primer mensaje, si la cola esta vacía retorna status 500.
	 */
	@POST
	public Response getMessage() {
		try {
			//Nueva conexión con el servidor Redis
			Jedis jedis = new Jedis("localhost");
			
			//Extrae el primer elemento de la cola 'queue'
			String firstItem=jedis.lpop("queue");
			
			//Para validar que no sea un objeto nulo
			firstItem.chars();
			
			//Cierra la conexión con el servidor Redis
			jedis.close();
			
			//Construye la respuesta en formato JSON
			return Response.ok("{\"message\":\""+firstItem+"\"}",MediaType.APPLICATION_JSON).build(); 
		}
		catch(Exception e2){
			return Response.status(500).entity("No hay elementos en la cola.").type(MediaType.APPLICATION_JSON).build();
		}
	}
}
