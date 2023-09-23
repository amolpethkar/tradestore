package com.db.tradestore.dao;

import com.db.tradestore.model.Trade;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The TradeDao interface defines methods for interacting with a trade data store.
 */
public interface TradeDao {

    /**
     * A concurrent map to store trades by their trade ID.
     */
    Map<String, Trade> tradeMap = new ConcurrentHashMap<>();

    /**
     * Saves a trade in the data store.
     *
     * @param trade The Trade object to be saved.
     */
    void save(Trade trade);

    /**
     * Retrieves a list of all trades from the data store.
     *
     * @return List<Trade> containing all the trades in the data store.
     */
    List<Trade> findAll();

    /**
     * Finds a trade by its trade ID in the data store.
     *
     * @param tradeId The trade ID to search for.
     * @return The Trade object if found; otherwise, null.
     */
    Trade findTrade(String tradeId);
}

