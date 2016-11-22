/*
 *  Copyright (C) 2016 Australian Institute of Marine Science
 *
 *  Contact: Gael Lafond <g.lafond@aims.org.au>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package au.gov.aims.sld.symbolizer.attributes;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AttributeTest {
	@Test
	public void testParseCssParameter() throws Exception {
		String xmlDocument = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<xmlNode>\n" +
				"  <CssParameter name=\"stroke\">#FFFFFF</CssParameter>\n" +
				"  <CssParameter name=\"stroke-width\">2</CssParameter>\n" +
				"</xmlNode>\n";
		InputStream stream = new ByteArrayInputStream(xmlDocument.getBytes(StandardCharsets.UTF_8));

		Attribute attribute = new Attribute(null) {
			@Override
			public void parse(Node xmlNode, XPath xPath) throws Exception {}
		};

		XPathFactory xPathFactory = XPathFactory.newInstance();;
		XPath xPath = xPathFactory.newXPath();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(stream);
		stream.close();

		Node xmlNode = (Node)xPath.evaluate("/xmlNode", document, XPathConstants.NODE);
		Assert.assertNotNull("Can't find the node from the fake XML document.", xmlNode);

		Map<String, String> cssParameters = attribute.parseCssParameter(xmlNode, xPath);
		Assert.assertNotNull("No CSS parameters found in the fake XML document.", cssParameters);

		Assert.assertTrue("Can't find the 'stroke' CSS parameter.", cssParameters.containsKey("stroke"));
		Assert.assertEquals("Wrong value for CSS parameter 'stroke'.", "#FFFFFF", cssParameters.get("stroke"));

		Assert.assertTrue("Can't find the 'stroke' CSS parameter.", cssParameters.containsKey("stroke-width"));
		Assert.assertEquals("Wrong value for CSS parameter 'stroke-width'.", "2", cssParameters.get("stroke-width"));
	}
}
