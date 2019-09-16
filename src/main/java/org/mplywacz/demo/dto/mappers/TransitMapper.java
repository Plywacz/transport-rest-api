package org.mplywacz.demo.dto.mappers;
/*
Author: BeGieU
Date: 16.09.2019
*/

import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;

public interface TransitMapper {
     Transit convertTransitDto(TransitDto dto);
}
