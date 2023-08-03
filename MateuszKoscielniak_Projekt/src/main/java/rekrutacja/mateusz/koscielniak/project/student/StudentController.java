package rekrutacja.mateusz.koscielniak.project.student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "api/v1/students")
@Api(value = "Student Management System")
public class StudentController {
   private final StudentService studentService;

   @Autowired
   public StudentController(StudentService studentService) {
      this.studentService = studentService;
   }

   @ApiOperation(value = "Add a new student")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully added the student")
   })
   @PostMapping
   public ResponseEntity<Student> addStudent(@RequestBody Student student) {
      Student addedStudent = studentService.addStudent(student);
      return ResponseEntity.ok(addedStudent);
   }

   @ApiOperation(value = "Get a student by ID")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully retrieved the student"),
         @ApiResponse(code = 404, message = "Student not found")
   })
   @GetMapping("/{id}")
   public ResponseEntity<Student> getStudent(@PathVariable Long id) {
      Student student = studentService.getStudentById(id);
      if (student != null) {
         return ResponseEntity.ok(student);
      } else {
         return ResponseEntity.notFound().build();
      }
   }

   @ApiOperation(value = "Get all students")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully retrieved all students"),
   })
   @GetMapping
   public List<Student> getAllStudents() {
      return studentService.getAllStudents();
   }

   @ApiOperation(value = "Update a student by ID")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully updated the student"),
         @ApiResponse(code = 404, message = "Student not found")
   })
   @PutMapping("/{id}")
   public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
      Student student = studentService.getStudentById(id);
      if (student != null) {
         updatedStudent.setStudentId(id); // Set the ID from the path variable to the updated student
         Student savedStudent = studentService.addStudent(updatedStudent);
         return ResponseEntity.ok(savedStudent);
      } else {
         return ResponseEntity.notFound().build();
      }
   }

   @ApiOperation(value = "Delete a student by ID")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully deleted the student"),
         @ApiResponse(code = 404, message = "Student not found")
   })
   @DeleteMapping("/{id}")
   public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
      boolean deleted = studentService.deleteStudentById(id);
      if (deleted) {
         return ResponseEntity.ok("Student with ID " + id + " deleted successfully.");
      } else {
         return ResponseEntity.notFound().build();
      }
   }
}