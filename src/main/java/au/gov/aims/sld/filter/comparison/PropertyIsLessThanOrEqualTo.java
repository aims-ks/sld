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

import java.util.Map;

/**
 * Returns true if x <= y. Parameters can be either numbers or strings (in the second case lexicographic ordering is used
 */
public class PropertyIsLessThanOrEqualTo extends ComparisonFilter {
	@Override
	public boolean filter(GeoShape geoShape) {
		String propertyName = this.getPropertyName();
		String literal = this.getLiteral();

		if (propertyName != null && !propertyName.isEmpty()) {
			Map<String, PropertyValue> properties = geoShape.getProperties();
			if (properties != null && !properties.isEmpty()) {
				PropertyValue rawPropertyValue = properties.get(propertyName);
				if (rawPropertyValue != null) {
					Double propertyValue = rawPropertyValue.getDoubleValue();

					if (propertyValue != null && SldUtils.isNumeric(literal)) {
						Double literalNumeric = Double.parseDouble(literal);

						return propertyValue.compareTo(literalNumeric) <= 0;
					} else {
						String propertyValueStr = rawPropertyValue.getStringValue();
						// It's seems to be case sensitive
						return propertyValueStr.compareTo(literal) <= 0;
					}
				}
			}
		}

		return false;
	}
}
