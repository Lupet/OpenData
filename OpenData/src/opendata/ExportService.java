package opendata;

import javax.xml.soap.SOAPException;


public class ExportService {


    public static void main(String args[]) throws SOAPException, Exception {

    	// Quader erstellen
		DataExport quader = new DataExport("71137KJ001");

		// default-Parameter zb so �berschreiben:
		//quader.setStartjahr("2012");
		
		// R�ckgabe ist der Inhalt des <quaderDaten>-Knotens der SOAP-Response
		String data = quader.getData();
        
		System.out.println(data);
}



}