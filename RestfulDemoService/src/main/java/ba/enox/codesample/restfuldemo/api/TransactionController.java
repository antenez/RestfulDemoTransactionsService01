package ba.enox.codesample.restfuldemo.api;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ba.enox.codesample.restfuldemo.model.Transaction;
import ba.enox.codesample.restfuldemo.model.TransactionStatistic;
import ba.enox.codesample.restfuldemo.service.TransactionService;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE

	)
	public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {

		if (transaction.getTimestamp() < getEpochLastMinuteInMillis()) {
			System.out.println("Older then one minnute! No Content");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		transactionService.saveTransaction(transaction);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionStatistic> last60SecsStatistic() {
		final Optional<TransactionStatistic> statistic = transactionService.getStatisticInTime(getEpochLastMinuteInMillis());
		if (statistic.isPresent()) {
			return new ResponseEntity<>(statistic.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	private Long getEpochLastMinuteInMillis() {
		return  (Instant.now().getEpochSecond() - 60) * 1000;
	}
}
