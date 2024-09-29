package com.api.controller;

import com.api.entity.Registration;
import com.api.payload.RegistrationDto;
import com.api.repository.RegistrationRepository;
import com.api.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    from the browser we are calling controller,
    controller calls service layer, service layer gets all the data and
    gives it to controller and controller converts it into json objects and displays on browser

 */

@RestController //using @RestController it makes this file as API
@RequestMapping("/api/v1/registration") //Why v1: this value tells which version of the software and by writing api it showcases this is not a ordinary url but api url
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping //By using this annotations all the data that comes from database which is java objects is converted into json objects
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations(){
        List<RegistrationDto> dtos = registrationService.getRegistrations();
        //return registrations; //these registraions bring java ojects from database and using @GetMapping converts into json objects
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    /*
        The method return type should be ResponseEntity: One of the reason for using ResponseEntity is we need to return status code to post man
        @PostMapping is used for saving the data in database
        The variable name that we have mentioned in the Registration.java are the keys in json in postman(need to be same)
        @ReuestBody: what this annotation does is it takes the json objects from postman and stores in registration. Only from this annotation, we can
        copy json into registration if and only if the variable names in Entity class and key present in json matches
     */


    /*
    Here we are returning complete entity after completion of task
    But what if i want only some part of the content to be returned
    Let us say i dont want to return mobile number and i need only name and email..
    I want to customise what all data need to be returned and how do i control this
    For this i wont use <Registration> as returned entity class instead i will create a DTO
     */


    /*
    @PostMapping
    public ResponseEntity<RegistrationDto> createRegistration( //Creating Registration
           @Valid @RequestBody RegistrationDto registrationDto,
           BindingResult result

    ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getField() ,HttpStatus.CREATED);
            //since the return type here is multiple types we will add "?" in place of<RegistrationDto> or add <Object> which is super most class in java
            //as shouwn below
        }
        RegistrationDto regDto = registrationService.createRegistration(registrationDto);
        return new ResponseEntity<>(regDto,HttpStatus.CREATED);
    }
     */
    @PostMapping
    public ResponseEntity<?> createRegistration( //Creating Registration
           @Valid @RequestBody RegistrationDto registrationDto,
           BindingResult result

    ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.CREATED);
        }
        RegistrationDto regDto = registrationService.createRegistration(registrationDto);
        return new ResponseEntity<>(regDto,HttpStatus.CREATED);
    }

    //Here i want to return simple string response, so we are using String generics
    @DeleteMapping //helps in deleting data from database via API
    public ResponseEntity<String> deleteRegistration(
            @RequestParam long id                        //Why Request Param: will read the value from url and initialize this variable
    ){
        registrationService.deleteRegistration(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK); //Delete,UPdate, Retrieve  all have status code 200, here Deleted is a String
    }

    @PutMapping ("/{id}")  //PutMapping helps us to update the record in the database via API
    public ResponseEntity<Registration> updateRegistration(
            @PathVariable long id, //
            @RequestBody Registration registration
    ){
       Registration updateReg= registrationService.updateRegistration(id, registration);
       return new ResponseEntity<>(updateReg, HttpStatus.OK);
    }

    /*
    Let us say i give id=1 in url and data does not exist. I get an exception saying no data found
    Instead I want a message saying registration does not exist
    Here we are creating a custom exception named ResourceNotFoundException
     */

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDto> getRegistrationById(
            @PathVariable long id
    ){
       RegistrationDto dto = registrationService.getRegistrationById(id);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    

}
