package rekrutacja.mateusz.koscielniak.project.lecture;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import rekrutacja.mateusz.koscielniak.project.student.Student;
import rekrutacja.mateusz.koscielniak.project.university.University;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "lectureId")
public class Lecture {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long lectureId;
   private String title;


   // Many-to-One relationship with University
   @ManyToOne
   @JoinColumn(name = "university_id")
   private University university;

   // Many-to-Many relationship with Student
   @ManyToMany
   @JoinTable(
         name = "lecture_student",
         joinColumns = @JoinColumn(name = "lecture_id"),
         inverseJoinColumns = @JoinColumn(name = "student_id")
   )
   private List<Student> students = new ArrayList<>();;


   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public University getUniversity() {
      return university;
   }

   public Lecture() {
   }

   public Lecture(String title) {
      this.title = title;
   }

   public void setUniversity(University university) {
      this.university = university;
   }

   public List<Student> getStudents() {
      return students;
   }

   public void setStudents(List<Student> students) {
      this.students = students;
   }

   public Long getLectureId() {
      return lectureId;
   }

   public void setLectureId(Long lectureId) {
      this.lectureId = lectureId;
   }

   public void addStudent(Student student){
      if(!students.contains((student))){
         students.add(student);
      }
   }
}
