package rekrutacja.mateusz.koscielniak.project.lecture;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rekrutacja.mateusz.koscielniak.project.student.Student;
import rekrutacja.mateusz.koscielniak.project.student.StudentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/lectures")
@Api(value = "Lecture Management System")
public class LectureController {
   private final LectureService lectureService;
   private final StudentService studentService;
   @Autowired
   public LectureController(LectureService lectureService, StudentService studentService) {
      this.lectureService = lectureService;
      this.studentService = studentService;
   }
   @ApiOperation(value = "Create a new lecture", response = Lecture.class)
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully created the lecture"),
         @ApiResponse(code = 400, message = "Invalid request")
   })
   @PostMapping
   public Lecture createLecture(@RequestBody Lecture lecture) {
      return lectureService.saveLecture(lecture);
   }

   @ApiOperation(value = "Get students attending a lecture", response = Student.class, responseContainer = "List")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully retrieved students attending the lecture"),
         @ApiResponse(code = 404, message = "Lecture not found")
   })
   @GetMapping("/{lectureId}/students")
   public ResponseEntity<List<Student>> getStudentsAttendingLecture(@PathVariable Long lectureId) {
      Lecture lecture = lectureService.getLectureById(lectureId);
      if (lecture == null) {
         return ResponseEntity.notFound().build();
      }

      List<Student> studentsAttending = lecture.getStudents();
      return ResponseEntity.ok(studentsAttending);
   }

   @ApiOperation(value = "Get a lecture by ID", response = Lecture.class)
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully retrieved the lecture"),
         @ApiResponse(code = 404, message = "Lecture not found")
   })
   @GetMapping("/{id}")
   public Lecture getLecture(@PathVariable Long id) {
      return lectureService.getLectureById(id);
   }

   @ApiOperation(value = "Get all lectures", response = Lecture.class, responseContainer = "List")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully retrieved all lectures"),
   })
   @GetMapping
   public List<Lecture> getAllLectures() {
      return lectureService.getAllLectures();
   }

   @ApiOperation(value = "Enroll a student in a lecture")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully enrolled the student"),
         @ApiResponse(code = 400, message = "Invalid request")
   })
   @PostMapping("/{lectureId}/enroll/{studentId}")
   public ResponseEntity<String> enrollStudentInLecture(@PathVariable Long lectureId, @PathVariable Long studentId) {
      Lecture lecture = lectureService.getLectureById(lectureId);
      Student student = studentService.getStudentById(studentId);

      if (lecture == null || student == null) {
         return ResponseEntity.badRequest().body("Invalid lecture or student ID.");
      }

      lectureService.enrollStudentInLecture(student, lecture);
      return ResponseEntity.ok("Student enrolled in the lecture.");
   }

   @ApiOperation(value = "Remove a student from a lecture")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully removed the student from the lecture"),
         @ApiResponse(code = 400, message = "Invalid request"),
         @ApiResponse(code = 304, message = "Student was not enrolled in the lecture")
   })
   @DeleteMapping("/{lectureId}/remove/{studentId}")
   public ResponseEntity<String> removeStudentFromLecture(@PathVariable Long lectureId, @PathVariable Long studentId) {
      Lecture lecture = lectureService.getLectureById(lectureId);
      Student student = studentService.getStudentById(studentId);

      if (lecture == null || student == null) {
         return ResponseEntity.badRequest().body("Invalid lecture or student ID.");
      }

      boolean removed = lectureService.removeStudentFromLecture(student, lecture);

      if (removed) {
         return ResponseEntity.ok("Student removed from the lecture.");
      } else {
         return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Student was not enrolled in the lecture.");
      }
   }

   @ApiOperation(value = "Update a lecture by ID", response = Lecture.class)
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully updated the lecture"),
         @ApiResponse(code = 404, message = "Lecture not found")
   })
   @PutMapping("/{id}")
   public ResponseEntity<Lecture> updateLecture(@PathVariable Long id, @RequestBody Lecture updatedLecture) {
      Lecture lecture = lectureService.getLectureById(id);
      if (lecture != null) {
         updatedLecture.setLectureId(id); // Set the ID from the path variable to the updated lecture
         Lecture savedLecture = lectureService.saveLecture(updatedLecture);
         return ResponseEntity.ok(savedLecture);
      } else {
         return ResponseEntity.notFound().build();
      }
   }

   @ApiOperation(value = "Delete a lecture by ID")
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Successfully deleted the lecture"),
         @ApiResponse(code = 404, message = "Lecture not found")
   })
   @DeleteMapping("/{id}")
   public ResponseEntity<String> deleteLecture(@PathVariable Long id) {
      boolean deleted = lectureService.deleteLectureById(id);
      if (deleted) {
         return ResponseEntity.ok("Lecture with ID " + id + " deleted successfully.");
      } else {
         return ResponseEntity.notFound().build();
      }
   }

}
