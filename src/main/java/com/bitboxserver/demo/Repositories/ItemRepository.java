package com.bitboxserver.demo.Repositories;

import com.bitboxserver.demo.models.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ItemRepository extends JpaRepository<Item, Long> {
}
