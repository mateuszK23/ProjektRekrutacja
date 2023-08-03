package rekrutacja.mateusz.koscielniak.project.student;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import rekrutacja.mateusz.koscielniak.project.lecture.Lecture;
import rekrutacja.mateusz.koscielniak.project.university.University;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentId")

public class Student {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long studentId;

   private String name;
   private String email;

   // Many-to-One relationship with University
   @ManyToOne
   @JoinColumn(name = "university_id")
   private University university;

   // Many-to-Many relationship with Lecture
   @ManyToMany(mappedBy = "students")
   private List<Lecture> lectures = new ArrayList<>();

   public Student(String name, String email) {
      this.name = name;
      this.email = email;
   }

   public Student() {
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public University getUniversity() {
      return university;
   }

   public void setUniversity(University university) {
      this.university = university;
   }

   public List<Lecture> getLectures() {
      return lectures;
   }

   public void setLectures(List<Lecture> lectures) {
      this.lectures = lectures;
   }

   public void addLecture(Lecture lecture){
      if(!lectures.contains(lecture)){
         lectures.add(lecture);
      }
   }
   public Long getStudentId() {
      return studentId;
   }

   public void setStudentId(Long studentId) {
      this.studentId = studentId;
   }
}
