package org.mplywacz.transitapi.dto.mappers;
/*
Author: BeGieU
Date: 08.10.2019
*/

/**
 *
 * @param <DTO> Dto type to be mapped on model class
 * @param <DOMAIN> model class
 */
public interface Mapper <DTO,DOMAIN> {
    DOMAIN convertDto(DTO driverDto);
}
