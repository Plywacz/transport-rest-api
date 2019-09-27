package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 27.09.2019
*/

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public abstract class BasicDto {
    public  static final LocalDate buildDate(JsonNode date){
        LocalDate ld=null;
        if (date != null) {
            try {
                ld = LocalDate.parse(date.asText());
            }
            catch (DateTimeParseException e) {
                CharSequence cs = date.asText();
                throw new DateTimeParseException("provide date in correct format i.e: yyyy-mm-dd", cs, 400);
            }
        }
        return ld;
    }
}
