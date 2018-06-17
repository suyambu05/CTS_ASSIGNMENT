package org.rabo.assignment.handler;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.rabo.assignment.model.RaboModel;
import org.rabo.assignment.validation.TransactionValidation;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


public class CsvHandler implements Handler{
	private static final String[] CSV_COLUMN_HEADER = new String[]{"transactionReference","accountNumber","description","startBalanceEuro","mutation","endBalanceEuro"};
	private static final String[] CSV_WRITER_HEADER = {"transactionReference", "description"};
	TransactionValidation transactionValidation = new TransactionValidation();
	
	@Override
	public void reader(){
	CSVReader csvReader = null;
	List<RaboModel> raboModel = new ArrayList<>();
	try{
		csvReader = new CSVReader(new FileReader("C:/cts/records.csv"),',','"',1);
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
	
	private void writer(List<RaboModel> raboModel) throws IOException{
		FileWriter fileWriter = null;
		CSVWriter csvWriter = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
		InputStream is = classLoader.getResourceAsStream("failureRecordReport.csv");
		fileWriter = new FileWriter("C:/cts/failureRecordReport.csv");
		try{
			csvWriter = new CSVWriter(fileWriter,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
			csvWriter.writeNext(CSV_WRITER_HEADER);
			 
			for (RaboModel rabobean : raboModel) {
				String[] data = {String.valueOf(rabobean.getTransactionReference()),rabobean.getDescription()};
				csvWriter.writeNext(data);
			}
		}
		catch(Exception e){
			 System.out.println(e.getMessage());
		}
	}
	
}
