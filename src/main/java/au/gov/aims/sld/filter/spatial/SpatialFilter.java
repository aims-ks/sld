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
package au.gov.aims.sld.filter.spatial;

import au.gov.aims.sld.filter.Filter;
import au.gov.aims.sld.geom.GeoShape;
import org.w3c.dom.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.xpath.XPath;

/**
 * DTD: http://schemas.opengis.net/filter/1.1.0/filter.xsd
 */
public abstract class SpatialFilter extends Filter {
	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		throw new NotImplementedException();
	}

	@Override
	public boolean filter(GeoShape stylable) {
		throw new NotImplementedException();
	}
}
