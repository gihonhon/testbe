package com.binar.finalproject.flightticket.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("FlightTicket")
                        .description("API for Application Flight Ticketing")
                        .version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearer-jwt", Arrays.asList("read", "write"))
                                .addList("bearer-key", Collections.emptyList())
                )
                .servers(servers());
    }

    private List<Server> servers() {
        List<Server> servers = new ArrayList<>();

        Server serverDevLocal = new Server();
        serverDevLocal.setUrl("http://localhost:8080/");
        serverDevLocal.setDescription("Main server for Development local");

        Server serverProd = new Server();
        serverProd.setUrl("https://be-flightticket-production.up.railway.app/");
        serverProd.setDescription("Main server for Production");

        Server serverDev = new Server();
        serverDev.setUrl("https://api-flight.up.railway.app/");
        serverDev.setDescription("Main server for Development");

        servers.add(serverDevLocal);
        servers.add(serverDev);
        servers.add(serverProd);

        return servers;
    }
}
