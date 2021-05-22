package cl.ionix.test.web;

import com.github.javafaker.Faker;

import cl.ionix.test.repository.entity.UserEntity;

public class PopulateUserTest {

	private Faker faker = Faker.instance();
	
	public UserEntity fillEntity() {
		UserEntity entity = new UserEntity();
		entity.setEmail(faker.internet().emailAddress());
		entity.setName(faker.name().fullName());
		entity.setPhone(faker.phoneNumber().phoneNumber());
		entity.setUsername(faker.name().username());
		return entity;
	}
}
