package com.C706Back.repository;

import com.C706Back.models.entity.Pet;
import jdk.jfr.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findByUserId(Long userId, Pageable pageable);

    /*@Query("select p from Pet p where p.updatedDate <= :updatedDate and p.contentType = :contentType")
    Page<Pet> findLastUpdatedByContentType(
            @Param("updatedDate") Date updatedDate, @Param("contentType")ContentType contentType);*/
}
