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
import java.awt.BasicStroke;
import java.awt.Paint;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "Stroke" specifies the appearance of a linear geometry. It is defined in parallel with SVG strokes.
 * The following CssParameters may be used:
 *   - "stroke" (color),
 *   - "stroke-opacity",
 *   - "stroke-width",
 *   - "stroke-linejoin",
 *   - "stroke-linecap",
 *   - "stroke-dasharray", and
 *   - "stroke-dashoffset".
 *
 */
public class Stroke extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(Stroke.class.getName());

	private Graphic graphicFill;
	private Graphic graphicStroke;

	// CssParameters
	private String strokeColour; // Hexadecimal colour (#FFFFFF)
	private Double strokeOpacity;
	private Float strokeWidth;
	private String strokeLineJoin;
	private String strokeLineCap;
	private String strokeDashArray;
	private String strokeDashOffset;

	public Stroke(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Graphic getGraphicFill() {
		return this.graphicFill;
	}

	public Graphic getGraphicStroke() {
		return this.graphicStroke;
	}

	public String getStrokeColour() {
		return this.strokeColour;
	}

	public Double getStrokeOpacity() {
		return this.strokeOpacity;
	}

	public Float getStrokeWidth() {
		return this.strokeWidth;
	}

	public String getStrokeLineJoin() {
		return this.strokeLineJoin;
	}

	public String getStrokeLineCap() {
		return this.strokeLineCap;
	}

	public String getStrokeDashArray() {
		return this.strokeDashArray;
	}

	public String getStrokeDashOffset() {
		return this.strokeDashOffset;
	}

	public Paint getStrokePaint() {
		return this.strokeColour == null ? null :
				Utils.parseHexColor(this.strokeColour, this.strokeOpacity);
	}

	public java.awt.Stroke getStroke() {
		StyleSheet styleSheet = this.getStyleSheet();
		double strokeWidthRatio = styleSheet == null ? 1.0 : styleSheet.getStrokeWidthRatio();

		return this.strokeWidth == null ? null :
				new BasicStroke(
						Math.round(this.strokeWidth * strokeWidthRatio),
						BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND);
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node graphicFillNode = (Node)xPath.evaluate("GraphicFill/Graphic", xmlNode, XPathConstants.NODE);
		if (graphicFillNode != null) {
			this.graphicFill = new Graphic(this.getStyleSheet());
			this.graphicFill.parse(graphicFillNode, xPath);
		}

		Node graphicStrokeNode = (Node)xPath.evaluate("GraphicFill/GraphicStroke", xmlNode, XPathConstants.NODE);
		if (graphicStrokeNode != null) {
			this.graphicStroke = new Graphic(this.getStyleSheet());
			this.graphicStroke.parse(graphicStrokeNode, xPath);
		}

		Map<String, String> cssParameters = this.parseCssParameter(xmlNode, xPath);
		this.strokeColour = cssParameters.get("stroke");
		this.strokeLineJoin = cssParameters.get("stroke-linejoin");
		this.strokeLineCap = cssParameters.get("stroke-linecap");
		this.strokeDashArray = cssParameters.get("stroke-dasharray");
		this.strokeDashOffset = cssParameters.get("stroke-dashoffset");

		String strokeOpacityStr = cssParameters.get("stroke-opacity");
		if (strokeOpacityStr != null && !strokeOpacityStr.isEmpty()) {
			try {
				this.strokeOpacity = Double.parseDouble(strokeOpacityStr);
			} catch(Exception ex) {
				LOGGER.log(Level.WARNING, "Invalid stroke-opacity '" + strokeOpacityStr + "'.");
			}
		}

		String strokeWidthStr = cssParameters.get("stroke-width");
		if (strokeWidthStr != null && !strokeWidthStr.isEmpty()) {
			try {
				this.strokeWidth = Float.parseFloat(strokeWidthStr);
			} catch(Exception ex) {
				LOGGER.log(Level.WARNING, "Invalid stroke-width '" + strokeWidthStr + "'.");
			}
		}
	}

	@Override
	public String toString() {
		return "Stroke{" +
				"graphicFill=" + graphicFill +
				", graphicStroke=" + graphicStroke +
				", strokeColour='" + strokeColour + '\'' +
				", strokeOpacity='" + strokeOpacity + '\'' +
				", strokeWidth='" + strokeWidth + '\'' +
				", strokeLineJoin='" + strokeLineJoin + '\'' +
				", strokeLineCap='" + strokeLineCap + '\'' +
				", strokeDashArray='" + strokeDashArray + '\'' +
				", strokeDashOffset='" + strokeDashOffset + '\'' +
				'}';
	}
}
