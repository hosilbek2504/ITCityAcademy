package it.city.itcityacademy.controller;

import it.city.itcityacademy.payload.ApiResponse;
import it.city.itcityacademy.payload.ReqCategory;
import it.city.itcityacademy.payload.ResCategory;
import it.city.itcityacademy.service.CategoryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/category")
public class CategoryController {
    final
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get")
    @ResponseBody
    public List<ResCategory> getCategory() {
        return categoryService.getCategoryList();
    }

    @PostMapping("/add")
    public HttpEntity<?> addCategory(@RequestBody ReqCategory reqCategory) {
        ApiResponse apiResponse = categoryService.addCategory(reqCategory);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable Integer id) {
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> editCategory(@PathVariable Integer id,@RequestBody ReqCategory reqCategory){
        ApiResponse apiResponse=categoryService.editCategory(reqCategory,id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
