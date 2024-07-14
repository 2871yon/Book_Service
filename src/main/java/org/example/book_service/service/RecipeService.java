package org.example.book_service.service;

import lombok.AllArgsConstructor;
import org.example.book_service.entity.Recipe;
import org.example.book_service.repository.IngredientRepository;
import org.example.book_service.exception.ResourceNotFoundException;
import org.example.book_service.repository.RecipeRepository;
import org.example.book_service.entity.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

        public Recipe addRecipe(Recipe recipe) {
            return recipeRepository.save(recipe);
        }

        public List<Recipe> getAllRecipes() {
            return recipeRepository.findAll();
        }


        public Recipe getRecipeById(long recipeId){
            Recipe recipe=  recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Recipe is not exist given id :" + recipeId));
            return recipe;
        }
       public void deleteRecipe(Long recipeId) {
           Recipe recipe = recipeRepository.findById(recipeId)
                   .orElseThrow(() -> new ResourceNotFoundException("Recipe is not exist given id :" + recipeId));

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(null);
        }
        recipeRepository.delete(recipe);
    }
}
