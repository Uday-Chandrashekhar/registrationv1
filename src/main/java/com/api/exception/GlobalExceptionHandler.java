package com.api.exception;

import com.api.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

 /*
    To handle exception we write try and catch block but writing try catch block for whole code is not a good practice
    In Spring boot, there is a special implementation called as Developer controller advice class
    They came with annotation @ControllerAdvice
    What does this annotation does: Any exception that occurs in your project will automatically go to the @ControllerAdvice class
    That acts like a global catch block
    where exception is thrown in the project, will automatically go to this class
     */

//In interview there can be a question asked as what is the difference between controller, restcontroller, controllerAdvice
//Answer is: @Controller: acts as a mediator between view and backend business logic
//@RestController: helps us to build API's in SpringBoot
//@ControllerAdvice : used for handling exception
@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)
//Here i need error message, date and uri from where exception is thrown
    public ResponseEntity<ErrorDto> resourceNotFound(
            ResourceNotFoundException r,
            WebRequest request //Gets the object address by springBoot, from where request was made and this exception was thrown
    ){
        ErrorDto error = new ErrorDto(r.getMessage(), new Date() , request.getDescription(true));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //Any other exception occurs other than ResourceNotFoundException, then that exception will come here
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(
           Exception e
    ){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
