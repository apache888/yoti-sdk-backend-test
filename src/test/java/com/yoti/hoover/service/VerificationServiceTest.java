package com.yoti.hoover.service;

import com.yoti.hoover.TestHooverFactory;
import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.exception.CoordinatesOutOfRoomDimensionException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VerificationServiceTest {

    VerificationService verificationService = new VerificationService();

    @Test
    public void testVerifyIncomingInstructions_wrongHooverCoords() {
        CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        instructionsDto.setCoords(new ArrayList<>(List.of(6, 2)));

        assertThrows(CoordinatesOutOfRoomDimensionException.class, () -> verificationService.verifyIncomingInstructions(instructionsDto));
    }

    @Test
    public void testVerifyIncomingInstructions_wrongHooverCoords_notFull() {
        CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        instructionsDto.setCoords(new ArrayList<>(List.of(2)));

        assertThrows(IllegalArgumentException.class, () -> verificationService.verifyIncomingInstructions(instructionsDto));
    }

    @Test
    public void testVerifyIncomingInstructions_wrongPatchCoords() {
        CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        instructionsDto.setPatches(new ArrayList<>(List.of(new ArrayList<>(List.of(6, 2)))));

        assertThrows(CoordinatesOutOfRoomDimensionException.class, () -> verificationService.verifyIncomingInstructions(instructionsDto));
    }

    @Test
    public void testVerifyIncomingInstructions_wrongPatchCoords_notFull() {
        CleaningInstructionsDto instructionsDto = TestHooverFactory.createCleaningInstructionsDto();
        instructionsDto.setPatches(new ArrayList<>(List.of(new ArrayList<>(List.of(2)))));

        assertThrows(IllegalArgumentException.class, () -> verificationService.verifyIncomingInstructions(instructionsDto));
    }
}