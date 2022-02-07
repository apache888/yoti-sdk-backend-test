package com.yoti.hoover.service;

import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;
import com.yoti.hoover.model.CleaningInstructionsModel;
import com.yoti.hoover.model.CleaningResultModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class HooverService {

    private final VerificationService verificationService;
    private final DaoService daoService;
    private final MapperFacade mapper;

    public CleaningResultDto clean(CleaningInstructionsDto instructionsDto) {

        /*verify incoming data*/
        verificationService.verifyIncomingInstructions(instructionsDto);

        /*save incoming instructions into DB*/
        daoService.saveCleaningInstructions(mapper.map(instructionsDto, CleaningInstructionsModel.class));

        /*get room dimension counting [0,0] as initial coords*/
        int roomSizeX = instructionsDto.getRoomSize().get(0) - 1;
        int roomSizeY = instructionsDto.getRoomSize().get(1) - 1;

        /*get initial coords of hoover*/
        Integer[] hooverCoords = new Integer[2];
        instructionsDto.getCoords().toArray(hooverCoords);

        /*get sequence of directions*/
        final List<Directions> directions = Utils.parseDirections(instructionsDto.getInstructions());

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
        daoService.saveCleaningResult(cleaningResult);

        return mapper.map(cleaningResult, CleaningResultDto.class);
    }

    private int move(Directions direction, int roomSizeX, int roomSizeY, Integer[] hooverCoords, List<PatchDto> patches) {
        int newY;
        int newX;
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
        if (Utils.isPatchCleaned(hooverCoords, patches)) {
            cleanedPatches = cleanedPatches + 1;
        }
        return cleanedPatches;
    }

}
