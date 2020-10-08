package com.thoughtworks.rslist.config;

import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.RsEventService;
import com.thoughtworks.rslist.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RsServiceConfig {

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public RsEventService rsEventService(RsEventRepository rsEventRepository, UserRepository userRepository) {
        return new RsEventService(rsEventRepository, userRepository);
    }
}
