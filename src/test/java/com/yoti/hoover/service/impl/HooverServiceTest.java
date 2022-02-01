package com.yoti.hoover.service.impl;

import com.yoti.hoover.TestHooverFactory;
import com.yoti.hoover.config.MapperConfig;
import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;
import com.yoti.hoover.exception.CoordinatesOutOfRoomDimensionException;
import com.yoti.hoover.model.CleaningInstructionsModel;
import com.yoti.hoover.model.CleaningResultModel;
import com.yoti.hoover.repository.CleaningInstructionsRepository;
import com.yoti.hoover.repository.CleaningResultRepository;
import com.yoti.hoover.service.HooverService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HooverServiceTest {

    @Mock
    private CleaningInstructionsRepository cleaningInstructionsRepository;
    @Mock
    private CleaningResultRepository cleaningResultRepository;

    HooverService hooverService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
        hooverService = new HooverServiceImpl(cleaningInstructionsRepository, cleaningResultRepository, new MapperConfig());
    }

    @Test
    public void testClean_successfulWay() {
        final CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();

        ArgumentCaptor<CleaningInstructionsModel> instructionsCaptor = ArgumentCaptor.forClass(CleaningInstructionsModel.class);
        ArgumentCaptor<CleaningResultModel> resultCaptor = ArgumentCaptor.forClass(CleaningResultModel.class);

        final CleaningResultDto resultDto = hooverService.clean(instructionsDto);

        assertArrayEquals(resultDto.getCoords().toArray(new Integer[0]), TestHooverFactory.FINAL_COORDS);
        assertEquals(resultDto.getPatches(), TestHooverFactory.CLEANED_PATCHES_COUNT);

        verify(cleaningInstructionsRepository, times(1)).save(instructionsCaptor.capture());
        verify(cleaningResultRepository, times(1)).save(resultCaptor.capture());

        final CleaningInstructionsModel instructionsModel = instructionsCaptor.getValue();
        assertEquals(instructionsModel.getRoomSize(), instructionsDto.getRoomSize());

        final CleaningResultModel resultModel = resultCaptor.getValue();
        assertArrayEquals(resultModel.getCoords().toArray(new Integer[0]), TestHooverFactory.FINAL_COORDS);
        assertEquals(resultModel.getPatches(), TestHooverFactory.CLEANED_PATCHES_COUNT);
    }

    @Test
    public void testClean_wrongHooverCoords() {
        CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        instructionsDto.setCoords(new ArrayList<>(List.of(6, 2)));

        assertThrows(CoordinatesOutOfRoomDimensionException.class, () -> hooverService.clean(instructionsDto));
    }

    @Test
    public void testClean_wrongDirection() {
        CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        instructionsDto.setInstructions(instructionsDto.getInstructions().concat("R"));

        assertThrows(IllegalArgumentException.class, () -> hooverService.clean(instructionsDto));
    }
}