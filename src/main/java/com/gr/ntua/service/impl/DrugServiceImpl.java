package com.gr.ntua.service.impl;

import com.gr.ntua.service.DrugService;
import com.gr.ntua.domain.Drug;
import com.gr.ntua.repository.DrugRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Drug.
 */
@Service
@Transactional
public class DrugServiceImpl implements DrugService{

    private final Logger log = LoggerFactory.getLogger(DrugServiceImpl.class);
    
    @Inject
    private DrugRepository drugRepository;

    /**
     * Save a drug.
     *
     * @param drug the entity to save
     * @return the persisted entity
     */
    public Drug save(Drug drug) {
        log.debug("Request to save Drug : {}", drug);
        Drug result = drugRepository.save(drug);
        return result;
    }

    /**
     *  Get all the drugs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Drug> findAll(Pageable pageable) {
        log.debug("Request to get all Drugs");
        Page<Drug> result = drugRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one drug by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Drug findOne(Long id) {
        log.debug("Request to get Drug : {}", id);
        Drug drug = drugRepository.findOne(id);
        return drug;
    }

    /**
     *  Delete the  drug by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Drug : {}", id);
        drugRepository.delete(id);
    }
}
