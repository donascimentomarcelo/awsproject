package br.com.awsproject.controller;

import br.com.awsproject.model.Product;
import br.com.awsproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) throws Exception {
        Optional<Product> optional = productRepository.findById(id);
        final Product product = optional.orElseThrow(() -> new Exception());
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product obj = productRepository.save(product);
        return new ResponseEntity<Product>(obj, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @RequestBody Product product,
            @PathVariable long id) throws Exception {

        productRepository.findById(id)
                        .orElseThrow(() -> new Exception());

        product.setId(id);

        productRepository.save(product);

        return new ResponseEntity<Product>(product, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(
            @PathVariable long id) throws Exception {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception());

        product.setId(id);

        productRepository.delete(product);

        return new ResponseEntity<Product>(product, HttpStatus.NO_CONTENT);
    }
}
