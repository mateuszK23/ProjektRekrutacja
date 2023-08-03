package rekrutacja.mateusz.koscielniak.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rekrutacja.mateusz.koscielniak.project.student.Student;
import rekrutacja.mateusz.koscielniak.project.student.StudentController;
import rekrutacja.mateusz.koscielniak.project.student.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerIntegrationTest {

   private MockMvc mockMvc;

   @Mock
   private StudentService studentService;

   @InjectMocks
   private StudentController studentController;

   @BeforeEach
   public void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
   }

   @Test
   public void testGetAllStudents() throws Exception {
      List<Student> students = new ArrayList<>();
      students.add(new Student("a", "a@mail.com"));
      students.add(new Student("b", "b@mail.com"));

      when(studentService.getAllStudents()).thenReturn(students);

      mockMvc.perform(get("/api/v1/students"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name").value("a"))
            .andExpect(jsonPath("$[0].email").value("a@mail.com"))
            .andExpect(jsonPath("$[1].name").value("b"))
            .andExpect(jsonPath("$[1].email").value("b@mail.com"));

      verify(studentService, times(1)).getAllStudents();
      verifyNoMoreInteractions(studentService);
   }

   @Test
   public void testGetStudentById() throws Exception {
      long studentId = 1L;
      Student student = new Student("a", "a@mail.com");
      student.setStudentId(studentId);

      when(studentService.getStudentById(studentId)).thenReturn(student);

      mockMvc.perform(get("/api/v1/students/{id}", studentId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.studentId").value(studentId))
            .andExpect(jsonPath("$.name").value("a"))
            .andExpect(jsonPath("$.email").value("a@mail.com"));

      verify(studentService, times(1)).getStudentById(studentId);
      verifyNoMoreInteractions(studentService);
   }

   @Test
   public void testGetStudentByIdNotFound() throws Exception {
      long studentId = 1L;
      when(studentService.getStudentById(studentId)).thenReturn(null);

      mockMvc.perform(get("/api/v1/students/{id}", studentId))
            .andExpect(status().isNotFound());

      verify(studentService, times(1)).getStudentById(studentId);
      verifyNoMoreInteractions(studentService);
   }

   @Test
   public void testAddStudent() throws Exception {
      Student newStudent = new Student("a", "a@mail.com");
      Student savedStudent = new Student("a", "a@mail.com");
      savedStudent.setStudentId(1L);

      when(studentService.addStudent(any(Student.class))).thenReturn(savedStudent);

      String requestBody = "{\"name\":\"a\",\"email\":\"a@mail.com\"}";
      RequestBuilder requestBuilder = post("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

      mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.studentId").value(1))
            .andExpect(jsonPath("$.name").value("a"))
            .andExpect(jsonPath("$.email").value("a@mail.com"));

      verify(studentService, times(1)).addStudent(any(Student.class));
      verifyNoMoreInteractions(studentService);
   }

   @Test
   public void testUpdateStudent() throws Exception {
      long studentId = 1L;
      Student existingStudent = new Student("a", "a@mail.com");
      existingStudent.setStudentId(studentId);

      Student updatedStudent = new Student("b", "b@mail.com");
      updatedStudent.setStudentId(studentId);

      when(studentService.getStudentById(studentId)).thenReturn(existingStudent);
      when(studentService.addStudent(any(Student.class))).thenReturn(updatedStudent);

      String requestBody = "{\"name\":\"b\",\"email\":\"b@mail.com\"}";
      RequestBuilder requestBuilder = put("/api/v1/students/{id}", studentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

      mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.studentId").value(studentId))
            .andExpect(jsonPath("$.name").value("b"))
            .andExpect(jsonPath("$.email").value("b@mail.com"));

      verify(studentService, times(1)).getStudentById(studentId);
      verify(studentService, times(1)).addStudent(any(Student.class));
      verifyNoMoreInteractions(studentService);
   }

   @Test
   public void testUpdateStudentNotFound() throws Exception {
      long studentId = 1L;
      when(studentService.getStudentById(studentId)).thenReturn(null);

      String requestBody = "{\"name\":\"b\",\"email\":\"b@mail.com\"}";
      RequestBuilder requestBuilder = put("/api/v1/students/{id}", studentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

      mockMvc.perform(requestBuilder)
            .andExpect(status().isNotFound());

      verify(studentService, times(1)).getStudentById(studentId);
      verifyNoMoreInteractions(studentService);
   }

   @Test
   public void testDeleteStudent() throws Exception {
      long studentId = 1L;

      when(studentService.deleteStudentById(studentId)).thenReturn(true);

      mockMvc.perform(delete("/api/v1/students/{id}", studentId))
            .andExpect(status().isOk())
            .andExpect(content().string("Student with ID " + studentId + " deleted successfully."));

      verify(studentService, times(1)).deleteStudentById(studentId);
      verifyNoMoreInteractions(studentService);
   }

   @Test
   public void testDeleteStudentNotFound() throws Exception {
      long studentId = 1L;

      when(studentService.deleteStudentById(studentId)).thenReturn(false);

      mockMvc.perform(delete("/api/v1/students/{id}", studentId))
            .andExpect(status().isNotFound());

      verify(studentService, times(1)).deleteStudentById(studentId);
      verifyNoMoreInteractions(studentService);
   }
}
