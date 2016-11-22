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

import au.gov.aims.sld.StyleSheet;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.HashMap;
import java.util.Map;

public abstract class Attribute {
	private StyleSheet styleSheet;

	public abstract void parse(Node xmlNode, XPath xPath) throws Exception;

	public Attribute(StyleSheet styleSheet) {
		this.styleSheet = styleSheet;
	}

	public StyleSheet getStyleSheet() {
		return this.styleSheet;
	}

	/**
	 * <xmlNode>
	 *   ...
	 *   <CssParameter name="stroke">#FFFFFF</CssParameter>
	 *   <CssParameter name="stroke-width">2</CssParameter>
	 * </xmlNode>
	 */
	protected Map<String, String> parseCssParameter(Node xmlNode, XPath xPath) throws Exception {
		Map<String, String> cssParameters = new HashMap<String, String>();
		NodeList cssParameterNodes = (NodeList)xPath.evaluate("CssParameter", xmlNode, XPathConstants.NODESET);
		if (cssParameterNodes != null) {
			for (int i=0; i<cssParameterNodes.getLength(); i++) {
				Node cssParameterNode = cssParameterNodes.item(i);
				if (cssParameterNode != null) {
					NamedNodeMap attributes = cssParameterNode.getAttributes();
					if (attributes != null) {
						Node keyNode = attributes.getNamedItem("name");
						if (keyNode != null) {
							String key = keyNode.getTextContent();
							String value = cssParameterNode.getTextContent();
							cssParameters.put(key, value);
						}
					}
				}
			}
		}

		return cssParameters;
	}
}
