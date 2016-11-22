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
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.logging.Logger;

/**
 * A Geometry gives reference to a (the) geometry property of a feature to be used for rendering.
 */
public class Geometry extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(Geometry.class.getSimpleName());

	private String propertyName;

	public Geometry(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	// I don't know what this is... I can't find any example
	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node propertyNameNode = (Node)xPath.evaluate("PropertyName", xmlNode, XPathConstants.NODE);
		if (propertyNameNode != null) {
			this.propertyName = propertyNameNode.getTextContent();
		}
	}
}
