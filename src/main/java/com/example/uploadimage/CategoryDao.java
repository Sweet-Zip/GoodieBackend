package com.example.uploadimage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryDao {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category addNewCategory(Category employee) {
        return categoryRepository.save(employee);
    }

    public void deleteCategory(int categoryID) {
        categoryRepository.deleteById(categoryID);
    }

    public List<Category> getAllCategory() {
        List<Category> employees = new ArrayList<>();
        Streamable.of(categoryRepository.findAll())
                .forEach(employees::add);
        return employees;
    }
}
