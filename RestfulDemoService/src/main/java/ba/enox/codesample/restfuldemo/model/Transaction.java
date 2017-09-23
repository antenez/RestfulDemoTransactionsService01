package ba.enox.codesample.restfuldemo.model;

public class Transaction {
	/*
	 * transaction amount
	 */
	Double amount;
	/*
	 * transaction time in epoch in milliseconds in UTC time zone (this is not
	 * current timestamp)
	 */
	Long timestamp;

	public Transaction() {
	}

	public Transaction(Double amount, Long timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
