package com.C706Back.repository;

import com.C706Back.models.entity.Pet;
import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Pet p WHERE p.animalType = :animalType")
    Page<Pet> findAllByAnimalType(@Param("animalType") AnimalType animalType, Pageable pageable);

    @Query("select p from Pet p where (:animalType is null or p.animalType=:animalType) and (:gender is null or p.gender=:gender) and (:startAge is null or p.age >= :startAge) and (:endAge is null or p.age <= :endAge) and (:race is null or p.race=:race) and (:location is null or p.location.name=:location)")
    Page<Pet> findAllByFilter(@Param("animalType") AnimalType animalType,
                              @Param("gender") Gender gender,
                              @Param("startAge") Integer startAge,
                              @Param("endAge") Integer endAge,
                              @Param("race") String race,
                              @Param("location") String location,
                              Pageable pageable);


}
