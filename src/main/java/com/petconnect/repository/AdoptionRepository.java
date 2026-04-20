package com.petconnect.repository;

import com.petconnect.model.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findByUserId(Long userId);
    List<Adoption> findByPetId(Long petId);
    List<Adoption> findByStatus(Adoption.AdoptionStatus status);
}
