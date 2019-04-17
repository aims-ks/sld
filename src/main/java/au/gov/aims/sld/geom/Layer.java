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

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Layer {
	private String name;
	private List<GeoShapeGroup> groups;

	public Layer(String name) {
		this.name = name;
		this.groups = new ArrayList<GeoShapeGroup>();
	}

	public boolean isEmpty() {
		return this.groups.isEmpty();
	}

	public void add(GeoShapeGroup group) {
		this.groups.add(group);
	}

	public List<GeoShapeGroup> getShapeGroups() {
		return this.groups;
	}

	public Layer createTransformedLayer(AffineTransform transform) {
		Layer layer = new Layer(this.getName());

		for (GeoShapeGroup group : this.groups) {
			layer.add(group.createTransformedGeoShapeGroup(transform));
		}

		return layer;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	// Used for debugging
	@Override
	public String toString() {
		return "Layer{" +
				"name='" + name + '\'' +
				", groups=" + groups +
				'}';
	}
}
