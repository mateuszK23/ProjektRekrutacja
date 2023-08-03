package rekrutacja.mateusz.koscielniak.project.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rekrutacja.mateusz.koscielniak.project.student.Student;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {

   private final LectureRepository lectureRepository;
   @Autowired
   public LectureService(LectureRepository lectureRepository) {
      this.lectureRepository = lectureRepository;
   }

   public Lecture saveLecture(Lecture lecture) {
      return lectureRepository.save(lecture);
   }

   public Lecture getLectureById(Long id) {
      return lectureRepository.findById(id).orElse(null);
   }

   public boolean deleteLectureById(Long id) {
      Optional<Lecture> optionalLecture = lectureRepository.findById(id);
      if (optionalLecture.isPresent()) {
         Lecture lecture = optionalLecture.get();
         lectureRepository.delete(lecture);
         return true;
      } else {
         return false;
      }
   }

   public List<Lecture> getAllLectures() {
      return lectureRepository.findAll();
   }

   public void enrollStudentInLecture(Student student, Lecture lecture) {
      lecture.addStudent(student);
      lectureRepository.save(lecture);
   }

   public boolean removeStudentFromLecture(Student student, Lecture lecture) {
      if (lecture.getStudents().contains(student)) {
         lecture.getStudents().remove(student);
         lectureRepository.save(lecture);
         return true;
      } else {
         return false;
      }
   }
}
