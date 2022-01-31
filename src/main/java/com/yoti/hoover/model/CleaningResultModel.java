package com.yoti.hoover.model;

import com.yoti.hoover.converter.ListOfIntConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * CleaningResultModel.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Table(name = "clean_results")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class CleaningResultModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = ListOfIntConverter.class)
    private List<Integer> coords;

    private int patches;
}
