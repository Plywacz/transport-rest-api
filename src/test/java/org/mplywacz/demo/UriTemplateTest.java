package org.mplywacz.demo;
/*
Author: BeGieU
Date: 05.07.2019
*/

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class UriTemplateTest {

    public static final UriTemplate uriTemplate = new UriTemplate(
            "http://open.mapquestapi.com/directions/v2/route?key=wNq7cvi0pFSuG50szmeFaYI6VH9c2KEP&unit=k&from={from}&to={to}");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testUriTemplateWorksFine() throws IOException {
        var uriVariables = new HashMap<String, String>();
        uriVariables.put("from", "Zakręt 8, Poznań");
        uriVariables.put("to", "Złota 44, Warszawa");
        URI expandedURI = uriTemplate.expand(uriVariables);

        //compare json from legit Url and from Url created by uriTemplate
        URL legitUrl = new URL(
                "http://open.mapquestapi.com/directions/v2/route?key=wNq7cvi0pFSuG50szmeFaYI6VH9c2KEP&unit=k&from=Zakr%C4%99t%208,%20Pozna%C5%84&to=Z%C5%82ota%2044,%20Warszawa");

        //IMO BEST WAY TO READ SINGLE LINE OF JSON !!!
        JsonNode jsonNodeLegit = objectMapper.readTree(legitUrl);
        JsonNode jsonNodeFromExpandedUri = objectMapper.readTree(expandedURI.toURL());

        Assert.assertEquals(jsonNodeFromExpandedUri.get("route").get("distance"), jsonNodeLegit.get("route").get("distance"));
    }

    @Test
    public void uriTemplateDoesntChangeAfterExpanding() {
        //Note expanding uriTemplate doesnt affect it in any way!!!
        var uriVariables = new HashMap<String, String>();
        uriVariables.put("from", "Zakręt 8, Poznań");
        uriVariables.put("to", "Złota 44, Warszawa");
        URI expandedURI = uriTemplate.expand(uriVariables);

    }
}
