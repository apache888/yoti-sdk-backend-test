package com.yoti.hoover.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * CleaningResultDto.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@ApiModel(value = "Cleaning Result", description = "Represents result of cleaning")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CleaningResultDto {
    @ApiModelProperty(value = "Latest coordinates of hoover", required = true)
    private List<Integer> coords;

    @ApiModelProperty(value = "Number of patches were cleaned", required = true, position = 1)
    private int patches;
}
