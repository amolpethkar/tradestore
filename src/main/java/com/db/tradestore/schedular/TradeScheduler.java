package com.db.tradestore.schedular;

import com.db.tradestore.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A Spring component for scheduling trade-related tasks.
 */
@Service
@EnableScheduling
public class TradeScheduler {

    private static final Logger log = LoggerFactory.getLogger(TradeScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    TradeService tradeService;

    /**
     * Scheduled task to update the expiry flags of trades.
     */
    @Scheduled(cron = "${trade.expiry.schedule}")
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        tradeService.updateExpiryFlagOfTrade();
    }
}
