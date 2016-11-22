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
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A "PointPlacement" specifies how a text label should be rendered relative to a geometric point.
 */
public class PointPlacement extends Placement {
	private static final Logger LOGGER = Logger.getLogger(PointPlacement.class.getSimpleName());

	// An "AnchorPoint" identifies the location inside of a text label to use an an 'anchor' for positioning it relative to a point geometry.
	private Point2D anchorPoint;

	// A "Displacement" gives X and Y offset displacements to use for rendering a text label near a point.
	private Point2D displacement;

	private Double rotation; // ParameterValueType

	public PointPlacement(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Point2D getAnchorPoint() {
		return this.anchorPoint;
	}

	public Point2D getDisplacement() {
		return this.displacement;
	}

	public Double getRotation() {
		return this.rotation;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node anchorPointNode = (Node)xPath.evaluate("AnchorPoint", xmlNode, XPathConstants.NODE);
		if (anchorPointNode != null) {
			Node anchorPointXNode = (Node)xPath.evaluate("AnchorPointX", anchorPointNode, XPathConstants.NODE);
			Node anchorPointYNode = (Node)xPath.evaluate("AnchorPointY", anchorPointNode, XPathConstants.NODE);
			if (anchorPointXNode != null && anchorPointYNode != null) {
				String anchorPointXStr = anchorPointXNode.getTextContent();
				String anchorPointYStr = anchorPointYNode.getTextContent();
				if (anchorPointXStr != null && !anchorPointXStr.isEmpty() && anchorPointYStr != null && !anchorPointYStr.isEmpty()) {
					try {
						this.anchorPoint = new Point2D.Double(
							Double.parseDouble(anchorPointXStr),
							Double.parseDouble(anchorPointYStr)
						);
					} catch(Exception ex) {
						LOGGER.log(Level.WARNING, "Invalid AnchorPoint (" + anchorPointXStr + ", " + anchorPointYStr + ").");
					}
				}
			}
		}

		Node displacementNode = (Node)xPath.evaluate("Displacement", xmlNode, XPathConstants.NODE);
		if (displacementNode != null) {
			Node displacementXNode = (Node)xPath.evaluate("DisplacementX", displacementNode, XPathConstants.NODE);
			Node displacementYNode = (Node)xPath.evaluate("DisplacementY", displacementNode, XPathConstants.NODE);
			if (displacementXNode != null && displacementYNode != null) {
				String displacementXStr = displacementXNode.getTextContent();
				String displacementYStr = displacementYNode.getTextContent();
				if (displacementXStr != null && !displacementXStr.isEmpty() && displacementYStr != null && !displacementYStr.isEmpty()) {
					try {
						this.displacement = new Point2D.Double(
							Double.parseDouble(displacementXStr),
							Double.parseDouble(displacementYStr)
						);
					} catch(Exception ex) {
						LOGGER.log(Level.WARNING, "Invalid Displacement (" + displacementXStr + ", " + displacementYStr + ").");
					}
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
}
