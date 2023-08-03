package rekrutacja.mateusz.koscielniak.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rekrutacja.mateusz.koscielniak.project.lecture.Lecture;
import rekrutacja.mateusz.koscielniak.project.student.Student;
import rekrutacja.mateusz.koscielniak.project.university.University;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class UniversityTests {

   private University university;
   private Lecture lecture;
   private Student student;

   @BeforeEach
   public void setUp() {
      university = new University("University", "Address");
      lecture = mock(Lecture.class);
      student = mock(Student.class);
   }

   @Test
   public void testSetName() {
      String newName = "UniversityA";
      university.setName(newName);
      assertEquals(newName, university.getName());
   }

   @Test
   public void testSetAddress() {
      String newAddress = "Address";
      university.setAddress(newAddress);
      assertEquals(newAddress, university.getAddress());
   }

   @Test
   public void testGetLectures() {
      Lecture lecture1 = new Lecture("LectureA");
      Lecture lecture2 = new Lecture("LectureB");
      List<Lecture> lectures = Arrays.asList(lecture1, lecture2);

      university.setLectures(lectures);
      assertEquals(lectures, university.getLectures());
   }

   @Test
   public void testGetStudents() {
      Student student1 = new Student("A", "a@mail.com");
      Student student2 = new Student("B", "b@mail.com");
      List<Student> students = Arrays.asList(student1, student2);

      university.setStudents(students);
      assertEquals(students, university.getStudents());
   }

   @Test
   public void testGetUniversityId() {
      Long universityId = 12345L;
      university.setUniversityId(universityId);
      assertEquals(universityId, university.getUniversityId());
   }

   @Test
   public void testAddLecture() {
      university.addLecture(lecture);
      assertTrue(university.getLectures().contains(lecture));
      assertEquals(university, lecture.getUniversity());
   }

   @Test
   public void testAddDuplicateLecture() {
      university.addLecture(lecture);
      university.addLecture(lecture);
      assertEquals(1, university.getLectures().size());
   }

   @Test
   public void testAddStudent() {
      university.addStudent(student);
      assertTrue(university.getStudents().contains(student));
      assertEquals(university, student.getUniversity());
   }

   @Test
   public void testAddDuplicateStudent() {
      university.addStudent(student);
      university.addStudent(student);
      assertEquals(1, university.getStudents().size());
   }
}
