package cl.ionix.test.web.manager;

import java.util.List;

import cl.ionix.test.repository.entity.UserEntity;
import cl.ionix.test.web.model.request.UserRequest;

public interface UserManager {

	UserEntity create(UserRequest request);
	List<UserEntity> getAllRegistered();
	UserEntity getByEmail(String email);
}
