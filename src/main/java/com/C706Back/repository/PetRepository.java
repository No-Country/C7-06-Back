package com.C706Back.repository;

import com.C706Back.models.entity.Pet;
import com.C706Back.models.enums.AnimalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Pet p WHERE p.animalType = :animalType")
    Page<Pet> findAllByAnimalType(@Param("animalType") AnimalType animalType, Pageable pageable);

}
