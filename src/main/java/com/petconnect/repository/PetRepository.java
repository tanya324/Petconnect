package com.petconnect.repository;

import com.petconnect.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByType(String type);

    List<Pet> findByStatus(Pet.PetStatus status);

    List<Pet> findByTypeAndStatus(String type, Pet.PetStatus status);

    @Query("SELECT p FROM Pet p WHERE " +
           "(:type IS NULL OR p.type = :type) AND " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:breed IS NULL OR LOWER(p.breed) LIKE LOWER(CONCAT('%',:breed,'%')))")
    List<Pet> filterPets(@Param("type") String type,
                         @Param("status") String status,
                         @Param("breed") String breed);

    @Query("SELECT p FROM Pet p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:q,'%')) " +
           "OR LOWER(p.breed) LIKE LOWER(CONCAT('%',:q,'%')) " +
           "OR LOWER(p.type) LIKE LOWER(CONCAT('%',:q,'%'))")
    List<Pet> searchPets(@Param("q") String query);

    long countByStatus(Pet.PetStatus status);
}
