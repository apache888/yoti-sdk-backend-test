package com.yoti.hoover.service;

import com.yoti.hoover.TestHooverFactory;
import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;
import com.yoti.hoover.mapper.CustomHooverMapper;
import com.yoti.hoover.model.CleaningInstructionsModel;
import com.yoti.hoover.model.CleaningResultModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HooverServiceTest {

    @Mock
    private VerificationService verificationService;

    @Mock
    private DaoService daoService;

    HooverService hooverService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
        hooverService = new HooverService(verificationService, daoService, new CustomHooverMapper());
        doNothing().when(verificationService).verifyIncomingInstructions(any(CleaningInstructionsDto.class));
        doNothing().when(daoService).saveCleaningInstructions(any(CleaningInstructionsModel.class));
        doNothing().when(daoService).saveCleaningResult(any(CleaningResultModel.class));
    }

    @Test
    public void testClean_successfulWay() {
        final CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();

        final CleaningResultDto resultDto = hooverService.clean(instructionsDto);

        assertArrayEquals(resultDto.getCoords().toArray(new Integer[0]), TestHooverFactory.FINAL_COORDS);
        assertEquals(resultDto.getPatches(), TestHooverFactory.CLEANED_PATCHES_COUNT);
    }

    @Test
    public void testClean_wrongDirection() {
        CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        instructionsDto.setInstructions(instructionsDto.getInstructions().concat("R"));

        assertThrows(IllegalArgumentException.class, () -> hooverService.clean(instructionsDto));
    }
}