package org.rabo.assignment.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.rabo.assignment.model.RaboModel;
import org.rabo.assignment.model.Records;
import org.rabo.assignment.validation.TransactionValidation;

public class XmlHandler implements Handler{
	private final String PATH ="src/main/resources";
	
	List<RaboModel> raboModelList;
	TransactionValidation transactionValidation = new TransactionValidation();
	List<RaboModel> filteredRaboModelList;
	
	@Override
	public void reader(){
		raboModelList = new ArrayList<>();
		RaboModel raboModel = null;
		ClassLoader classloader = getClass().getClassLoader();
		InputStream inputStream = null;
		
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {
			inputStream = classloader.getResourceAsStream("records.xml");
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){
                   StartElement startElement = xmlEvent.asStartElement();
                   if(startElement.getName().getLocalPart().equals("record")){
                	   raboModel = new RaboModel();
                	   Attribute referenceAttr = startElement.getAttributeByName(new QName("reference"));
                	   if(referenceAttr !=null){
                		   raboModel.setTransactionReference(Long.valueOf(referenceAttr.getValue()));
                	   }
                   }else if(startElement.getName().getLocalPart().equals("accountNumber")){
                	   xmlEvent = xmlEventReader.nextEvent();
                	   raboModel.setAccountNumber(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals("description")){
                	   xmlEvent = xmlEventReader.nextEvent();
                	   raboModel.setDescription(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals("startBalance")){
                	   xmlEvent = xmlEventReader.nextEvent();
                	   raboModel.setStartBalanceEuro(Double.valueOf(xmlEvent.asCharacters().getData()));
                   }else if(startElement.getName().getLocalPart().equals("mutation")){
                	   xmlEvent = xmlEventReader.nextEvent();
                	   raboModel.setMutation(Double.valueOf(xmlEvent.asCharacters().getData()));
                   }else if(startElement.getName().getLocalPart().equals("endBalance")){
                	   xmlEvent = xmlEventReader.nextEvent();
                	   raboModel.setEndBalanceEuro(Double.valueOf(xmlEvent.asCharacters().getData()));
                   }
               }
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
                   if(endElement.getName().getLocalPart().equals("record")){
                       raboModelList.add(raboModel);
                   }
               }
            }
            filteredRaboModelList = transactionValidation.validationTransaction(raboModelList);
            writeXml(filteredRaboModelList);
            
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	private void writeXml(List<RaboModel> filteredRaboModelList) {
		Records records = new Records();
		List<RaboModel> raboList =new ArrayList<>();
	    File resourcesDirectory = new File(PATH);
	    
		
		for(RaboModel rabo : filteredRaboModelList){
			RaboModel raboModel = new RaboModel();
			raboModel.setTransactionReference(rabo.getTransactionReference());
			raboModel.setDescription(rabo.getDescription());
			raboList.add(raboModel);
		}
		records.setRecord(raboList);
		try {
			JAXBContext context =JAXBContext.newInstance(Records.class);
			Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            System.out.println(" ");
            System.out.println("Errored Records from xml : ");
            marshaller.marshal(records, System.out);
            OutputStream os = new FileOutputStream(resourcesDirectory.getAbsolutePath()+ "/erroredRecords.xml" );
            marshaller.marshal(records, os );
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }		
	}

}
