package com.epam.ecobites.domain.converter;

import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.dto.EcoUserDTO;
import org.springframework.core.convert.converter.Converter;

public class EcoUserConvertToEntity implements Converter<EcoUserDTO, EcoUser> {
    @Override
    public EcoUser convert(EcoUserDTO source) {
        EcoUser user = new EcoUser();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setEmail(source.getEmail());
        user.setImage(source.getImage());
        user.setDateCreated(source.getDateCreated());
        return user;
    }
}
