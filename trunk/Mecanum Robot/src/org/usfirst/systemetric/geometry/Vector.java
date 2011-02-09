package org.usfirst.systemetric.geometry;

/**
 *
 * @author Eric
 */
public class Vector
{
	public final double x;
	public final double y;

	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector times(double factor)
	{
		return new Vector(x * factor, y * factor);
	}
	
	public Vector divideBy(double factor)
	{
		return new Vector(x / factor, y / factor);
	}

	public Vector add(Vector that)
	{
		return new Vector(x + that.x, y + that.y);
	}

	public Vector minus(Vector that)
	{
		return new Vector(x - that.x, y - that.y);
	}

	public double length()
	{
		return Math.sqrt(x * x + y * y);
	}

	public Vector unit()
	{
		return this.times(1 / this.length());
	}

	public double dot(Vector that)
	{
		return x*that.x + y*that.y;
	}

	public static Vector fromPolarCoords(double r, double theta)
	{
		return new Vector(r*Math.sin(theta), r*Math.cos(theta));
	}
	
	public String toString() {
		return "Vector: (" + x + ", " + y + ")";
	}
}
