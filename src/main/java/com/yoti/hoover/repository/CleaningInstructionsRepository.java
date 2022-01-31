package com.yoti.hoover.repository;

import com.yoti.hoover.model.CleaningInstructionsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CleaningInstructionsRepository.
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Repository
public interface CleaningInstructionsRepository extends JpaRepository<CleaningInstructionsModel, Long> {
}
