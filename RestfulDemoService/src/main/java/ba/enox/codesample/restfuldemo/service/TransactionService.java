package ba.enox.codesample.restfuldemo.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ba.enox.codesample.restfuldemo.db.TransactionsHome;
import ba.enox.codesample.restfuldemo.model.Transaction;
import ba.enox.codesample.restfuldemo.model.TransactionStatistic;

@Service
public class TransactionService {
	@Autowired
	private TransactionsHome transactionRequestHome;

	public synchronized ConcurrentNavigableMap<Long, List<Transaction>> saveTransaction(Transaction transaction) {
		ConcurrentNavigableMap<Long, List<Transaction>> cnm = transactionRequestHome.saveTransaction(transaction);
		return cnm;
	}

	/**
	 * Method is created to support different time ranges.
	 * 
	 * @param fromEpochTimeWithZeroMilliseconds
	 *            this is 13digits value. Timestamp + 000
	 * @return
	 */
	public synchronized TransactionStatistic getStatisticInTime(Long fromEpochTimeWithZeroMilliseconds) {
		// get tail from map
		System.out.println("+++From epoch " + fromEpochTimeWithZeroMilliseconds);
		TransactionStatistic transactionStatistic = new TransactionStatistic();

		final List<Transaction> transactions = new ArrayList<Transaction>();

		this.getTransactions(fromEpochTimeWithZeroMilliseconds).values().parallelStream()
				.forEach(list -> transactions.addAll(list));

		ConcurrentNavigableMap<Long, List<Transaction>> cnm = this.getTransactions(123L);

		if (transactions.isEmpty()) {
			System.out.println("+++No Transactions in list! from epoch " + fromEpochTimeWithZeroMilliseconds);
			return transactionStatistic;
		}

		// for (Transaction t : transactions){
		// System.out.println("+++t "+t.getTimestamp() + " amount "+
		// t.getAmount() );
		// }

		transactionStatistic.setCount(new Long(transactions.size()));
		transactionStatistic.setSum(transactions.parallelStream().mapToDouble(a -> a.getAmount()).sum());

		transactionStatistic
				.setAvg(transactions.parallelStream().mapToDouble(a -> a.getAmount()).average().getAsDouble());
		transactionStatistic.setMax(transactions.parallelStream().mapToDouble(a -> a.getAmount()).max().getAsDouble());
		transactionStatistic.setMin(transactions.parallelStream().mapToDouble(a -> a.getAmount()).min().getAsDouble());

		return transactionStatistic;
	}

	public ConcurrentNavigableMap<Long, List<Transaction>> getTransactions(long lastSeconds) {
		return transactionRequestHome.getTransactions().tailMap(lastSeconds);
	}

}
