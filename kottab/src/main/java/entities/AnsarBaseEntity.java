package entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Nationalized;

@MappedSuperclass
public class AnsarBaseEntity {
	private Long id;
	private Long code;
	private String name;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true)
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	@Nationalized
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
