package com.stefanski.cars.store;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dariusz Stefanski
 */
@Configuration
class CacheConfiguration {

    static final String CAR_CACHE = "cars";

    @Bean
    CacheManager cacheManager() {
        return new EhCacheCacheManager(net.sf.ehcache.CacheManager.newInstance());
    }
}
