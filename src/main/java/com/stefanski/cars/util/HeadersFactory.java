package com.stefanski.cars.util;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Dariusz Stefanski
 */
public class HeadersFactory {

    public static HttpHeaders withLocation(Object id) {
        HttpHeaders headers = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();
        headers.setLocation(uri);
        return headers;
    }
}
