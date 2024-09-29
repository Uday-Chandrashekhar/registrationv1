package com.api.service;

import com.api.entity.Registration;
import com.api.exception.ResourceNotFoundException;
import com.api.payload.RegistrationDto;
import com.api.repository.RegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private RegistrationRepository registrationRepository;
    private ModelMapper modelMapper;


    public RegistrationService(RegistrationRepository registrationRepository, ModelMapper modelMapper){
        this.registrationRepository=registrationRepository;
        this.modelMapper = modelMapper;//IOC does not have nay idea which object to create and inject into this dependency
    //There are 2 ways to achieve this(that is.,saying springboot about which object to be created and which dependency to inject it)
        //way 1: creating ConfigClass and using @configuration and @Bean annotations in it
    //As of now i have commented out ConfigClass to learn way2
    }

    public List<RegistrationDto> getRegistrations(){
        List<Registration> registrations = registrationRepository.findAll();
        List<RegistrationDto> dtos= registrations.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return dtos;
    }


    public RegistrationDto createRegistration(RegistrationDto registrationDto) {
        //copy dto to entity
        Registration registration=mapToEntity(registrationDto);

        Registration savedEntity = registrationRepository.save(registration); //After saving it returns back Entity

    //copy entity to dto
        RegistrationDto dto = mapToDto(savedEntity);
        return dto;
    }

    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);

    }

    public Registration updateRegistration(long id, Registration registration) {
        Registration r=registrationRepository.findById(id).get();
        r.setId(id);
        r.setName(registration.getName());
        r.setEmail(registration.getEmail());
        r.setMobile(registration.getMobile());
        Registration savedEntity = registrationRepository.save(r);
        return savedEntity;
    }

/*In todays class(02/09/2024) we try to optimize below code
Here we have 3 fields and code looks fine but what if we have 15-20 fields
    Then the code become large
    But we have few java libraries that we can add in pom.xml to do this job
    Ex: ModelMapper, MapsStruct
    Here we went to ModelMapper maven dependency and copied dependency from there and pasted it in pom.xml


   */

    Registration mapToEntity(RegistrationDto registrationDto){
        Registration registration=modelMapper.map(registrationDto,Registration.class);
//        Registration registration = new Registration();
//        registration.setName(registrationDto.getName());
//        registration.setEmail(registrationDto.getEmail());
//        registration.setMobile(registrationDto.getMobile());
        return registration;
    }

    RegistrationDto mapToDto(Registration registration){
        RegistrationDto dto = modelMapper.map(registration, RegistrationDto.class);//map("from","to.class")
//        RegistrationDto dto = new RegistrationDto();
//        dto.setName(registration.getName());
//        dto.setEmail(registration.getEmail());
//        dto.setMobile(registration.getMobile());
        return dto;
    }
//Here if file is found ok, not found then throw custom exception ResourceNotFoundException
    public RegistrationDto getRegistrationById(long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Record Not Found"));

        return mapToDto(registration);
    }

    /*
    To handle exception we write try and catch block but writing try catch block for whole code is not a good practice
    In Spring boot, there is a special implementation called as Developer controller advice class
    They came with annotation @ControllerAdvice
    What does this annotation does: Any exception that occurs in your project will automatically go to the @ControllerAdvice class
    That acts like a global catch block
    where exception is thrown in the project, will automatically go to this class
     */
}
