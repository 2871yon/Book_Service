package org.example.book_service.service;

import org.example.book_service.entity.Ingredient;
import org.example.book_service.exception.IngredientInUseException;
import org.example.book_service.exception.ResourceNotFoundException;
import org.example.book_service.repository.IngredientRepository;
import org.example.book_service.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientCatalogService {
    @Autowired
    private IngredientRepository ingredientRepository;


    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient is not exist given id :" + id));
    }

    public Ingredient updateIngredient(Long id, double PricePerUnit) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient is not exist given id :" + id));

        ingredient.setPricePerUnit(PricePerUnit);
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient is not exist given id :" + id));

        if (ingredient.getRecipe() != null) {
            throw new IngredientInUseException("Cannot delete ingredient with id " + id + " as it is used in a recipe.");
        }

        ingredientRepository.delete(ingredient);
    }
}
