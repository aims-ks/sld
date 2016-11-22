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
import au.gov.aims.sld.Utils;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.awt.Paint;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "Fill" specifies the pattern for filling an area geometry.
 * The allowed CssParameters are:
 *   - "fill" (color) and
 *   - "fill-opacity".
 */
public class Fill extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(Fill.class.getName());

	private Graphic graphicFill;

	// CssParameters
	private String fillColour; // Hexadecimal colour (#FFFFFF)
	private Double fillOpacity;

	public Fill(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Graphic getGraphicFill() {
		return this.graphicFill;
	}

	public String getFillColour() {
		return this.fillColour;
	}

	public Double getFillOpacity() {
		return this.fillOpacity;
	}

	public Paint getFillPaint() {
		return this.fillColour == null ? null :
				Utils.parseHexColor(this.fillColour, this.fillOpacity);
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node graphicFillNode = (Node)xPath.evaluate("GraphicFill/Graphic", xmlNode, XPathConstants.NODE);
		if (graphicFillNode != null) {
			this.graphicFill = new Graphic(this.getStyleSheet());
			this.graphicFill.parse(graphicFillNode, xPath);
		}

		Map<String, String> cssParameters = this.parseCssParameter(xmlNode, xPath);
		this.fillColour = cssParameters.get("fill");

		String fillOpacityStr = cssParameters.get("fill-opacity");
		if (fillOpacityStr != null && !fillOpacityStr.isEmpty()) {
			try {
				this.fillOpacity = Double.parseDouble(fillOpacityStr);
			} catch(Exception ex) {
				LOGGER.log(Level.WARNING, "Invalid fill-opacity '" + fillOpacityStr + "'.");
			}
		}
	}
}
