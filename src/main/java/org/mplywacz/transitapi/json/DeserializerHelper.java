package org.mplywacz.transitapi.json;
/*
Author: BeGieU
Date: 10.11.2019
*/

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

abstract class DeserializerHelper {
    static String getNodeStringValue(JsonNode node, String errorMsg) {
        if (node != null) {
            return node.asText();
        }
        else {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    static BigDecimal getNodeBigDecimalValue(JsonNode node, String errorMsg) {
        if (node != null) {
            try {
                return new BigDecimal(node.asText());
            }
            catch (NumberFormatException e) {
                throw new NumberFormatException("provide correct numerical price value given: " + node.asText());
            }
        }
        else
            throw new IllegalArgumentException(errorMsg);
    }

    static Long getNodeLongValue(JsonNode node, String errorMsg) {
        if (node != null) {
            try {
                return Long.valueOf(node.asText());
            }
            catch (NumberFormatException e) {
                throw new NumberFormatException("provide correct numerical price value given: " + node.asText());
            }
        }
        else
            throw new IllegalArgumentException(errorMsg);
    }

    static LocalDate buildDateFromNode(JsonNode date) {
        if (date != null) {
            try {
                return LocalDate.parse(date.asText());
            }
            catch (DateTimeParseException e) {
                CharSequence cs = date.asText();
                throw new DateTimeParseException("provide date in correct format i.e: yyyy-mm-dd", cs, 400);
            }
        }
        else
            throw new IllegalArgumentException("Date not provided");
    }
}
