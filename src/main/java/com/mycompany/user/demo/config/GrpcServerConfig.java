package com.mycompany.user.demo.config;

import com.mycompany.user.demo.grpc.UserGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {
    @Bean(destroyMethod = "shutdown")
    public Server userGrpcServer(
            UserGrpcService userGrpcService,
            @Value("${grpc.server.port:9090}") int grpcServerPort
    ) throws IOException {
        return ServerBuilder.forPort(grpcServerPort)
                .addService(userGrpcService)
                .build()
                .start();
    }
}
