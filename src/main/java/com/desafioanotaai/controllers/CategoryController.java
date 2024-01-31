package com.desafioanotaai.controllers;

import com.desafioanotaai.domain.category.Category;
import com.desafioanotaai.domain.category.CategoryDTO;
import com.desafioanotaai.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDTO data)
    {
        Category category = this.categoryService.create(data);
        return ResponseEntity.ok().body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(
            @PathVariable("id") String id,
            @RequestBody CategoryDTO data
    )
    {
        Category category = this.categoryService.update(id, data);
        return ResponseEntity.ok().body(category);
    }


    @GetMapping
    public ResponseEntity<List<Category>> getAll()
    {
        List<Category> categorys = this.categoryService.getAll();
        return ResponseEntity.ok().body(categorys);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(
            @PathVariable("id") String id
    )
    {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> findById(
            @PathVariable("id") String id
    )
    {
        Optional<Category> category = this.categoryService.getById(id);
        return ResponseEntity.ok().body(category);
    }
}
