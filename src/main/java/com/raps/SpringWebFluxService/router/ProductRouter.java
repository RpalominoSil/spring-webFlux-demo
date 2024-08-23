package com.raps.SpringWebFluxService.router;

import com.raps.SpringWebFluxService.handler.ProductHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {
    private static final String PATH = "product";

    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }

    @Bean
    RouterFunction<ServerResponse> router(ProductHandler handler){
        return RouterFunctions.route()
                .GET(PATH,handler::getAll)
                .GET(PATH+"/{id}",handler::getOne)
                .POST(PATH,handler::save)
                .PUT(PATH+"/{id}",handler::update)
                .DELETE(PATH+"/{id}",handler::delete)
                .build();
    }
}
