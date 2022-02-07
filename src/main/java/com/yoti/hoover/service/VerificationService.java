package com.yoti.hoover.service;

import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.exception.CoordinatesOutOfRoomDimensionException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VerificationService.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Service
public class VerificationService {

    public static final String COORDINATES_OF_HOOVER_ARE_OUT_OF_ROOM_DIMENSION = "Coordinates of hoover are out of room dimension. Note: If room size 5 then axes range 0-4";
    public static final String COORDINATES_OF_PATCHES_ARE_OUT_OF_ROOM_DIMENSION = "Coordinates of patches are out of room dimension. Note: If room size 5 then axes range 0-4";


    public void verifyIncomingInstructions(CleaningInstructionsDto instructionsDto) {
        if (instructionsDto.getRoomSize() == null || instructionsDto.getRoomSize().size() != 2) {
            throw new IllegalArgumentException("Room size mustn't be empty and must have strongly two numbers of coords");
        }
        int roomSizeX = instructionsDto.getRoomSize().get(0) - 1;
        int roomSizeY = instructionsDto.getRoomSize().get(1) - 1;

        /*check hoover coords*/
        if (instructionsDto.getCoords() == null || instructionsDto.getCoords().size() != 2) {
            throw new IllegalArgumentException("Hoover coords mustn't be empty and must have strongly two numbers of coords");
        }
        if (instructionsDto.getCoords().get(0) > roomSizeX
                || instructionsDto.getCoords().get(0) < 0
                || instructionsDto.getCoords().get(1) > roomSizeY
                || instructionsDto.getCoords().get(1) < 0) {
            throw new CoordinatesOutOfRoomDimensionException(COORDINATES_OF_HOOVER_ARE_OUT_OF_ROOM_DIMENSION);
        }

        /*check patches coords*/
        if (instructionsDto.getPatches().stream().anyMatch(patch -> patch == null || patch.size() != 2)) {
            throw new IllegalArgumentException("Patch mustn't be empty and must have strongly two numbers of coords");
        }
        for (List<Integer> patch : instructionsDto.getPatches()) {
            if (patch.get(0) > roomSizeX || patch.get(0) < 0
                    || patch.get(1) > roomSizeY || patch.get(1) < 0) {
                throw new CoordinatesOutOfRoomDimensionException(COORDINATES_OF_PATCHES_ARE_OUT_OF_ROOM_DIMENSION);
            }
        }
    }
}
