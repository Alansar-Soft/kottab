package entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import utilities.IEntity;
import utilities.ObjectChecker;
import utilities.Result;

@MappedSuperclass
@JsonIncludeProperties(value = { "id", "code", "name" })
public abstract class AnsarBaseEntity implements IEntity {
	private Long id;
	private String code;
	private String name;
	private LocalDateTime creationDate;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Nationalized
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	@Transient
	public Result isValidForCommit() {
		Result result = new Result();
		if (ObjectChecker.isEmptyOrZeroOrNull(name))
			result.accmulate(Result.createFailureResult("You must enter name"));
		return result;
	}

	@Override
	public Result postCommit() {
		return new Result();
	}
}
