package com.desafioanotaai.services;

import com.desafioanotaai.domain.category.Category;
import com.desafioanotaai.domain.category.CategoryDTO;
import com.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.desafioanotaai.domain.message.MessageDTO;
import com.desafioanotaai.repositories.CategoryRepository;
import com.desafioanotaai.services.aws.AwsSnsService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AwsSnsService snsService;
    public CategoryService(CategoryRepository categoryRepository, AwsSnsService snsService)
    {
        this.categoryRepository = categoryRepository;
        this.snsService = snsService;
    }

    public Category create(CategoryDTO data)
    {
        Category category = new Category(data);
        this.categoryRepository.save(category);

        this.snsService.publish(new MessageDTO(category.toString()));

        return category;
    }


    public Category update(String id, CategoryDTO data)
    {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if(data.title() != null && !data.title().isEmpty()) category.setTitle(data.title());
        if(data.description() != null && !data.description().isEmpty()) category.setDescription(data.description());

        this.categoryRepository.save(category);

        this.snsService.publish(new MessageDTO(category.toString()));
        return category;
    }

    public Optional<Category> getById(String id)
    {
        return this.categoryRepository.findById(id);
    }

    public void delete(String id)
    {
        Category category = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        this.categoryRepository.delete(category);


        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("action", "delete");
        json.put("ownerId", category.getOwnerId());
        json.put("type", "categoria");

        this.snsService.publish(new MessageDTO(json.toString()));
    }

    public List<Category> getAll()
    {
        return this.categoryRepository.findAll();
    }

}
