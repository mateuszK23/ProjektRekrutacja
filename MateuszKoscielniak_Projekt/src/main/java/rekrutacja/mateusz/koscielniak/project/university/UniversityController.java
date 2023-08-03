package rekrutacja.mateusz.koscielniak.project.university;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rekrutacja.mateusz.koscielniak.project.student.Student;

import java.util.List;
@RestController
@RequestMapping("api/v1/universities")
@Api(value = "University Management System")
public class UniversityController {
   private final UniversityService universityService;

   @Autowired
   public UniversityController(UniversityService universityService) {
      this.universityService = universityService;
   }

   @ApiOperation(value = "Create a new university", response = University.class)
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully created the university")
   })
   @PostMapping
   public ResponseEntity<University> createUniversity(@RequestBody University university) {
      University createdUniversity = universityService.saveUniversity(university);
      return ResponseEntity.ok(createdUniversity);
   }

   @ApiOperation(value = "Get a university by ID", response = University.class)
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully retrieved the university"),
         @ApiResponse(code = 404, message = "University not found")
   })
   @GetMapping("/{id}")
   public ResponseEntity<University> getUniversity(@PathVariable Long id) {
      University university = universityService.getUniversityById(id);
      if (university != null) {
         return ResponseEntity.ok(university);
      } else {
         return ResponseEntity.notFound().build();
      }
   }

   @ApiOperation(value = "Get all universities", response = University.class, responseContainer = "List")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully retrieved all universities")
   })
   @GetMapping
   public List<University> getAllUniversities() {
      return universityService.getAllUniversities();
   }

   @ApiOperation(value = "Get students by university ID", response = Student.class, responseContainer = "List")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully retrieved students by university ID"),
         @ApiResponse(code = 404, message = "University not found")
   })
   @GetMapping("/{id}/students")
   public ResponseEntity<List<Student>> getStudentsByUniversity(@PathVariable Long id) {
      University university = universityService.getUniversityById(id);
      if (university != null) {
         List<Student> students = university.getStudents();
         return ResponseEntity.ok(students);
      } else {
         return ResponseEntity.notFound().build();
      }
   }

   @ApiOperation(value = "Update a university by ID", response = University.class)
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully updated the university"),
         @ApiResponse(code = 404, message = "University not found")
   })
   @PutMapping("/{id}")
   public ResponseEntity<University> updateUniversity(@PathVariable Long id, @RequestBody University updatedUniversity) {
      University university = universityService.getUniversityById(id);
      if (university != null) {
         updatedUniversity.setUniversityId(id); // Set the ID from the path variable to the updated university
         University savedUniversity = universityService.saveUniversity(updatedUniversity);
         return ResponseEntity.ok(savedUniversity);
      } else {
         return ResponseEntity.notFound().build();
      }
   }

   @ApiOperation(value = "Delete a university by ID")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully deleted the university"),
         @ApiResponse(code = 404, message = "University not found")
   })
   @DeleteMapping("/{id}")
   public ResponseEntity<String> deleteUniversity(@PathVariable Long id) {
      boolean deleted = universityService.deleteUniversityById(id);
      if (deleted) {
         return ResponseEntity.ok("University with ID " + id + " deleted successfully.");
      } else {
         return ResponseEntity.notFound().build();
      }
   }
}

