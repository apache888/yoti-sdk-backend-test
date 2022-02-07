package com.yoti.hoover.service;

import lombok.experimental.UtilityClass;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Utils.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@UtilityClass
public class Utils {

    public static List<Directions> parseDirections(String instructions) {
        final char[] directionsArray = instructions.toUpperCase().toCharArray();
        final List<Directions> directions = new LinkedList<>();
        for (char symbol : directionsArray) {
            directions.add(Directions.valueOf(String.valueOf(symbol)));
        }
        return directions;
    }

    /**
     * Checks whether hoover intersects with any patch and marks patch as cleaned in case intersection
     * We should mark patch as cleaned due to hover can intersect patch coords several times,
     * after first intersection patch should be disappeared
     */
    public static boolean isPatchCleaned(Integer[] hooverCoords, List<PatchDto> patches) {
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
}
