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

public class StudentTests {

   private Student student;
   private University university;

   @BeforeEach
   public void setUp() {
      student = new Student("A", "a@mail.com");
      university = mock(University.class);
   }

   @Test
   public void testSetName() {
      String newName = "A";
      student.setName(newName);
      assertEquals(newName, student.getName());
   }

   @Test
   public void testSetEmail() {
      String newEmail = "a@mail.com";
      student.setEmail(newEmail);
      assertEquals(newEmail, student.getEmail());
   }

   @Test
   public void testGetUniversity() {
      student.setUniversity(university);
      assertEquals(university, student.getUniversity());
   }

   @Test
   public void testGetLectures() {
      Lecture lecture1 = new Lecture("LectureA");
      Lecture lecture2 = new Lecture("LectureB");
      List<Lecture> lectures = Arrays.asList(lecture1, lecture2);

      student.setLectures(lectures);
      assertEquals(lectures, student.getLectures());
   }

   @Test
   public void testGetStudentId() {
      Long studentId = 12345L;
      student.setStudentId(studentId);
      assertEquals(studentId, student.getStudentId());
   }

   @Test
   public void testAddLecture() {
      Lecture lecture1 = new Lecture("LectureA");
      Lecture lecture2 = new Lecture("LectureB");

      student.addLecture(lecture1);
      student.addLecture(lecture2);

      assertTrue(student.getLectures().contains(lecture1));
      assertTrue(student.getLectures().contains(lecture2));
   }

   @Test
   public void testAddDuplicateLecture() {
      Lecture lecture1 = new Lecture("LectureA");

      student.addLecture(lecture1);
      student.addLecture(lecture1);

      assertEquals(1, student.getLectures().size());
   }

   @Test
   public void testSetAndGetUniversity() {
      student.setUniversity(university);
      assertEquals(university, student.getUniversity());
   }
}
