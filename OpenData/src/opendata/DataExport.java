package opendata;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DataExport {

	// Parameter
	private String kennung = "";
	private String passwort = "";
	private String namen;
	private String bereich = "alle";
	private String format = "csv";
	private String werte = "true";
	private String metadaten = "false";
	private String zusatz = "false";
	private String startjahr = "2010";
	private String endjahr = "2014";
	private String zeitscheiben = "3";
	private String regionalschluessel = "085";
	private String sachmerkmal = "";
	private String sachschluessel = "";
	private String stand = "";
	private String sprache = "de";
	
	// Webservice
	private String url = "https://www.regionalstatistik.de/genesisws/services/ExportService";
	private String serverUri = "https://genesis-regional.it.nrw.de";
	private String namespace = "http://webservice.genesis";
	
	public DataExport(String namen){
		// Quadernamen
		this.namen = namen;
	}
	
	// Daten ziehen
	public String getData() throws Exception, SOAPException{
        // SOAP Connection zeugen
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        javax.xml.soap.SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        
        // Request erzeugen und abschicken
        SOAPMessage soapRequest = createMessage();
        SOAPMessage soapResponse = soapConnection.call(soapRequest, url);
        
        // <quaderDaten> aus der Response holen
    	NodeList quaderList = soapResponse.getSOAPBody().getElementsByTagName("quaderDaten");
        Node quaderNode = quaderList.item(0);
        String quaderData = quaderNode.getTextContent();

        soapConnection.close();
        
        return quaderData;
	}
		
	// SOAP Message erzeugen
	private SOAPMessage createMessage() throws Exception{
		
		// SOAP Message
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
		
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("web", namespace);
        
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("DatenExport", "web");
        
        // Einzelnen Parameter hinzufügen
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("kennung", "web");
        soapBodyElem1.addTextNode(kennung);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("passwort", "web");
        soapBodyElem2.addTextNode(passwort);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("namen", "web");
        soapBodyElem3.addTextNode(namen);
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("bereich", "web");
        soapBodyElem4.addTextNode(bereich);
        SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("format", "web");
        soapBodyElem5.addTextNode(format);
        SOAPElement soapBodyElem6 = soapBodyElem.addChildElement("werte", "web");
        soapBodyElem6.addTextNode(werte);
        SOAPElement soapBodyElem7 = soapBodyElem.addChildElement("metadaten", "web");
        soapBodyElem7.addTextNode(metadaten);
        SOAPElement soapBodyElem8 = soapBodyElem.addChildElement("zusatz", "web");
        soapBodyElem8.addTextNode(zusatz);
        SOAPElement soapBodyElem9 = soapBodyElem.addChildElement("startjahr", "web");
        soapBodyElem9.addTextNode(startjahr);
        SOAPElement soapBodyElem10 = soapBodyElem.addChildElement("endjahr", "web");
        soapBodyElem10.addTextNode(endjahr);
        SOAPElement soapBodyElem11 = soapBodyElem.addChildElement("zeitscheiben", "web");
        soapBodyElem11.addTextNode(zeitscheiben);
        SOAPElement soapBodyElem12 = soapBodyElem.addChildElement("regionalschluessel", "web");
        soapBodyElem12.addTextNode(regionalschluessel);
        SOAPElement soapBodyElem13 = soapBodyElem.addChildElement("sachmerkmal", "web");
        soapBodyElem13.addTextNode(sachmerkmal);
        SOAPElement soapBodyElem14 = soapBodyElem.addChildElement("sachschluessel", "web");
        soapBodyElem14.addTextNode(sachschluessel);
        SOAPElement soapBodyElem15 = soapBodyElem.addChildElement("stand", "web");
        soapBodyElem15.addTextNode(stand);
        SOAPElement soapBodyElem16 = soapBodyElem.addChildElement("sprache", "web");
        soapBodyElem16.addTextNode(sprache);
        
        // Header
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", this.serverUri  + "DatenExport");

        soapMessage.saveChanges();
        
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
        
		return soapMessage;
	}

	
	
	public String getKennung() {
		return kennung;
	}
	public void setKennung(String kennung) {
		this.kennung = kennung;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public String getNamen() {
		return namen;
	}
	public void setNamen(String namen) {
		this.namen = namen;
	}
	public String getBereich() {
		return bereich;
	}
	public void setBereich(String bereich) {
		this.bereich = bereich;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getWerte() {
		return werte;
	}
	public void setWerte(String werte) {
		this.werte = werte;
	}
	public String getMetadaten() {
		return metadaten;
	}
	public void setMetadaten(String metadaten) {
		this.metadaten = metadaten;
	}
	public String getZusatz() {
		return zusatz;
	}
	public void setZusatz(String zusatz) {
		this.zusatz = zusatz;
	}
	public String getStartjahr() {
		return startjahr;
	}
	public void setStartjahr(String startjahr) {
		this.startjahr = startjahr;
	}
	public String getEndjahr() {
		return endjahr;
	}
	public void setEndjahr(String endjahr) {
		this.endjahr = endjahr;
	}
	public String getZeitscheiben() {
		return zeitscheiben;
	}
	public void setZeitscheiben(String zeitscheiben) {
		this.zeitscheiben = zeitscheiben;
	}
	public String getRegionalschluessel() {
		return regionalschluessel;
	}
	public void setRegionalschluessel(String regionalschluessel) {
		this.regionalschluessel = regionalschluessel;
	}
	public String getSachmerkmal() {
		return sachmerkmal;
	}
	public void setSachmerkmal(String sachmerkmal) {
		this.sachmerkmal = sachmerkmal;
	}
	public String getStand() {
		return stand;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	public String getSachschluessel() {
		return sachschluessel;
	}
	public void setSachschluessel(String sachschluessel) {
		this.sachschluessel = sachschluessel;
	}
	public String getSprache() {
		return sprache;
	}
	public void setSprache(String sprache) {
		this.sprache = sprache;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServerUri() {
		return serverUri;
	}
	public void setServerUri(String serverUri) {
		this.serverUri = serverUri;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
