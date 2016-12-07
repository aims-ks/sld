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

import au.gov.aims.sld.filter.spatial.BBOX;
import au.gov.aims.sld.filter.comparison.PropertyIsBetween;
import au.gov.aims.sld.filter.comparison.PropertyIsEqualTo;
import au.gov.aims.sld.filter.comparison.PropertyIsGreaterThan;
import au.gov.aims.sld.filter.comparison.PropertyIsGreaterThanOrEqualTo;
import au.gov.aims.sld.filter.comparison.PropertyIsLessThan;
import au.gov.aims.sld.filter.comparison.PropertyIsLessThanOrEqualTo;
import au.gov.aims.sld.filter.comparison.PropertyIsLike;
import au.gov.aims.sld.filter.comparison.PropertyIsNotEqualTo;
import au.gov.aims.sld.filter.comparison.PropertyIsNull;
import au.gov.aims.sld.filter.spatial.Beyond;
import au.gov.aims.sld.filter.spatial.DWithin;
import au.gov.aims.sld.filter.logical.And;
import au.gov.aims.sld.filter.logical.Not;
import au.gov.aims.sld.filter.logical.Or;
import au.gov.aims.sld.filter.spatial.Contains;
import au.gov.aims.sld.filter.spatial.Crosses;
import au.gov.aims.sld.filter.spatial.Disjoint;
import au.gov.aims.sld.filter.spatial.Equals;
import au.gov.aims.sld.filter.spatial.Intersects;
import au.gov.aims.sld.filter.spatial.Overlaps;
import au.gov.aims.sld.filter.spatial.Touches;
import au.gov.aims.sld.filter.spatial.Within;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;

/**
 * Specification: http://docs.geoserver.org/2.7.0/user/styling/sld-reference/filters.html
 * DTD: http://www.opengis.net/ogc
 * Standard doc: http://www.opengeospatial.org/standards/sld
 *
 * Comparison operators (ogc:ComparisonOpsType)
 *   <PropertyIsEqualTo> (ogc:BinaryComparisonOpType)
 *   <PropertyIsNotEqualTo> (ogc:BinaryComparisonOpType)
 *   <PropertyIsLessThan> (ogc:BinaryComparisonOpType)
 *   <PropertyIsLessThanOrEqualTo> (ogc:BinaryComparisonOpType)
 *   <PropertyIsGreaterThan> (ogc:BinaryComparisonOpType)
 *   <PropertyIsGreaterThanOrEqualTo> (ogc:BinaryComparisonOpType)
 *   <PropertyIsLike> (ogc:PropertyIsLikeType)
 *   <PropertyIsNull> (ogc:PropertyIsNullType)
 *   <PropertyIsBetween> (ogc:PropertyIsBetweenType)
 *
 * Spatial operators (ogc:SpatialOpsType)
 *   <Equals> (ogc:BinarySpatialOpType)
 *   <Disjoint> (ogc:BinarySpatialOpType)
 *   <Touches> (ogc:BinarySpatialOpType)
 *   <Within> (ogc:BinarySpatialOpType)
 *   <Overlaps> (ogc:BinarySpatialOpType)
 *   <Crosses> (ogc:BinarySpatialOpType)
 *   <Intersects> (ogc:BinarySpatialOpType)
 *   <Contains> (ogc:BinarySpatialOpType)
 *
 * Distance Operators (ogc:SpatialOpsType)
 *   <DWithin> (ogc:DistanceBufferType)
 *   <Beyond> (ogc:DistanceBufferType)
 *
 * Bounding Box Operator (ogc:SpatialOpsType)
 *   <BBOX> (ogc:BBOXType)
 *
 * Logical operators (ogc:LogicOpsType)
 *   <And> (ogc:BinaryLogicOpType)
 *   <Or> (ogc:BinaryLogicOpType)
 *   <Not> (ogc:UnaryLogicOpType)
 */
public class FilterFactory {
	public static Filter fromXML(Node xmlNode, XPath xPath) throws Exception {
		Filter filter = null;
		String filterName = xmlNode.getNodeName();

		// Comparison operators
		if("ogc:PropertyIsEqualTo".equals(filterName)) {
			filter = new PropertyIsEqualTo();
		} else if("ogc:PropertyIsNotEqualTo".equals(filterName)) {
			filter = new PropertyIsNotEqualTo();
		} else if("ogc:PropertyIsLessThan".equals(filterName)) {
			filter = new PropertyIsLessThan();
		} else if("ogc:PropertyIsLessThanOrEqualTo".equals(filterName)) {
			filter = new PropertyIsLessThanOrEqualTo();
		} else if("ogc:PropertyIsGreaterThan".equals(filterName)) {
			filter = new PropertyIsGreaterThan();
		} else if("ogc:PropertyIsGreaterThanOrEqualTo".equals(filterName)) {
			filter = new PropertyIsGreaterThanOrEqualTo();
		} else if("ogc:PropertyIsLike".equals(filterName)) {
			filter = new PropertyIsLike();
		} else if("ogc:PropertyIsNull".equals(filterName)) {
			filter = new PropertyIsNull();
		} else if("ogc:PropertyIsBetween".equals(filterName)) {
			filter = new PropertyIsBetween();

		// Spatial operators
		} else if("ogc:Intersects".equals(filterName)) {
			filter = new Intersects();
		} else if("ogc:Equals".equals(filterName)) {
			filter = new Equals();
		} else if("ogc:Disjoint".equals(filterName)) {
			filter = new Disjoint();
		} else if("ogc:Touches".equals(filterName)) {
			filter = new Touches();
		} else if("ogc:Within".equals(filterName)) {
			filter = new Within();
		} else if("ogc:Overlaps".equals(filterName)) {
			filter = new Overlaps();
		} else if("ogc:Crosses".equals(filterName)) {
			filter = new Crosses();
		} else if("ogc:Contains".equals(filterName)) {
			filter = new Contains();

		// Distance Operators
		} else if("ogc:DWithin".equals(filterName)) {
			filter = new DWithin();
		} else if("ogc:Beyond".equals(filterName)) {
			filter = new Beyond();

		// Bounding Box Operator
		} else if("ogc:BBOX".equals(filterName)) {
			filter = new BBOX();

		// Logical operators
		} else if("ogc:And".equals(filterName)) {
			filter = new And();
		} else if("ogc:Or".equals(filterName)) {
			filter = new Or();
		} else if("ogc:Not".equals(filterName)) {
			filter = new Not();

		} else {
			throw new IllegalArgumentException("Invalid SLD filter: " + filterName);
		}

		if (filter != null) {
			filter.parse(xmlNode, xPath);
		}

		return filter;
	}
}
