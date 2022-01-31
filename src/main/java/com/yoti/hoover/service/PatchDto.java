package com.yoti.hoover.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * PatchDto.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatchDto {
    int coordX;
    int coordY;
    boolean cleaned;
}
