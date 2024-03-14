package com.epam.ecobites.service;

import com.epam.ecobites.converter.IngredientConverter;
import com.epam.ecobites.data.IngredientRepository;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.dto.IngredientDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService implements CrudService<IngredientDTO, Long> {
    private IngredientRepository ingredientRepository;
    private IngredientConverter ingredientConverter;

    public IngredientService(IngredientRepository ingredientRepository, IngredientConverter ingredientConverter) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientConverter = ingredientConverter;
    }

    @Override
    public IngredientDTO create(IngredientDTO ingredientDto) {
        Ingredient ingredient = ingredientConverter.fromDTO(ingredientDto);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientConverter.toDTO(savedIngredient);
    }

    @Override
    public IngredientDTO get(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient with id " + id + " not found"));
        return ingredientConverter.toDTO(ingredient);
    }

    @Override
    public List<IngredientDTO> getAll() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream().map(ingredientConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public IngredientDTO update(Long id, IngredientDTO updatedIngredientDto) {
        validateIds(id, updatedIngredientDto);
        Ingredient existingIngredient = findIngredientById(id);
        Ingredient updatedIngredient = ingredientConverter.fromDTO(updatedIngredientDto);
        updateIngredientFields(existingIngredient, updatedIngredient);
        Ingredient savedIngredient = ingredientRepository.save(existingIngredient);
        return ingredientConverter.toDTO(savedIngredient);
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
        if (!ingredientRepository.existsById(id)) {
            throw new NotFoundException("Ingredient with id " + id + " not found");
        }
        ingredientRepository.deleteById(id);
    }
}
