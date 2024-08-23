package com.raps.SpringWebFluxService.repository;

import com.raps.SpringWebFluxService.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {

    @Query("Select * from Product where id <> :id and name = :name")
    Mono<Product> repeatedName(int id,String name);
}
