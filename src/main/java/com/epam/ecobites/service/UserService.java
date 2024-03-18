package com.epam.ecobites.service;

import com.epam.ecobites.converter.UserConverter;
import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.dto.EcoUserDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements CrudService<EcoUserDTO, Long> {
    private EcoUserRepository ecoUserRepository;
    private UserConverter userConverter;

    public UserService(EcoUserRepository ecoUserRepository, UserConverter userConverter) {
        this.ecoUserRepository = ecoUserRepository;
        this.userConverter = userConverter;
    }

    @Override
    public EcoUserDTO create(EcoUserDTO userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("userDTO cannot be null");
        }
        EcoUser user = userConverter.convertToEntity(userDto);
        EcoUser savedUser = ecoUserRepository.save(user);
        return userConverter.convertToDTO(savedUser);
    }

    @Override
    public EcoUserDTO get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        EcoUser user = ecoUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        return userConverter.convertToDTO(user);
    }

    @Override
    public List<EcoUserDTO> getAll() {
        List<EcoUser> users = ecoUserRepository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return users.stream().map(userConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EcoUserDTO update(Long id, EcoUserDTO updatedUserDto) {
        if (id == null || id <=0 ) {
            throw new IllegalArgumentException("id must be positive");
        }
        if (updatedUserDto == null) {
            throw new IllegalArgumentException("updatedUserDto cannot be null");
        }
        validateIds(id, updatedUserDto);
        EcoUser existingUser = findUserById(id);
        EcoUser updatedUser = userConverter.convertToEntity(updatedUserDto);
        updateUserFields(existingUser, updatedUser);
        EcoUser savedUser = ecoUserRepository.save(existingUser);
        return userConverter.convertToDTO(savedUser);
    }

    private void validateIds(Long id, EcoUserDTO updatedUserDto) {
        if (!id.equals(updatedUserDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the user object must be the same");
        }
    }

    private EcoUser findUserById(Long id) {
        return ecoUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    private void updateUserFields(EcoUser existingUser, EcoUser updatedUser) {
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setImage(updatedUser.getImage());
        existingUser.setDateCreated(updatedUser.getDateCreated());
        existingUser.setReviews(updatedUser.getReviews());
        existingUser.setShoppingItems(updatedUser.getShoppingItems());
    }

    @Override
    public void delete(Long id) {
        if (ecoUserRepository.existsById(id)) {
            ecoUserRepository.deleteById(id);
        } else {
            throw new NotFoundException("User with id " + id + " not found");
        }
    }
}
