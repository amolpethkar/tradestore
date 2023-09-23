package com.db.tradestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * A class representing a trade.
 */
@Entity
@Table(name = "Trades")
public class Trade {

    @Id
    private String tradeId;

    private int version;

    private String counterParty;

    private String bookId;

    private LocalDate maturityDate;

    private LocalDate createdDate;

    private String expiredFlag;

    /**
     * Gets the trade ID.
     *
     * @return The trade ID.
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * Sets the trade ID.
     *
     * @param tradeId The trade ID to set.
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    /**
     * Gets the version of the trade.
     *
     * @return The trade version.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the version of the trade.
     *
     * @param version The trade version to set.
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Gets the counterparty associated with the trade.
     *
     * @return The counterparty.
     */
    public String getCounterParty() {
        return counterParty;
    }

    /**
     * Sets the counterparty associated with the trade.
     *
     * @param counterParty The counterparty to set.
     */
    public void setCounterParty(String counterParty) {
        this.counterParty = counterParty;
    }

    /**
     * Gets the book ID associated with the trade.
     *
     * @return The book ID.
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * Sets the book ID associated with the trade.
     *
     * @param bookId The book ID to set.
     */
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the maturity date of the trade.
     *
     * @return The maturity date.
     */
    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    /**
     * Sets the maturity date of the trade.
     *
     * @param maturityDate The maturity date to set.
     */
    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    /**
     * Gets the created date of the trade.
     *
     * @return The created date.
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date of the trade.
     *
     * @param createdDate The created date to set.
     */
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the expired flag of the trade.
     *
     * @return The expired flag.
     */
    public String getExpiredFlag() {
        return expiredFlag;
    }

    /**
     * Sets the expired flag of the trade.
     *
     * @param expiredFlag The expired flag to set.
     */
    public void setExpiredFlag(String expiredFlag) {
        this.expiredFlag = expiredFlag;
    }

    /**
     * Generates a string representation of the trade.
     *
     * @return A string representation of the trade.
     */
    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", version=" + version +
                ", counterParty='" + counterParty + '\'' +
                ", bookId='" + bookId + '\'' +
                ", maturityDate=" + maturityDate +
                ", createdDate=" + createdDate +
                ", expiredFlag='" + expiredFlag + '\'' +
                '}';
    }
}
