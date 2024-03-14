package com.epam.ecobites.service;

import com.epam.ecobites.converter.IngredientDetailConverter;
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
    private IngredientDetailConverter ingredientDetailConverter;

    public IngredientDetailService(IngredientDetailRepository ingredientDetailRepository, IngredientDetailConverter ingredientDetailConverter) {
        this.ingredientDetailRepository = ingredientDetailRepository;
        this.ingredientDetailConverter = ingredientDetailConverter;
    }

    @Override
    public IngredientDetailDTO create(IngredientDetailDTO ingredientDetailDto) {
        IngredientDetail ingredientDetail = ingredientDetailConverter.fromDTO(ingredientDetailDto);
        IngredientDetail savedIngredientDetail = ingredientDetailRepository.save(ingredientDetail);
        return ingredientDetailConverter.toDTO(savedIngredientDetail);
    }

    @Override
    public IngredientDetailDTO get(Long id) {
        IngredientDetail ingredientDetail = ingredientDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("IngredientDetail with id " + id + " not found"));
        return ingredientDetailConverter.toDTO(ingredientDetail);
    }

    @Override
    public List<IngredientDetailDTO> getAll() {
        List<IngredientDetail> ingredientDetails = ingredientDetailRepository.findAll();
        return ingredientDetails.stream().map(ingredientDetailConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public IngredientDetailDTO update(Long id, IngredientDetailDTO updatedIngredientDetailDto) {
        validateIds(id, updatedIngredientDetailDto);
        IngredientDetail existingIngredientDetail = findIngredientDetailById(id);
        IngredientDetail updatedIngredientDetail = ingredientDetailConverter.fromDTO(updatedIngredientDetailDto);
        updateIngredientDetailFields(existingIngredientDetail, updatedIngredientDetail);
        IngredientDetail savedIngredientDetail = ingredientDetailRepository.save(existingIngredientDetail);
        return ingredientDetailConverter.toDTO(savedIngredientDetail);
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
        if (!ingredientDetailRepository.existsById(id)) {
            throw new NotFoundException("IngredientDetail with id " + id + " not found");
        }
        ingredientDetailRepository.deleteById(id);
    }
}
