package com.rafaelsousa.api.dto;

import org.modelmapper.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MappingToModel {

    public static final Converter<String, LocalDate> toStringDate = (source) -> {
        if (Objects.nonNull(source) && !source.toString().trim().isEmpty()) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            return LocalDate.parse(source.toString(), format);
        }

        return null;
    };

}
