package com.stefanski.cars

import com.stefanski.cars.Application
import com.stefanski.cars.store.CarFixture
import groovy.json.JsonSlurper
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import static com.stefanski.cars.rest.Versions.API_CONTENT_TYPE
import static com.stefanski.cars.store.CarFixture.CAR_WITHOUT_MODEL
import static com.stefanski.cars.store.CarFixture.OPEL_CORSA
import static com.stefanski.cars.store.CarFixture.OPEL_CORSA
import static org.springframework.http.HttpMethod.DELETE
import static org.springframework.http.HttpMethod.PUT
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

/**
 * End-to-end black box tests.
 * This is a single multi-step scenario, where the order of tests matter in order
 * to keep them short.
 *
 * @author Dariusz Stefanski
 */
@Stepwise
@IntegrationTest
@WebAppConfiguration
@ContextConfiguration(classes = [Application], loader = SpringApplicationContextLoader)
class EndToEndIntegrationSpec extends Specification {

    private static final String CARS_URL = "http://localhost:8888/cars"

    @Shared
    TestRestTemplate rest = new TestRestTemplate()

    @Shared
    Long carId

    def "should return 404 when getting not existing car"() {
        when:
            def response = rest.getForEntity("$CARS_URL/123", String)
        then:
            response.statusCode == NOT_FOUND
    }

    def "should return 404 when updating not existing car"() {
        given:
            def entity = requestWith(OPEL_CORSA)
        when:
            def response = rest.exchange("$CARS_URL/123", PUT, entity, String)
        then:
            response.statusCode == NOT_FOUND
    }

    def "should return 404 when deleting not existing car"() {
        when:
            def response = rest.exchange("$CARS_URL/123", DELETE, null, String)
        then:
            response.statusCode == NOT_FOUND
    }

    def "validation should fail when car does not contain model"() {
        given:
            def entity = requestWith(CarFixture.CAR_WITHOUT_MODEL)
        when:
            def response = rest.postForEntity(CARS_URL, entity, String)
        then:
            response.statusCode == BAD_REQUEST
            def jsonResp = parseJson(response)
            jsonResp['error'] == 'Invalid parameter'
            jsonResp['message'] == 'model may not be empty'
    }

    def "should create car"() {
        given:
            def entity = requestWith(OPEL_CORSA)
        when:
            def response = rest.postForEntity(CARS_URL, entity, String)
            carId = parseJson(response)['id']
        then:
            response.statusCode == CREATED
            carId != null
    }

    def "should find car"() {
        when:
            def response = rest.getForEntity("$CARS_URL/$carId", String)
        then:
            def jsonResp = parseJson(response)
            jsonResp['make'] == OPEL_CORSA.make
            jsonResp['model'] == OPEL_CORSA.model
            jsonResp['attributes']['mileage'] == OPEL_CORSA.getAttribute('mileage')
    }

    def "should update car"() {
        given:
            def car = OPEL_CORSA
            car.year = 2015
            car.attributes['mileage'] = '1'
            def entity = requestWith(car)
        when:
            def response = rest.exchange("$CARS_URL/${carId}", PUT, entity, String)
        then:
            response.statusCode == OK
            def jsonResp = parseJson(rest.getForEntity("$CARS_URL/$carId", String))
            jsonResp['year'] == car.year
            jsonResp['attributes']['mileage'] == car.getAttribute('mileage')
    }

    def "should delete car"() {
        when:
            def response = rest.exchange("$CARS_URL/$carId", DELETE, null, String)
        then:
            response.statusCode == OK
            rest.getForEntity("$CARS_URL/$carId", String).statusCode == NOT_FOUND
    }

    private Object parseJson(ResponseEntity<String> response) {
        new JsonSlurper().parseText(response.body)
    }

    private HttpEntity<Object> requestWith(Object body) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.valueOf(API_CONTENT_TYPE))
        return new HttpEntity<Object>(body, headers)
    }

}
