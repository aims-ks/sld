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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "Halo" fills an extended area outside the glyphs of a rendered text label
 * to make the label easier to read over a background.
 */
public class Halo extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(Halo.class.getSimpleName());

	private Double radius; // ParameterValueType
	private Fill fill;

	public Halo(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Double getRadius() {
		return this.radius;
	}

	public Fill getFill() {
		return this.fill;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node radiusNode = (Node)xPath.evaluate("Radius", xmlNode, XPathConstants.NODE);
		if (radiusNode != null) {
			String radiusStr = radiusNode.getTextContent();
			if (radiusStr != null && !radiusStr.isEmpty()) {
				try {
					this.radius = Double.parseDouble(radiusStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid Radius '" + radiusStr + "'.");
				}
			}
		}

		Node fillNode = (Node)xPath.evaluate("Fill", xmlNode, XPathConstants.NODE);
		if (fillNode != null) {
			this.fill = new Fill(this.getStyleSheet());
			this.fill.parse(fillNode, xPath);
		}
	}
}
