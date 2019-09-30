package farrant.christopher.api.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerConfiguration 
	extends ResponseEntityExceptionHandler {
	
	@Autowired
	private Logger logger;
	
	@ExceptionHandler(value=AuthenticationException.class)
    private ResponseEntity<Object> handleAuthenticationException(
    		AuthenticationException ex,
    		WebRequest request) {
		
		return new ResponseEntity<Object>("", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value=ValueNotUniqueException.class)
    private ResponseEntity<Object> handleValueNotUniqueException(
    		ValueNotUniqueException ex,
    		WebRequest request) {
		
		return new ResponseEntity<Object>("", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=ReferencedByForeignKeyException.class)
    private ResponseEntity<Object> handleReferencedByForeignKeyException(
    		ReferencedByForeignKeyException ex,
    		WebRequest request) {
		
		return new ResponseEntity<Object>("", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value=NoSuchEntityException.class)
    private ResponseEntity<Object> handleNoSuchEntityException(
    		NoSuchEntityException ex,
    		WebRequest request) {
		
		Map<String, Object> body = new HashMap<>();
		body.put("id", ex.getId());
		body.put("type", ex.getType());
		logger.error("Error on request " + request, ex);
		return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
	}
}
