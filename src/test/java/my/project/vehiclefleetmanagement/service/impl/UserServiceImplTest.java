package my.project.vehiclefleetmanagement.service.impl;

import my.project.vehiclefleetmanagement.config.UserMapper;
import my.project.vehiclefleetmanagement.exceptions.AppException;
import my.project.vehiclefleetmanagement.model.dtos.user.*;
import my.project.vehiclefleetmanagement.model.entity.user.UserEntity;
import my.project.vehiclefleetmanagement.model.entity.user.UserRole;
import my.project.vehiclefleetmanagement.model.enums.UserRoleEnum;
import my.project.vehiclefleetmanagement.repository.UserRepository;
import my.project.vehiclefleetmanagement.repository.UserRolesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl toTest;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserMapper mockUserMapper;
    @Mock
    private UserRolesRepository mockUserRolesRepository;


    @BeforeEach
    void setUp() {
        toTest = new UserServiceImpl(
                mockPasswordEncoder,
                mockUserRepository,
                mockUserRolesRepository,
                mockUserMapper,
                new ModelMapper());
    }

    @Test
    void testUserRegistration() {
        SignUpDto signUpDto = new SignUpDto("Pesho", "1234", "1234", "pesho@example.com");

        when(mockPasswordEncoder.encode(signUpDto.getPassword()))
                .thenReturn(signUpDto.getPassword() + signUpDto.getPassword());
        when(mockUserMapper.signUpToUser(signUpDto))
                .thenReturn(new ModelMapper().map(signUpDto, UserEntity.class));
        when(mockUserRolesRepository.findById(1L))
                .thenReturn(Optional.of(new UserRole(1L, UserRoleEnum.ADMIN)));

        toTest.register(signUpDto);

        verify(mockUserRepository).save(userEntityCaptor.capture());
        UserEntity actualSavedEntity = userEntityCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(signUpDto.getUsername(), actualSavedEntity.getUsername());
        assertEquals(signUpDto.getPassword(), signUpDto.getConfirmPassword());
        assertEquals(signUpDto.getPassword() + signUpDto.getPassword(),
                actualSavedEntity.getPassword());
        assertEquals(signUpDto.getEmail(), actualSavedEntity.getEmail());
    }

    @Test
    void testUserRegistrationFailedMismatchPass() {
        SignUpDto signUpDto = new SignUpDto("Pesho", "1", "123", "pesho@example.com");

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.register(signUpDto)
        );
        String expectedMessage = "Password and Confirm Password Field do not match";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserRegistrationFailedUserExist() {
        SignUpDto signUpDto = new SignUpDto("Pesho", "123", "123", "pesho@example.com");
        when(mockUserRepository.findByUsernameOrEmail("Pesho", "pesho@example.com"))
                .thenReturn(Optional.of(new ModelMapper().map(signUpDto, UserEntity.class)));
        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.register(signUpDto)
        );

        String expectedMessage = "User already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserFindByUsername() {
        UserEntity user = new UserEntity("pesho@example.com", "1234", "Pesho", List.of(new UserRole(1L, UserRoleEnum.ADMIN)));
        UserDto userDto = new UserDto(1L, "pesho@example.com", "1234", "Pesho", List.of(new UserRoleDto(1L, UserRoleEnum.ADMIN)));
        String username = "Pesho";

        when(mockUserRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(mockUserMapper.toUserDto(user))
                .thenReturn(userDto);

        toTest.findByUsername(username);
        Assertions.assertEquals(userDto.getUsername(), userDto.getUsername());
        Assertions.assertEquals(userDto.getEmail(), userDto.getEmail());
    }

    @Test
    void testUserFindByUsernameFailedUserExist() {
        String username = "Pesho";

        when(mockUserRepository.findByUsername(username))
                .thenReturn(Optional.ofNullable(null));
        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.findByUsername(username)
        );

        String expectedMessage = "Unknown user";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserLogin() {
        UserEntity user = new UserEntity("pesho@example.com", "1234", "Pesho", List.of(new UserRole(1L, UserRoleEnum.ADMIN)));
        CredentialsDto credentialsDto = new CredentialsDto("Pesho", "1234");

        when(mockUserRepository.findByUsername(credentialsDto.username()))
                .thenReturn(Optional.of(user));
        when(mockPasswordEncoder.matches(credentialsDto.password(), user.getPassword()))
                .thenReturn(Objects.equals(credentialsDto.password(), user.getPassword()));

        toTest.login(credentialsDto);
        Assertions.assertEquals(credentialsDto.username(), user.getUsername());
    }

    @Test
    void testUserLoginFailedUserNotFound() {
        CredentialsDto credentialsDto = new CredentialsDto("Pesho", "1234");

        when(mockUserRepository.findByUsername(credentialsDto.username()))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.findByUsername(credentialsDto.username())
        );

        String expectedMessage = "Unknown user";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserLoginFailedPasswordMismatch() {
        UserEntity user = new UserEntity("pesho@example.com", "1234", "Pesho", List.of(new UserRole(1L, UserRoleEnum.ADMIN)));
        CredentialsDto credentialsDto = new CredentialsDto("Pesho", "125543");

        when(mockUserRepository.findByUsername(credentialsDto.username()))
                .thenReturn(Optional.of(user));
        when(mockPasswordEncoder.matches(credentialsDto.password(), user.getPassword()))
                .thenReturn(Objects.equals(credentialsDto.password(), user.getPassword()));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.login(credentialsDto)
        );

        String expectedMessage = "Invalid password";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllUsers() {
        List<UserEntity> users = List.of(
                new UserEntity("pesho@example.com", "1234", "Pesho", List.of(new UserRole(1L, UserRoleEnum.ADMIN))),
                new UserEntity("gosho@example.com", "5534", "Gosho", List.of(new UserRole(2L, UserRoleEnum.USER)))
        );
        List<UserListDTO> userListDTOS = List.of(
                new UserListDTO(1, "Pesho", "pesho@example.com", List.of("ADMIN")),
                new UserListDTO(1, "Gosho", "gosho@example.com", List.of("USER"))
        );

        when(mockUserRepository.findAll()).thenReturn(users);

        toTest.getAllUsers();

        Assertions.assertEquals(users.toArray().length, userListDTOS.toArray().length);
    }

    @Test
    void testUpdateUser() {
        UserEntity user = new UserEntity("pesho@example.com", "1234", "Pesho", List.of(new UserRole(1L, UserRoleEnum.ADMIN)));
        UserEditDTO userEditDTO =
                new UserEditDTO(1L, "Pesho_66", "pesho66@example.com",
                        List.of(new UserRoleDto(1L, UserRoleEnum.ADMIN), new UserRoleDto(2L, UserRoleEnum.USER)));
        Long userId = 1L;

        when(mockUserRepository.findById(userId))
                .thenReturn(Optional.of(user));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateUser(userId, userEditDTO)
        );

        String expectedMessage = "User successfully updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(mockUserRepository).save(userEntityCaptor.capture());
        UserEntity actualSavedEntity = userEntityCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        assertEquals(userEditDTO.getUsername(), actualSavedEntity.getUsername());
        assertEquals(userEditDTO.getEmail(), actualSavedEntity.getEmail());
        assertEquals(userEditDTO.getRoles().toArray().length, actualSavedEntity.getRoles().toArray().length);
    }

    @Test
    void testUpdateUserFailedUserNotFound() {
        UserEditDTO userEditDTO =
                new UserEditDTO(1L, "Pesho_66", "pesho66@example.com",
                        List.of(new UserRoleDto(1L, UserRoleEnum.ADMIN), new UserRoleDto(2L, UserRoleEnum.USER)));
        Long userId = 1L;

        when(mockUserRepository.findById(userId))
                .thenReturn(Optional.ofNullable(null));


        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.updateUser(userId, userEditDTO)
        );

        String expectedMessage = "User is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetUserById() {
        UserEntity user = new UserEntity("pesho@example.com", "1234", "Pesho",
                List.of(new UserRole(1L, UserRoleEnum.ADMIN)));
        UserByIdDto userByIdDto =
                new UserByIdDto(1L, "Pesho", "pesho@example.com",
                        List.of(new UserRoleDto(1L, UserRoleEnum.ADMIN)));
        Long userId = 1L;

        when(mockUserRepository.findById(userId))
                .thenReturn(Optional.of(user));
        toTest.getUserById(userId);

        assertEquals(userByIdDto.getUsername(), user.getUsername());
        assertEquals(userByIdDto.getEmail(), user.getEmail());
        assertEquals(userByIdDto.getRoles().toArray().length, user.getRoles().toArray().length);
    }

    @Test
    void testDeleteUser() {
        UserEntity user = new UserEntity("pesho@example.com", "1234", "Pesho",
                List.of(new UserRole(1L, UserRoleEnum.ADMIN)));

        Long userId = 1L;

        when(mockUserRepository.findById(userId))
                .thenReturn(Optional.of(user));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteUser(userId)
        );

        String expectedMessage = "User successfully deleted!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteUserFailedUserNotFound() {
        Long userId = 1L;
        when(mockUserRepository.findById(userId))
                .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(
                AppException.class,
                () -> toTest.deleteUser(userId)
        );

        String expectedMessage = "User is not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllRoles() {
        List<UserRole> roles = List.of(
                new UserRole(1L, UserRoleEnum.ADMIN),
                new UserRole(2L, UserRoleEnum.USER)
        );
        List<UserRoleDto> roleDtos = List.of(
                new UserRoleDto(1L, UserRoleEnum.ADMIN),
                new UserRoleDto(2L, UserRoleEnum.USER)
        );

        when(mockUserRolesRepository.findAll()).thenReturn(roles);

        toTest.getAllRoles();

        Assertions.assertEquals(roles.toArray().length, roleDtos.toArray().length);
    }
}
