package restfulcontrollers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.LoginScreen;
import entities.AbsenceEntry;
import entities.AnsarUser;
import entities.RecitationEntry;
import entities.Student;
import model.Persister;
import utilities.DateTimeUtility;
import utilities.ObjectChecker;

@RestController
@RequestMapping("/kotab")
public class MobileController {
	@PostMapping("/login")
	public Boolean login(@RequestBody Map<String, Object> body) {
		return LoginScreen.authenticate(body.get("username").toString(), body.get("password").toString()).succeeded();
	}

	@GetMapping("/{username}/students")
	public String getStudent(@PathVariable String username) {
		AnsarUser user = Persister.getSingleResult(
				"FROM " + AnsarUser.class.getSimpleName() + " WHERE userName=:userName",
				Persister.params("userName", username));
		List<Student> students = Persister.searchFor("SELECT s FROM Student s JOIN s.group g JOIN g.teacher t  "
				+ " WHERE t.code = " + user.getPersonCode());
		Map<String, Object> response = new HashMap<>();
		response.put("response", students);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "error: something went wrong";
	}

	@PostMapping("/addRecitationEntry")
	public void addRecitationEntry(@RequestBody Map<String, Object> body) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			RecitationEntry entry = mapper.readValue(ObjectChecker.toStringOrEmpty(body.get("entry")),
					RecitationEntry.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ;
	}
	@PostMapping("/addAbsenceEntry")
	public String addAbsenceEntry(@RequestBody Map<String, Object> body) {
		ObjectMapper mapper = new  ObjectMapper();
		Map<String, Object> response = (Map<String, Object>) body.get("response");
		/*
		 * try { System.out.println(body.get("response").toString()); response =
		 * mapper.readValue(body.get("response").toString(),new
		 * TypeReference<Map<String, Object>>(){}); } catch (JsonProcessingException e)
		 * { e.printStackTrace(); }
		 */
		AbsenceEntry entry = new AbsenceEntry();
		Student student = Persister.findById(Student.class, Long.valueOf(response.get("studentId").toString()));
		entry.setStudent(student);
		entry.setGroup(student.getGroup());
		entry.setCreationDate(DateTimeUtility.parseDateTime(response.get("creationDate").toString()).toLocalDate());
		
		
		return "";
	}
	@GetMapping()
	public String get() {
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> body = new HashMap<>();
		body.put("studentId",1);
		body.put("creationDate", Date.from(Instant.now()));
		response.put("response", body);
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		try {
			result = mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
