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

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

/**
 * Inspired from GeoTools
 *  modules/library/render/src/main/java/org/geotools/renderer/style/WellKnownMarkFactory.java
 */
public class WellKnownMarkFactory {
	private static final Logger LOGGER = Logger.getLogger(WellKnownMarkFactory.class.getSimpleName());

	/** Cross general path */
	public static Shape cross;

	/** Star general path */
	public static Shape star;

	/** Triangle general path */
	public static Shape triangle;

	/** Arrow general path */
	public static Shape arrow;

	/** X general path */
	public static Shape X;

	/** hatch path */
	public static Shape hatch;

	/** square */
	public static Shape square;

	/** circle */
	public static Shape circle;

	static {
		Path2D crossPath = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		crossPath.moveTo(0.5f, 0.125f);
		crossPath.lineTo(0.125f, 0.125f);
		crossPath.lineTo(0.125f, 0.5f);
		crossPath.lineTo(-0.125f, 0.5f);
		crossPath.lineTo(-0.125f, 0.125f);
		crossPath.lineTo(-0.5f, 0.125f);
		crossPath.lineTo(-0.5f, -0.125f);
		crossPath.lineTo(-0.125f, -0.125f);
		crossPath.lineTo(-0.125f, -0.5f);
		crossPath.lineTo(0.125f, -0.5f);
		crossPath.lineTo(0.125f, -0.125f);
		crossPath.lineTo(0.5f, -0.125f);
		crossPath.lineTo(0.5f, 0.125f);

		WellKnownMarkFactory.cross = crossPath;


		AffineTransform at = new AffineTransform();
		at.rotate(Math.PI / 4.0);

		WellKnownMarkFactory.X = crossPath.createTransformedShape(at);


		Path2D starPath = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		starPath.moveTo(0.191f, 0.0f);
		starPath.lineTo(0.25f, 0.344f);
		starPath.lineTo(0.0f, 0.588f);
		starPath.lineTo(0.346f, 0.638f);
		starPath.lineTo(0.5f, 0.951f);
		starPath.lineTo(0.654f, 0.638f);
		starPath.lineTo(1.0f, 0.588f); // max = 7.887
		starPath.lineTo(0.75f, 0.344f);
		starPath.lineTo(0.89f, 0f);
		starPath.lineTo(0.5f, 0.162f);
		starPath.lineTo(0.191f, 0.0f);

		at = new AffineTransform();
		at.translate(-.5, -.5);
		starPath.transform(at);

		WellKnownMarkFactory.star = starPath;


		Path2D trianglePath = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		trianglePath.moveTo(0f, 1f);
		trianglePath.lineTo(0.866f, -.5f);
		trianglePath.lineTo(-0.866f, -.5f);
		trianglePath.lineTo(0f, 1f);

		at = new AffineTransform();
		at.translate(0, -.25);
		at.scale(.5, .5);
		trianglePath.transform(at);

		WellKnownMarkFactory.triangle = trianglePath;


		Path2D arrowPath = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		arrowPath.moveTo(0f, -.5f);
		arrowPath.lineTo(.5f, 0f);
		arrowPath.lineTo(0f, .5f);
		arrowPath.lineTo(0f, .1f);
		arrowPath.lineTo(-.5f, .1f);
		arrowPath.lineTo(-.5f, -.1f);
		arrowPath.lineTo(0f, -.1f);
		arrowPath.lineTo(0f, -.5f);

		WellKnownMarkFactory.arrow = arrowPath;


		Path2D hatchPath = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		hatchPath.moveTo(.55f,.57f);
		hatchPath.lineTo(.52f,.57f);
		hatchPath.lineTo(-.57f,-.52f);
		hatchPath.lineTo(-.57f,-.57f);
		hatchPath.lineTo(-.52f, -.57f);
		hatchPath.lineTo(.57f, .52f);
		hatchPath.lineTo(.57f,.57f);

		hatchPath.moveTo(.57f,-.49f);
		hatchPath.lineTo(.49f, -.57f);
		hatchPath.lineTo(.57f,-.57f);
		hatchPath.lineTo(.57f,-.49f);

		hatchPath.moveTo(-.57f,.5f);
		hatchPath.lineTo(-.5f, .57f);
		hatchPath.lineTo(-.57f,.57f);
		hatchPath.lineTo(-.57f,.5f);

		WellKnownMarkFactory.hatch = hatchPath;


		WellKnownMarkFactory.square = new Rectangle2D.Double(-.5, -.5, 1., 1.);

		WellKnownMarkFactory.circle = new Ellipse2D.Double(-.5, -.5, 1., 1.);
	}

	public static Shape getShape(String wellKnownName) {
		if (wellKnownName == null) {
			return null;
		}

		Shape shape = null;

		if (wellKnownName.equalsIgnoreCase("cross")) {
			shape = WellKnownMarkFactory.cross;

		} else if (wellKnownName.equalsIgnoreCase("circle")) {
			shape = WellKnownMarkFactory.circle;

		} else if (wellKnownName.equalsIgnoreCase("triangle")) {
			shape = WellKnownMarkFactory.triangle;

		} else if (wellKnownName.equalsIgnoreCase("X")) {
			shape = WellKnownMarkFactory.X;

		} else if (wellKnownName.equalsIgnoreCase("star")) {
			shape = WellKnownMarkFactory.star;

		} else if (wellKnownName.equalsIgnoreCase("arrow")) {
			shape = WellKnownMarkFactory.arrow;

		} else if (wellKnownName.equalsIgnoreCase("hatch")) {
			shape = WellKnownMarkFactory.hatch;

		} else if (wellKnownName.equalsIgnoreCase("square")) {
			shape = WellKnownMarkFactory.square;

		} else {
			throw new IllegalArgumentException("Invalid SLD wellKnownName: " + wellKnownName);
		}

		return shape;
	}
}
