package cl.ionix.test.repository.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "user")
@Table(name = "ionix_user")
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = -7210861395081168780L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Basic
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	@Basic
	@Column(name = "username", nullable = false, length = 30)
	private String username;
	@Basic
	@Column(name = "email", nullable = false, length = 40)
	private String email;
	@Basic
	@Column(name = "phone", nullable = true, length = 30)
	private String phone;
}
