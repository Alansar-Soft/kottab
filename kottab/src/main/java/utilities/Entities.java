package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.AnsarUser;
import entities.GroupLevel;
import entities.MemorizationGroup;
import entities.MemorizationTeacher;
import entities.Student;

public class Entities {
	public static final String MemorizationTeacher = MemorizationTeacher.class.getSimpleName();
	public static final String Student = Student.class.getSimpleName();
	public static final String MemorizationGroup = MemorizationGroup.class.getSimpleName();
	public static final String GroupLevel = GroupLevel.class.getSimpleName();
	public static final String AnsarUser = AnsarUser.class.getSimpleName();
	public static final List<String> PersonEntities = new ArrayList<>(Arrays.asList(MemorizationTeacher, Student));
}
