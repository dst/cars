package com.stefanski.cars.store.rest;

import javax.validation.Valid;

import com.wordnik.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.stefanski.cars.store.Car;
import com.stefanski.cars.store.CarNotFoundException;
import com.stefanski.cars.store.CarService;
import com.stefanski.cars.util.ErrorMessageFactory;
import com.stefanski.cars.util.HeadersFactory;

import static com.stefanski.cars.api.Versions.API_CONTENT_TYPE;
import static com.stefanski.cars.store.rest.ErrorResp.CAR_NOT_FOUND_ERR;
import static com.stefanski.cars.store.rest.ErrorResp.INVALID_PARAM_ERR;
import static com.stefanski.cars.store.rest.ErrorResp.INVALID_TYPE_ERR;
import static java.net.HttpURLConnection.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Dariusz Stefanski
 */
@Slf4j
@RequestMapping("/cars")
@RestController
@Api(value = "Cars", description = "Car storage")
class StoreController {

    private CarService carService;

    @Autowired
    StoreController(CarService carService) {
        this.carService = carService;
    }

    @RequestMapping(method = POST, consumes = API_CONTENT_TYPE, produces = API_CONTENT_TYPE)
    @ApiOperation(value = "Creates a new car", notes = "Returns ID of created car", response = CreationResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_CREATED, message = "Success"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid input", response = ErrorResp.class)
    })
    ResponseEntity<CreationResp> createCar(
            @ApiParam(value = "Car object that needs to be created")
            @Valid @RequestBody CarToStore carToStore) {

        Long carId = carService.createCar(carToStore.toCar());
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
            @Valid @RequestBody CarToStore carToStore) {

        Car car = carToStore.toCar();
        car.setId(carId);
        carService.updateCar(car);
        return OK;
    }

    @RequestMapping(value = "/{carId}", method = GET)
    @ApiOperation(value = "Finds car by ID", notes = "Returns a car based on ID",
            response = CarResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Success"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid input", response = ErrorResp.class),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "Resource not found", response = ErrorResp.class)
    })
    ResponseEntity<CarResource> findCar(
            @ApiParam(value = "ID of car that needs to be fetched")
            @PathVariable Long carId) {

        Car car = carService.findCar(carId);
        return new ResponseEntity<>(CarResource.fromCar(car), OK);
    }

    @RequestMapping(value = "/{carId}", method = DELETE)
    @ApiOperation(value = "Deletes car by ID")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Success"),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "Resource not found", response = ErrorResp.class)
    })
    HttpStatus deleteCar(
            @ApiParam(value = "ID of car that needs to be deleted")
            @PathVariable Long carId) {

        carService.deleteCar(carId);
        return OK;
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorResp> handleCarNotFoundException(CarNotFoundException ex) {
        log.warn("Car not found: {}", ex.getMessage());
        ErrorResp error = new ErrorResp(CAR_NOT_FOUND_ERR, ex.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResp> handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        log.warn("Validation error: {}", result);
        String message = ErrorMessageFactory.fromFailedValidation(result);
        ErrorResp error = new ErrorResp(INVALID_PARAM_ERR, message, BAD_REQUEST);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorResp> handleTypeMismatchException(TypeMismatchException ex) {
        log.warn("Invalid type of parameter: {}", ex.getMessage());
        ErrorResp error = new ErrorResp(INVALID_TYPE_ERR, INVALID_TYPE_ERR, BAD_REQUEST);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }
}
