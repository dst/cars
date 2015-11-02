package com.stefanski.cars.store;

import javax.validation.Valid;

import com.wordnik.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stefanski.cars.api.CarResource;
import com.stefanski.cars.api.CreationResp;
import com.stefanski.cars.api.ErrorResp;
import com.stefanski.cars.util.HeadersFactory;

import static com.stefanski.cars.api.Versions.API_CONTENT_TYPE;
import static java.net.HttpURLConnection.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Dariusz Stefanski
 */
@Slf4j
@RequestMapping("/cars")
@RestController
@Api(value = "Cars")
class StoreController {

    private CarStore carStore;

    @Autowired
    StoreController(CarStore carStore) {
        this.carStore = carStore;
    }

    @RequestMapping(method = POST, consumes = API_CONTENT_TYPE, produces = API_CONTENT_TYPE)
    @ApiOperation(value = "Creates a new car", notes = "Returns ID of created car", response = CreationResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_CREATED, message = "Success"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid input", response = ErrorResp.class)
    })
    ResponseEntity<CreationResp> createCar(
            @ApiParam(value = "Car object that needs to be created")
            @Valid @RequestBody CarResource carResource) {

        Long carId = carStore.createCar(carResource.toCar());
        HttpHeaders headers = HeadersFactory.withLocation(carId);
        CreationResp creation = new CreationResp(carId);
        return new ResponseEntity<>(creation, headers, CREATED);
    }

    @RequestMapping(value = "/{carId}", method = PUT, consumes = API_CONTENT_TYPE)
    @ApiOperation(value = "Updates a new car")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Success"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid input", response = ErrorResp.class)
    })
    HttpStatus updateCar(
            @ApiParam(value = "ID of car that needs to be updated")
            @PathVariable Long carId,
            @ApiParam(value = "Car object that needs to be updated")
            @Valid @RequestBody CarResource carResource) {

        Car car = carResource.toCar();
        car.setId(carId);
        carStore.updateCar(car);
        return OK;
    }

    @RequestMapping(value = "/{carId}", method = GET)
    @ApiOperation(value = "Finds a car by ID", notes = "Returns a car based on ID",
            response = CarResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Success"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid input", response = ErrorResp.class),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "Resource not found", response = ErrorResp.class)
    })
    ResponseEntity<CarResource> findCar(
            @ApiParam(value = "ID of car that needs to be fetched")
            @PathVariable Long carId) {

        Car car = carStore.findCar(carId);
        return new ResponseEntity<>(CarResource.fromCar(car), OK);
    }

    @RequestMapping(value = "/{carId}", method = DELETE)
    @ApiOperation(value = "Deletes a car by ID")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Success"),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "Resource not found", response = ErrorResp.class)
    })
    HttpStatus deleteCar(
            @ApiParam(value = "ID of car that needs to be deleted")
            @PathVariable Long carId) {

        carStore.deleteCar(carId);
        return OK;
    }

    @ExceptionHandler(CarNotFoundException.class)
    private ResponseEntity<ErrorResp> handleCarNotFoundException(CarNotFoundException ex) {
        log.warn("Car not found: {}", ex.getMessage());
        ErrorResp error = new ErrorResp("Car does not exist", ex.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(error, NOT_FOUND);
    }
}
