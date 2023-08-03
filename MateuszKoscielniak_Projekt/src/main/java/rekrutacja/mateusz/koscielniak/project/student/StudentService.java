package rekrutacja.mateusz.koscielniak.project.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
   private final StudentRepository studentRepository;

   @Autowired
   public StudentService(StudentRepository studentRepository) {
      this.studentRepository = studentRepository;
   }

   public Student addStudent(Student student) {
      return studentRepository.save(student);
   }

   public List<Student> getAllStudents() {
      return studentRepository.findAll();
   }

   public Student getStudentById(Long id) {
      return studentRepository.findById(id).orElse(null);
   }

   public Student updateStudent(Student updatedStudent) {
      Student existingStudent = studentRepository.findById(updatedStudent.getStudentId()).orElse(null);
      if (existingStudent != null) {
         existingStudent.setName(updatedStudent.getName());
         existingStudent.setEmail(updatedStudent.getEmail());
         return studentRepository.save(existingStudent);
      }
      return null;
   }

   public boolean deleteStudentById(Long id) {
      if (studentRepository.existsById(id)) {
         studentRepository.deleteById(id);
         return true;
      }
      return false;
   }
}

