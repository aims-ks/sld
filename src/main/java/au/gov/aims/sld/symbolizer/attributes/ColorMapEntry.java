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
import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "ColorMap" defines either the colors of a pallet-type raster source
 * or the mapping of numeric pixel values to colors.
 */
public class ColorMapEntry extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(ColorMapEntry.class.getSimpleName());

	private String color; // Required
	private Double opacity;
	private Double quantity;
	private String label;

	public ColorMapEntry(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public String getColor() {
		return this.color;
	}

	public Double getOpacity() {
		return this.opacity;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public String getLabel() {
		return this.label;
	}

	/**
	 * <ColorMapEntry color="#323232" quantity="-300" label="label1" opacity="1"/>
	 */
	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		NamedNodeMap attributes = xmlNode.getAttributes();
		if (attributes != null) {
			Node colorNode = attributes.getNamedItem("color");
			if (colorNode == null) {
				throw new SAXException("ColorMapEntry without color.");
			}
			this.color = colorNode.getTextContent();

			Node opacityNode = attributes.getNamedItem("opacity");
			if (opacityNode != null) {
				String opacityStr = opacityNode.getTextContent();
				if (opacityStr != null && !opacityStr.isEmpty()) {
					try {
						this.opacity = Double.parseDouble(opacityStr);
					} catch(Exception ex) {
						LOGGER.log(Level.WARNING, "Invalid opacity '" + opacityStr + "'.", ex);
					}
				}
			}

			Node quantityNode = attributes.getNamedItem("quantity");
			if (quantityNode != null) {
				String quantityStr = quantityNode.getTextContent();
				if (quantityStr != null && !quantityStr.isEmpty()) {
					try {
						this.quantity = Double.parseDouble(quantityStr);
					} catch(Exception ex) {
						LOGGER.log(Level.WARNING, "Invalid quantity '" + quantityStr + "'.", ex);
					}
				}
			}

			Node labelNode = attributes.getNamedItem("label");
			if (labelNode != null) {
				this.label = labelNode.getTextContent();
			}
		}
	}
}
