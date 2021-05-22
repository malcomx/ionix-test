package cl.ionix.test.web.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import cl.ionix.test.web.exception.IonixWebException;
import cl.ionix.test.web.model.response.DataWebResponse;
import cl.ionix.test.web.utils.IonixResponseWebUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalHandlerException {

	@Setter
	@Autowired
	private IonixResponseWebUtils responseWebUtils;
	
	@ExceptionHandler(IonixWebException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<DataWebResponse> handleGracaException(IonixWebException ex, WebRequest request) {
		log.warn(ex.getMessage());
		return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
    @ExceptionHandler({
			ServerWebInputException.class,
			MethodNotAllowedException.class,
			MethodArgumentNotValidException.class,
			MissingServletRequestParameterException.class,
			MissingServletRequestParameterException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<DataWebResponse> handleBadRequest(Exception ex, WebRequest request){
    	log.error(ex.getMessage());
		return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<DataWebResponse> handleUnssuprtedMediaType(UnsupportedMediaTypeStatusException ex, WebRequest request){
    	log.error(ex.getMessage());
		return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildErrorResponse(ex.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

	@ExceptionHandler(ServerErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<DataWebResponse> handleServerError(RuntimeException ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<DataWebResponse> handleNotFound(Exception ex, WebRequest request) {
		log.error(ex.getMessage());
		return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}
}
