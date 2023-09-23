package com.db.tradestore.repository;

import com.db.tradestore.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A Spring Data JPA repository for managing Trade entities.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {
}
