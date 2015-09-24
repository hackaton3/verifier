package com.verify.main.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XpathUtil {

    private static Logger logger = Logger.getLogger(XpathUtil.class);

    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private String filePath;

    public XpathUtil(String filePath) {
        this.filePath = filePath;
    }

    private Document parse(String filePath) {
        DocumentBuilder builder = null;
        Document doc = null;
        try {
            factory.setNamespaceAware(true);
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(filePath));
            return doc;
        } catch (Exception e) {
            logger.error("Parse \"" + filePath + "\" encountered error!", e);
            return null;
        }
    }

    public List<String> getValue(String xpath) {

        List<String> result = new ArrayList<String>();

        if (this.filePath != null && !"".equals(this.filePath)) {
            logger.error("The file path for XpathHelper class should not be empty!");
            return result;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("The Xpath expression is: " + xpath);
            logger.debug("Start to parse " + this.filePath);
        }

        final Document doc = parse(this.filePath);
        if (doc == null) {
            return result;
        }

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpathObject = xPathfactory.newXPath();
        xpathObject.setNamespaceContext(new NamespaceContext() {

            /**
             * The lookup for the namespace uris is delegated to the stored
             * document.
             * 
             * @param prefix
             *            to search for
             * @return uri
             */
            public String getNamespaceURI(String prefix) {
                if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
                    return doc.lookupNamespaceURI(null);
                } else {
                    return doc.lookupNamespaceURI(prefix);
                }
            }

            /**
             * This method is not needed in this context, but can be implemented
             * in a similar way.
             */
            public String getPrefix(String namespaceURI) {
                return doc.lookupPrefix(namespaceURI);
            }

            @SuppressWarnings("rawtypes")
            public Iterator getPrefixes(String namespaceURI) {
                // not implemented yet
                return null;
            }

        });

        try {
            NodeList nodes = (NodeList) xpathObject.evaluate(xpath, doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                result.add(node.getTextContent());
            }
            return result;

        } catch (XPathExpressionException e) {
            logger.error("We encountered error while we tried to get the value by Xpath string: " + xpath, e);
        }

        return result;
    }

}
