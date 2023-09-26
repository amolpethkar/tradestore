package com.db.tradestore.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.db.tradestore.exception.InvalidTradeException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.service.TradeService;

import java.util.List;

@RestController
public class TradeController {
	
	
	private static final Logger log = LoggerFactory.getLogger(TradeController.class);
	
	@Autowired
    TradeService tradeService;	
	
	/**
	 * Validates and stores a trade in the system.
	 *
	 * This method validates the provided trade object and, if it passes validation,
	 * stores it in the system. If the trade is successfully stored, it returns a
	 * ResponseEntity with a status indicating success and a message.
	 * If the trade fails validation or encounters an error during storage, an
	 * appropriate error response is returned.
	 *
	 * @param trade The Trade object to be validated and stored.
	 * @return ResponseEntity<String> containing a status code and a message.
	 *   - HttpStatus.OK (200) if the trade is successfully validated and stored.
	 *   - HttpStatus.BAD_REQUEST (400) if the trade fails validation.
	 *   - HttpStatus.INTERNAL_SERVER_ERROR (500) if an error occurs during storage.
	 */
    @PostMapping("/trade")
    public ResponseEntity<String> validateAndStoreTrade(@RequestBody Trade trade) {
        log.info("Entering validateAndStoreTrade method.");
        log.debug("Received trade: {}", trade);

        if (tradeService.isValid(trade)) {
            log.info("Trade is valid. Proceeding to persist.");
            tradeService.persist(trade);
        } else {
            log.warn("Invalid trade received. Trade ID: {}", trade.getTradeId());
            throw new InvalidTradeException(trade.getTradeId() + " Trade ID is not found");
        }

        log.info("Exiting validateAndStoreTrade method with HttpStatus OK (200).");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Retrieves a list of all trades stored in the system.
     *
     * This method retrieves and returns a list of all trades currently stored in the system.
     * The list may be empty if no trades are found.
     *
     * @return List<Trade> containing all the trades in the system.
     */
    @GetMapping("/trade")
    public List<Trade> findAllTrades() {
        log.info("Entering findAllTrades method.");

        List<Trade> trades = tradeService.findAll();

        if (trades.isEmpty()) {
            log.info("No trades found in the system.");
        } else {
            log.info("Found {} trades in the system.", trades.size());
        }

        log.info("Exiting findAllTrades method.");
        return trades;
    }

    
}
