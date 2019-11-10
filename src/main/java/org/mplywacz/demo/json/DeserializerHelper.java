package org.mplywacz.demo.json;
/*
Author: BeGieU
Date: 10.11.2019
*/

import com.fasterxml.jackson.databind.JsonNode;

abstract class DeserializerHelper {
    static String getNodeValue(JsonNode node, String errorMsg) {
        if (node != null) {
            return node.asText();
        }
        else {
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
