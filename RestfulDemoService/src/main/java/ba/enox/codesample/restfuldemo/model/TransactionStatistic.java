package ba.enox.codesample.restfuldemo.model;

public class TransactionStatistic {

	/**
	 * sum is a double specifying the total sum of transaction value in the last 60 seconds
	 */
	private Double sum;
	/**
	 * avg is a double specifying the average amount of transaction value in the last 60 seconds
	 */
	private Double avg;
	/**
	 * max is a double specifying single highest transaction value in the last 60 seconds
	 */
	private Double max;
	/**
	 * min is a double specifying single lowest transaction value in the last 60 seconds
	 */
	private Double min;
	/**
	 * count is a long specifying the total number of transactions happened in the last 60	seconds
	 */
	private Long count;
	
	public TransactionStatistic() {
		super();
		this.sum = 0D;
		this.avg = 0D;
		this.max = 0D;
		this.min = 0D;
		this.count = 0L;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
}
