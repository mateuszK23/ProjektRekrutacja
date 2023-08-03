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
import rekrutacja.mateusz.koscielniak.project.university.University;
import rekrutacja.mateusz.koscielniak.project.university.UniversityController;
import rekrutacja.mateusz.koscielniak.project.university.UniversityService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UniversityControllerIntegrationTest {

   private MockMvc mockMvc;

   @Mock
   private UniversityService universityService;

   @InjectMocks
   private UniversityController universityController;

   @BeforeEach
   public void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(universityController).build();
   }

   @Test
   public void testGetAllUniversities() throws Exception {
      List<University> universities = new ArrayList<>();
      universities.add(new University("University A", "Address A"));
      universities.add(new University("University B", "Address B"));

      when(universityService.getAllUniversities()).thenReturn(universities);

      mockMvc.perform(get("/api/v1/universities"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name").value("University A"))
            .andExpect(jsonPath("$[0].address").value("Address A"))
            .andExpect(jsonPath("$[1].name").value("University B"))
            .andExpect(jsonPath("$[1].address").value("Address B"));

      verify(universityService, times(1)).getAllUniversities();
      verifyNoMoreInteractions(universityService);
   }

   @Test
   public void testGetUniversityById() throws Exception {
      long universityId = 1L;
      University university = new University("University A", "Address A");
      university.setUniversityId(universityId);

      when(universityService.getUniversityById(universityId)).thenReturn(university);

      mockMvc.perform(get("/api/v1/universities/{id}", universityId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.universityId").value(universityId))
            .andExpect(jsonPath("$.name").value("University A"))
            .andExpect(jsonPath("$.address").value("Address A"));

      verify(universityService, times(1)).getUniversityById(universityId);
      verifyNoMoreInteractions(universityService);
   }

   @Test
   public void testGetUniversityByIdNotFound() throws Exception {
      long universityId = 1L;
      when(universityService.getUniversityById(universityId)).thenReturn(null);

      mockMvc.perform(get("/api/v1/universities/{id}", universityId))
            .andExpect(status().isNotFound());

      verify(universityService, times(1)).getUniversityById(universityId);
      verifyNoMoreInteractions(universityService);
   }

   @Test
   public void testAddUniversity() throws Exception {
      University newUniversity = new University("University A", "Address A");
      University savedUniversity = new University("University A", "Address A");
      savedUniversity.setUniversityId(1L);

      when(universityService.saveUniversity(any(University.class))).thenReturn(savedUniversity);

      String requestBody = "{\"name\":\"University A\",\"address\":\"Address A\"}";
      RequestBuilder requestBuilder = post("/api/v1/universities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

      mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.universityId").value(1))
            .andExpect(jsonPath("$.name").value("University A"))
            .andExpect(jsonPath("$.address").value("Address A"));

      verify(universityService, times(1)).saveUniversity(any(University.class));
      verifyNoMoreInteractions(universityService);
   }

   @Test
   public void testUpdateUniversity() throws Exception {
      long universityId = 1L;
      University existingUniversity = new University("University A", "Address A");
      existingUniversity.setUniversityId(universityId);

      University updatedUniversity = new University("University B", "Address B");
      updatedUniversity.setUniversityId(universityId);

      when(universityService.getUniversityById(universityId)).thenReturn(existingUniversity);
      when(universityService.saveUniversity(any(University.class))).thenReturn(updatedUniversity);

      String requestBody = "{\"name\":\"University B\",\"address\":\"Address B\"}";
      RequestBuilder requestBuilder = put("/api/v1/universities/{id}", universityId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

      mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.universityId").value(universityId))
            .andExpect(jsonPath("$.name").value("University B"))
            .andExpect(jsonPath("$.address").value("Address B"));

      verify(universityService, times(1)).getUniversityById(universityId);
      verify(universityService, times(1)).saveUniversity(any(University.class));
      verifyNoMoreInteractions(universityService);
   }

   @Test
   public void testUpdateUniversityNotFound() throws Exception {
      long universityId = 1L;
      when(universityService.getUniversityById(universityId)).thenReturn(null);

      String requestBody = "{\"name\":\"University B\",\"address\":\"Address B\"}";
      RequestBuilder requestBuilder = put("/api/v1/universities/{id}", universityId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

      mockMvc.perform(requestBuilder)
            .andExpect(status().isNotFound());

      verify(universityService, times(1)).getUniversityById(universityId);
      verifyNoMoreInteractions(universityService);
   }

   @Test
   public void testDeleteUniversity() throws Exception {
      long universityId = 1L;

      when(universityService.deleteUniversityById(universityId)).thenReturn(true);

      mockMvc.perform(delete("/api/v1/universities/{id}", universityId))
            .andExpect(status().isOk())
            .andExpect(content().string("University with ID " + universityId + " deleted successfully."));

      verify(universityService, times(1)).deleteUniversityById(universityId);
      verifyNoMoreInteractions(universityService);
   }

   @Test
   public void testDeleteUniversityNotFound() throws Exception {
      long universityId = 1L;

      when(universityService.deleteUniversityById(universityId)).thenReturn(false);

      mockMvc.perform(delete("/api/v1/universities/{id}", universityId))
            .andExpect(status().isNotFound());

      verify(universityService, times(1)).deleteUniversityById(universityId);
      verifyNoMoreInteractions(universityService);
   }
}
