package org.example.book_service.repository;

import org.example.book_service.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
//    @Query("SELECT r FROM Recipe r WHERE r.name = :name")
//    List<Recipe> findByName(@Param("name") String name);
}
