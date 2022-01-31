package com.yoti.hoover.service.impl;

import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;
import com.yoti.hoover.exception.CoordinatesOutOfRoomDimensionException;
import com.yoti.hoover.model.CleaningInstructionsModel;
import com.yoti.hoover.model.CleaningResultModel;
import com.yoti.hoover.repository.CleaningInstructionsRepository;
import com.yoti.hoover.repository.CleaningResultRepository;
import com.yoti.hoover.service.Directions;
import com.yoti.hoover.service.HooverService;
import com.yoti.hoover.service.PatchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * HooverServiceImpl.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HooverServiceImpl implements HooverService {

    private final CleaningInstructionsRepository cleaningInstructionsRepository;
    private final CleaningResultRepository cleaningResultRepository;
    private final MapperFacade mapper;

    @Override
    public CleaningResultDto clean(CleaningInstructionsDto instructionsDto) {

        /*verify incoming data*/
        if (!verifyIncomingInstructions(instructionsDto)) {
            throw new CoordinatesOutOfRoomDimensionException("Coordinates of hoover or patches are out of room dimension");
        }

        /*save incoming instructions into DB*/
        cleaningInstructionsRepository.save(mapper.map(instructionsDto, CleaningInstructionsModel.class));

        /*get room dimension*/
        int roomSizeX = instructionsDto.getRoomSize().get(0);
        int roomSizeY = instructionsDto.getRoomSize().get(1);

        /*get initial coords of hoover*/
        Integer[] hooverCoords = new Integer[2];
        instructionsDto.getCoords().toArray(hooverCoords);

        /*get sequence of directions*/
        final List<Directions> directions = getDirections(instructionsDto.getInstructions());

        /*get patches DTO*/
        final List<PatchDto> patches = instructionsDto.getPatches().stream()
                .map(coords -> new PatchDto().setCoordX(coords.get(0)).setCoordY(coords.get(1)))
                .collect(Collectors.toList());

        int cleanedPatches = 0;

        /*apply instructions to move hoover*/
        for (Directions direction : directions) {
            cleanedPatches = cleanedPatches + move(direction, roomSizeX, roomSizeY, hooverCoords, patches);
        }

        /*save result into DB*/
        CleaningResultModel cleaningResult = new CleaningResultModel()
                .setCoords(List.of(hooverCoords[0], hooverCoords[1]))
                .setPatches(cleanedPatches);
        cleaningResultRepository.save(cleaningResult);

        return mapper.map(cleaningResult, CleaningResultDto.class);
    }

    private int move(Directions direction, int roomSizeX, int roomSizeY, Integer[] hooverCoords, List<PatchDto> patches) {
        int newY = 0;
        int newX = 0;
        int cleanedPatches = 0;
        switch (direction) {
            case N:
                newY = hooverCoords[1] + 1;
                if (newY <= roomSizeY) {
                    hooverCoords[1] = newY;
                }
                break;
            case S:
                newY = hooverCoords[1] - 1;
                if (newY >= 0) {
                    hooverCoords[1] = newY;
                }
                break;
            case W:
                newX = hooverCoords[0] - 1;
                if (newX >= 0) {
                    hooverCoords[0] = newX;
                }
                break;
            case E:
                newX = hooverCoords[0] + 1;
                if (newX <= roomSizeX) {
                    hooverCoords[0] = newX;
                }
                break;
        }
        if (isPatchCleaned(hooverCoords, patches)) {
            cleanedPatches = cleanedPatches + 1;
        }
        return cleanedPatches;
    }

    /**
     * Checks whether hoover intersects with any patch and marks patch as cleaned in case intersection
     * We should mark patch as cleaned due to hover can intersect patch coords several times,
     * after first intersection patch should be disappeared
     */
    private boolean isPatchCleaned(Integer[] hooverCoords, List<PatchDto> patches) {
        AtomicBoolean result = new AtomicBoolean(false);
        patches.forEach(patch -> {
            if (hooverCoords[0].equals(patch.getCoordX())
                    && hooverCoords[1].equals(patch.getCoordY())
                    && !patch.isCleaned()) {
                patch.setCleaned(true);
                result.set(true);
            }
        });
        return result.get();
    }

    private List<Directions> getDirections(String instructions) {
        final char[] directionsArray = instructions.toUpperCase().toCharArray();
        final List<Directions> directions = new LinkedList<>();
        for (char symbol : directionsArray) {
            directions.add(Directions.valueOf(String.valueOf(symbol)));
        }
        return directions;
    }

    private boolean verifyIncomingInstructions(CleaningInstructionsDto instructionsDto) {
        int roomSizeX = instructionsDto.getRoomSize().get(0);
        int roomSizeY = instructionsDto.getRoomSize().get(1);

        /*check hoover coords*/
        if (instructionsDto.getCoords().get(0) > roomSizeX
                || instructionsDto.getCoords().get(0) < 0
                || instructionsDto.getCoords().get(1) > roomSizeY
                || instructionsDto.getCoords().get(1) < 0) {
            return false;
        }

        /*check patches coords*/
        for (List<Integer> patch : instructionsDto.getPatches()) {
            if (patch.get(0) > roomSizeX || patch.get(0) < 0
                    || patch.get(1) > roomSizeY || patch.get(1) < 0) {
                return false;
            }
        }
        return true;
    }
}
