package org.example.book_service.controller;

import lombok.AllArgsConstructor;
import org.example.book_service.entity.Ingredient;
import org.example.book_service.entity.Recipe;
import org.example.book_service.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

        private RecipeService recipeService;

        @GetMapping
        public List<Recipe> getAllRecipes() {
            List<Recipe> recipes = recipeService.getAllRecipes();
            return ResponseEntity.ok(recipes).getBody();
        }

        @GetMapping("/{id}")
        public Recipe getRecipeById(@PathVariable Long id) {
            return recipeService.getRecipeById(id);
        }


        @PostMapping
        public Recipe addRecipe(@RequestBody Recipe recipe) {
            return recipeService.addRecipe(recipe);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
            recipeService.deleteRecipe(id);
            return ResponseEntity.noContent().build();
        }

}
