package ba.enox.codesample.restfuldemo.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.enox.codesample.restfuldemo.db.TransactionsHome;
import ba.enox.codesample.restfuldemo.model.Transaction;
import ba.enox.codesample.restfuldemo.model.TransactionStatistic;

@Service
public class TransactionService {
	@Autowired
	private TransactionsHome transactionRequestHome;

	public synchronized void saveTransaction(Transaction transaction) {
		transactionRequestHome.saveTransaction(transaction);
	}

	/**
	 * Method is created to support different time ranges.
	 * 
	 * @param fromEpochTimeWithZeroMilliseconds
	 *            this is 13digits value. Timestamp + 000
	 * @return
	 */
	public synchronized Optional<TransactionStatistic> getStatisticInTime(Long fromEpochTimeWithZeroMilliseconds) {
		// get tail from map
		System.out.println("+++From epoch " + fromEpochTimeWithZeroMilliseconds);
		// List of lists can be converted into a list of all values with flatMap
		final List<Transaction> transactions = getTransactions(fromEpochTimeWithZeroMilliseconds)
				.values().parallelStream()
				.flatMap(List::stream)
				.collect(Collectors.toList());

		if (transactions.isEmpty()) {
			System.out.println("+++No Transactions in list! from epoch " + fromEpochTimeWithZeroMilliseconds);
			return Optional.empty();
		}

		return Optional.of(createTransactionStatistic(transactions));
	}

	private TransactionStatistic createTransactionStatistic(List<Transaction> transactions) {
		TransactionStatistic transactionStatistic = new TransactionStatistic();
		transactionStatistic.setCount((long) transactions.size());
		transactionStatistic.setSum(transactions.parallelStream().mapToDouble(Transaction::getAmount).sum());

		transactionStatistic
				.setAvg(transactions.parallelStream().mapToDouble(Transaction::getAmount).average().getAsDouble());
		transactionStatistic.setMax(transactions.parallelStream().mapToDouble(Transaction::getAmount).max().getAsDouble());
		transactionStatistic.setMin(transactions.parallelStream().mapToDouble(Transaction::getAmount).min().getAsDouble());
		return transactionStatistic;
	}

	private ConcurrentNavigableMap<Long, List<Transaction>> getTransactions(long lastSeconds) {
		return transactionRequestHome.getTransactions().tailMap(lastSeconds);
	}

}
