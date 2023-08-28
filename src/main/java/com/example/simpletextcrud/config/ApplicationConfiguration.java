package com.example.simpletextcrud.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
  @Bean
  public ErrorAttributeOptions errorAttributeOptions(
      @Value("${server.error.include-message}") IncludeAttribute includeMessage,
      @Value("${server.error.include-stacktrace}") IncludeStacktrace includeStackTrace) {
    Set<ErrorAttributeOptions.Include>
        includes = new HashSet<>(Set.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.BINDING_ERRORS));
    if (includeMessage.equals(IncludeAttribute.ALWAYS)) {
      includes.add(ErrorAttributeOptions.Include.MESSAGE);
    }
    if (includeStackTrace.equals(IncludeStacktrace.ALWAYS)) {
      includes.add(ErrorAttributeOptions.Include.STACK_TRACE);
    }
    return ErrorAttributeOptions.of(includes);
  }
}
