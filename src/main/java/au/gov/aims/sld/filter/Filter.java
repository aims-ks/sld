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
package au.gov.aims.sld.filter;

import au.gov.aims.sld.geom.GeoShape;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;

/**
 * http://docs.geoserver.org/2.8.x/en/user/filter/function_reference.html
 */
public abstract class Filter {
	public abstract void parse(Node xmlNode, XPath xPath) throws Exception;
	public abstract boolean filter(GeoShape stylable);
}
