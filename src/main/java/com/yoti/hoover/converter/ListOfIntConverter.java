package com.yoti.hoover.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.Collections;
import java.util.List;

/**
 * ListOfIntConverter.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@RequiredArgsConstructor
@Slf4j
public class ListOfIntConverter implements AttributeConverter<List<Integer>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        try {
            return (attribute == null) ? null : objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("Error converting from entity attribute with converter {}: {}", ListOfIntConverter.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        try {
            return (dbData == null) ? Collections.emptyList() : objectMapper.readValue(dbData, new TypeReference<List<Integer>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Error converting to entity attribute with converter {}: {}", ListOfIntConverter.class.getSimpleName(), e.getMessage());
            return Collections.emptyList();
        }
    }
}
