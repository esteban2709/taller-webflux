package co.nequi.r2dbc.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
public class DbDiag {

    @Autowired
    private ConnectionFactory connectionFactory; // Cambiado de ConnectionPool a ConnectionFactory

    @Bean
    public CommandLineRunner checkDatabaseConnection() {
        return args -> {
            System.out.println("Verificando conexión a la base de datos...");

            Mono.from(connectionFactory.create())
                    .flatMap(connection ->
                            Mono.from(connection.createStatement("SELECT current_database(), current_schema()")
                                            .execute())
                                    .flatMap(result ->
                                            Mono.from(result.map((row, rowMetadata) ->
                                                    Map.of(
                                                            "database", row.get(0, String.class),
                                                            "schema", row.get(1, String.class)
                                                    )
                                            ))
                                    )
                                    .doOnNext(data -> System.out.println("Conectado a: " + data))
                                    .doFinally(signalType -> connection.close())
                    )
                    .subscribe(
                            data -> System.out.println("Conexión exitosa"),
                            error -> System.err.println("Error de conexión: " + error.getMessage())
                    );

            Thread.sleep(2000);
        };
    }


}