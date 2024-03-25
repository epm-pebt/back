package com.epam.ecobites.service;

import com.epam.ecobites.domain.converter.IngredientConvertToDTO;
import com.epam.ecobites.domain.converter.IngredientConvertToEntity;
import com.epam.ecobites.data.IngredientRepository;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.dto.IngredientDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService implements CrudService<IngredientDTO, Long> {
    private IngredientRepository ingredientRepository;
    private IngredientConvertToDTO convertToDTO;
    private IngredientConvertToEntity convertToEntity;

    public IngredientService(IngredientRepository ingredientRepository, IngredientConvertToDTO convertToDTO, IngredientConvertToEntity ingredientConvertToEntity) {
        this.ingredientRepository = ingredientRepository;
        this.convertToDTO = convertToDTO;
        this.convertToEntity = ingredientConvertToEntity;
    }

    @Override
    public IngredientDTO create(IngredientDTO ingredientDto) {
        nullCheck(ingredientDto);
        Ingredient ingredient = convertToEntity.convert(ingredientDto);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return convertToDTO.convert(savedIngredient);
    }

    @Override
    public IngredientDTO getById(Long id) {
        nullCheck(id);
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient with id " + id + " not found"));
        return convertToDTO.convert(ingredient);
    }

    @Override
    public List<IngredientDTO> getAll() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream().map(convertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public IngredientDTO update(Long id, IngredientDTO updatedIngredientDto) {
        nullCheck(id);
        nullCheck(updatedIngredientDto);
        validateIds(id, updatedIngredientDto);
        Ingredient existingIngredient = findIngredientById(id);
        Ingredient updatedIngredient = convertToEntity.convert(updatedIngredientDto);
        updateIngredientFields(existingIngredient, updatedIngredient);
        Ingredient savedIngredient = ingredientRepository.save(existingIngredient);
        return convertToDTO.convert(savedIngredient);
    }

    private void validateIds(Long id, IngredientDTO updatedIngredientDto) {
        if (!id.equals(updatedIngredientDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the Ingredient object must be the same");
        }
    }

    private Ingredient findIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient with id " + id + " not found"));
    }

    private void updateIngredientFields(Ingredient existingIngredient, Ingredient updatedIngredient) {
        existingIngredient.setName(updatedIngredient.getName());
    }

    @Override
    public void delete(Long id) {
        nullCheck(id);
        if (!ingredientRepository.existsById(id)) {
            throw new NotFoundException("Ingredient with id " + id + " not found");
        }
        ingredientRepository.deleteById(id);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
