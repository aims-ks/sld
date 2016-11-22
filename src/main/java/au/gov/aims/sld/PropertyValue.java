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

public class PropertyValue {
	private Object value;

	public PropertyValue(Object value) {
		this.value = value;
	}

	public boolean isNumeric() {
		if (this.value == null) {
			return false;
		}
		return (this.value instanceof Double) || (this.value instanceof Float) || (this.value instanceof Integer);
	}

	public Double getDoubleValue() {
		if (this.isNumeric()) {
			if (this.value instanceof Double) {
				return (Double) this.value;

			} else if (this.value instanceof Float) {
				Float floatValue = (Float)this.value;
				return floatValue.doubleValue();

			} else if (this.value instanceof Integer) {
				Integer integerValue = (Integer)this.value;
				return integerValue.doubleValue();
			}
		}
		return null;
	}

	public String getStringValue() {
		return this.value == null ? null : this.value.toString();
	}

	@Override
	public String toString() {
		return "PropertyValue{" + this.value + '}';
	}
}
