package utils;

import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.XPath;
import org.jaxen.XPathSyntaxException;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Node;
import java.util.List;

public class XPathUtils
{
	private static SimpleNamespaceContext initNsContext()
	{
		SimpleNamespaceContext nsContext;
		nsContext = new SimpleNamespaceContext();
		nsContext.addNamespace( "xsl", "http://www.w3.org/1999/XSL/Transform" );
		//nsContext.addNamespace( "xs", "urn:schemas-microsoft-com:xml-data" );
		//nsContext.addNamespace( "d", "urn:schemas-microsoft-com:datatypes" );
		//nsContext.addNamespace( "b", "urn:schemas-microsoft-com:BizTalkServer" );
		return nsContext;
	}
	
	public static Node getNode(Node parent, String strXpath)
	{
		SimpleNamespaceContext nsContext = initNsContext();
    	try{
		    XPath xpath = new DOMXPath(strXpath);
			xpath.setNamespaceContext( nsContext );
            return (Node)xpath.selectSingleNode( parent );
		}
		catch (XPathSyntaxException e) {
		    System.err.println( e.getMultilineMessage() );
		} catch (JaxenException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}
	
	public static List getNodes(Node parent, String strXpath)
	{
		SimpleNamespaceContext nsContext = initNsContext();
    	try{
		    XPath xpath = new DOMXPath(strXpath);
			xpath.setNamespaceContext( nsContext );
            return xpath.selectNodes( parent );
		}
		catch (XPathSyntaxException e) {
		    System.err.println( e.getMultilineMessage() );
		} catch (JaxenException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}
	
	public static String getNodeText(Node parent, String strXpath)
	{
		Node node = getNode(parent, strXpath);
		if(null != node)
			return node.getNodeValue();
		else
			return "";
	}
}
