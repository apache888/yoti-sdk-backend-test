package com.yoti.hoover.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * CleaningInstructionsDto.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@ApiModel(value = "Cleaning Instructions", description = "Represents all required data to execute cleaning")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CleaningInstructionsDto {
    /**
     * 1st element - MAX coordinate X
     * 2d element - MAX coordinate Y
     */
    @ApiModelProperty(value = "Room size", required = true)
    @NotNull(message = "Room size can't be NULL")
    @Size(min = 2, max = 2, message = "Room size must be strongly two numbers, [x,y]")
    private List<Integer> roomSize;

    /**
     * 1st element - coordinate X
     * 2d element - coordinate Y
     */
    @ApiModelProperty(value = "Coordinates of hoover", required = true, position = 1)
    @NotNull(message = "Hoover coords can't be NULL")
    @Size(min = 2, max = 2, message = "Hoover coords must be strongly two numbers, [x,y]")
    private List<Integer> coords;

    /**
     * Each element of list contains:
     * 1st element - coordinate X
     * 2d element - coordinate Y
     */
    @ApiModelProperty(value = "Coordinates of patches", required = true, position = 2)
//    @NotNull //TODO: requires more business details
//    @Size(min = 1)
    private List<List<Integer>> patches;

    @ApiModelProperty(value = "Instructions about moving directions", required = true, position = 3)
    @NotBlank(message = "Instructions can't be empty")
    @Pattern(regexp = "^[NSWEnswe]*$", message = "Wrong direction. Must be only 'N' 'S' 'W' 'E'")
    private String instructions;
}
