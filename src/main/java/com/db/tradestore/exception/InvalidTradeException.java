package com.db.tradestore.exception;

/**
 * Exception thrown when an invalid trade is encountered.
 */
public class InvalidTradeException extends RuntimeException {

    private final String id;

    /**
     * Constructs an InvalidTradeException with the specified trade ID.
     *
     * @param id The trade ID associated with the invalid trade.
     */
    public InvalidTradeException(final String id) {
        super("Invalid Trade: " + id);
        this.id = id;
    }

    /**
     * Gets the trade ID associated with the invalid trade.
     *
     * @return The trade ID.
     */
    public String getId() {
        return id;
    }
}
