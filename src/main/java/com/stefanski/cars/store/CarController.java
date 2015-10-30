package com.stefanski.cars.store;

import java.net.URI;
import javax.validation.Valid;

import com.wordnik.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.stefanski.cars.error.ErrorResp;

import static com.stefanski.cars.rest.Versions.API_CONTENT_TYPE;
import static java.net.HttpURLConnection.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Dariusz Stefanski
 */
@Slf4j
@RequestMapping("/cars")
@RestController
@Api(value = "Cars", description = "Car storage")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @RequestMapping(method = POST, consumes = API_CONTENT_TYPE, produces = API_CONTENT_TYPE)
    @ApiOperation(value = "Creates a new car", notes = "Returns ID of created car",
            response = CreationResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_CREATED, message = "Success"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid input", response = ErrorResp.class)
    })
    public ResponseEntity<CreationResp> createCar(
            @ApiParam(value = "Car object that needs to be created")
            @Valid @RequestBody CarDto carDto) {

        Long carId = carService.create(carDto);
        HttpHeaders headers = getHttpHeadersWithLocation("/{carId}", carId);
        CreationResp creation = new CreationResp(carId);
        return new ResponseEntity<>(creation, headers, CREATED);
    }

    @RequestMapping(value = "/{carId}", method = PUT, consumes = API_CONTENT_TYPE)
    @ApiOperation(value = "Updates a new car")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Success"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid input", response = ErrorResp.class)
    })
    public HttpStatus updateCar(
            @ApiParam(value = "ID of car that needs to be updated")
            @PathVariable Long carId,
            @ApiParam(value = "Car object that needs to be updated")
            @Valid @RequestBody CarDto carDto) {

        carService.update(carId, carDto);
        return OK;
    }

    @RequestMapping(value = "/{carId}", method = GET)
    @ApiOperation(value = "Finds car by ID", notes = "Returns a car based on ID",
            response = CarDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Success"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid input", response = ErrorResp.class),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "Resource not found", response = ErrorResp.class)
    })
    public ResponseEntity<CarDto> findCar(
            @ApiParam(value = "ID of car that needs to be fetched")
            @PathVariable Long carId) {

        Car car = carService.findById(carId);
        CarDto resp = CarDto.fromCar(car);
        return new ResponseEntity<>(resp, OK);
    }

    @RequestMapping(value = "/{carId}", method = DELETE)
    @ApiOperation(value = "Deletes car by ID")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Success"),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "Resource not found", response = ErrorResp.class)
    })
    public HttpStatus deleteCar(
            @ApiParam(value = "ID of car that needs to be deleted")
            @PathVariable Long carId) {

        carService.deleteCar(carId);
        return OK;
    }

    private HttpHeaders getHttpHeadersWithLocation(String pathSuffix, Object... args) {
        HttpHeaders headers = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(pathSuffix).buildAndExpand(args).toUri();
        headers.setLocation(uri);
        return headers;
    }
}
