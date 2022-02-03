package com.yoti.hoover.service;

import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;

/**
 * HooverService.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
public interface HooverService {
    String COORDINATES_OF_HOOVER_ARE_OUT_OF_ROOM_DIMENSION = "Coordinates of hoover are out of room dimension. Note: If room size 5 then axes range 0-4";
    String COORDINATES_OF_PATCHES_ARE_OUT_OF_ROOM_DIMENSION = "Coordinates of patches are out of room dimension. Note: If room size 5 then axes range 0-4";

    CleaningResultDto clean(CleaningInstructionsDto instructionsDto);
}
