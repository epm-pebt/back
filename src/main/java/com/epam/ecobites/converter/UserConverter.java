package com.epam.ecobites.converter;

import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.dto.EcoUserDTO;

public class UserConverter implements EntityConverter<EcoUserDTO, EcoUser> {
    @Override
    public EcoUserDTO convertToDTO(EcoUser user) {
        return EcoUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .image(user.getImage())
                .dateCreated(user.getDateCreated())
                .build();
    }

    @Override
    public EcoUser convertToEntity(EcoUserDTO userDTO) {
        EcoUser user = new EcoUser();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setImage(userDTO.getImage());
        user.setDateCreated(userDTO.getDateCreated());
        return user;
    }
}
