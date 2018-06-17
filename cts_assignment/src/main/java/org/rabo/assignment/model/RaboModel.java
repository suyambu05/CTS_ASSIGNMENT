package org.rabo.assignment.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
public class RaboModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1117959516903489746L;
	@XmlElement(name="reference")
	private Long transactionReference;
	
	private String accountNumber;
	@XmlElement
	private String description;

	private double startBalanceEuro;
	
	private double mutation;

	private double endBalanceEuro;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		long temp;
		temp = Double.doubleToLongBits(endBalanceEuro);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mutation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(startBalanceEuro);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((transactionReference == null) ? 0 : transactionReference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RaboModel other = (RaboModel) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Double.doubleToLongBits(endBalanceEuro) != Double.doubleToLongBits(other.endBalanceEuro))
			return false;
		if (Double.doubleToLongBits(mutation) != Double.doubleToLongBits(other.mutation))
			return false;
		if (Double.doubleToLongBits(startBalanceEuro) != Double.doubleToLongBits(other.startBalanceEuro))
			return false;
		if (transactionReference == null) {
			if (other.transactionReference != null)
				return false;
		} else if (!transactionReference.equals(other.transactionReference))
			return false;
		return true;
	}

	public Long getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(Long transactionReference) {
		this.transactionReference = transactionReference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getStartBalanceEuro() {
		return startBalanceEuro;
	}

	public void setStartBalanceEuro(double startBalanceEuro) {
		this.startBalanceEuro = startBalanceEuro;
	}

	public double getMutation() {
		return mutation;
	}

	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	public double getEndBalanceEuro() {
		return endBalanceEuro;
	}

	public void setEndBalanceEuro(double endBalanceEuro) {
		this.endBalanceEuro = endBalanceEuro;
	}


}
