package rekrutacja.mateusz.koscielniak.project.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rekrutacja.mateusz.koscielniak.project.lecture.Lecture;
import rekrutacja.mateusz.koscielniak.project.lecture.LectureRepository;
import rekrutacja.mateusz.koscielniak.project.student.Student;
import rekrutacja.mateusz.koscielniak.project.student.StudentRepository;
import rekrutacja.mateusz.koscielniak.project.university.University;
import rekrutacja.mateusz.koscielniak.project.university.UniversityRepository;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
public class ProjectConfig {
   @Bean
   public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
   }
   @Bean
   CommandLineRunner commandLineRunner(
         UniversityRepository universityRepository,
         StudentRepository studentRepository,
         LectureRepository lectureRepository
   ) {
      return args -> {
         Student adam = new Student("Adam", "adam@gmail.com");
         Student bartek = new Student("Bartek", "bartek@gmail.com");
         Student mateusz = new Student("Mateusz", "mateusz@gmail.com");
         Student jacek = new Student("Jacek", "jacek@gmail.com");
         Student basia = new Student("Basia", "basia@gmail.com");
         Student emma = new Student("Emma", "emma@gmail.com");
         Student kasia = new Student("Kasia", "kasia@gmail.com");
         Student oliwia = new Student("Oliwia", "oliwia@gmail.com");

         University aberystwyth = new University("Aberystwyth University", "Aberystwyth");
         University politechnikaWarszawska = new University("Politechnika Warszawska", "Warszawa");

         aberystwyth.addStudent(adam);
         aberystwyth.addStudent(bartek);
         aberystwyth.addStudent(mateusz);
         aberystwyth.addStudent(jacek);

         politechnikaWarszawska.addStudent(basia);
         politechnikaWarszawska.addStudent(emma);
         politechnikaWarszawska.addStudent(kasia);
         politechnikaWarszawska.addStudent(oliwia);

         Lecture polski = new Lecture("Polski");
         Lecture angielski = new Lecture("Angielski");
         Lecture historia = new Lecture("Historia");
         Lecture fizyka = new Lecture("Fizyka");

         polski.addStudent(adam);
         polski.addStudent(bartek);

         angielski.addStudent(mateusz);
         angielski.addStudent(jacek);

         historia.addStudent(basia);
         historia.addStudent(emma);

         fizyka.addStudent(kasia);
         fizyka.addStudent(oliwia);

         aberystwyth.addLecture(polski);
         aberystwyth.addLecture(angielski);
         politechnikaWarszawska.addLecture(historia);
         politechnikaWarszawska.addLecture(fizyka);

         universityRepository.saveAll(List.of(aberystwyth, politechnikaWarszawska));
         studentRepository.saveAll(List.of(adam, bartek, mateusz, jacek, basia, emma, kasia, oliwia));
         lectureRepository.saveAll(List.of(polski, angielski, historia, fizyka));

      };
   }
}
