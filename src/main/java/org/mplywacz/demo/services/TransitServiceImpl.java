package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.json.JSONException;
import org.json.JSONObject;
import org.mplywacz.demo.dto.RangeReportDto;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.dto.mappers.TransitMapper;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.repositories.TransitRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class TransitServiceImpl implements TransitService {
    private final TransitRepo transitRepository;
    private final TransitMapper transitMapper;

    public TransitServiceImpl(TransitRepo transitRepository, TransitMapper transitMapperImpl) {
        this.transitRepository = transitRepository;
        this.transitMapper = transitMapperImpl;
    }

    public Transit addTransit(final TransitDto transitDto) {
        return transitRepository.save(transitMapper.convertTransitDto(transitDto));
    }

    @Override public JSONObject getRangeReport(RangeReportDto rangeReportDto) {
        Set<Transit> transits = transitRepository
                .selectTransitsInDateRange(rangeReportDto.getStartDate(), rangeReportDto.getEndDate());

        BigDecimal distanceSum = new BigDecimal(0);
        BigDecimal priceSum = new BigDecimal(0);

        for (Transit t : transits) {
            distanceSum = distanceSum.add(t.getDistance());
            priceSum = priceSum.add(t.getPrice());
        }
        //todo check what happens when exception is thrown inside this block
        try {
            return new JSONObject()
                    .put("total_distance", distanceSum)
                    .put("total_price", priceSum);
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}
