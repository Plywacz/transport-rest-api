package org.mplywacz.demo.dto.mappers;

import com.fasterxml.jackson.databind.node.POJONode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.exceptions.IncorrectLocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest //brings context so that i can autowire transitMapper
@WebAppConfiguration
public class TransitMapperImplIT {

    @Autowired
    TransitMapper transitMapper;

    public static final String SOURCE_ADR = "Złota 44, Warszawa";
    public static final String DESTINATION_ADR = "ul. Zakret 8, Poznan";
    public static final int DEFAULT_PRICE = 100;

    @Test
    public void convertTransitDtoHappy() {
        var transitDto = new TransitDto();
        transitDto.setSourceAddress(new TextNode(SOURCE_ADR));
        transitDto.setDestinationAddress(new TextNode(DESTINATION_ADR));
        transitDto.setDate(new POJONode(LocalDate.now()));
        transitDto.setPrice(new POJONode(BigDecimal.valueOf(DEFAULT_PRICE)));

        var convertedTransit = transitMapper.convertTransitDto(transitDto);

        assertEquals(transitDto.getSourceAddress(), convertedTransit.getSourceAddress());
        assertEquals(transitDto.getDestinationAddress(), convertedTransit.getDestinationAddress());
        assertEquals(transitDto.getDate(), convertedTransit.getDate());
        assertEquals(transitDto.getPrice(), convertedTransit.getPrice());

        assertTrue(convertedTransit.getDistance().compareTo(BigDecimal.ZERO) > 0);

    }

    @Test(expected = IncorrectLocationException.class)
    public void convertTransitDtoWrongAdr() {
        var transitDto = new TransitDto();
        transitDto.setSourceAddress(new TextNode("213h"));
        transitDto.setDestinationAddress(new TextNode("fu3cas"));
        transitDto.setDate(new POJONode(LocalDate.now()));
        transitDto.setPrice(new POJONode(BigDecimal.valueOf(DEFAULT_PRICE)));

        var convertedTransit = transitMapper.convertTransitDto(transitDto);

        assertEquals(transitDto.getSourceAddress(), convertedTransit.getSourceAddress());
        assertEquals(transitDto.getDestinationAddress(), convertedTransit.getDestinationAddress());
        assertEquals(transitDto.getDate(), convertedTransit.getDate());
        assertEquals(transitDto.getPrice(), convertedTransit.getPrice());
    }

    @Test(expected = IncorrectLocationException.class)
    public void convertTransitDtoNoAddr() {
        var transitDto = new TransitDto();
        transitDto.setDate(new POJONode(LocalDate.now()));
        transitDto.setPrice(new POJONode(BigDecimal.valueOf(DEFAULT_PRICE)));

        var convertedTransit = transitMapper.convertTransitDto(transitDto);

        assertEquals(transitDto.getDate(), convertedTransit.getDate());
        assertEquals(transitDto.getPrice(), convertedTransit.getPrice());
    }
}