package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;

public interface TransitService {
    Transit addTransit(final TransitDto transitDto);
}
