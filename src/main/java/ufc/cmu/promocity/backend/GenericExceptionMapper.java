package ufc.cmu.promocity.backend;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Excecao Generica do Mapper para Response do Servico Restful
 * @author armandosoaressousa
 *
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		return Response.serverError().entity(exception.getMessage()).build();
	}

}