package com.gr.ntua.repository;

import com.gr.ntua.domain.Drug;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Drug entity.
 */
@SuppressWarnings("unused")
public interface DrugRepository extends JpaRepository<Drug,Long> {

}
