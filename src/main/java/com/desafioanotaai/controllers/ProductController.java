package com.desafioanotaai.controllers;

import com.desafioanotaai.domain.product.Product;
import com.desafioanotaai.domain.product.ProductDTO;
import com.desafioanotaai.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO data)
    {
        Product product = this.productService.create(data);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable("id") String id,
            @RequestBody ProductDTO data
    )
    {
        Product product = this.productService.update(id, data);
        return ResponseEntity.ok().body(product);
    }


    @GetMapping
    public ResponseEntity<List<Product>> getAll()
    {
        List<Product> products = this.productService.getAll();
        return ResponseEntity.ok().body(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(
            @PathVariable("id") String id
    )
    {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> findById(
            @PathVariable("id") String id
    )
    {
        Optional<Product> product = this.productService.getById(id);
        return ResponseEntity.ok().body(product);
    }

}
