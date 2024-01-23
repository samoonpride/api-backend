package com.samoonpride.backend.config;

import com.samoonpride.backend.dto.request.CreateLineUserRequest;
import com.samoonpride.backend.model.LineUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<CreateLineUserRequest, LineUser>() {
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        return modelMapper;
    }


}
