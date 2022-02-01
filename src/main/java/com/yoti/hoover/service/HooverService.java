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
    String COORDINATES_OF_HOOVER_OR_PATCHES_ARE_OUT_OF_ROOM_DIMENSION = "Coordinates of hoover or patches are out of room dimension";

    CleaningResultDto clean(CleaningInstructionsDto instructionsDto);
}
