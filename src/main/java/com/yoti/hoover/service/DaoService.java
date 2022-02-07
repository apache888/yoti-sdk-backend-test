package com.yoti.hoover.service;

import com.yoti.hoover.model.CleaningInstructionsModel;
import com.yoti.hoover.model.CleaningResultModel;
import com.yoti.hoover.repository.CleaningInstructionsRepository;
import com.yoti.hoover.repository.CleaningResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * DaoService.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Service
@RequiredArgsConstructor
public class DaoService {

    private final CleaningInstructionsRepository cleaningInstructionsRepository;
    private final CleaningResultRepository cleaningResultRepository;

    public void saveCleaningInstructions(CleaningInstructionsModel cleaningInstructions) {
        cleaningInstructionsRepository.save(cleaningInstructions);
    }

    public void saveCleaningResult(CleaningResultModel cleaningResult) {
        cleaningResultRepository.save(cleaningResult);
    }
}
