package com.stefanski.cars.search;

import java.io.IOException;

import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dariusz Stefanski
 */
@Configuration
class MongoConfiguration {

    @Bean(destroyMethod="close")
    public Mongo mongo(
            @Value("${mongodb.host}") String host,
            @Value("${mongodb.port}") int port) throws IOException {
        return new EmbeddedMongoBuilder()
                .version("2.4.5")
                .bindIp(host)
                .port(port)
                .build();
    }
}
