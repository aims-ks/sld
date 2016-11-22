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
package au.gov.aims.sld;

import au.gov.aims.sld.geom.GeoShape;
import au.gov.aims.sld.geom.GeoShapeGroup;
import au.gov.aims.sld.geom.Layer;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleSheetTest {

	@Test
	public void testGenerateStyledLayers() throws Exception {
		// Define some properties
		int width = 800;
		int height = 600;

		int scale = 3000000;


		// Load style sheet
		SldParser parser = new SldParser();
		StyleSheet styleSheet = TestUtils.getStyleSheet(parser, "styles/GBR-features_Outlook.sld");


		// Create a dummy layer - A rectangle in the center of the "page"
		Map<String, PropertyValue> properties = new HashMap<String, PropertyValue>();
		properties.put("FEAT_NAME", new PropertyValue("Mainland"));
		properties.put("GBR_NAME", new PropertyValue("Mainland"));
		properties.put("SHAPE_AREA", new PropertyValue(0.5));

		Rectangle2D shape = new Rectangle2D.Double(width/4, height/4, width/2, height/2);
		GeoShape geoShape = new GeoShape(shape, properties);
		geoShape.setFillPaint(new Color(200, 220, 220));

		GeoShapeGroup group = new GeoShapeGroup("shapeGroup");
		group.add(geoShape);

		Layer shapeLayer = new Layer("Shape");
		shapeLayer.add(group);


		// Verify the layer
		Assert.assertNotNull("Layer is null.", shapeLayer);
		List<GeoShapeGroup> _shapeGroups = shapeLayer.getShapeGroups();
		Assert.assertNotNull("Layer contains no shape group.", _shapeGroups);
		Assert.assertEquals("Wrong number of shape groups in the layer.", 1, _shapeGroups.size());

		GeoShapeGroup _shapeGroup = _shapeGroups.get(0);
		Assert.assertNotNull("Shape group is null", _shapeGroup);

		List<GeoShape> _shapes = _shapeGroup.getGeoShapes();
		Assert.assertNotNull("Group contains no shape.", _shapes);
		Assert.assertEquals("Wrong number of shape in the group.", 1, _shapes.size());

		GeoShape _shape = _shapes.get(0);
		Assert.assertNotNull("Shape is null", _shape);

		Object _rect = _shape.getShape();
		Assert.assertTrue("Shape is not a rectangle", (_rect instanceof Rectangle2D));


		// Style the layer
		List<Layer> styledLayers = styleSheet.generateStyledLayers(shapeLayer, scale);



		// Verify the styled layer (containing shapes)
		Assert.assertNotNull("Styled layers is null.", styledLayers);
		Assert.assertEquals("Wrong number of layer in the styled layers.", 1, styledLayers.size());

		Layer _styledShapeLayer = styledLayers.get(0);
		Assert.assertNotNull("Styled shape layer is null.", _styledShapeLayer);

		List<GeoShapeGroup> _styledShapeGroups = _styledShapeLayer.getShapeGroups();
		Assert.assertNotNull("Styled layer contains no shape group.", _styledShapeGroups);
		Assert.assertEquals("Wrong number of shape groups in the styled layer.", 1, _styledShapeGroups.size());

		GeoShapeGroup _styledShapeGroup = _styledShapeGroups.get(0);
		Assert.assertNotNull("Styled shape group is null", _styledShapeGroup);

		List<GeoShape> _styledShapes = this.getGeoShapes(_styledShapeGroup);
		Assert.assertNotNull("Group contains no styled shape.", _styledShapes);
		Assert.assertEquals("Wrong number of styled shape in the group.", 1, _styledShapes.size());

		GeoShape _styledShape = _styledShapes.get(0);
		Assert.assertNotNull("Styled shape is null", _styledShape);

		Object _styledRect = _styledShape.getShape();
		Assert.assertTrue("Styled shape is not a rectangle", (_styledRect instanceof Rectangle2D));

		Assert.assertEquals("Wrong fill colour.", new Color(252, 246, 227), _styledShape.getFillPaint());
		Assert.assertEquals("Wrong fill colour.", new Color(169, 172, 173), _styledShape.getStrokePaint());
	}


	private List<GeoShape> getGeoShapes(GeoShapeGroup group) {
		List<GeoShape> shapes = new ArrayList<GeoShape>();

		if (!group.isEmpty()) {
			List<GeoShapeGroup> subGroups = group.getGeoShapeGroups();
			if (subGroups != null && !subGroups.isEmpty()) {
				for (GeoShapeGroup subGroup : subGroups) {
					shapes.addAll(this.getGeoShapes(subGroup));
				}
			}

			List<GeoShape> subShapes = group.getGeoShapes();
			if (subShapes != null && !subShapes.isEmpty()) {
				shapes.addAll(subShapes);
			}
		}

		return shapes;
	}
}
