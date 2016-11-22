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
package au.gov.aims.sld.filter.comparison;

import au.gov.aims.sld.filter.Filter;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ComparisonFilter extends Filter {
	private static final Logger LOGGER = Logger.getLogger(ComparisonFilter.class.getSimpleName());

	private String propertyName;
	private String literal;

	public String getPropertyName() {
		return this.propertyName;
	}

	public String getLiteral() {
		return this.literal;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node propertyNameNode = (Node)xPath.evaluate("PropertyName", xmlNode, XPathConstants.NODE);
		if (propertyNameNode == null) {
			LOGGER.log(Level.WARNING, "Comparison filter contains no 'PropertyName'.");
		} else {
			this.propertyName = propertyNameNode.getTextContent();
		}

		Node literalNode = (Node)xPath.evaluate("Literal", xmlNode, XPathConstants.NODE);
		if (literalNode != null) {
			this.literal = literalNode.getTextContent();
		}
	}

	@Override
	public String toString() {
		return "ComparisonFilter{" +
				"propertyName='" + propertyName + '\'' +
				", literal='" + literal + '\'' +
				'}';
	}
}
