package com.concurso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToListConverter());
    }

    public static class StringToListConverter implements Converter<String, List<String>> {
        @Override
        public List<String> convert(String source) {
            if (source == null || source.isEmpty()) {
                return List.of();
            }
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(source, List.class);
            } catch (Exception e) {
                return List.of(source);
            }
        }
    }
}
