package beamer.net.hello.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.ibatis.exceptions.PersistenceException;

@Provider 
public class PersistenceMapper implements ExceptionMapper<PersistenceException> { 

  @Override 
  public Response toResponse(PersistenceException arg0) { 
    if(arg0.getCause() instanceof SQLIntegrityConstraintViolationException) {
      return Response.status(Response.Status.CONFLICT).entity("Duplicate Record").build(); 
    } else {
      return Response.status(Response.Status.BAD_REQUEST).entity("Persistence Exception: " + arg0.getMessage()).build();
    }
  } 

}