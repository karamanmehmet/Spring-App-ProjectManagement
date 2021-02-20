package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Role;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.ProjectRepository;
import com.cybertek.repository.TaskRepository;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.cybertek.util.Gender;
import com.cybertek.util.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeTags;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Task Implementation Test Cases")
class TaskServiceImplTest {

    TaskServiceImpl classToTest;

    TaskRepository taskRepository;

    UserRepository userRepository;

    UserMapper userMapper;

    ProjectMapper projectMapper;

    TaskMapper taskMapper;

    private Role roleManager;
    private RoleDTO roleManagerDTO;
    private User manager;
    private Project projectCompleted;
    private UserDTO managerDTO;
    private ProjectDTO projectCompletedDTO;

    private Task task;
    private TaskDTO taskDTO;


    @BeforeEach
    void setUp() {

        taskRepository = mock(TaskRepository.class);
        taskMapper=mock(TaskMapper.class);

        classToTest = new TaskServiceImpl(taskRepository,taskMapper,userMapper,projectMapper,userRepository);


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

        task = new Task("REST - GET API", "Product Rest Get Api should be completed",manager, projectCompleted, LocalDateTime.now().minusDays(5),
                LocalDateTime.now().plusDays(5), Status.COMPLETED);

        taskDTO = new TaskDTO(1,"REST - GET API", "Product Rest Get Api should be completed",managerDTO, projectCompletedDTO,managerDTO, LocalDateTime.now().minusDays(5),
                LocalDateTime.now().plusDays(5), Status.COMPLETED);


    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void findById_success_Test(){
        doReturn(Optional.of(task)).when(taskRepository).findById(1l);
        doReturn(taskDTO).when(taskMapper).convertToDto(any(Task.class));

        TaskDTO testObject= classToTest.findById(1);

        assertSame(testObject.getTitle(),"REST - GET API");
    }


    @RepeatedTest(3)
    public void findById_fail_Test(){
        doThrow(new RuntimeException("No Such Task Id")).when(taskRepository).findById(-1l);


        Throwable exception = assertThrows(RuntimeException.class,()->classToTest.findById(-1));


        verify(taskRepository).findById(anyLong());

        assertSame(exception.getMessage(),"No Such Task Id");

    }

    @ParameterizedTest
    @ValueSource(longs = {1l,2l,3l})
    public void findById_success_parametirized_Test(){


        doReturn(Optional.of(task)).when(taskRepository).findById(anyLong());
        doReturn(taskDTO).when(taskMapper).convertToDto(any(Task.class));

        TaskDTO testObject= classToTest.findById(1);

        assertSame(testObject.getTitle(),"REST - GET API");

    }



    @Test
    public void delete_void_success_Test() {

        doNothing().when(taskRepository).deleteById(isA(Long.class));

        classToTest.delete(1l);
        verify(taskRepository).deleteById(1l);

    }

}