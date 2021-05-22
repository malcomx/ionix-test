package cl.ionix.test.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;

import cl.ionix.test.repository.entity.UserEntity;
import cl.ionix.test.web.model.request.UserRequest;

public class PopulateUserTest {

	private Faker faker = Faker.instance();
	private ObjectWriter writer;
	
	public PopulateUserTest() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		writer = mapper.writer().withDefaultPrettyPrinter();
	}
	
	public UserEntity fillEntity() {
		UserEntity entity = new UserEntity();
		entity.setEmail(faker.internet().emailAddress());
		entity.setName(faker.name().fullName());
		entity.setPhone(faker.phoneNumber().phoneNumber());
		entity.setUsername(faker.name().username());
		return entity;
	}
	
	public UserRequest fillRequest() {
		UserRequest request = new UserRequest();
		request.setEmail(faker.internet().emailAddress());
		request.setName(faker.name().fullName());
		request.setPhone(faker.phoneNumber().phoneNumber());
		request.setUsername(faker.name().username());
		return request;
	}
	
	public String getStringObject() throws JsonProcessingException {
		return writer.writeValueAsString(this.fillRequest());
	}
	
	public String getStringObject(UserRequest request) throws JsonProcessingException {
		return writer.writeValueAsString(request);
	}
}
