package org.rabo.assignment;

import org.rabo.assignment.handler.CsvHandler;
import org.rabo.assignment.handler.XmlHandler;

public class App 
{
    public static void main( String[] args )
    {
    	CsvHandler csvHandler = new CsvHandler();
    	csvHandler.reader();
        
        XmlHandler xmlHandler = new XmlHandler();
        xmlHandler.reader();
    }
}
