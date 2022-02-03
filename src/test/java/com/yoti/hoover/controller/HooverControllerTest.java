package com.yoti.hoover.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.hoover.TestHooverFactory;
import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;
import com.yoti.hoover.exception.CoordinatesOutOfRoomDimensionException;
import com.yoti.hoover.service.HooverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.yoti.hoover.service.HooverService.COORDINATES_OF_HOOVER_ARE_OUT_OF_ROOM_DIMENSION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {HooverController.class})
class HooverControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HooverService hooverService;

    @Test
    public void testClean_successfulWay() throws Exception {
        final CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        final CleaningResultDto resultDto = TestHooverFactory.createCleaningResultDto();

        when(hooverService.clean(eq(instructionsDto))).thenReturn(resultDto);

        mvc.perform(MockMvcRequestBuilders.post("/hoover/clean")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructionsDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.coords").value(resultDto.getCoords()))
                .andExpect(jsonPath("$.patches").value(resultDto.getPatches()));
    }

    @Test
    public void testClean_wrongHooverCoords() throws Exception {
        final CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();

        when(hooverService.clean(any(CleaningInstructionsDto.class)))
                .thenThrow(new CoordinatesOutOfRoomDimensionException(COORDINATES_OF_HOOVER_ARE_OUT_OF_ROOM_DIMENSION));

        mvc.perform(MockMvcRequestBuilders.post("/hoover/clean")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructionsDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value(COORDINATES_OF_HOOVER_ARE_OUT_OF_ROOM_DIMENSION));
    }

    @Test
    public void testClean_wrongDirection() throws Exception {
        final CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        instructionsDto.setInstructions(instructionsDto.getInstructions().concat("R"));
        final CleaningResultDto resultDto = TestHooverFactory.createCleaningResultDto();

        when(hooverService.clean(eq(instructionsDto))).thenReturn(resultDto);

        mvc.perform(MockMvcRequestBuilders.post("/hoover/clean")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructionsDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}