package com.yoti.hoover.repository;

import com.yoti.hoover.model.CleaningResultModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CleaningResultRepository.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Repository
public interface CleaningResultRepository extends JpaRepository<CleaningResultModel, Long> {
}
