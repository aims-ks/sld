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
import au.gov.aims.sld.symbolizer.attributes.placement.LinePlacement;
import au.gov.aims.sld.symbolizer.attributes.placement.Placement;
import au.gov.aims.sld.symbolizer.attributes.placement.PointPlacement;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

/**
 * The "LabelPlacement" specifies where and how a text label should be rendered relative to a geometry.
 * The present mechanism is poorly aligned with CSS/SVG.
 */
public class LabelPlacement extends Attribute {
	private Placement placement;

	public LabelPlacement(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Placement getPlacement() {
		return this.placement;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		// The placement is either a PointPlacement or a LinePlacement.
		Node pointPlacementNode = (Node)xPath.evaluate("PointPlacement", xmlNode, XPathConstants.NODE);
		if (pointPlacementNode != null) {
			this.placement = new PointPlacement(this.getStyleSheet());
			this.placement.parse(pointPlacementNode, xPath);
		} else {
			Node linePlacementNode = (Node)xPath.evaluate("LinePlacement", xmlNode, XPathConstants.NODE);
			if (linePlacementNode != null) {
				this.placement = new LinePlacement(this.getStyleSheet());
				this.placement.parse(linePlacementNode, xPath);
			}
		}
	}
}
