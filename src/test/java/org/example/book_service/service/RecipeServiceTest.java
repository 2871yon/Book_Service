package org.example.book_service.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.book_service.entity.Ingredient;
import org.example.book_service.entity.Recipe;
import org.example.book_service.exception.IngredientInUseException;
import org.example.book_service.exception.ResourceNotFoundException;
import org.example.book_service.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;
    private Ingredient ingredient;
    private Recipe recipe;

    @BeforeEach
    void setup() {
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Flour");
        ingredient.setPricePerUnit(1.5);

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pancakes");
        recipe.setInstruction("Mix ingredients and cook.");

        ingredient.setRecipe(recipe);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Test Recipe");

        when(recipeRepository.save(recipe)).thenReturn(recipe);

        Recipe savedRecipe = recipeService.addRecipe(recipe);

        assertNotNull(savedRecipe);
        assertEquals("Test Recipe", savedRecipe.getName());

        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void testGetAllRecipes() {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> fetchedRecipes = recipeService.getAllRecipes();

        assertNotNull(fetchedRecipes);
        assertEquals(2, fetchedRecipes.size());

        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testGetRecipeById() {
        long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setName("Test Recipe");

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        Recipe fetchedRecipe = recipeService.getRecipeById(recipeId);

        assertNotNull(fetchedRecipe);
        assertEquals(recipeId, fetchedRecipe.getId());
        assertEquals("Test Recipe", fetchedRecipe.getName());

        verify(recipeRepository, times(1)).findById(recipeId);
    }

    @Test
    void testGetRecipeById_ResourceNotFoundException() {
        long recipeId = 1L;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        assertThrows(ResourceNotFoundException.class, () -> recipeService.getRecipeById(recipeId));
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> recipeService.getRecipeById(recipeId)
        );
        assertEquals("Recipe is not exist given id : " + recipeId, exception.getMessage());
        Mockito.verify(recipeRepository, times(1)).findById(recipeId);
    }
}