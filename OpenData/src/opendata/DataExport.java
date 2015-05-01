package opendata;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	private String namespaceGenesis = "http://webservice.genesis";
	private String namespaceTags = "web";
	
	public DataExport(String namen){
		this.namen = namen;
	}
	
	// Quaderdaten aus dem Webservice ziehen
	public String getData() throws Exception, SOAPException{
		
        // SOAP Connection zeugen
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        javax.xml.soap.SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        
        // Request erzeugen und abschicken
        SOAPMessage soapRequest = createMessage();
        SOAPMessage soapResponse = soapConnection.call(soapRequest, url);
        
        // Element <quaderDaten> aus der Response holen
    	NodeList quaderList = soapResponse.getSOAPBody().getElementsByTagName("quaderDaten");
        Node quaderNode = quaderList.item(0);
        String quaderData = quaderNode.getTextContent();

        // Connention schließen
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
        envelope.addNamespaceDeclaration(namespaceTags, namespaceGenesis);
        
        // SOAP Body
        createSOAPBody(envelope);
        
        // Header
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", this.serverUri  + "DatenExport");

        soapMessage.saveChanges();
        
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
        
		return soapMessage;
	}

	// SOAPBody aus den Parametern erstellen
	private void createSOAPBody(SOAPEnvelope envelope) throws SOAPException{
		
		// LinkedHashMap statt normaler HashMap, weil diese die Reihenfolge der Parameter nicht ändert. Der Webservice erwartet genau die folgende Reihenfolge..
		Map<String, String> paraMap = new LinkedHashMap<String, String>();
		paraMap.put("kennung", kennung);
		paraMap.put("passwort", passwort);
		paraMap.put("namen", namen);
		paraMap.put("bereich", bereich);
		paraMap.put("format", format);
		paraMap.put("werte", werte);
		paraMap.put("metadaten", metadaten);
		paraMap.put("zusatz", zusatz);
		paraMap.put("startjahr", startjahr);
		paraMap.put("endjahr", endjahr);
		paraMap.put("zeitscheiben", zeitscheiben);
		paraMap.put("regionalschluessel", regionalschluessel);
		paraMap.put("sachmerkmal", sachmerkmal);
		paraMap.put("sachschluessel", sachschluessel);
		paraMap.put("stand", stand);
		paraMap.put("sprache", sprache);
		
		// Parent-Element <DatenExport>
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("DatenExport", namespaceTags);
        
        // Durch die Parameter-Map iterieren und jeweils ein SOAPElement erstellen, füllen und an <DatenExport> hängen
        Iterator<Entry<String, String>> it = paraMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,String> pair = (Map.Entry<String,String>)it.next();
            
            SOAPElement soapTmpElement = soapBodyElem.addChildElement(pair.getKey(), namespaceTags);
            soapTmpElement.addTextNode(pair.getValue());
            
            it.remove();
        }
	}
	
	
	// setter/getter, falls man die default-Parameter überschreiben will
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
	public String getNamespaceGenesis() {
		return namespaceGenesis;
	}
	public void setNamespaceGenesis(String namespace) {
		this.namespaceGenesis = namespace;
	}
	public String getNamespaceTags() {
		return namespaceTags;
	}
	public void setNamespaceTags(String namespaceTags) {
		this.namespaceTags = namespaceTags;
	}
}
