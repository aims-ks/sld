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

import au.gov.aims.sld.Utils;
import au.gov.aims.sld.filter.Filter;
import au.gov.aims.sld.filter.FilterFactory;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class UnaryLogicalFilter extends LogicalFilter {
	private static final Logger LOGGER = Logger.getLogger(BinaryLogicalFilter.class.getSimpleName());

	private Filter rightFilter;

	public Filter getRightFilter() {
		return this.rightFilter;
	}

	@Override
	public void parse(Node xmlNode, XPath xPath) throws Exception {
		List<Node> elements = Utils.getChildNodes(xmlNode, Node.ELEMENT_NODE);
		if (elements == null || elements.isEmpty()) {
			LOGGER.log(Level.WARNING, "Unary filter contains no elements.");
			return;
		}

		if (elements.size() != 1) {
			LOGGER.log(Level.WARNING, "Unary filter contains " + elements.size() + " elements. Expected 1.");
			return;
		}

		Filter rightFilter = FilterFactory.fromXML(elements.get(0), xPath);
		if (rightFilter == null) {
			LOGGER.log(Level.WARNING, "Unary filter contains invalid right element.");
			return;
		}

		this.rightFilter = rightFilter;
	}

	@Override
	public String toString() {
		return "UnaryLogicalFilter{" +
				"rightFilter=" + rightFilter +
				'}';
	}
}
