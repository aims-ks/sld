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
import au.gov.aims.sld.geom.GeoShape;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Returns true if the string matches the specified pattern.
 * For the full syntax of the pattern specification see the Java Pattern class javadocs
 *   http://java.sun.com/javase/6/docs/api/java/util/regex/Pattern.html
 */
public class PropertyIsLike extends ComparisonFilter {
	private Pattern pattern;

	/**
	 * According to the DTD, this one is different from the other comparison filters.
	 */
	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		super.parse(xmlNode, xPath);

		String literal = this.getLiteral();
		if (literal != null && !literal.isEmpty()) {
			this.pattern = Pattern.compile(literal);
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
					return this.pattern.matcher(rawPropertyValue.getStringValue()).matches();
				}
			}
		}

		return false;
	}
}
