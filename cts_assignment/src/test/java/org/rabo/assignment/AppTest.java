package org.rabo.assignment;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.rabo.assignment.handler.CsvHandler;
import org.rabo.assignment.handler.XmlHandler;
import org.rabo.assignment.model.RaboModel;
import org.rabo.assignment.validation.TransactionValidation;

/**
 * Unit test for simple App.
 */
public class AppTest
{
	
	@Test
	public void csvTest(){
		CsvHandler csvHandler = new CsvHandler();
		//csv file will be generated in src/main/resources
		csvHandler.reader();
	}
	
	@Test
	public void xmlTest(){
		XmlHandler xmlHandler = new XmlHandler();
		//xml file will be generated in src/main/resources
		xmlHandler.reader();
	}

	
	@Test
	public void referenceValidationTest(){
		List<RaboModel> raboModelList = new ArrayList<>();
		RaboModel raboModel1 = new RaboModel(123l,"transaction1","description1",10.00,-5.00,5.00);
		//duplicate reference id
		RaboModel raboModel2 = new RaboModel(123l,"transaction2","description2",10.00,-5.00,5.00);
		RaboModel raboModel3 = new RaboModel(124l,"transaction3","description3",10.00,-5.00,5.00);
		RaboModel raboModel4 = new RaboModel(125l,"transaction4","description4",10.00,-5.00,5.00);
		//duplicate reference id
		RaboModel raboModel5 = new RaboModel(124l,"transaction5","description5",10.00,-5.00,5.00);
		raboModelList.add(raboModel1);
		raboModelList.add(raboModel2);
		raboModelList.add(raboModel3);
		raboModelList.add(raboModel4);
		raboModelList.add(raboModel5);
		
		TransactionValidation transactionValidation = new TransactionValidation();
		List<RaboModel> erroredRecords = transactionValidation.validationTransaction(raboModelList);
		assertEquals(2,erroredRecords.size());
	}
	
	@Test
	public void mutationValidationTest(){
		List<RaboModel> raboModelList = new ArrayList<>();
		RaboModel raboModel1 = new RaboModel(123l,"transaction1","description1",10.00,-5.00,5.00);
		RaboModel raboModel2 = new RaboModel(124l,"transaction2","description2",3.00,-5.00,-2.00);
		//error record (end balance)
		RaboModel raboModel3 = new RaboModel(125l,"transaction3","description3",10.00,-5.00,7.00);
		//error record (end balance)
		RaboModel raboModel4 = new RaboModel(126l,"transaction4","description4",10.00,-12.00,-3.00);
		RaboModel raboModel5 = new RaboModel(127l,"transaction5","description5",20.00,-7.00,13.00);
		raboModelList.add(raboModel1);
		raboModelList.add(raboModel2);
		raboModelList.add(raboModel3);
		raboModelList.add(raboModel4);
		raboModelList.add(raboModel5);
		
		TransactionValidation transactionValidation = new TransactionValidation();
		List<RaboModel> erroredRecords = transactionValidation.validationTransaction(raboModelList);
		assertEquals(2,erroredRecords.size());
	}

}
