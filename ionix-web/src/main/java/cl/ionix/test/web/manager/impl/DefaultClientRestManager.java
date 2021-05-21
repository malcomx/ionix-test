package cl.ionix.test.web.manager.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.ionix.test.web.exception.IonixWebException;
import cl.ionix.test.web.manager.ClientRestManager;
import cl.ionix.test.web.model.response.ExternalResponse;
import cl.ionix.test.web.utils.IonixSecurityUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultClientRestManager implements ClientRestManager {

	private static final String URI = "https://sandbox.ionix.cl/test-tecnico/search?rut=PARAM";
	private static final String KEY = "ionix123456";
	
	@Setter
	@Autowired
	private RestTemplate restTemplate;

	@Setter
	@Autowired
	private IonixSecurityUtils securityUtils;
	
	@Override
	public int getTestTecnico(String param) {
		try {
			String encrypted = securityUtils.cipher(param, KEY);
			String endPoint = StringUtils.replace(URI, "PARAM", encrypted);
			log.info("Consultando servicio: {}", endPoint);

			ExternalResponse response = restTemplate.getForObject(endPoint, ExternalResponse.class);
			if(null == response) {
				throw new IonixWebException("external.service.response");
			}
			int count = Optional.ofNullable(response.getResult()).map(r -> r.getItems()).get().size();
			
			return count;
			
		} catch (IonixWebException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IonixWebException(String.format("Error consultando el servicio: %s", ex.getMessage()));
		}
	}

}
