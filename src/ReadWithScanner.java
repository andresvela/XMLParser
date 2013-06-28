import java.io.*;
import java.util.Scanner;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ReadWithScanner {

	
	private DocumentBuilder docBuilder;
	private Document doc ;
	private Element rootElement;


  public static void main(String... aArgs) throws FileNotFoundException, ParserConfigurationException, TransformerException {
    ReadWithScanner parser = new ReadWithScanner("C:\\Temp\\Acc BN 14 (Gates) Syndication Loan Interest Remittance Statement_1.0.txt");
    parser.processLineByLine();
    log("Done.");
  }
  
  /**
   Constructor.
   @param aFileName full name of an existing, readable file.
  */
  public ReadWithScanner(String aFileName){
    fFile = new File(aFileName);  
  }
  
  /** Template method that calls {@link #processLine(String)}.  
 * @throws ParserConfigurationException 
 * @throws TransformerException */
  public final void processLineByLine() throws FileNotFoundException, ParserConfigurationException, TransformerException {
	  //create XMLM
	  this.createXML();
	  //create root
	  this.createRootXML("CASSIOPEROOT");
	  
    //Note that FileReader is used, not File, since File is not Closeable
    Scanner scanner = new Scanner(new FileReader(fFile));
    try {
      //first use a Scanner to get each line
      while ( scanner.hasNextLine() ){
        processLine( scanner.nextLine() );
      }
      this.writeXML();
    }
    catch (Exception e){
    	
    	log(e.getMessage());
    }
    finally {
      //ensure the underlying stream is always closed
      //this only has any effect if the item passed to the Scanner
      //constructor implements Closeable (which it does in this case).
      scanner.close();
      
      
    }
  }
  
  /** 
   Overridable method for processing lines in different ways.
    
   <P>This simple default implementation expects simple name-value pairs, separated by an 
   '=' sign. Examples of valid input : 
   <tt>height = 167cm</tt>
   <tt>mass =  65kg</tt>
   <tt>disposition =  "grumpy"</tt>
   <tt>this is the name = this is the value</tt>
  */
  protected void processLine(String aLine){
    //use a second Scanner to parse the content of each line 
    Scanner scanner = new Scanner(aLine);
    scanner.useDelimiter("#");  
    String ID = null;
    Element staff = null;
    try {
    	 if (scanner.hasNext() ){
    	    	ID = scanner.next();    
    	    	
    	    	// staff elements
    	    	staff = doc.createElement(ID);
    			rootElement.appendChild(staff);		
    	    	//log("ID is : " + quote(ID.trim()));
    	    	int i= 0;
    	   	    while (scanner.hasNext()){
    	   	    	i++;
    	   	    	String value = scanner.next();
    	   		  
    	   	    	// sons elements
    	   			Element dataElement = doc.createElement("DATA"+i);
    	   			dataElement.appendChild(doc.createTextNode(value));
    	   			staff.appendChild(dataElement);
    	   		  
    	   		  //log("Value is : " + quote(value.trim()) );
    	   	  }  
    	    	
    	    }  
    	    else {
    	        log("Empty or invalid line. Unable to process.");
    	        //no need to call scanner.close(), since the source is a String
    	      }
    }
    catch (Exception e){
    	log("- Line "+ ID + " error: " + e.getMessage());
    }
   
  }
  
  // PRIVATE 
  private final File fFile;
  
  private static void log(Object aObject){
    System.out.println(String.valueOf(aObject));
  }
  
  private String quote(String aText){
    String QUOTE = "'";
    return QUOTE + aText + QUOTE;
  }
  
  private void createXML() throws ParserConfigurationException{
  	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	this.docBuilder = docFactory.newDocumentBuilder();		
	  
  }
  
  private void createRootXML(String rootString){
	// root elements
		this.doc = docBuilder.newDocument();
		this.rootElement = doc.createElement(rootString);
		doc.appendChild(rootElement);
	  
  }
  
  private void writeXML() throws TransformerException{
	  
	// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\Temp\\Acc BN 14 (Gates) Syndication Loan Interest Remittance Statement_1.0.txt.xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
  }
} 