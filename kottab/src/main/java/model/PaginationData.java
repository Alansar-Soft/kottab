package model;

import utilities.NumbersUtility;

public class PaginationData {
	private int totalRecords;
	private int firstResult;
	private int maxResult;

	public PaginationData(int maxResult) {
		this(0, maxResult);
	}

	public PaginationData(int firstResult, int maxResult) {
		this(firstResult, maxResult, 100);
	}

	public PaginationData(int firstResult, int maxResult, int totalRecords) {
		this.firstResult = firstResult;
		this.maxResult = maxResult;
		this.totalRecords = totalRecords;
	}

	public PaginationData() {
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public PaginationData next() {
		int newFirstResult = firstResult + maxResult;
		if (newFirstResult < totalRecords)
			firstResult = newFirstResult;
		return this;
	}

	public PaginationData previous() {
		firstResult = NumbersUtility.toZeroIfNegativeOrNull(firstResult - maxResult);
		return this;
	}
}
