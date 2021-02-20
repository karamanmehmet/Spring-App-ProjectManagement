package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Role;
import com.cybertek.entity.User;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.repository.ProjectRepository;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.cybertek.util.Gender;
import com.cybertek.util.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Project Implementation Test Cases")
class ProjectServiceImplTest {

    @InjectMocks
    ProjectServiceImpl classToTest;

    @Mock
    UserService userService;

    @Mock
    TaskService taskService;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectMapper projectMapper;

    private Role roleManager;
    private RoleDTO roleManagerDTO;
    private User manager;
    private Project projectCompleted;
    private UserDTO managerDTO;
    private ProjectDTO projectCompletedDTO;
    private List<Project> projectList = new ArrayList<>();
    private List<ProjectDTO> projectDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //Default Value Assignment
        roleManager = new Role(2L,"Manager");

        roleManagerDTO = new RoleDTO(2L,"Manager");

        manager = new User("John", "Doe", "johndoe@gmail.com", "password", true, "9734256874",
                roleManager, Gender.Male);

        projectCompleted = new Project("PRJ-001", "Testing Project", manager, LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), Status.COMPLETED,
                "Project Detail");

        managerDTO = new UserDTO("John", "Doe", "johndoe@gmail.com", "password", true, "9734256874",
                roleManagerDTO, Gender.Male);

        projectCompletedDTO = new ProjectDTO("PRJ-001", "Testing Project", managerDTO, LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), Status.COMPLETED,
                "Project Detail");

        projectList.add(projectCompleted);
        projectList.add(projectCompleted);
        projectList.add(projectCompleted);
        projectList.add(projectCompleted);
        projectList.add(projectCompleted);

        projectDtoList.add(projectCompletedDTO);
        projectDtoList.add(projectCompletedDTO);
        projectDtoList.add(projectCompletedDTO);
        projectDtoList.add(projectCompletedDTO);
        projectDtoList.add(projectCompletedDTO);



    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getByProjectCode_success_Test() {

        //getByProjectCode_success_Test
        Mockito.when(projectRepository.getByCode("PRJ-001")).thenReturn(projectCompleted);
        Mockito.when(projectMapper.convertToDto(projectCompleted)).thenReturn(projectCompletedDTO);

        ProjectDTO projectDTO = classToTest.getByProjectCode("PRJ-001");

        Mockito.verify(projectRepository).getByCode(Mockito.anyString());
        Mockito.verify(projectMapper).convertToDto(Mockito.any(Project.class));

        assertEquals(projectDTO.getStatus(),Status.COMPLETED);
        assertNotNull(projectDTO);
    }

    @Test
    void getByProjectCode_exception_Test() {


        Mockito.when(projectRepository.getByCode("")).thenThrow(new RuntimeException("Project Not Found"));

        Throwable exception = assertThrows(RuntimeException.class,()->classToTest.getByProjectCode(""));

        Mockito.verify(projectRepository).getByCode(Mockito.anyString());

        assertEquals(exception.getMessage(),"Project Not Found");

    }

    @Test
    void listAllProjects_success_Test() {

        Mockito.when(projectRepository.findAll(Sort.by("code"))).thenReturn(projectList);
        Mockito.when(projectMapper.convertToDto(projectCompleted)).thenReturn(projectCompletedDTO);

        List<ProjectDTO> list = classToTest.listAllProjects();

        Mockito.verify(projectRepository).findAll(Sort.by("code"));

        assertNotNull(list);
        assertEquals(list.size(),5);
        assertIterableEquals(list,projectDtoList);
    }


    @Test
    @Disabled
    void listAllProjects_emtpy_Test() {



        Mockito.when(projectRepository.findAll(Sort.by("code"))).thenReturn(null);

        List<ProjectDTO> list = classToTest.listAllProjects();

        Mockito.verify(projectRepository).findAll(Sort.by("code"));

        assertNull(list);
        assertIterableEquals(list,null);
    }



    @Test
    void delete_success_Test() {

        Mockito.when(projectRepository.deleteProject("PRJ-001")).thenReturn(true);
        boolean isDeleted= classToTest.delete("PRJ-001");

        Mockito.verify(projectRepository).deleteProject(Mockito.anyString());
        assertTrue(isDeleted);
    }

    @Test
    void delete_fail_Test() {

        Mockito.when(projectRepository.deleteProject(null)).thenThrow(new RuntimeException("Project Not Found"));
        boolean isDeleted= classToTest.delete(null);

        Mockito.verify(projectRepository).deleteProject(null);
        assertFalse(isDeleted);
    }


}