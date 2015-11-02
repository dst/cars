package com.stefanski.cars

import com.stefanski.cars.api.CarResource
import com.stefanski.cars.search.CarFilters
import com.stefanski.cars.store.Car
import groovy.json.JsonSlurper
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import static com.stefanski.cars.api.CarResourceExamples.CAR_WITHOUT_MODEL_RESOURCE
import static com.stefanski.cars.api.CarResourceExamples.OPEL_CORSA_RESOURCE
import static com.stefanski.cars.api.Versions.API_CONTENT_TYPE
import static org.springframework.http.HttpMethod.DELETE
import static org.springframework.http.HttpMethod.PUT
import static org.springframework.http.HttpStatus.*

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
@ContextConfiguration(classes = [CarApplication], loader = SpringApplicationContextLoader)
class EndToEndIntegrationSpec extends Specification {

    private static final String CARS_URL = "http://localhost:8888/cars"
    private static final long NOT_EXISTING_CAR_ID = 123L

    @Shared
    TestRestTemplate rest = new TestRestTemplate()

    @Shared
    Long carId

    @Shared
    CarResource opelCorsa

    def "should return 404 when getting not existing car"() {
        when:
            def response = findCar(NOT_EXISTING_CAR_ID)
        then:
            response.statusCode == NOT_FOUND
    }

    def "should return 404 when updating not existing car"() {
        when:
            def response = updateCar(NOT_EXISTING_CAR_ID, OPEL_CORSA_RESOURCE)
        then:
            response.statusCode == NOT_FOUND
    }

    def "should return 404 when deleting not existing car"() {
        when:
            def response = deleteCar(NOT_EXISTING_CAR_ID)
        then:
            response.statusCode == NOT_FOUND
    }

    def "validation should fail when car does not contain model"() {
        when:
            def response = createCar(CAR_WITHOUT_MODEL_RESOURCE)
        then:
            response.statusCode == BAD_REQUEST
            def jsonResp = parseJson(response)
            jsonResp['error'] == 'Invalid parameter'
            jsonResp['message'] == 'model may not be empty'
    }

    def "should create car"() {
        when:
            def response = createCar(OPEL_CORSA_RESOURCE)
            carId = parseJson(response)['id']
        then:
            response.statusCode == CREATED
            carId != null
    }

    def "should find car by id"() {
        when:
            def response = findCar(carId)
        then:
            response.statusCode == OK
            def jsonResp = parseJson(response)
            assertTheSame(jsonResp, OPEL_CORSA_RESOURCE)
    }

    def "should find car by attributes"() {
        when:
            def response = findCar(OPEL_CORSA_RESOURCE.attributes)
        then:
            response.statusCode == OK
            def jsonResp = parseJson(response)
            assertTheSame(jsonResp[0], OPEL_CORSA_RESOURCE)
    }

    def "should update car"() {
        given:
            opelCorsa = OPEL_CORSA_RESOURCE
            opelCorsa.year = 2015
            opelCorsa.attributes.remove('origin')
            opelCorsa.attributes['age'] = 'very new'
            opelCorsa.attributes['speed'] = 'fast'
        when:
            def response = updateCar(carId, opelCorsa)
        then:
            response.statusCode == OK
            def jsonResp = parseJson(findCar(carId))
            assertTheSame(jsonResp, opelCorsa)
    }

    def "should find car by updated attributes"() {
        when:
            def response = findCar(opelCorsa.attributes)
        then:
            response.statusCode == OK
            def jsonResp = parseJson(response)
            assertTheSame(jsonResp[0], opelCorsa)
    }


    def "should delete car"() {
        when:
            def response = deleteCar(carId)
        then:
            response.statusCode == OK
            findCar(carId).statusCode == NOT_FOUND
    }

    private ResponseEntity<String> createCar(CarResource car) {
        def entity = requestWith(car)
        return rest.postForEntity(CARS_URL, entity, String)
    }

    private ResponseEntity<String> findCar(Long carId) {
        return rest.getForEntity("$CARS_URL/$carId", String)
    }

    private ResponseEntity<String> findCar(Map<String, String> attributes) {
        def entity = requestWith(new CarFilters(attributes: attributes))
        return rest.postForEntity("$CARS_URL/search", entity, String)
    }

    private ResponseEntity<String> updateCar(Long carId, CarResource car) {
        def entity = requestWith(car)
        return rest.exchange("$CARS_URL/${carId}", PUT, entity, String)
    }

    private ResponseEntity<String> deleteCar(Long carId) {
        return rest.exchange("$CARS_URL/$carId", DELETE, null, String)
    }

    private Object parseJson(ResponseEntity<String> response) {
        return new JsonSlurper().parseText(response.body)
    }

    private HttpEntity<Object> requestWith(Object body) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.valueOf(API_CONTENT_TYPE))
        return new HttpEntity<Object>(body, headers)
    }

    private void assertTheSame(Object json, CarResource car) {
        assert json['make'] == car.make
        assert json['model'] == car.model
        assert json['year'] == car.year
        assert json['engineDisplacement'] == car.engineDisplacement
        assert json['attributes'].size() == car.attributes.size()
        OPEL_CORSA_RESOURCE.attributes.each { name, value ->
            assert json['attributes'][name] == value
        }
    }
}
