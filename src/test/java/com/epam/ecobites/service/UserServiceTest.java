package com.epam.ecobites.service;

import com.epam.ecobites.converter.UserConverter;
import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.dto.EcoUserDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private EcoUserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserService userService;

    /**
     * Test creating a user with valid data.
     *
     * @param username the username of the user to create
     */
    @DisplayName("Test creating a user with valid data")
    @ParameterizedTest
    @ValueSource(strings = {"test1", "test2", "test3", "test4", "test5"})
    public void testCreateUser(String username) {
        String email = username + "@test.com";
        EcoUser user = createUser(username, email);
        EcoUserDTO userDTO = createUserDTO(username, email);

        when(userConverter.fromDTO(userDTO)).thenReturn(user);
        when(userConverter.toDTO(user)).thenReturn(userDTO);
        when(userRepository.save(user)).thenReturn(user);

        EcoUserDTO result = userService.create(userDTO);

        assertAll(
                () -> assertEquals(userDTO, result),
                () -> assertEquals(userDTO.getId(), result.getId()),
                () -> assertEquals(userDTO.getUsername(), result.getUsername()),
                () -> assertEquals(userDTO.getEmail(), result.getEmail())
        );

        verify(userRepository).save(user);
    }

    /**
     * Test getting a user by id when the user exists.
     *
     * @param ids the id of the user to get
     */
    @DisplayName("Test getting a user by id when the user exists")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void Get_Successful_UserExistsInRepository(int ids) {
        Long id = (long) ids;
        EcoUser user = createUser("test", "test@test.com");
        EcoUserDTO userDTO = createUserDTO("test", "test@test.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userConverter.toDTO(user)).thenReturn(userDTO);

        EcoUserDTO result = userService.get(id);

        assertEquals(userDTO, result);

        verify(userConverter).toDTO(user);
    }

    /**
     * Test getting a user by id when the user does not exist.
     *
     * @param ids the id of the user to get
     */
    @DisplayName("Test getting a user by id when the user does not exist")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void Get_Failed_UserIsAbsent(int ids) {
        Long id = (long) ids;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.get(id));
    }

    /**
     * Test getting all users when the list has users.
     *
     * @param username the username of the users to get
     */
    @DisplayName("Test getting all users when the list has users")
    @ParameterizedTest
    @ValueSource(strings = {"test1", "test2", "test3", "test4", "test5"})
    public void GetAll_Successful_ListHasUsers(String username) {
        String email = username + "@test.com";
        EcoUser user1 = createUser(username, email);
        EcoUser user2 = createUser(username, email);
        List<EcoUser> userList = Arrays.asList(user1, user2);

        EcoUserDTO userDTO1 = createUserDTO(username, email);
        EcoUserDTO userDTO2 = createUserDTO(username, email);
        List<EcoUserDTO> userDTOList = Arrays.asList(userDTO1, userDTO2);

        when(userRepository.findAll()).thenReturn(userList);
        when(userConverter.toDTO(user1)).thenReturn(userDTO1);
        when(userConverter.toDTO(user2)).thenReturn(userDTO2);

        List<EcoUserDTO> resultList = userService.getAll();

        assertEquals(userDTOList, resultList);
    }

    /**
     * Test getting all users when the list is empty.
     */
    @DisplayName("Test getting all users when the list is empty")
    @Test
    public void GetAll_Failed_ListIsEmpty() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> userService.getAll());
    }

    /**
     * Test updating a user when the user exists in the repository.
     *
     * @param username the username of the user to update
     */
    @DisplayName("Test updating a user when the user exists in the repository")
    @ParameterizedTest
    @ValueSource(strings = {"test1", "test2", "test3", "test4", "test5"})
    public void Update_Successful_UserExistsInRepository(String username) {
        String email = username + "@test.com";
        String updatedEmail = username + "@changed.com";
        Long id = 1L;

        EcoUserDTO updatedUserDTO = createUserDTO(username,updatedEmail);
        EcoUser existingUser = createUser(username,email);
        EcoUser updatedUser = createUser(username,updatedEmail);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userConverter.fromDTO(updatedUserDTO)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(userConverter.toDTO(updatedUser)).thenReturn(updatedUserDTO);

        EcoUserDTO result = userService.update(id, updatedUserDTO);

        assertAll(
                () -> assertEquals(updatedUserDTO, result),
                () -> assertEquals(id, result.getId()),
                () -> assertEquals(updatedUserDTO.getUsername(), result.getUsername()),
                () -> assertEquals(updatedUserDTO.getEmail(), result.getEmail())
        );
        verify(userRepository).save(updatedUser);
    }

    /**
     * Test updating a user when the user does not exist in the repository.
     *
     * @param username the username of the user to update
     */
    @DisplayName("Test updating a user when the user does not exist in the repository")
    @ParameterizedTest
    @ValueSource(strings = {"test1", "test2", "test3", "test4", "test5"})
    public void Update_Failed_UserIsAbsent(String username) {
        String email = username + "@test.com";
        Long id = 1L;

        EcoUserDTO userDTO = createUserDTO(username, email);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(id, userDTO));
    }

    /**
     * Test updating a user with an invalid id.
     *
     * @param ids the invalid id to use
     */
    @DisplayName("Test updating a user with an invalid id")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -3, -4})
    public void Update_Failed_InvalidId(int ids) {
        Long id = (long) ids;

        EcoUserDTO userDTO = createUserDTO("test","test@test.com");

        assertThrows(IllegalArgumentException.class, () -> userService.update(id, userDTO));
    }

    /**
     * Test deleting a user when the user exists in the repository.
     *
     * @param ids the id of the user to delete
     */
    @DisplayName("Test deleting a user when the user exists in the repository")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void Delete_Successful_UserExistsInRepository(int ids) {
        Long id = (long) ids;

        when(userRepository.existsById(id)).thenReturn(true);

        userService.delete(id);

        verify(userRepository).deleteById(id);

    }

    /**
     * Test deleting a user when the user does not exist in the repository.
     *
     * @param ids the id of the user to delete
     */
    @DisplayName("Test deleting a user when the user does not exist in the repository")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void Delete_Failed_UserIsAbsent(int ids) {
        Long id = (long) ids;

        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.delete(id));
    }

    /**
     * Test creating a user with null data.
     */
    @DisplayName("Test creating a user with null data")
    @Test
    public void testCreateUser_NullUserDTO() {
        assertThrows(IllegalArgumentException.class, () -> userService.create(null));
    }

    /**
     * Test updating a user with null data.
     */
    @DisplayName("Test updating a user with null data")
    @Test
    public void testUpdateUser_NullUserDTO() {
        Long id = 1L;
        assertThrows(IllegalArgumentException.class, () -> userService.update(id, null));
    }

    /**
     * Test deleting a user with an invalid id.
     */
    @DisplayName("Test deleting a user with an invalid id")
    @Test
    public void testDeleteUser_InvalidId() {
        Long id = -1L;
        assertThrows(NotFoundException.class, () -> userService.delete(id));
    }

    /**
     * Create a user with the given username and email.
     *
     * @param username the username of the user
     * @param email the email of the user
     * @return the created user
     */
    private EcoUser createUser(String username, String email) {
        EcoUser user = new EcoUser();
        user.setId(1L);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }

    /**
     * Create a user DTO with the given username and email.
     *
     * @param username the username of the user DTO
     * @param email the email of the user DTO
     * @return the created user DTO
     */
    private EcoUserDTO createUserDTO(String username, String email) {
        EcoUserDTO userDTO = new EcoUserDTO();
        userDTO.setId(1L);
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        return userDTO;
    }

}
