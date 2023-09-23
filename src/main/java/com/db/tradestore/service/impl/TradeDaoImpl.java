package com.db.tradestore.service.impl;

import com.db.tradestore.dao.TradeDao;
import com.db.tradestore.model.Trade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the TradeDao interface.
 */
@Service
public class TradeDaoImpl implements TradeDao {

    @Override
    public void save(Trade trade) {
        trade.setCreatedDate(LocalDate.now());
        tradeMap.put(trade.getTradeId(), trade);
    }

    @Override
    public List<Trade> findAll() {
        return tradeMap.values().stream()
                .collect(Collectors.toList());
    }

    @Override
    public Trade findTrade(String tradeId) {
        return tradeMap.get(tradeId);
    }
}
