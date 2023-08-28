package com.example.simpletextcrud.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@TestConfiguration
@ComponentScan(basePackages = "com.example.simpletextcrud.service.mapper",
    includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "\\*Impl")})
public class TestsMappersConfig { }
