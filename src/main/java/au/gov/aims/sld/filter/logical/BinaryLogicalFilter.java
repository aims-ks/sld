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
package au.gov.aims.sld.filter.logical;

import au.gov.aims.sld.SldUtils;
import au.gov.aims.sld.filter.Filter;
import au.gov.aims.sld.filter.FilterFactory;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// http://schemas.opengis.net/filter/1.1.0/filter.xsd
public abstract class BinaryLogicalFilter extends LogicalFilter {
	private static final Logger LOGGER = Logger.getLogger(BinaryLogicalFilter.class.getSimpleName());

	private Filter leftFilter;
	private List<Filter> rightFilters;

	public Filter getLeftFilter() {
		return this.leftFilter;
	}

	public List<Filter> getRightFilters() {
		return this.rightFilters;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		List<Node> elements = SldUtils.getChildNodes(xmlNode, Node.ELEMENT_NODE);
		if (elements == null || elements.isEmpty()) {
			LOGGER.log(Level.WARNING, "Binary filter contains no elements.");
			return;
		}

		if (elements.size() < 2) {
			LOGGER.log(Level.WARNING, String.format("Binary filter contains %d elements. Expected minimum 2.", elements.size()));
			return;
		}

		Filter leftFilter = FilterFactory.fromXML(elements.get(0), xPath);
		if (leftFilter == null) {
			LOGGER.log(Level.WARNING, "Binary filter contains invalid left element.");
			return;
		}

		List<Filter> rightFilters = new ArrayList<Filter>();
		for (int i=1; i<elements.size(); i++) {
			Filter rightFilter = FilterFactory.fromXML(elements.get(i), xPath);
			if (rightFilter == null) {
				LOGGER.log(Level.WARNING, String.format("Binary filter contains invalid right element, index %d.", i));
				return;
			}
			rightFilters.add(rightFilter);
		}

		this.leftFilter = leftFilter;
		this.rightFilters = rightFilters;
	}

	@Override
	public String toString() {
		return "BinaryLogicalFilter{" +
				"leftFilter=" + this.leftFilter +
				", rightFilters=" + this.rightFilters +
				'}';
	}
}
