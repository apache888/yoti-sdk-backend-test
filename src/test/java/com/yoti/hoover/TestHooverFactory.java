package com.yoti.hoover;

import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TestHooverFactory.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@UtilityClass
public class TestHooverFactory {
    public static final Integer[] FINAL_COORDS = {1, 3};
    public static final int CLEANED_PATCHES_COUNT = 1;
    public static final List<Integer> ROOM_SIZE = new ArrayList<>(List.of(5, 5));
    public static final List<Integer> INIT_COORDS = new ArrayList<>(List.of(1, 2));
    public static final String INSTRUCTIONS = "NNESEESWNWW";
    public static final List<List<Integer>> PATCHES_COORDS = new ArrayList<>(List.of(
            new ArrayList<>(List.of(1, 0)),
            new ArrayList<>(List.of(2, 2)),
            new ArrayList<>(List.of(2, 3))));


    public static CleaningInstructionsDto createCleaningInstructionsDto() {
        return new CleaningInstructionsDto()
                .setRoomSize(ROOM_SIZE)
                .setCoords(INIT_COORDS)
                .setPatches(PATCHES_COORDS)
                .setInstructions(INSTRUCTIONS);
    }

    public static CleaningResultDto createCleaningResultDto() {
        return new CleaningResultDto()
                .setCoords(new ArrayList<>(Arrays.asList(FINAL_COORDS)))
                .setPatches(CLEANED_PATCHES_COUNT);
    }
}
