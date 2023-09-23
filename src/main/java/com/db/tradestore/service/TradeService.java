package com.db.tradestore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.tradestore.dao.TradeDao;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;

@Service
public class TradeService {

    private static final Logger log = LoggerFactory.getLogger(TradeService.class);


    @Autowired
    TradeDao tradeDao;

    @Autowired
    TradeRepository tradeRepository;

    /**
     * Validates whether a trade is valid for processing.
     *
     * This method performs validation checks on the provided trade, including
     * maturity date validation and version validation against an existing trade
     * (if it exists in the repository).
     *
     * @param trade The Trade object to be validated.
     * @return true if the trade is valid; false otherwise.
     */
    public boolean isValid(Trade trade) {
        log.info("Validating trade with ID: {}", trade.getTradeId());

        if (validateMaturityDate(trade)) {
            log.info("Maturity date validation passed.");
            
            Optional<Trade> existingTrade = tradeRepository.findById(trade.getTradeId());

            if (existingTrade.isPresent()) {
                log.info("Existing trade found in the repository. Performing version validation.");
                boolean versionValidationResult = validateVersion(trade, existingTrade.get());
                log.info("Version validation result: {}", versionValidationResult);
                return versionValidationResult;
            } else {
                log.info("No existing trade found in the repository. Trade is valid.");
                return true;
            }
        }

        log.info("Maturity date validation failed. Returning false.");
        return false;
    }


    /**
     * Validates the version of a trade.
     *
     * This method checks if the version of the new trade is greater than or equal to
     * the version of the existing (old) trade. If the version is valid, it returns true;
     * otherwise, it returns false.
     *
     * @param trade The new Trade object.
     * @param oldTrade The existing (old) Trade object.
     * @return true if the version is valid; false otherwise.
     */
    private boolean validateVersion(Trade trade, Trade oldTrade) {
        log.info("Validating trade version for Trade ID: {}", trade.getTradeId());

        if (trade.getVersion() >= oldTrade.getVersion()) {
            log.info("Version validation passed. New version: {}, Old version: {}", trade.getVersion(), oldTrade.getVersion());
            return true;
        } else {
            log.warn("Version validation failed. New version: {} is lower than Old version: {}", trade.getVersion(), oldTrade.getVersion());
            return false;
        }
    }


    
    /**
     * Validates the maturity date of a trade.
     *
     * This method checks if the maturity date of the trade is before the current date.
     * If the maturity date is valid, it returns true; otherwise, it returns false.
     *
     * @param trade The Trade object to be validated.
     * @return true if the maturity date is valid; false otherwise.
     */
    private boolean validateMaturityDate(Trade trade) {
        LocalDate maturityDate = trade.getMaturityDate();
        LocalDate currentDate = LocalDate.now();

        log.info("Validating maturity date for Trade ID: {}. Maturity Date: {}, Current Date: {}",
                 trade.getTradeId(), maturityDate, currentDate);

        boolean isValid = currentDate.isBefore(maturityDate);

        if (isValid) {
            log.info("Maturity date validation passed.");
        } else {
            log.warn("Maturity date validation failed. Maturity Date: {} is not before Current Date: {}",
                     maturityDate, currentDate);
        }

        return isValid;
    }


    /**
     * Persists a trade in the system.
     *
     * This method saves the provided trade object in the system. It sets the
     * created date to the current date before saving.
     *
     * @param trade The Trade object to be persisted.
     */
    public void persist(Trade trade) {
        log.info("Persisting trade with ID: {}", trade.getTradeId());

        // Set the created date to the current date before saving
        trade.setCreatedDate(LocalDate.now());

        // Save the trade in the repository
        tradeRepository.save(trade);

        log.info("Trade persisted successfully.");
    }


    /**
     * Retrieves a list of all trades from the repository.
     *
     * This method retrieves and returns a list of all trades currently stored in the system
     * by delegating the call to the underlying trade repository.
     * The list may be empty if no trades are found.
     *
     * @return List<Trade> containing all the trades in the system.
     */
    public List<Trade> findAll() {
        log.info("Retrieving all trades from the repository.");

        List<Trade> trades = tradeRepository.findAll();

        if (trades.isEmpty()) {
            log.info("No trades found in the repository.");
        } else {
            log.info("Found {} trades in the repository.", trades.size());
        }

        return trades;
    }


    /**
     * Updates the expiry flag of trades based on their maturity dates.
     *
     * This method retrieves all trades from the repository, checks their maturity dates,
     * and updates the expiry flag accordingly. Trades with maturity dates in the past
     * will have their expiry flag set to "Y", and a log message will be generated.
     */
    public void updateExpiryFlagOfTrade() {
        log.info("Updating expiry flags of trades based on maturity dates.");

        tradeRepository.findAll().forEach(trade -> {
            if (!validateMaturityExpiry(trade)) {
                trade.setExpiredFlag("Y");
                log.info("Updated expiry flag for Trade ID: {}. New expiry flag: Y", trade.getTradeId());

                // Save the updated trade
                tradeRepository.save(trade);
            }
        });

        log.info("Expiry flag update process completed.");
    }
    
    /**
     * Validates the maturity date of a trade.
     *
     * This method checks if the maturity date of the trade is before the current date.
     * If the maturity date is valid, it returns true; otherwise, it returns false.
     *
     * @param trade The Trade object to be validated.
     * @return true if the maturity date is valid; false otherwise.
     */
    private boolean validateMaturityExpiry(Trade trade) {
        LocalDate maturityDate = trade.getMaturityDate();
        LocalDate currentDate = LocalDate.now();

        log.info("Validating maturity date for Trade ID: {}. Maturity Date: {}, Current Date: {}",
                 trade.getTradeId(), maturityDate, currentDate);

        boolean isValid = currentDate.isBefore(maturityDate);

        if (isValid) {
            log.info("Maturity date validation passed.");
        } else {
            log.warn("Maturity date validation failed. Maturity Date: {} is not before Current Date: {}",
                     maturityDate, currentDate);
        }

        return isValid;
    }
    

}
