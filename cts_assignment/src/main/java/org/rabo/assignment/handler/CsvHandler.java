package org.rabo.assignment.handler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rabo.assignment.model.RaboModel;
import org.rabo.assignment.validation.TransactionValidation;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;



public class CsvHandler implements Handler{
	private static final String[] CSV_COLUMN_HEADER = new String[]{"transactionReference","accountNumber","description","startBalanceEuro","mutation","endBalanceEuro"};
	private static final String CSV_WRITER_HEADER = "transactionReference,description";
	private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
	private final String PATH ="src/main/resources";
	TransactionValidation transactionValidation = new TransactionValidation();
	
	@Override
	public void reader(){
	CSVReader csvReader = null;
	List<RaboModel> raboModel = new ArrayList<>();
	File resourcesDirectory = new File(PATH);
	
	try{
		csvReader = new CSVReader(new FileReader(resourcesDirectory.getAbsolutePath()+"/records.csv"),',','"',1);
		ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
		mappingStrategy.setType(RaboModel.class);
		mappingStrategy.setColumnMapping(CSV_COLUMN_HEADER);
    
		CsvToBean csvToBean = new CsvToBean();
		List<RaboModel> recList = csvToBean.parse(mappingStrategy,csvReader);
    
		raboModel =transactionValidation.validationTransaction(recList);
		
		System.out.println("Errored Records from csv : ");
		for(RaboModel model : raboModel){
			System.out.print(model.getTransactionReference());
			System.out.print(" ");
			System.out.print(model.getDescription());
			System.out.println("");			
		}
		writer(raboModel);
    
	}catch (Exception e){
		System.out.println(e.getMessage());
	}
	
	}
	
	private void writer(List<RaboModel> raboModelList) throws IOException{
		FileWriter fileWriter = null;
		File resourcesDirectory = new File(PATH);
		try{
		fileWriter = new FileWriter(resourcesDirectory.getAbsolutePath()+"/failureRecordReport.csv");
		
		fileWriter.append(CSV_WRITER_HEADER.toString());
		fileWriter.append(NEW_LINE_SEPARATOR);
		
		for(RaboModel raboModel :raboModelList){
			fileWriter.append(String.valueOf(raboModel.getTransactionReference()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(raboModel.getDescription()));
			fileWriter.append(NEW_LINE_SEPARATOR);
		}
		System.out.println("Errored records CSV File was created successfully in src/main/resources");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
	            try{
	            	fileWriter.flush();
				    fileWriter.close();
				}catch(IOException e){
				    System.out.println(e.getMessage());
				}
		}
	}
	
}
