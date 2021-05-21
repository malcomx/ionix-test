package cl.ionix.test.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ionix.test.repository.entity.UserEntity;
import cl.ionix.test.web.exception.IonixWebException;
import cl.ionix.test.web.manager.UserManager;
import cl.ionix.test.web.model.request.UserRequest;
import cl.ionix.test.web.model.response.DataWebResponse;
import cl.ionix.test.web.utils.IonixResponseWebUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Setter
	@Autowired
	private IonixResponseWebUtils responseWebUtils;
	
	@Setter
	@Autowired
	private UserManager usermanager;
	
	private EmailValidator emailValidator = EmailValidator.getInstance();

	@PostMapping
	public ResponseEntity<DataWebResponse> create(@Valid @RequestBody UserRequest payload, BindingResult errors) {
		log.info("Creando usuario: {}", payload.toString());
		try {
			if(errors.hasErrors()) {
				return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildErrorResponse(errors), HttpStatus.OK);
			}
			
			UserEntity user = this.usermanager.create(payload);
			
			return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildResponse(user), HttpStatus.OK);
			
		} catch (IonixWebException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IonixWebException("--DEFINIR--", ex);
		}
	}
	
	@GetMapping
	public ResponseEntity<DataWebResponse> findAll() {
		try {
			List<UserEntity> users = this.usermanager.getAllRegistered();
			
			return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildResponse(users), HttpStatus.OK);
			
		} catch (IonixWebException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IonixWebException("--DEFINIR--", ex);
		}
	}
	
	@GetMapping(value = "/{email}")
	public ResponseEntity<DataWebResponse> findByEmail(@PathVariable(name = "email", required = true) String email){//, @Valid Object model, BindingResult errors) {
		try {
			if(!emailValidator.isValid(email)) {
				throw new IonixWebException("user.email.not-valid");
			}
			
			UserEntity user = this.usermanager.getByEmail(email);
			
			return new ResponseEntity<DataWebResponse>(this.responseWebUtils.buildResponse(user), HttpStatus.OK);
			
		} catch (IonixWebException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IonixWebException("--DEFINIR--", ex);
		}
	}
}
