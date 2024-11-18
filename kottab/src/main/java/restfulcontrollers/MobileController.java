package restfulcontrollers;

import application.LoginScreen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import entities.entries.*;
import entities.files.*;
import model.Persister;
import org.springframework.web.bind.annotation.*;
import utilities.*;

import java.io.IOException;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/kotab")
public class MobileController
{
    @PostMapping("/login")
    public Boolean login(@RequestBody Map<String, Object> body)
    {
        return LoginScreen.authenticate(body.get("username").toString(), body.get("password").toString()).succeeded();
    }

    @GetMapping("/{username}/students")
    public String getStudent(@PathVariable String username)
    {
        AnsarUser user = Persister.getSingleResult("FROM " + AnsarUser.class.getSimpleName() + " WHERE userName=:userName", Persister.params("userName", username));
        List<Student> students = Persister.searchFor("SELECT s FROM Student s JOIN s.group g JOIN g.teacher t  " + " WHERE t.code = " + user.getPersonCode());
        Map<String, Object> response = new HashMap<>();
        response.put("response", students);
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return "error: something went wrong";
    }

    @PostMapping("/addRecitationEntry")
    public void addRecitationEntry(@RequestBody Map<String, Object> body)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            RecitationEntry entry = mapper.readValue(ObjectChecker.toStringOrEmpty(body.get("entry")), RecitationEntry.class);
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return;
    }

    @PostMapping("/createAbsenceEntry")
    public String createAbsenceEntry(@RequestBody Map<String, Object> body)
    {
        Map<String, Object> response = (Map<String, Object>) body.get("response");
        AbsenceEntry entry = new AbsenceEntry();
        Student student = Persister.findById(Student.class, ((Integer) response.get("studentId")).longValue());
        entry.setStudent(student);
        entry.setGroup(student.getGroup());
        Persister.saveOrUpdate(entry);
        return "done";
    }

    @DeleteMapping("/deleteAbsenceEntry/{studentId}")
    public String deleteAbsenceEntry(@PathVariable Long studentId)
    {
        Persister.executeNativeQuery("Delete FROM " + AbsenceEntry.class.getSimpleName() + " WHERE student_id =:student And creationDate = :toDay", Persister.params("student", studentId, "toDay", LocalDate.now()));
        return "done";
    }

    @GetMapping(value = "/{userName}/surahs", produces = "application/json; charset=UTF-8")
    public Map<String, Object> getSurahs(@PathVariable String userName)
    {
        String queryString = "SELECT g FROM MemorizationGroup g JOIN g.teacher t JOIN AnsarUser u ON t.code = u" + ".userDetails.personCode WHERE u.userName = :user ";
        MemorizationGroup group = Persister.getSingleResult(queryString, Persister.params("user", userName));
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("recitationSurahs", SurahsUtil.fetchRecitationSurahsOfLevel(group.getGroupLevel()));
        responseBody.put("revisionSurahs", SurahsUtil.fetchRevisionSurahsOfLevel(group.getGroupLevel()));
        return responseBody;
    }

    @GetMapping("/recitation")
    public String fetchRecitation(@RequestBody Map<String, Object> body)
    {

        return "done";
    }

    static Map<String, Object> map = new HashMap<>();

    @PostMapping("/saveRecitation")
    public String saveRecitation(@RequestBody Map<String, Object> body) throws IOException
    {
        Long studentId = Long.valueOf(body.get("studentId").toString());
        Student student = Persister.findById(Student.class, studentId);
        RecitationEntry entry = Persister.getSingleResult("From " + RecitationEntry.class.getSimpleName() + " WHERE student = " + ":student AND creationDate = :creationDate", Persister.params("student", student, "creationDate", LocalDate.now()));
        if (entry == null) entry = new RecitationEntry();
        entry.setStudent(student);
        RecitationInfoWithGrade recitation = new RecitationInfoWithGrade();
        recitation.setFromSurah(SurahsUtil.fetchSurah(NumbersUtility.castToByte((Number) body.get("fromSurah"))));
        recitation.setFromAya(Short.valueOf(body.get("fromAya").toString()));
        recitation.setToSurah(SurahsUtil.fetchSurah(NumbersUtility.castToByte((Number) body.get("toSurah"))));
        recitation.setToAya(Short.valueOf(body.get("toAya").toString()));
        recitation.setGrade(Byte.valueOf(body.get("grade").toString()));
        entry.setRecitation(recitation);
        entry.setNextRecitation(RecitationUtil.calcNextRecitationInfo(recitation,
                student.getGroup().getGroupLevel().getDailyRecitationInVerses().intValue()));
        //-------------------------need to calculate next revision---------------------------//
        Persister.saveOrUpdate(entry);
        return "done";
    }

    @PostMapping("/saveRevision")
    public String saveRevision(@RequestBody Map<String, Object> body) throws IOException
    {
        Long studentId = Long.valueOf(body.get("studentId").toString());
        Student student = Persister.findById(Student.class, studentId);
        RecitationEntry entry = Persister.getSingleResult("From " + RecitationEntry.class.getSimpleName() + " WHERE student = " + ":student AND creationDate = :creationDate", Persister.params("student", student, "creationDate", LocalDate.now()));
        if (entry == null) entry = new RecitationEntry();
        entry.setStudent(student);
        RecitationInfoWithGrade revision = new RecitationInfoWithGrade();
        revision.setFromSurah(SurahsUtil.fetchSurah(NumbersUtility.castToByte((Number) body.get("fromSurah"))));
        revision.setFromAya(Short.valueOf(body.get("fromAya").toString()));
        revision.setToSurah(SurahsUtil.fetchSurah(NumbersUtility.castToByte((Number) body.get("toSurah"))));
        revision.setToAya(Short.valueOf(body.get("toAya").toString()));
        revision.setGrade(Byte.valueOf(body.get("grade").toString()));
        entry.setRevision(revision);
        entry.setNextRevision(RecitationUtil.calcNextRecitationInfo(revision,
                student.getGroup().getGroupLevel().getRevisionRecitationInVerses().intValue()));
        //-------------------------need to calculate next recitation---------------------------//
        Persister.saveOrUpdate(entry);
        return "done";
    }

    @GetMapping("/recitationEntry/{studentId}")
    public RecitationEntry fetchRecitationEntry(@PathVariable Long studentId)
    {
        Student st = Persister.findById(Student.class, studentId);
        RecitationEntry entry = RecitationUtil.createRecitationEntryForStudent(st);
        return entry;
    }

    @GetMapping()
    public String get()
    {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        body.put("studentId", 1);
        body.put("creationDate", Date.from(Instant.now()));
        response.put("response", body);
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try
        {
            result = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
