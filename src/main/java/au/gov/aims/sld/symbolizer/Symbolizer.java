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
package au.gov.aims.sld.symbolizer;

import au.gov.aims.sld.StyleSheet;
import au.gov.aims.sld.geom.GeoShape;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Symbolizer {
	private StyleSheet styleSheet;

	public Symbolizer(StyleSheet styleSheet) {
		this.styleSheet = styleSheet;
	}

	public abstract void parse(Node xmlNode, XPath xPath) throws Exception;
	public abstract List<GeoShape> applyStyle(GeoShape geoShape);

	public StyleSheet getStyleSheet() {
		return this.styleSheet;
	}

	/**
	 * <xmlNode>
	 *   ...
	 *   <VendorOption name="autoWrap">60</VendorOption>
	 *   <VendorOption name="maxDisplacement">150</VendorOption>
	 * </xmlNode>
	 */
	protected Map<String, String> parseVendorOptions(Node xmlNode, XPath xPath) throws Exception {
		Map<String, String> vendorOptions = new HashMap<String, String>();
		NodeList vendorOptionNodes = (NodeList)xPath.evaluate("VendorOption", xmlNode, XPathConstants.NODESET);
		if (vendorOptionNodes != null) {
			for (int i=0; i<vendorOptionNodes.getLength(); i++) {
				Node vendorOptionNode = vendorOptionNodes.item(i);
				if (vendorOptionNode != null) {
					NamedNodeMap attributes = vendorOptionNode.getAttributes();
					if (attributes != null) {
						Node keyNode = attributes.getNamedItem("name");
						if (keyNode != null) {
							String key = keyNode.getTextContent();
							String value = vendorOptionNode.getTextContent();
							vendorOptions.put(key, value);
						}
					}
				}
			}
		}

		return vendorOptions;
	}
}
