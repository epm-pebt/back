package com.epam.ecobites.converter;

import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.dto.EcoUserDTO;
import org.springframework.core.convert.converter.Converter;

public class EcoUserConvertToDTO implements Converter<EcoUser, EcoUserDTO> {
    @Override
    public EcoUserDTO convert(EcoUser source) {
        return EcoUserDTO.builder()
                .id(source.getId())
                .username(source.getUsername())
                .email(source.getEmail())
                .image(source.getImage())
                .dateCreated(source.getDateCreated())
                .build();
    }
}
