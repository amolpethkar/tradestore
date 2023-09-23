package com.db.tradestore;

import com.db.tradestore.controller.TradeController;
import com.db.tradestore.exception.InvalidTradeException;
import com.db.tradestore.model.Trade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TradeStoreApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(TradeStoreApplicationTests.class);

	@Test
	void contextLoads() {
		log.info("Context loads test.");
	}

	@Autowired
	private TradeController tradeController;

	@Test
	void testTradeValidateAndStore_successful() {
		log.info("Starting testTradeValidateAndStore_successful...");

		ResponseEntity responseEntity = tradeController
				.validateAndStoreTrade(createTrade("T1", 1, getLocalDate(2024, 05, 21)));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);

		List<Trade> tradeList = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1", tradeList.get(0).getTradeId());

		log.info("Finished testTradeValidateAndStore_successful.");
	}

	@Test
	void testTradeValidateAndStoreWhenMaturityDatePast() {
		log.info("Starting testTradeValidateAndStoreWhenMaturityDatePast...");

		try {
			LocalDate localDate = getLocalDate(2024, 05, 21);
			ResponseEntity responseEntity = tradeController.validateAndStoreTrade(createTrade("T2", 1, localDate));
		} catch (InvalidTradeException ie) {
			Assertions.assertEquals("Invalid Trade: T2  Trade Id is not found", ie.getMessage());
		}

		log.info("Finished testTradeValidateAndStoreWhenMaturityDatePast.");
	}

	@Test
	void testTradeValidateAndStoreWhenOldVersion() {
		log.info("Starting testTradeValidateAndStoreWhenOldVersion...");

		ResponseEntity responseEntity = tradeController.validateAndStoreTrade(createTrade("T1", 2, getLocalDate(2024, 05, 21)));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);

		List<Trade> tradeList = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1", tradeList.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList.get(0).getVersion());
		Assertions.assertEquals("T1B1", tradeList.get(0).getBookId());

		try {
			ResponseEntity responseEntity1 = tradeController
					.validateAndStoreTrade(createTrade("T1", 1, LocalDate.now()));
		} catch (InvalidTradeException e) {
			log.error("Exception occurred during testTradeValidateAndStoreWhenOldVersion: {}", e.getMessage(), e);
		}

		List<Trade> tradeList1 = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList1.size());
		Assertions.assertEquals("T1", tradeList1.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList1.get(0).getVersion());
		Assertions.assertEquals("T1B1", tradeList.get(0).getBookId());

		log.info("Finished testTradeValidateAndStoreWhenOldVersion.");
	}

	@Test
	void testTradeValidateAndStoreWhenSameVersionTrade() {
		log.info("Starting testTradeValidateAndStoreWhenSameVersionTrade...");

		ResponseEntity responseEntity = tradeController.validateAndStoreTrade(createTrade("T1", 2, getLocalDate(2024, 05, 21)));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);

		List<Trade> tradeList = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1", tradeList.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList.get(0).getVersion());
		Assertions.assertEquals("T1B1", tradeList.get(0).getBookId());

		Trade trade2 = createTrade("T1", 2, getLocalDate(2024, 05, 21));
		trade2.setBookId("T1B1V2");
		ResponseEntity responseEntity2 = tradeController.validateAndStoreTrade(trade2);
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity2);

		List<Trade> tradeList2 = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList2.size());
		Assertions.assertEquals("T1", tradeList2.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList2.get(0).getVersion());
		Assertions.assertEquals("T1B1V2", tradeList2.get(0).getBookId());

		Trade trade3 = createTrade("T1", 2, getLocalDate(2024, 05, 21));
		trade3.setBookId("T1B1V3");
		ResponseEntity responseEntity3 = tradeController.validateAndStoreTrade(trade3);
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity3);

		List<Trade> tradeList3 = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList3.size());
		Assertions.assertEquals("T1", tradeList3.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList3.get(0).getVersion());
		Assertions.assertEquals("T1B1V3", tradeList3.get(0).getBookId());

		log.info("Finished testTradeValidateAndStoreWhenSameVersionTrade.");
	}

	private Trade createTrade(String tradeId, int version, LocalDate maturityDate) {
		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setBookId(tradeId + "B1");
		trade.setVersion(version);
		trade.setCounterParty(tradeId + "Cpty");
		trade.setMaturityDate(maturityDate);
		trade.setExpiredFlag("Y");
		return trade;
	}

	public static LocalDate getLocalDate(int year, int month, int day) {
		LocalDate localDate = LocalDate.of(year, month, day);
		return localDate;
	}
}
