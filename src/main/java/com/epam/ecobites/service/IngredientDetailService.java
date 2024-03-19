package com.epam.ecobites.service;

import com.epam.ecobites.converter.IngredientDetailConvertToDTO;
import com.epam.ecobites.converter.IngredientDetailConvertToEntity;
import com.epam.ecobites.data.IngredientDetailRepository;
import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.dto.IngredientDetailDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientDetailService implements CrudService<IngredientDetailDTO, Long> {
    private IngredientDetailRepository ingredientDetailRepository;
    private IngredientDetailConvertToDTO convertToDTO;
    private IngredientDetailConvertToEntity convertToEntity;

    public IngredientDetailService(IngredientDetailRepository ingredientDetailRepository, IngredientDetailConvertToDTO convertToDTO, IngredientDetailConvertToEntity convertToEntity) {
        this.ingredientDetailRepository = ingredientDetailRepository;
        this.convertToDTO = convertToDTO;
        this.convertToEntity = convertToEntity;
    }

    @Override
    public IngredientDetailDTO create(IngredientDetailDTO ingredientDetailDto) {
        nullCheck(ingredientDetailDto);
        IngredientDetail ingredientDetail = convertToEntity.convert(ingredientDetailDto);
        IngredientDetail savedIngredientDetail = ingredientDetailRepository.save(ingredientDetail);
        return convertToDTO.convert(savedIngredientDetail);
    }

    @Override
    public IngredientDetailDTO getById(Long id) {
        nullCheck(id);
        IngredientDetail ingredientDetail = ingredientDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("IngredientDetail with id " + id + " not found"));
        return convertToDTO.convert(ingredientDetail);
    }

    @Override
    public List<IngredientDetailDTO> getAll() {
        List<IngredientDetail> ingredientDetails = ingredientDetailRepository.findAll();
        return ingredientDetails.stream().map(convertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public IngredientDetailDTO update(Long id, IngredientDetailDTO updatedIngredientDetailDto) {
        nullCheck(id);
        nullCheck(updatedIngredientDetailDto);
        validateIds(id, updatedIngredientDetailDto);
        IngredientDetail existingIngredientDetail = findIngredientDetailById(id);
        IngredientDetail updatedIngredientDetail = convertToEntity.convert(updatedIngredientDetailDto);
        updateIngredientDetailFields(existingIngredientDetail, updatedIngredientDetail);
        IngredientDetail savedIngredientDetail = ingredientDetailRepository.save(existingIngredientDetail);
        return convertToDTO.convert(savedIngredientDetail);
    }

    private void validateIds(Long id, IngredientDetailDTO updatedIngredientDetailDto) {
        if (!id.equals(updatedIngredientDetailDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the IngredientDetail object must be the same");
        }
    }

    private IngredientDetail findIngredientDetailById(Long id) {
        return ingredientDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("IngredientDetail with id " + id + " not found"));
    }

    private void updateIngredientDetailFields(IngredientDetail existingIngredientDetail, IngredientDetail updatedIngredientDetail) {
        existingIngredientDetail.setQuantity(updatedIngredientDetail.getQuantity());
        existingIngredientDetail.setUnit(updatedIngredientDetail.getUnit());
    }

    @Override
    public void delete(Long id) {
        nullCheck(id);
        if (!ingredientDetailRepository.existsById(id)) {
            throw new NotFoundException("IngredientDetail with id " + id + " not found");
        }
        ingredientDetailRepository.deleteById(id);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
