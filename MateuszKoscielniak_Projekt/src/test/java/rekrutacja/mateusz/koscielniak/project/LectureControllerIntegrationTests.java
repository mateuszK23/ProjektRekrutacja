package rekrutacja.mateusz.koscielniak.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rekrutacja.mateusz.koscielniak.project.lecture.Lecture;
import rekrutacja.mateusz.koscielniak.project.lecture.LectureRepository;
import rekrutacja.mateusz.koscielniak.project.student.Student;
import rekrutacja.mateusz.koscielniak.project.student.StudentRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureControllerIntegrationTests {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private LectureRepository lectureRepository;

   @Autowired
   private StudentRepository studentRepository;

   @BeforeEach
   public void setUp() {
      lectureRepository.deleteAll(); // Clear the database before each test
   }

   @Test
   public void testCreateLecture() throws Exception {
      Lecture lecture = new Lecture();
      lecture.setTitle("Introduction to Computer Science");

      mockMvc.perform(post("/api/v1/lectures")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(asJsonString(lecture)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Introduction to Computer Science"));
   }

   @Test
   public void testGetLectureById() throws Exception {
      Lecture lecture = new Lecture();
      lecture.setTitle("Data Structures and Algorithms");
      lecture = lectureRepository.save(lecture);

      mockMvc.perform(get("/api/v1/lectures/{id}", lecture.getLectureId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Data Structures and Algorithms"));
   }

   @Test
   public void testEnrollStudentInLecture() throws Exception {
      Lecture lecture = new Lecture();
      lecture.setTitle("Introduction to Computer Science");
      lecture = lectureRepository.save(lecture);

      Student student = new Student();
      student.setName("John Doe");
      student.setEmail("john.doe@example.com");
      student = studentRepository.save(student);

      mockMvc.perform(post("/api/v1/lectures/{lectureId}/enroll/{studentId}", lecture.getLectureId(), student.getStudentId()))
            .andExpect(status().isOk())
            .andExpect(content().string("Student enrolled in the lecture."));
   }

   @Test
   public void testRemoveStudentFromLecture() throws Exception {
      Lecture lecture = new Lecture();
      lecture.setTitle("Introduction to Computer Science");
      lecture = lectureRepository.save(lecture);

      Student student = new Student();
      student.setName("John Doe");
      student.setEmail("john.doe@example.com");
      student = studentRepository.save(student);

      lecture.addStudent(student);
      lecture = lectureRepository.save(lecture);

      mockMvc.perform(delete("/api/v1/lectures/{lectureId}/remove/{studentId}", lecture.getLectureId(), student.getStudentId()))
            .andExpect(status().isOk())
            .andExpect(content().string("Student removed from the lecture."));
   }

   // Helper method to convert an object to JSON string
   private static String asJsonString(Object obj) {
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         return objectMapper.writeValueAsString(obj);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
