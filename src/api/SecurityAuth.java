package api;



import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.util.Base64;


@Provider
@ServerInterceptor
public class SecurityAuth implements PreProcessInterceptor{


	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethodInvoker method)
			throws Failure, WebApplicationException {
		//Encabezados de la solicitud
        final HttpHeaders headers = request.getHttpHeaders();
         
        //Encabezado de autorización
        final List<String> authorization = headers.getRequestHeader("Authorization");
         
        //Si no hay información de autorización presente el acceso es denegado.
        if(authorization == null || authorization.isEmpty())
        {
            return new ServerResponse("\"Ingrese información de autenticación.\"", 401, new Headers<Object>());
        }
		
        //Obtener el usuario y contraseña codificado
        final String encodedUserPassword = authorization.get(0).replaceFirst("Basic ", "");
         
        //Decodificar el usuario y contraseña
        String usernameAndPassword;
        try {
            usernameAndPassword = new String(Base64.decode(encodedUserPassword));
        } catch (IOException e) {
            return new ServerResponse("\"INTERNAL SERVER ERROR\"", 500, new Headers<Object>());
        }
        
        //Dividir el nombre de usuario y contraseña
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
		
        //Obtener roles en el encabezado
        Method methodR = method.getMethod();
        RolesAllowed rolesAnnotation = methodR.getAnnotation(RolesAllowed.class);
        Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

        //Validación que los valores sean los esperados
        if(rolesSet.contains("ADMIN")&&username.equals("admin")&&password.equals("admin")) {
        	return null;
        }
        else {
        	return new ServerResponse("\"Acceso no autorizado al servicio.\"", 403, new Headers<Object>());
        }
	}

}
