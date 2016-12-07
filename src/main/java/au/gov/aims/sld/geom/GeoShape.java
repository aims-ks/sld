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
package au.gov.aims.sld.geom;

import au.gov.aims.sld.PropertyValue;
import au.gov.aims.sld.TextAlignment;

import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

public class GeoShape {
	private Object shape; // Either a "Shape" or a "Point2D"
	private Map<String, PropertyValue> properties;

	// Fill
	private Paint fillPaint;

	// Stroke
	private Paint strokePaint;
	private Stroke stroke;

	// Text
	private TextAlignment textAlignment;
	private String label;
	private Font font;

	public GeoShape(Point2D point, Map<String, PropertyValue> properties) {
		this((Object)point, properties);
	}

	public GeoShape(Shape shape, Map<String, PropertyValue> properties) {
		this((Object)shape, properties);
	}

	public GeoShape(GeoShape original) {
		this(original.getShape(), original.getProperties());
		original.copyTo(this);
	}

	private GeoShape(Object shape, Map<String, PropertyValue> properties) {
		this.textAlignment = null;
		this.shape = shape;
		this.properties = properties;
	}

	public Map<String, PropertyValue> getProperties() {
		return this.properties;
	}

	public Object getShape() {
		return this.shape;
	}

	// Fill
	public Paint getFillPaint() {
		return this.fillPaint;
	}

	public void setFillPaint(Paint fillPaint) {
		this.fillPaint = fillPaint;
	}

	// Stroke
	public Paint getStrokePaint() {
		return this.strokePaint;
	}

	public void setStrokePaint(Paint strokePaint) {
		this.strokePaint = strokePaint;
	}

	public Stroke getStroke() {
		return this.stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	// Text
	public TextAlignment getTextAlignment() {
		return this.textAlignment == null ? TextAlignment.LEFT : this.textAlignment;
	}

	public void setTextAlignment(TextAlignment textAlignment) {
		this.textAlignment = textAlignment;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Font getFont() {
		return this.font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	// Tools

	public Point2D getCentroid() {
		if (this.shape instanceof Shape) {
			// Simply return the center of the bounding box.
			// Calculating the center of mass would be too hard.
			Rectangle2D bounds = ((Shape) this.shape).getBounds2D();
			return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());

		} else if (this.shape instanceof Point2D) {
			return (Point2D)this.shape;
		}

		return null;
	}

	/**
	 * Use to "clone" a GeoStylable. After creating a new empty object, call "original.copyTo(clone)"
	 * NOTE: It's more strait forward this way then implementing cloneable...
	 *   See: http://www.artima.com/intv/bloch13.html
	 */
	private void copyTo(GeoShape newObject) {
		// Fill
		newObject.fillPaint = this.fillPaint;

		// Stroke
		newObject.strokePaint = this.strokePaint;
		newObject.stroke = this.stroke;

		// Text
		newObject.label = this.label;
		newObject.font = this.font;
		newObject.textAlignment = this.textAlignment;
	}

	public GeoShape createTransformedGeoShape(AffineTransform transform) {
		Object transformedShape = null;
		if (this.shape instanceof Shape) {
			transformedShape = transform.createTransformedShape((Shape)this.shape);
		} else if (this.shape instanceof Point2D) {
			transformedShape = transform.transform((Point2D)this.shape, new Point2D.Double());
		}

		if (transformedShape != null) {
			GeoShape transformedGeoShape = new GeoShape(
				transformedShape,
				this.getProperties()
			);
			this.copyTo(transformedGeoShape);
			return transformedGeoShape;
		}

		return null;
	}

	@Override
	public String toString() {
		return "GeoShape{" +
				"shape=" + shape +
				", properties=" + properties +
				", fillPaint=" + fillPaint +
				", strokePaint=" + strokePaint +
				", stroke=" + stroke +
				", textAlignment=" + textAlignment +
				", label='" + label + '\'' +
				", font=" + font +
				'}';
	}
}
