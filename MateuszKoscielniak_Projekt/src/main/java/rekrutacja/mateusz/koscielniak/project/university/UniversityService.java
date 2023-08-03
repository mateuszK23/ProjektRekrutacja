package rekrutacja.mateusz.koscielniak.project.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {
   private final UniversityRepository universityRepository;
   @Autowired
   public UniversityService(UniversityRepository universityRepository) {
      this.universityRepository = universityRepository;
   }

   public University saveUniversity(University university) {
      return universityRepository.save(university);
   }

   public List<University> getAllUniversities() {
      return universityRepository.findAll();
   }

   public University getUniversityById(Long id) {
      return universityRepository.findById(id).orElse(null);
   }

   public University updateUniversity(University updatedUniversity) {
      University existingUniversity = universityRepository.findById(updatedUniversity.getUniversityId()).orElse(null);
      if (existingUniversity != null) {
         existingUniversity.setName(updatedUniversity.getName());
         existingUniversity.setAddress(updatedUniversity.getAddress());
         existingUniversity.setLectures(updatedUniversity.getLectures());
         existingUniversity.setStudents(updatedUniversity.getStudents());
         return universityRepository.save(existingUniversity);
      }
      return null;
   }

   public boolean deleteUniversityById(Long id) {
      if (universityRepository.existsById(id)) {
         universityRepository.deleteById(id);
         return true;
      }
      return false;
   }
}

