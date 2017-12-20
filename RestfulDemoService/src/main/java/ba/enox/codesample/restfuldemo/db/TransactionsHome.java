package ba.enox.codesample.restfuldemo.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.springframework.stereotype.Component;

import ba.enox.codesample.restfuldemo.model.Transaction;

/**
 * @author eno.ahmedspahic
 *
 *This will be class responsible for db communication if it is necessary later. For now it will be sharable list for all requests.
 */
@Component
public class TransactionsHome {

    private ConcurrentNavigableMap<Long, List<Transaction>> transactions = new ConcurrentSkipListMap<>();
	
	public synchronized ConcurrentNavigableMap<Long, List<Transaction>> saveTransaction(Transaction transaction){
        List<Transaction> actualTimeTransactions = transactions.getOrDefault(transaction.getTimestamp(), new ArrayList<>());
		actualTimeTransactions.add(transaction);
		transactions.put(transaction.getTimestamp(), actualTimeTransactions);
		return transactions;
	} 

	public ConcurrentNavigableMap<Long, List<Transaction>> getTransactions(){
		return transactions;
	} 

}
