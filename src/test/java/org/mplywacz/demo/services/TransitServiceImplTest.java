package org.mplywacz.demo.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.dto.mappers.TransitMapper;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.repositories.TransitRepo;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransitServiceImplTest {

    @Mock
    TransitRepo transitRepo;

    @Mock
    TransitMapper transitMapper;

    TransitService transitService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transitService = new TransitServiceImpl(transitRepo, transitMapper);
    }

    @Test
    public void addTransitHappyPath() {
        //given
        var transitDto = new TransitDto();
        transitDto.setSourceAddress("ul. Zakręt 8, Poznań");
        transitDto.setDestinationAddress("Złota 44, Warszawa");
        transitDto.setPrice(BigDecimal.valueOf(450));
        transitDto.setDate(LocalDate.parse(("2018-11-10")));

        var savedTransit = new Transit();
        savedTransit.setSourceAddress(transitDto.getSourceAddress());
        savedTransit.setDestinationAddress(transitDto.getDestinationAddress());
        savedTransit.setPrice(transitDto.getPrice());
        savedTransit.setDate(transitDto.getDate());
        savedTransit.setDistance(BigDecimal.valueOf(12));

        //when
        when(transitRepo.save(any())).thenReturn(savedTransit);
        var returned = transitService.addTransit(transitDto);

        //then
        assertEquals(returned.getSourceAddress(), transitDto.getSourceAddress());
        assertNotNull(returned.getDistance());
    }

}