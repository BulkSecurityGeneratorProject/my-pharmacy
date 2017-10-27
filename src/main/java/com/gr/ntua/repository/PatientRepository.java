package com.gr.ntua.repository;

import com.gr.ntua.domain.Patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("select patient from Patient patient where patient.user.login = ?#{principal.username}")
    Page<Patient> findByUserIsCurrentUser(Pageable pageable);

    @Query("select patient from Patient patient where patient.user.login = ?#{principal.username} and patient.id=?1")
    Patient findOneWhereUserIsCurrentUser(Long id);
}
