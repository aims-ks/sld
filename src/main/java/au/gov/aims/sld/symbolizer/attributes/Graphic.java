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
import au.gov.aims.sld.geom.GeoShape;
import au.gov.aims.sld.geom.WellKnownMarkFactory;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "Graphic" specifies or refers to a "graphic symbol" with inherent shape, size, and coloring.
 */
public class Graphic extends Attribute {
	private static final Logger LOGGER = Logger.getLogger(Graphic.class.getSimpleName());

	// Specifies an image file to use as the symbol.
	private ExternalGraphic externalGraphic;

	// Specifies a common shape to use as the symbol.
	private Mark mark;

	private Double opacity; // ParameterValueType

	// Determines the size of the symbol, in pixels. When used with an image file, this specifies the height of the image,
	// with the width being scaled accordingly.
	// Value may contain expressions.
	// Required when there is no "ExternalGraphic".
	private Double size; // ParameterValueType

	// Determines the rotation of the symbol, in degrees. The rotation increases in the clockwise direction.
	// Negative values indicate counter-clockwise rotation. Value may contain expressions.
	// Default is 0.
	private Double rotation; // ParameterValueType

	public Graphic(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public ExternalGraphic getExternalGraphic() {
		return this.externalGraphic;
	}

	public Mark getMark() {
		return this.mark;
	}

	public Double getOpacity() {
		return this.opacity;
	}

	public Double getSize() {
		return this.size;
	}

	public Double getRotation() {
		return this.rotation;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node externalGraphicNode = (Node)xPath.evaluate("ExternalGraphic", xmlNode, XPathConstants.NODE);
		if (externalGraphicNode != null) {
			this.externalGraphic = new ExternalGraphic(this.getStyleSheet());
			this.externalGraphic.parse(externalGraphicNode, xPath);
		}

		Node markNode = (Node)xPath.evaluate("Mark", xmlNode, XPathConstants.NODE);
		if (markNode != null) {
			this.mark = new Mark(this.getStyleSheet());
			this.mark.parse(markNode, xPath);
		}

		Node opacityNode = (Node)xPath.evaluate("Opacity", xmlNode, XPathConstants.NODE);
		if (opacityNode != null) {
			String opacityStr = opacityNode.getTextContent();
			if (opacityStr != null && !opacityStr.isEmpty()) {
				try {
					this.opacity = Double.parseDouble(opacityStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid Opacity '" + opacityStr + "'.", ex);
				}
			}
		}

		Node sizeNode = (Node)xPath.evaluate("Size", xmlNode, XPathConstants.NODE);
		if (sizeNode != null) {
			String sizeStr = sizeNode.getTextContent();
			if (sizeStr != null && !sizeStr.isEmpty()) {
				try {
					this.size = Double.parseDouble(sizeStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid Size '" + sizeStr + "'.", ex);
				}
			}
		}

		Node rotationNode = (Node)xPath.evaluate("Rotation", xmlNode, XPathConstants.NODE);
		if (rotationNode != null) {
			String rotationStr = rotationNode.getTextContent();
			if (rotationStr != null && !rotationStr.isEmpty()) {
				try {
					this.rotation = Double.parseDouble(rotationStr);
				} catch(Exception ex) {
					LOGGER.log(Level.WARNING, "Invalid Rotation '" + rotationStr + "'.");
				}
			}
		}
	}

	public List<GeoShape> applyStyle(GeoShape geoShape) {
		List<GeoShape> styledGeoShapes = new ArrayList<GeoShape>();

		if (this.externalGraphic != null) {
			LOGGER.log(Level.WARNING, "ExternalGraphic not supported.");
		}

		if (this.mark != null) {
			if (this.size != null && this.size > 0) {
				Shape shape = WellKnownMarkFactory.getShape(this.mark.getWellKnownName());

				Point2D center = geoShape.getCentroid();
				AffineTransform at = new AffineTransform();
				at.translate(center.getX(), center.getY());

				float ratio = this.getStyleSheet().getStrokeWidthRatio();
				at.scale(this.size * ratio, -this.size * ratio);

				shape = at.createTransformedShape(shape);

				if (shape != null) {
					GeoShape markShape = new GeoShape(shape, geoShape.getProperties());

					Fill fill = this.mark.getFill();
					if (fill != null) {
						markShape.setFillPaint(fill.getFillPaint());
					}

					Stroke stroke = this.mark.getStroke();
					if (stroke != null) {
						markShape.setStrokePaint(stroke.getStrokePaint());
						markShape.setStroke(stroke.getStroke());
					}

					styledGeoShapes.add(markShape);
				}
			}
		}

		return styledGeoShapes;
	}

	/**
	 * An "ExternalGraphic" gives a reference to an external raster or vector graphical object.
	 */
	public static class ExternalGraphic extends Attribute {
		private String onlineResource;
		private String format;

		public ExternalGraphic(StyleSheet styleSheet) {
			super(styleSheet);
		}

		public String getOnlineResource() {
			return this.onlineResource;
		}

		public String getFormat() {
			return this.format;
		}

		@Override
		public void parse(Node xmlNode, XPath xPath) throws Exception {
			Node onlineResourceNode = (Node)xPath.evaluate("OnlineResource", xmlNode, XPathConstants.NODE);
			if (onlineResourceNode != null) {
				this.onlineResource = onlineResourceNode.getTextContent();
			}

			Node formatNode = (Node)xPath.evaluate("Format", xmlNode, XPathConstants.NODE);
			if (formatNode != null) {
				this.format = formatNode.getTextContent();
			}
		}
	}

	/**
	 * A "Mark" specifies a geometric shape and applies coloring to it.
	 *
	 * Marks are predefined vector shapes identified by a well-known name. Their fill and stroke
	 * can be defined explicitly in the SLD. For GeoServer extensions for specifying mark symbols,
	 * see Point symbology in GeoServer.
	 */
	public static class Mark extends Attribute {
		// The name of the common shape. Options are
		//   - circle,
		//   - square,
		//   - triangle,
		//   - star,
		//   - cross, or
		//   - x.
		// Default is square.
		private String wellKnownName;
		private Fill fill;
		private Stroke stroke;

		public Mark(StyleSheet styleSheet) {
			super(styleSheet);
		}

		public String getWellKnownName() {
			return this.wellKnownName;
		}

		public Fill getFill() {
			return this.fill;
		}

		public Stroke getStroke() {
			return this.stroke;
		}

		@Override
		public void parse(Node xmlNode, XPath xPath) throws Exception {
			Node wellKnownNameNode = (Node)xPath.evaluate("WellKnownName", xmlNode, XPathConstants.NODE);
			if (wellKnownNameNode != null) {
				this.wellKnownName = wellKnownNameNode.getTextContent();
			}

			Node fillNode = (Node)xPath.evaluate("Fill", xmlNode, XPathConstants.NODE);
			if (fillNode != null) {
				this.fill = new Fill(this.getStyleSheet());
				this.fill.parse(fillNode, xPath);
			}

			Node strokeNode = (Node)xPath.evaluate("Stroke", xmlNode, XPathConstants.NODE);
			if (strokeNode != null) {
				this.stroke = new Stroke(this.getStyleSheet());
				this.stroke.parse(strokeNode, xPath);
			}
		}
	}
}
