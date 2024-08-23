package com.raps.SpringWebFluxService.handler;

import com.raps.SpringWebFluxService.entity.Product;
import com.raps.SpringWebFluxService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {
    private final ProductService productService;

    public Mono<ServerResponse> getAll(ServerRequest request){
        Flux<Product> products = productService.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, Product.class);
    }

    public Mono<ServerResponse> getOne(ServerRequest request){
        Mono<Product> product = productService.getById(Integer.parseInt(request.pathVariable("id")));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(product, Product.class);
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Product> product = request.bodyToMono(Product.class);
        return product.flatMap(p->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.save(p), Product.class));
    }

    public Mono<ServerResponse> update(ServerRequest request){
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<Product> product = request.bodyToMono(Product.class);
        return product.flatMap(p->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.update(id,p), Product.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        int id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.delete(id), Product.class);
    }
}
