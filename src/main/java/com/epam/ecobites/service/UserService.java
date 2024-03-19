package com.epam.ecobites.service;

import com.epam.ecobites.converter.EcoUserConvertToDTO;
import com.epam.ecobites.converter.EcoUserConvertToEntity;
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
    private EcoUserConvertToDTO ecoUserConvertToDTO;
    private EcoUserConvertToEntity ecoUserConvertToEntity;

    public UserService(EcoUserRepository ecoUserRepository, EcoUserConvertToDTO ecoUserConvertToDTO, EcoUserConvertToEntity ecoUserConvertToEntity) {
        this.ecoUserRepository = ecoUserRepository;
        this.ecoUserConvertToDTO = ecoUserConvertToDTO;
        this.ecoUserConvertToEntity = ecoUserConvertToEntity;
    }

    @Override
    public EcoUserDTO create(EcoUserDTO userDto) {
        nullCheck(userDto);
        EcoUser user = ecoUserConvertToEntity.convert(userDto);
        EcoUser savedUser = ecoUserRepository.save(user);
        return ecoUserConvertToDTO.convert(savedUser);
    }

    @Override
    public EcoUserDTO getById(Long id) {
        nullCheck(id);
        EcoUser user = ecoUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        return ecoUserConvertToDTO.convert(user);
    }

    @Override
    public List<EcoUserDTO> getAll() {
        List<EcoUser> users = ecoUserRepository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return users.stream().map(ecoUserConvertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public EcoUserDTO update(Long id, EcoUserDTO updatedUserDto) {
        nullCheck(id);
        nullCheck(updatedUserDto);
        validateIds(id, updatedUserDto);
        EcoUser existingUser = findUserById(id);
        EcoUser updatedUser = ecoUserConvertToEntity.convert(updatedUserDto);
        updateUserFields(existingUser, updatedUser);
        EcoUser savedUser = ecoUserRepository.save(existingUser);
        return ecoUserConvertToDTO.convert(savedUser);
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
        nullCheck(id);
        if (ecoUserRepository.existsById(id)) {
            ecoUserRepository.deleteById(id);
        } else {
            throw new NotFoundException("User with id " + id + " not found");
        }
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
