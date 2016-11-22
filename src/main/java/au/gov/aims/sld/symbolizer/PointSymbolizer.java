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

import au.gov.aims.sld.StyleSheet;
import au.gov.aims.sld.geom.GeoShape;
import au.gov.aims.sld.symbolizer.attributes.Geometry;
import au.gov.aims.sld.symbolizer.attributes.Graphic;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A "PointSymbolizer" specifies the rendering of a "graphic symbol" at a point.
 */
public class PointSymbolizer extends Symbolizer {
	private static final Logger LOGGER = Logger.getLogger(PointSymbolizer.class.getSimpleName());

	// The <Geometry> element is optional. If present, it specifies the featuretype property from which to obtain
	//   the geometry to style using a <PropertyName> element.
	//   See also Geometry transformations in SLD for GeoServer extensions for specifying geometry.
	// Any kind of geometry may be styled with a <PointSymbolizer>.
	//   For non-point geometries, a representative point is used (such as the centroid of a line or polygon).
	private Geometry geometry;

	// Symbology is specified using a <Graphic> element. The point symbol is specified by either
	//   an <ExternalGraphic> or a <Mark> element.
	// External Graphics are image files (in formats such as PNG or SVG) that contain the shape and color
	//   information defining how to render a symbol.
	// Marks are vector shapes whose stroke and fill are defined explicitly in the symbolizer.
	// There are five possible sub-elements of the <Graphic> element.
	//   One of <ExternalGraphic> or <Mark> must be specified; the others are optional.
	private Graphic graphic;

	public PointSymbolizer(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Geometry getGeometry() {
		return this.geometry;
	}

	public Graphic getGraphic() {
		return this.graphic;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node geometryNode = (Node)xPath.evaluate("Geometry", xmlNode, XPathConstants.NODE);
		if (geometryNode != null) {
			this.geometry = new Geometry(this.getStyleSheet());
			this.geometry.parse(geometryNode, xPath);
		}

		Node graphicNode = (Node)xPath.evaluate("Graphic", xmlNode, XPathConstants.NODE);
		if (graphicNode != null) {
			this.graphic = new Graphic(this.getStyleSheet());
			this.graphic.parse(graphicNode, xPath);
		}
	}

	@Override
	public List<GeoShape> applyStyle(GeoShape geoShape) {
		List<GeoShape> stylesGeoShapes = new ArrayList<GeoShape>();

		if (this.graphic != null) {
			List<GeoShape> styledGeoShapes = this.graphic.applyStyle(geoShape);
			if (styledGeoShapes != null && !styledGeoShapes.isEmpty()) {
				stylesGeoShapes.addAll(styledGeoShapes);
			}
		}

		return stylesGeoShapes;
	}
}
