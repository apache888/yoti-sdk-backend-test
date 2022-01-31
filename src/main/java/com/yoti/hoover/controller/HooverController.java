package com.yoti.hoover.controller;

import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;
import com.yoti.hoover.service.HooverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * HooverController.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Api(tags = "Hoover", description = "Operations with hoover")
@RestController
@RequestMapping(path = "/hoover")
@RequiredArgsConstructor
@Validated
public class HooverController {
    private final HooverService hooverService;

    @ApiOperation(value = "Clean patches of dirt")
    @PostMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_VALUE)
    public CleaningResultDto clean(@RequestBody @Valid CleaningInstructionsDto instructionsDto) {

        return hooverService.clean(instructionsDto);
    }
}
