package org.rabo.assignment.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.rabo.assignment.model.RaboModel;

public class TransactionValidation {
	
	public List<RaboModel> validationTransaction(List<RaboModel> raboModel){
		List<RaboModel> errorRecords = new ArrayList<>();
		Set<Long> filterSet = new HashSet<>();
		
		raboModel.forEach(model -> {
			if(!filterSet.add(model.getTransactionReference()) || endBalanceValidation(model)){
				errorRecords.add(model);
			}
	    });
		
		return errorRecords;
		
	}

	private boolean endBalanceValidation(RaboModel model) {
		
		BigDecimal startBalance = new BigDecimal(Double.toString(model.getStartBalanceEuro()));
		BigDecimal mutation = new BigDecimal(Double.toString(model.getMutation()));
		BigDecimal endBalance = startBalance.add(mutation);
		BigDecimal endBal= new BigDecimal(Double.toString(model.getEndBalanceEuro()));
		if(endBalance.compareTo(endBal) == 0 ? false: true){
			return true;
		}
		return false;
	}
}
