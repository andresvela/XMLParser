import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.w3c.dom.xpath.XPathExpression;
import org.xml.sax.SAXException;


public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
		 throws ParserConfigurationException, SAXException, 
         IOException, XPathExpressionException {
		
		 ArrayList al = new ArrayList();
		 // add elements to the array list
		 
			
		  File dir = new File("C:\\temp\\testPrioritaire");
		  for (File file : dir.listFiles()) {
		    if (file.getName().endsWith((".xml"))) {
		    	al.add(file.getCanonicalFile().toString());
		    }
		  }
		  
		  File file = new File("c:\\temp\\dataDIREXIPrioritaire.csv");
		
		  
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("File;Modele" );
			  bw.write(System.getProperty("line.separator"));
			
		  				
		 
		 Iterator itr = al.iterator();
	      while(itr.hasNext()) {
	         Object element = itr.next();
	         
	         DocumentBuilderFactory domFactory = 
	      		   DocumentBuilderFactory.newInstance();
	      		         domFactory.setNamespaceAware(true); 
	      		   DocumentBuilder builder = domFactory.newDocumentBuilder();
	      		   org.w3c.dom.Document doc =  builder.parse(element.toString());
	      		   XPath xpath = XPathFactory.newInstance().newXPath();
	      		      // XPath Query for showing all nodes value
	      		   javax.xml.xpath.XPathExpression expr =  xpath.compile("//*/APPLICATIONTEMPLATENAME[1]/text()");
	      		  
	      		   Object result = expr.evaluate(doc, XPathConstants.NODESET);
	      		   NodeList nodes = (NodeList) result;
	      		   
	      		   if(nodes.getLength() == 0)
		      			System.out.println(element.toString() + ",- " );
		      		    
	      		 
	      		  // for (int i = 0; i < nodes.getLength(); i++) {
	      		 for (int i = 0; i < 1; i++) {
	      		    System.out.println(element.toString() + ";" +nodes.item(i).getNodeValue());
	      		  bw.write(element.toString() + ";" +nodes.item(i).getNodeValue());
	      		  bw.write(System.getProperty("line.separator"));
	  			
	      		  

	      }
	      //System.out.println();
	      		
	      
		  
	      }
	      bw.close();
	}

}
