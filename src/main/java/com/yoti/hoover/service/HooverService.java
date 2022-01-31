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
    CleaningResultDto clean(CleaningInstructionsDto instructionsDto);
}
