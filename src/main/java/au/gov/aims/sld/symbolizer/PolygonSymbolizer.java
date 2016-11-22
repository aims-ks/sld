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
import au.gov.aims.sld.symbolizer.attributes.Fill;
import au.gov.aims.sld.symbolizer.attributes.Geometry;
import au.gov.aims.sld.symbolizer.attributes.Stroke;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

/**
 * A "PolygonSymbolizer" specifies the rendering of a polygon or area geometry,
 * including its interior fill and border stroke.
 */
public class PolygonSymbolizer extends Symbolizer {
	private Geometry geometry;
	private Fill fill;
	private Stroke stroke;

	public PolygonSymbolizer(StyleSheet styleSheet) {
		super(styleSheet);
	}

	public Geometry getGeometry() {
		return this.geometry;
	}

	public Fill getFill() {
		return this.fill;
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

	@Override
	public String toString() {
		return "PolygonSymbolizer{" +
				"geometry=" + geometry +
				", fill=" + fill +
				", stroke=" + stroke +
				'}';
	}

	@Override
	public List<GeoShape> applyStyle(GeoShape geoShape) {
		List<GeoShape> styledGeoShapes = new ArrayList<GeoShape>();

		if (geoShape != null) {
			GeoShape styledGeoShape = new GeoShape(geoShape);

			if (this.fill != null) {
				Paint fillPaint = this.fill.getFillPaint();
				if (fillPaint != null) {
					styledGeoShape.setFillPaint(fillPaint);
				}
			}

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
