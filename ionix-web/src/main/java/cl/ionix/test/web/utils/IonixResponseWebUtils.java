package cl.ionix.test.web.utils;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import cl.ionix.test.web.model.response.DataWebResponse;
import cl.ionix.test.web.model.response.ErrorResponse;

@Component
public class IonixResponseWebUtils {

	@Autowired
	private MessageSource messages;
	
	/**
	 * Configura la respuesta para los casos satisfactorios
	 * @param data - Representacion de los datos de respuesta del servicio
	 * @return 
	 */
	public DataWebResponse buildResponse(Object data) {
		return new DataWebResponse(data);
	}
	
	/**
	 * Configura la respuesta fallida via JSR-303
	 * @param errors - Objeto del error
	 * @return 
	 */
	public DataWebResponse buildErrorResponse(BindingResult errors) {
		try {
			return new DataWebResponse(-1, errors.getAllErrors()
				.stream()
				.flatMap(e -> Stream.of(
					new ErrorResponse(e.getDefaultMessage(), getMessageValue(e.getDefaultMessage(), e.getArguments()))))
				.collect(Collectors.toList()));
			
		} catch (Exception ex) {
			return new DataWebResponse(-1, "ERROR: Consulte con el administrador");
		}
	}
	
	/**
	 * Configura la respuesta fallida por obtencion del codigo del mensaje
	 * para ser resuelto via propiedades definidas del sistema
	 * @param message - Codigo del mensaje definido en el archivo de mensaje
	 * @return
	 */
	public DataWebResponse buildErrorResponse(String message) {
		return new DataWebResponse(-1, Arrays.asList(new ErrorResponse(message, getMessageValue(message, null))));
	}
	
	/**
	 * Obtiene la descripcion del mensaje en el Locale definido
	 * @param code - Codigo del mensaje
	 * @param arguments - Argumentos segun la definicion del mensaje
	 * @return
	 */
	private String getMessageValue(String code, Object[] arguments) {
		String message = StringUtils.EMPTY;
		try { 
			message = messages.getMessage(code, arguments, Locale.getDefault()); 
		}catch(Exception ex) {}
		
		return message;
	}
}
