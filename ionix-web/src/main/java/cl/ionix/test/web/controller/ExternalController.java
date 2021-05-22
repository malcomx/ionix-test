package cl.ionix.test.web.controller;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ionix.test.web.exception.IonixWebException;
import cl.ionix.test.web.manager.ClientRestManager;
import cl.ionix.test.web.model.response.DataWebResponse;
import cl.ionix.test.web.utils.IonixResponseWebUtils;
import lombok.Setter;

@RestController
@RequestMapping(value = "/externals")
public class ExternalController {

	@Setter
	@Autowired
	private IonixResponseWebUtils responseWebUtils;
	@Setter
	@Autowired
	private ClientRestManager clientRest;

	@CrossOrigin
	@PostMapping
	public ResponseEntity<DataWebResponse> invoke(@RequestParam(name = "param") String param) {
		try {
			if(StringUtils.isBlank(param)) {
				throw new IonixWebException("external.param.required");
			}
			int count = this.clientRest.getTestTecnico(param);
			return new ResponseEntity<DataWebResponse>(
				this.responseWebUtils.buildResponse(
					Collections.singletonMap("registerCount", count)), HttpStatus.OK);
			
		} catch (IonixWebException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IonixWebException("--DEFINIR--", ex);
		}
	}
}
