package com.bitboxserver.demo.Repositories;

import com.bitboxserver.demo.models.entities.PriceReduction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceReductionRepository extends JpaRepository<PriceReduction, Long> {
}
