package com.epam.ecobites.service;

import com.epam.ecobites.converter.EcoUserConvertToDTO;
import com.epam.ecobites.converter.EcoUserConvertToEntity;
import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.dto.EcoUserDTO;
import com.epam.ecobites.dto.ShoppingItemDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private EcoUserConvertToDTO convertToDTO;

    @Mock
    private EcoUserConvertToEntity convertToEntity;

    @InjectMocks
    private UserService userService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String NAME = "test";
    private static final String EMAIL = "@test.com";
    private static final String UPDATED_EMAIL = "@changed.com";

    @DisplayName("Test creating a user with valid data")
    @Test
    public void testCreateUser() {
        EcoUser user = createUser(ID, NAME, EMAIL);
        EcoUserDTO userDTO = createUserDTO(ID, NAME, EMAIL);

        when(convertToEntity.convert(userDTO)).thenReturn(user);
        when(convertToDTO.convert(user)).thenReturn(userDTO);
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

    @DisplayName("Test getting a user by id when the user exists")
    @Test
    public void testGetUserById_Successful() {
        EcoUser user = createUser(ID, NAME, EMAIL);
        EcoUserDTO userDTO = createUserDTO(ID, NAME, EMAIL);

        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(convertToDTO.convert(user)).thenReturn(userDTO);

        EcoUserDTO actual = userService.getById(ID);

        assertEquals(userDTO, actual);

        verify(convertToDTO).convert(user);
    }

    @DisplayName("Test getting a user by id when the user does not exist")
    @Test
    public void testGetUserById_Failed() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(ID));
    }

    @DisplayName("Test getting all users when the list has users")
    @Test
    public void testGetAllUser_Successful() {
        EcoUser user1 = createUser(ID, NAME, EMAIL);
        EcoUser user2 = createUser(2L, NAME, EMAIL);
        List<EcoUser> userList = Arrays.asList(user1, user2);

        EcoUserDTO userDTO1 = createUserDTO(ID, NAME, EMAIL);
        EcoUserDTO userDTO2 = createUserDTO(2L, NAME, EMAIL);
        List<EcoUserDTO> userDTOList = Arrays.asList(userDTO1, userDTO2);

        when(userRepository.findAll()).thenReturn(userList);
        when(convertToDTO.convert(user1)).thenReturn(userDTO1);
        when(convertToDTO.convert(user2)).thenReturn(userDTO2);

        List<EcoUserDTO> resultList = userService.getAll();

        assertEquals(userDTOList, resultList);
    }

    @DisplayName("Test getting all users when the list is empty")
    @Test
    public void testGetAllUser_Failed() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> userService.getAll());
    }

    @DisplayName("Test updating a user when the user exists in the repository")
    @Test
    public void testUpdateExistingUser() {
        EcoUserDTO updatedUserDTO = createUserDTO(ID, NAME,EMAIL);
        EcoUser existingUser = createUser(ID, NAME,EMAIL);
        EcoUser updatedUser = createUser(ID, NAME,UPDATED_EMAIL);

        when(userRepository.findById(ID)).thenReturn(Optional.of(existingUser));
        when(convertToEntity.convert(updatedUserDTO)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(convertToDTO.convert(updatedUser)).thenReturn(updatedUserDTO);

        EcoUserDTO result = userService.update(ID, updatedUserDTO);

        assertAll(
                () -> assertEquals(updatedUserDTO, result),
                () -> assertEquals(ID, result.getId()),
                () -> assertEquals(updatedUserDTO.getUsername(), result.getUsername()),
                () -> assertEquals(updatedUserDTO.getEmail(), result.getEmail())
        );
        verify(userRepository).save(updatedUser);
    }

    @DisplayName("Test updating a user when the user does not exist in the repository")
    @Test
    public void testUpdateAbsentUser() {
        EcoUserDTO userDTO = createUserDTO(ID, NAME, EMAIL);

        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(ID, userDTO));
    }

    @DisplayName("Test updating a user with an invalid id")
    @Test
    public void testUpdateUserWithInvalidId() {
        EcoUserDTO userDTO = createUserDTO(ID, NAME,EMAIL);

        assertThrows(IllegalArgumentException.class, () -> userService.update(INVALID_ID, userDTO));
    }

    @DisplayName("Test deleting a user when the user exists in the repository")
    @Test
    public void testDeleteExistingUser() {
        when(userRepository.existsById(ID)).thenReturn(true);

        userService.delete(ID);

        verify(userRepository).deleteById(ID);

    }

    @DisplayName("Test deleting a user when the user does not exist in the repository")
    @Test
    public void testDeleteAbsentUser() {
        when(userRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.delete(ID));
    }

    @DisplayName("Test creating a user with null data")
    @Test
    public void testCreateUserWithNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.create(null));
    }

    @DisplayName("Test updating a user with null data")
    @Test
    public void testUpdateUserWithNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(ID, null));
    }

    @DisplayName("Test updating a user with null id")
    @Test
    public void testUpdateUser_NullId() {
        EcoUserDTO ecoUserDTO = createUserDTO(ID,NAME,EMAIL);
        assertThrows(IllegalArgumentException.class, () -> userService.update(null, ecoUserDTO));
    }

    @DisplayName("Test deleting a user with an invalid id")
    @Test
    public void testDeleteUserWithInvalidId() {
        assertThrows(NotFoundException.class, () -> userService.delete(INVALID_ID));
    }

    @DisplayName("Test deleting a user with null id")
    @Test
    public void testDeleteUserWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> userService.delete(null));
    }

    @DisplayName("Test getting a user with null id")
    @Test
    public void testGetUserWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> userService.getById(null));
    }

    private EcoUser createUser(Long id, String name, String email) {
        EcoUser user = new EcoUser();
        user.setId(id);
        user.setUsername(name);
        user.setEmail(email);
        return user;
    }

    private EcoUserDTO createUserDTO(Long id, String name, String email) {
        return EcoUserDTO.builder()
                .id(id)
                .username(name)
                .email(email)
                .build();
    }

}
