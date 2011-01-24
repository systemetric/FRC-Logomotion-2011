/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.systemetric.geometry;

/**
 *
 * @author Robotics
 */
public class Matrix
{

	public final double a, b, c, d;
	/*
	 *  [ a  b ]
	 *  [ c  d ]
	 */

	public Matrix(double a, double b, double c, double d)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public Matrix(Vector xAxis, Vector yAxis)
	{
		this(xAxis.x, yAxis.x, xAxis.y, yAxis.y);
	}

	public Matrix multiply(double k)
	{
		return new Matrix(a*k, b*k, c*k, d*k);
	}

	public Vector multiply(Vector v)
	{
		return new Vector(a * v.x + b * v.y, c * v.x + d * v.y);
	}

	public Matrix multiply(Matrix that)
	{
		return new Matrix(a * that.a + b * that.c, a * that.b + b * that.d,
						  c * that.a + d * that.c, c * that.b + d * that.d);
	}

	public double determinant()
	{
		return a*d - b*c;
	}

	public Matrix inverse()
	{
		return new Matrix(d, -c, -b, a).multiply(1/determinant());
	}

	public String toString()
	{
		return "matrix:\n" + a + " " + b + "\n" + c + " " + d;
	}

	public boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof Matrix))
		{
			return false;
		}

		Matrix that = (Matrix) obj;
		return a == that.a && b == that.b && c == that.c && d == that.d;

	}

	public static Matrix fromRotation(double angle)
	{
		return new Matrix(Math.cos(angle), Math.sin(angle),
						  -Math.sin(angle), Math.cos(angle));
	}
}
