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

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GeoShapeGroup {
	private String name;
	private List<GeoShapeGroup> groups;
	private List<GeoShape> geoShapes;

	public GeoShapeGroup(String name) {
		this.name = name;
		this.groups = new ArrayList<GeoShapeGroup>();
		this.geoShapes = new ArrayList<GeoShape>();
	}

	public String getName() {
		return this.name;
	}

	public boolean isEmpty() {
		return this.groups.isEmpty() && this.geoShapes.isEmpty();
	}

	public void add(GeoShapeGroup group) {
		if (group != null && !group.isEmpty()) {
			this.groups.add(group);
		}
	}

	public void add(Shape shape, Map<String, PropertyValue> properties) {
		this.add(new GeoShape(shape, properties));
	}

	public void add(Point2D point, Map<String, PropertyValue> properties) {
		this.add(new GeoShape(point, properties));
	}

	public void addAll(Collection<GeoShape> geoShapes) {
		if (geoShapes != null && !geoShapes.isEmpty()) {
			this.geoShapes.addAll(geoShapes);
		}
	}

	public void add(GeoShape geoShape) {
		if (geoShape != null) {
			this.geoShapes.add(geoShape);
		}
	}

	public GeoShapeGroup createTransformedGeoShapeGroup(AffineTransform transform) {
		GeoShapeGroup transformedGroup = new GeoShapeGroup(this.name);
		for (GeoShapeGroup group : this.groups) {
			transformedGroup.add(group.createTransformedGeoShapeGroup(transform));
		}
		for (GeoShape geoShape : this.geoShapes) {
			transformedGroup.add(geoShape.createTransformedGeoShape(transform));
		}
		return transformedGroup;
	}

	public List<GeoShapeGroup> getGeoShapeGroups() {
		return this.groups;
	}

	public List<GeoShape> getGeoShapes() {
		return this.geoShapes;
	}

	@Override
	public String toString() {
		return "GeoShapeGroup{" +
				"groups=" + groups +
				", geoShapes=" + geoShapes +
				'}';
	}
}
