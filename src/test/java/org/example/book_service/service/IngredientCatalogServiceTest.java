 package org.example.book_service.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.book_service.entity.Ingredient;
import org.example.book_service.entity.Recipe;
import org.example.book_service.exception.IngredientInUseException;
import org.example.book_service.exception.ResourceNotFoundException;
import org.example.book_service.repository.IngredientRepository;
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
class IngredientCatalogServiceTest {
@Mock
private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientCatalogService ingredientService;

    private Ingredient ingredient;
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Flour");
        ingredient.setPricePerUnit(1.5);

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pancakes");
        recipe.setInstruction("Mix ingredients and cook.");

        ingredient.setRecipe(recipe);
    }

    @Test
    void testAddIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient");
        ingredient.setPricePerUnit(1.5);

        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Ingredient savedIngredient = ingredientService.addIngredient(ingredient);

        assertNotNull(savedIngredient);
        assertEquals("Test Ingredient", savedIngredient.getName());

        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void testGetAllIngredient() {
        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        when(ingredientRepository.findAll()).thenReturn(ingredients);

        List<Ingredient> fetchedIngredient = ingredientService.getAllIngredients();

        assertNotNull(fetchedIngredient);
        assertEquals(2, fetchedIngredient.size());

        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    void testGetIngredientById() {
        long ingredientId = 1L;
        Ingredient ingredient = new Ingredient();
        recipe.setId(ingredientId);
        recipe.setName("Test Ingredient");

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        Ingredient fetchedIngredient = ingredientService.getIngredientById(ingredientId);

        assertNotNull(fetchedIngredient);
        assertEquals(ingredientId, fetchedIngredient.getId());
        assertEquals("Test Ingredient", fetchedIngredient.getName());

        verify(ingredientRepository, times(1)).findById(ingredientId);
    }

    @Test
    void testGetIngredientById_ResourceNotFoundException() {
        long ingredientId = 1L;

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        assertThrows(ResourceNotFoundException.class, () -> ingredientService.getIngredientById(ingredientId));
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> ingredientService.getIngredientById(ingredientId)
        );
        assertEquals("Ingredient is not exist given id : " + ingredientId, exception.getMessage());
        Mockito.verify(recipeRepository, times(1)).findById(ingredientId);
    }

    @Test
    public void testDeleteIngredient_WhenIngredientInUse_ShouldThrowException() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        IngredientInUseException exception = assertThrows(
                IngredientInUseException.class,
                () -> ingredientService.deleteIngredient(1L)
        );

        assertEquals("Cannot delete ingredient with id 1 as it is used in a recipe.", exception.getMessage());
        verify(ingredientRepository, never()).delete(ingredient);
    }

    @Test
    public void testDeleteIngredient_WhenIngredientNotInUse_ShouldDeleteIngredient() {
        ingredient.setRecipe(null);
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        assertDoesNotThrow(() -> ingredientService.deleteIngredient(1L));
        verify(ingredientRepository, times(1)).delete(ingredient);
    }

}