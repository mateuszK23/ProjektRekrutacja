package rekrutacja.mateusz.koscielniak.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rekrutacja.mateusz.koscielniak.project.lecture.Lecture;
import rekrutacja.mateusz.koscielniak.project.student.Student;
import rekrutacja.mateusz.koscielniak.project.university.University;

import java.util.Arrays;
import java.util.List;

public class LectureTests {
   private Lecture lecture;
   private University university;

   @BeforeEach
   public void setUp() {
      lecture = new Lecture("Introduction to Computer Science");
      university = mock(University.class);
   }

   @Test
   public void testSetTitle() {
      String newTitle = "Data Structures and Algorithms";
      lecture.setTitle(newTitle);
      assertEquals(newTitle, lecture.getTitle());
   }

   @Test
   public void testGetUniversity() {
      lecture.setUniversity(university);
      assertEquals(university, lecture.getUniversity());
   }

   @Test
   public void testGetStudents() {
      Student student1 = new Student("A", "a@mail.com");
      Student student2 = new Student("B", "b@mail.com");
      List<Student> students = Arrays.asList(student1, student2);

      lecture.setStudents(students);
      assertEquals(students, lecture.getStudents());
   }

   @Test
   public void testGetLectureId() {
      Long lectureId = 12345L;
      lecture.setLectureId(lectureId);
      assertEquals(lectureId, lecture.getLectureId());
   }

   @Test
   public void testAddStudent() {
      Student student1 = new Student("A", "a@mail.com");
      Student student2 = new Student("B", "b@mail.com");

      lecture.addStudent(student1);
      lecture.addStudent(student2);

      assertTrue(lecture.getStudents().contains(student1));
      assertTrue(lecture.getStudents().contains(student2));
   }

   @Test
   public void testAddDuplicateStudent() {
      Student student1 = new Student("A", "a@mail.com");

      lecture.addStudent(student1);
      lecture.addStudent(student1);

      assertEquals(1, lecture.getStudents().size());
   }

   @Test
   public void testSetAndGetUniversity() {
      lecture.setUniversity(university);
      assertEquals(university, lecture.getUniversity());
   }
}