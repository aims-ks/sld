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
 * "ContrastEnhancement" defines the 'stretching' of contrast for a channel of a
 * false-color image or for a whole grey/color image.
 * Contrast enhancement is used to make ground features in images more visible.
 */
public class ContrastEnhancement extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(ContrastEnhancement.class.getSimpleName());

	private String normalize; // xsd:complexType
	private String histogram; // xsd:complexType
	private Double gammaValue;

	public ContrastEnhancement(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public String getNormalize() {
		return this.normalize;
	}

	public String getHistogram() {
		return this.histogram;
	}

	public Double getGammaValue() {
		return this.gammaValue;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node normalizeNode = (Node)xPath.evaluate("Normalize", xmlNode, XPathConstants.NODE);
		if (normalizeNode != null) {
			this.normalize = normalizeNode.getTextContent();
		}

		Node histogramNode = (Node)xPath.evaluate("Histogram", xmlNode, XPathConstants.NODE);
		if (histogramNode != null) {
			this.histogram = histogramNode.getTextContent();
		}

		Node gammaValueNode = (Node)xPath.evaluate("GammaValue", xmlNode, XPathConstants.NODE);
		if (gammaValueNode != null) {
			String gammaValueStr = gammaValueNode.getTextContent();
			if (gammaValueStr != null && !gammaValueStr.isEmpty()) {
				try {
					this.gammaValue = Double.parseDouble(gammaValueStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid GammaValue '" + gammaValueStr + "'.", ex);
				}
			}
		}
	}
}
