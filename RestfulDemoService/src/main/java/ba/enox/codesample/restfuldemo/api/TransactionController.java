package ba.enox.codesample.restfuldemo.api;

import java.math.BigInteger;
import java.time.Instant;

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

@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE

	)
	public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {

		if (transaction.getTimestamp() < getEpochLastMinnuteValue()) {
			System.out.println("Older then one minnute! No Content");
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		transactionService.saveTransaction(transaction);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE

	)
	// TransactionStatistic
	public ResponseEntity<TransactionStatistic> last60SecsStatistic() {

		return new ResponseEntity<TransactionStatistic>(
				transactionService.getStatisticInTime(this.getEpochLastMinnuteValue()), HttpStatus.OK);
	}

	private Long getEpochLastMinnuteValue() {
		long unixTimestamp = Instant.now().getEpochSecond();
		Long secondForLastMinnute = unixTimestamp - 60;
		return secondForLastMinnute * 1000;// add 000 because demo data

	}

}
