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
 * "ShadedRelief" specifies the application of relief shading (or "hill shading") to a DEM raster
 * to give it somewhat of a three-dimensional effect and to make elevation changes more visible.
 */
public class ShadedRelief extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(ShadedRelief.class.getSimpleName());

	private Boolean brightnessOnly;
	private Double reliefFactor;

	public ShadedRelief(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public boolean isBrightnessOnly() {
		return this.brightnessOnly == null ? false : this.brightnessOnly;
	}

	public Double getReliefFactor() {
		return this.reliefFactor;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node brightnessOnlyNode = (Node)xPath.evaluate("BrightnessOnly", xmlNode, XPathConstants.NODE);
		if (brightnessOnlyNode != null) {
			String brightnessOnlyStr = brightnessOnlyNode.getTextContent();
			if (brightnessOnlyStr != null && !brightnessOnlyStr.isEmpty()) {
				try {
					this.brightnessOnly = Boolean.parseBoolean(brightnessOnlyStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid BrightnessOnly '" + brightnessOnlyStr + "'.", ex);
				}
			}
		}

		Node reliefFactorNode = (Node)xPath.evaluate("ReliefFactor", xmlNode, XPathConstants.NODE);
		if (reliefFactorNode != null) {
			String reliefFactorStr = reliefFactorNode.getTextContent();
			if (reliefFactorStr != null && !reliefFactorStr.isEmpty()) {
				try {
					this.reliefFactor = Double.parseDouble(reliefFactorStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid ReliefFactor '" + reliefFactorStr + "'.", ex);
				}
			}
		}
	}
}
