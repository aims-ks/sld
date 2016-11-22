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
package au.gov.aims.sld.symbolizer.attributes.placement;

import au.gov.aims.sld.StyleSheet;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "LinePlacement" specifies how a text label should be rendered relative to a linear geometry.
 */
public class LinePlacement extends Placement {
	private static final Logger LOGGER = Logger.getLogger(LinePlacement.class.getSimpleName());

	// A "PerpendicularOffset" gives the perpendicular distance away from a line to draw a label.
	private Double perpendicularOffset;

	public LinePlacement(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Double getPerpendicularOffset() {
		return this.perpendicularOffset;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node perpendicularOffsetNode = (Node)xPath.evaluate("PerpendicularOffset", xmlNode, XPathConstants.NODE);
		if (perpendicularOffsetNode != null) {
			String perpendicularOffsetStr = perpendicularOffsetNode.getTextContent();
			if (perpendicularOffsetStr != null && !perpendicularOffsetStr.isEmpty()) {
				try {
					this.perpendicularOffset = Double.parseDouble(perpendicularOffsetStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid PerpendicularOffset '" + perpendicularOffsetStr + "'.", ex);
				}
			}
		}
	}
}
