package it.city.itcityacademy.repository;

import it.city.itcityacademy.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameEqualsIgnoreCase(String name);
}
