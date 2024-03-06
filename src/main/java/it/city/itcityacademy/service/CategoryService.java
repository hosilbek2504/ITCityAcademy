package it.city.itcityacademy.service;

import it.city.itcityacademy.entity.Category;
import it.city.itcityacademy.payload.ApiResponse;
import it.city.itcityacademy.payload.ReqCategory;
import it.city.itcityacademy.payload.ResCategory;
import it.city.itcityacademy.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    final
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ApiResponse addCategory(ReqCategory reqCategory) {
        if (categoryRepository.existsByNameEqualsIgnoreCase(reqCategory.getName()))
            return new ApiResponse("category name already exist", false);
        if (reqCategory.getPrice() == null) return new ApiResponse("Price is not null", false);
        return addOrEditCategory(reqCategory, new Category());
    }

    public ApiResponse editCategory(ReqCategory reqCategory, Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceAccessException("get category"));
        return addOrEditCategory(reqCategory, category);
    }

    public List<ResCategory> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        List<ResCategory> resCategoryList = new ArrayList<>();
        for (Category category : categoryList) {
            ResCategory resCategory = new ResCategory();
            resCategory.setId(category.getId());
            resCategory.setName(category.getName());
            resCategory.setPrice(category.getPrice());
            resCategory.setDescription(category.getDescription());
            resCategoryList.add(resCategory);
        }
        return resCategoryList;
    }

    public ApiResponse deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }


    public ApiResponse addOrEditCategory(ReqCategory reqCategory, Category category) {
        if (reqCategory.getName().isEmpty()) return new ApiResponse("Name is not null", false);
        category.setName(reqCategory.getName());
        category.setPrice(reqCategory.getPrice());
        category.setDescription(reqCategory.getDescription());
        categoryRepository.save(category);
        return new ApiResponse("Successfully saved category", true);
    }

}
