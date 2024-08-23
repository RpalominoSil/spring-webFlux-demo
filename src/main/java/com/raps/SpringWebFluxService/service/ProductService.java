package com.raps.SpringWebFluxService.service;

import com.raps.SpringWebFluxService.entity.Product;
import com.raps.SpringWebFluxService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<Product> getAll(){
        return productRepository.findAll();
    }

    public Mono<Product> getById(int id){
        return productRepository.findById(id).switchIfEmpty(Mono.error(new Exception("Product not found")));
    }

    public Mono<Product> save(Product product){
        Mono<Boolean> exists = productRepository.existsById(product.getId());
        return exists.flatMap(e->e
            ? Mono.error(new Exception("Product already created"))
            : productRepository.save(Product.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                .build()));
    }

    public Mono<Product> update(int id, Product product){
        Mono<Boolean> exists = productRepository.findById(id).hasElement();
        Mono<Boolean> repeated = productRepository.repeatedName(id, product.getName()).hasElement();
        return exists.flatMap(e->e
            ? repeated.flatMap(r->r
                ? Mono.error(new Exception("Product already exists"))
                : productRepository.save(Product.builder()
                        .id(id)
                        .name(product.getName())
                        .price(product.getPrice())
                    .build()))
            : Mono.error(new Exception("Product does not exist")));
    }

    public Mono<Void> delete(int id){
        Mono<Boolean> exists = productRepository.existsById(id);
        return exists.flatMap(e->e
            ? productRepository.deleteById(id)
            : Mono.error(new Exception("Product does not exist")));
    }
}
