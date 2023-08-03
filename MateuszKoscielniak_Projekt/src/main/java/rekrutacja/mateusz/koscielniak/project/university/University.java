package rekrutacja.mateusz.koscielniak.project.university;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import rekrutacja.mateusz.koscielniak.project.lecture.Lecture;
import rekrutacja.mateusz.koscielniak.project.student.Student;

import java.util.ArrayList;
import java.util.List;
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "universityId")
public class University {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long universityId;

   private String name;
   private String address;

   // One-to-Many relationship with Lecture

   @OneToMany(mappedBy = "university")
   @JsonIgnoreProperties("university")
   private List<Lecture> lectures = new ArrayList<>();

   // One-to-Many relationship with Student

   @OneToMany(mappedBy = "university")
   @JsonIgnoreProperties("university")
   private List<Student> students = new ArrayList<>();;

   public University() {
   }

   public University(String name, String address) {
      this.name = name;
      this.address = address;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String location) {
      this.address = location;
   }

   public List<Lecture> getLectures() {
      return lectures;
   }

   public void setLectures(List<Lecture> lectures) {
      this.lectures = lectures;
   }

   public List<Student> getStudents() {
      return students;
   }

   public void setStudents(List<Student> students) {
      this.students = students;
   }

   public Long getUniversityId() {
      return universityId;
   }

   public void setUniversityId(Long universityId) {
      this.universityId = universityId;
   }

   public void addLecture(Lecture lecture){
      if(!lectures.contains(lecture)){
         lectures.add(lecture);
         lecture.setUniversity(this);
      }
   }

   public void addStudent(Student student){
      if(!students.contains(student)){
         students.add(student);
         student.setUniversity(this);
      }
   }
}
