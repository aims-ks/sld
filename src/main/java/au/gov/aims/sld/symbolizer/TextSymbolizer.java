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
package au.gov.aims.sld.symbolizer;

import au.gov.aims.sld.PropertyValue;
import au.gov.aims.sld.StyleSheet;
import au.gov.aims.sld.TextAlignment;
import au.gov.aims.sld.geom.GeoShape;
import au.gov.aims.sld.symbolizer.attributes.Fill;
import au.gov.aims.sld.symbolizer.attributes.Font;
import au.gov.aims.sld.symbolizer.attributes.Geometry;
import au.gov.aims.sld.symbolizer.attributes.Halo;
import au.gov.aims.sld.symbolizer.attributes.LabelPlacement;
import au.gov.aims.sld.symbolizer.attributes.placement.Placement;
import au.gov.aims.sld.symbolizer.attributes.placement.PointPlacement;
import org.w3c.dom.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "TextSymbolizer" is used to render text labels according to various graphical parameters.
 */
public class TextSymbolizer extends Symbolizer {
	private static final Logger LOGGER = Logger.getLogger(TextSymbolizer.class.getSimpleName());

	private Geometry geometry;

	// A "Label" specifies the textual content to be rendered.
	private String label; // ParameterValueType

	private Font font;

	// The <LabelPlacement> element specifies the placement of the label relative to the geometry being labelled.
	// There are two possible sub-elements: <PointPlacement> or <LinePlacement>. Exactly one of these must be specified.
	// The <PointPlacement> element indicates the label is placed at a labelling point derived from the geometry being labelled.
	// The <LinePlacement> element indicates the label is placed along a linear path derived from the geometry being labelled.
	private LabelPlacement labelPlacement;
	private Halo halo;
	private Fill fill;

	// Attributes not described in the DTD
	//   http://docs.geoserver.org/stable/en/user/styling/sld/reference/textsymbolizer.html
	private String priority;
	private Map<String, String> vendorOptions;

	public TextSymbolizer(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Geometry getGeometry() {
		return this.geometry;
	}

	public String getLabel() {
		return this.label;
	}

	public Font getFont() {
		return this.font;
	}

	public LabelPlacement getLabelPlacement() {
		return this.labelPlacement;
	}

	public Halo getHalo() {
		return this.halo;
	}

	public Fill getFill() {
		return this.fill;
	}

	// Attributes not described in the DTD
	public String getPriority() {
		return this.priority;
	}

	public Map<String, String> getVendorOptions() {
		return this.vendorOptions;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node geometryNode = (Node)xPath.evaluate("Geometry", xmlNode, XPathConstants.NODE);
		if (geometryNode != null) {
			this.geometry = new Geometry(this.getStyleSheet());
			this.geometry.parse(geometryNode, xPath);
		}

		Node labelNode = (Node)xPath.evaluate("Label/PropertyName", xmlNode, XPathConstants.NODE);
		if (labelNode != null) {
			this.label = labelNode.getTextContent();
		}

		Node fontNode = (Node)xPath.evaluate("Font", xmlNode, XPathConstants.NODE);
		if (fontNode != null) {
			this.font = new Font(this.getStyleSheet());
			this.font.parse(fontNode, xPath);
		}

		Node labelPlacementNode = (Node)xPath.evaluate("LabelPlacement", xmlNode, XPathConstants.NODE);
		if (labelPlacementNode != null) {
			this.labelPlacement = new LabelPlacement(this.getStyleSheet());
			this.labelPlacement.parse(labelPlacementNode, xPath);
		}

		Node haloNode = (Node)xPath.evaluate("Halo", xmlNode, XPathConstants.NODE);
		if (haloNode != null) {
			this.halo = new Halo(this.getStyleSheet());
			this.halo.parse(haloNode, xPath);
		}

		Node fillNode = (Node)xPath.evaluate("Fill", xmlNode, XPathConstants.NODE);
		if (fillNode != null) {
			this.fill = new Fill(this.getStyleSheet());
			this.fill.parse(fillNode, xPath);
		}

		Node priorityNode = (Node)xPath.evaluate("Priority/PropertyName", xmlNode, XPathConstants.NODE);
		if (priorityNode != null) {
			this.priority = priorityNode.getTextContent();
		}

		this.vendorOptions = this.parseVendorOptions(xmlNode, xPath);
	}

	@Override
	public List<GeoShape> applyStyle(GeoShape geoShape) {
		String labelPropertyName = this.label;
		List<GeoShape> styledGeoShapes = new ArrayList<GeoShape>();

		if (labelPropertyName != null && !labelPropertyName.isEmpty()) {
			Map<String, PropertyValue> properties = geoShape.getProperties();
			if (properties != null && !properties.isEmpty()) {
				PropertyValue rawLabelValue = properties.get(labelPropertyName);
				if (rawLabelValue != null) {
					String labelValue = rawLabelValue.getStringValue();
					if (labelValue != null && !labelValue.isEmpty()) {
						Placement placement = this.labelPlacement.getPlacement();
						if (placement instanceof PointPlacement) {
							PointPlacement pointPlacement = (PointPlacement)placement;
							Point2D ratio = pointPlacement.getAnchorPoint();
							if (ratio == null) {
								// (Default) Bottom left of the box is (0, 0)
								//   http://docs.geoserver.org/latest/en/user/styling/sld/reference/labeling.html
								ratio = new Point2D.Double(0, 0);
							}

							Point2D anchor = geoShape.getCentroid();
							TextAlignment textAlignment = null;

							java.awt.Font awtFont = null;
							if (this.font != null) {
								awtFont = this.font.getFont();
							}

							// Offset the anchor point (if needed)
							if (awtFont != null) {
								BufferedImage dummyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
								Graphics g = dummyImage.getGraphics();
								FontMetrics fm = g.getFontMetrics(awtFont);

								double offsetX, ratioX = ratio.getX();
								if (ratioX > 0.66) {
									textAlignment = TextAlignment.RIGHT;
									offsetX = fm.stringWidth(labelValue) * (ratioX - 1);
								} else if (ratioX > 0.34) {
									textAlignment = TextAlignment.CENTRE;
									offsetX = fm.stringWidth(labelValue) * (ratioX - 0.5);
								} else {
									textAlignment = TextAlignment.LEFT;
									offsetX = fm.stringWidth(labelValue) * ratioX;
								}

								// NOTE: Graphics2D place string on a point higher than it's bounding box. Some characters goes bellow that point, such as "p", "g", etc.
								//   The anchor point needs to be corrected by the maximum height of the bits that goes bellow the "font line". Those bits are called "descent".
								anchor = new Point2D.Double(
									anchor.getX() - offsetX,
									anchor.getY() + (fm.getHeight() * ratio.getY()) - fm.getMaxDescent()
								);
							} else {
								LOGGER.log(Level.WARNING, "Label placement can't be determined when 'Font' is not defined.");
							}

							GeoShape labelShape = new GeoShape(anchor, properties);

							labelShape.setLabel(labelValue);

							if (textAlignment != null) {
								labelShape.setTextAlignment(textAlignment);
							}

							if (this.fill != null) {
								labelShape.setFillPaint(this.fill.getFillPaint());
							}

							if (awtFont != null) {
								labelShape.setFont(awtFont);
							}

							styledGeoShapes.add(labelShape);
						} else {
							// TODO LinePlacement
							LOGGER.log(Level.SEVERE, "Label placement with '" + placement.getClass().getSimpleName() + "' not yet implemented.");
							throw new NotImplementedException();
						}
					}
				}
			}
		}

		return styledGeoShapes;
	}
}
