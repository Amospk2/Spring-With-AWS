package com.desafioanotaai.services;

import com.desafioanotaai.domain.category.Category;
import com.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.desafioanotaai.domain.message.MessageDTO;
import com.desafioanotaai.domain.product.Product;
import com.desafioanotaai.domain.product.ProductDTO;
import com.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.desafioanotaai.repositories.ProductRepository;
import com.desafioanotaai.services.aws.AwsSnsService;
import org.json.JSONObject;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private final AwsSnsService snsService;

    public ProductService(
            ProductRepository productRepository, CategoryService categoryService, AwsSnsService snsService
    ){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.snsService = snsService;
    }

    public Product create(ProductDTO data)
    {
        Product newProduct = new Product(data);

        this.productRepository.save(newProduct);

        this.snsService.publish(new MessageDTO(newProduct.toString()));

        return newProduct;
    }


    public Product update(String id, ProductDTO data) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.categoryService.getById(data.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        if(data.title() != null && !data.title().isEmpty()) product.setTitle(data.title());
        if(data.description() != null && !data.description().isEmpty()) product.setDescription(data.description());
        if(!(data.price() == null)) product.setPrice(data.price());
        if(!(data.categoryId() == null)) product.setCategory(data.categoryId());

        this.productRepository.save(product);

        this.snsService.publish(new MessageDTO(product.toString()));

        return product;
    }

    public List<Product> getAll(){
        return this.productRepository.findAll();
    }

    public Optional<Product> getById(String id)
    {
        return this.productRepository.findById(id);
    }


    public void delete(String id)
    {
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        this.productRepository.delete(product);

        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("action", "delete");
        json.put("ownerId", product.getOwnerId());
        json.put("type", "produto");
        this.snsService.publish(new MessageDTO(json.toString()));
    }



}
