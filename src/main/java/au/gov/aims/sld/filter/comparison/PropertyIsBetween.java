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

import au.gov.aims.sld.PropertyValue;
import au.gov.aims.sld.SldUtils;
import au.gov.aims.sld.geom.GeoShape;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * returns true if low <= num <= high
 * The <PropertyIsBetween> operator tests whether an expression value lies within a range given by a lower and upper bound (inclusive).
 */
public class PropertyIsBetween extends ComparisonFilter {
	private static final Logger LOGGER = Logger.getLogger(PropertyIsBetween.class.getSimpleName());

	private String lowerBoundary;
	private String upperBoundary;

	/**
	 * According to the DTD, this one is different from the other comparison filters.
	 */
	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		super.parse(xmlNode, xPath);

		Node lowerBoundaryNode = (Node)xPath.evaluate("LowerBoundary/Literal", xmlNode, XPathConstants.NODE);
		if (lowerBoundaryNode == null) {
			LOGGER.log(Level.WARNING, "PropertyIsBetween filter contains no 'LowerBoundary'.");
		} else {
			this.lowerBoundary = lowerBoundaryNode.getTextContent();
		}

		Node upperBoundaryNode = (Node)xPath.evaluate("UpperBoundary/Literal", xmlNode, XPathConstants.NODE);
		if (upperBoundaryNode == null) {
			LOGGER.log(Level.WARNING, "PropertyIsBetween filter contains no 'UpperBoundary'.");
		} else {
			this.upperBoundary = upperBoundaryNode.getTextContent();
		}
	}

	@Override
	public boolean filter(GeoShape geoShape) {
		String propertyName = this.getPropertyName();

		if (propertyName != null && !propertyName.isEmpty()) {
			Map<String, PropertyValue> properties = geoShape.getProperties();
			if (properties != null && !properties.isEmpty()) {
				PropertyValue rawPropertyValue = properties.get(propertyName);
				if (rawPropertyValue != null) {
					Double propertyValue = rawPropertyValue.getDoubleValue();

					if (propertyValue != null && SldUtils.isNumeric(this.lowerBoundary) && SldUtils.isNumeric(this.upperBoundary)) {
						Double lowerBoundaryNumeric = Double.parseDouble(this.lowerBoundary);
						Double upperBoundaryNumeric = Double.parseDouble(this.upperBoundary);

						return lowerBoundaryNumeric <= propertyValue && propertyValue <= upperBoundaryNumeric;
					} else {
						String propertyValueStr = rawPropertyValue.getStringValue();
						// It's seems to be case sensitive
						return this.lowerBoundary.compareTo(propertyValueStr) <= 0 && propertyValueStr.compareTo(upperBoundary) <= 0;
					}
				}
			}
		}

		return false;
	}
}
