package com.api.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //By adding this annotation what happens is moment program is run it gets loaded to spring boot and springbott understands whatever code is written inside in it
public class configClass {

    @Bean //role: when i start the project, the first set of files that gets loaded in your spring boot, configuration class will run and in configuration spring IOC will see @Bean and now it takes the object(Here ModelMapper) and puts it in modelMapper reference variable
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}


