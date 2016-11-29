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
import au.gov.aims.sld.symbolizer.attributes.Stroke;
import org.w3c.dom.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

/**
 * A LineSymbolizer is used to render a "stroke" along a linear geometry.
 */
public class LineSymbolizer extends Symbolizer {
	private Geometry geometry;
	private Stroke stroke;

	public LineSymbolizer(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Geometry getGeometry() {
		return this.geometry;
	}

	public Stroke getStroke() {
		return this.stroke;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		Node geometryNode = (Node)xPath.evaluate("Geometry", xmlNode, XPathConstants.NODE);
		if (geometryNode != null) {
			this.geometry = new Geometry(this.getStyleSheet());
			this.geometry.parse(geometryNode, xPath);
		}

		Node strokeNode = (Node)xPath.evaluate("Stroke", xmlNode, XPathConstants.NODE);
		if (strokeNode != null) {
			this.stroke = new Stroke(this.getStyleSheet());
			this.stroke.parse(strokeNode, xPath);
		}
	}

	@Override
	public List<GeoShape> applyStyle(GeoShape geoShape) {
		List<GeoShape> styledGeoShapes = new ArrayList<GeoShape>();

		if (geoShape != null) {
			GeoShape styledGeoShape = new GeoShape(geoShape);

			styledGeoShape.setFillPaint(null);

			if (this.stroke != null) {
				Paint strokePaint = this.stroke.getStrokePaint();
				if (strokePaint != null) {
					styledGeoShape.setStrokePaint(strokePaint);
				}

				java.awt.Stroke stroke = this.stroke.getStroke();
				if (stroke != null) {
					styledGeoShape.setStroke(stroke);
				}
			}

			styledGeoShapes.add(styledGeoShape);
		}

		return styledGeoShapes;
	}
}
