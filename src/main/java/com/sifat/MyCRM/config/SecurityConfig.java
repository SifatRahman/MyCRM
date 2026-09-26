package com.sifat.MyCRM.config;

import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//
//@Bean
//public SecurityFilterChain securityFilterChain(
//        HttpSecurity http
//) throws Exception {
//
//    http
//            .cors(cors -> {})
//            .csrf(csrf -> csrf.disable())
//
//            // your existing authorization configuration
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                    // your other rules...
//                    .anyRequest().authenticated()
//            );
//
//    return http.build();
//}