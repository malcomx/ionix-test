package cl.ionix.test.web.manager.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cl.ionix.test.repository.UserRepository;
import cl.ionix.test.repository.entity.UserEntity;
import cl.ionix.test.web.exception.IonixWebException;
import cl.ionix.test.web.manager.UserManager;
import cl.ionix.test.web.model.request.UserRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultUserManager implements UserManager {

	@Setter
	@Autowired
	private UserRepository userRepository;
	
	private ModelMapper mapper;
	
	@PostConstruct
	public void init() {
		this.mapper = new ModelMapper();
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public UserEntity create(UserRequest request) {
		try {
			// Mapper al entity
			UserEntity entity = mapper.map(request, UserEntity.class);
			
			return this.userRepository.save(entity);
			
		} catch (Exception ex) {
			throw new IonixWebException(String.format("No pudo registrar el usuario: %s - %s", request.getName(), ex.getMessage()));
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserEntity> getAllRegistered() {
		try {
			return this.userRepository.findAll();
			
		} catch (Exception ex) {
			throw new IonixWebException(String.format("No se pudo obtener lista de usuario %s", ex.getMessage()));
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserEntity getByEmail(String email) {
		try {
			// Consulta el usuario
			UserEntity entity = this.userRepository.findByEmail(email);
			// Verifica que exista
			if(null == entity) {
				log.warn("Email no se encuentra registrado en el sistema");
				throw new IonixWebException("user.email.not-exists");
			}
			
			return entity;
			
		} catch (IonixWebException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IonixWebException(String.format("No se pudo obtener usuario x email (%s) - %s", email, ex.getMessage()));
		}
	}

}
