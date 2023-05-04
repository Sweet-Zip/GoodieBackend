package com.example.uploadimage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    @Autowired
    public CategoryDao categoryDao;
    @Autowired
    public CategoryRepository categoryRepository;

    @PostMapping("/category/add")
    public Category addNewCategory(@RequestBody Category category) {
        return categoryDao.addNewCategory(category);
    }

    @GetMapping("/category/get-all")
    public List<Category> getAllCategory() {
        return categoryDao.getAllCategory();
    }

    @GetMapping("/category/{id}")
    public Optional<Category> getCategoryByID(@PathVariable("id") int id) {
        return categoryRepository.findById(id);
    }

    @DeleteMapping("/category/delete/{id}")
    public void deleteCategoryByID(@PathVariable("id") int categoryID) {
        categoryDao.deleteCategory(categoryID);
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") int id, @RequestBody Category newCategory) {
        Optional<Category> optionalCategory  = categoryRepository.findById(id);
        if (optionalCategory .isPresent()) {
            Category category = optionalCategory.get();
            category.setName(newCategory.getName());
            Category updatedCategory = categoryRepository.save(category);
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
