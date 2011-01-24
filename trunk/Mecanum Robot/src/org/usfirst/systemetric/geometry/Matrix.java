package org.usfirst.systemetric.geometry;

/**
 * 
 * @author Eric
 * 
 *         Represents a 2 by 2 Matrix for use in geometric transformations
 */
public class Matrix {
	/**
	 * The identity matrix
	 * 
	 * <pre>
	 * [ 1 0 ]
	 * [ 0 1 ]
	 * </pre>
	 */
	public static Matrix IDENTITY = new Matrix(1, 0, 0, 1);
	/**
	 * 90 Degree counterclockwise rotation
	 */
	public static Matrix ROTATE90 = new Matrix(0, 1, -1, 0);
	/**
	 * 180 Degree counterclockwise rotation
	 */
	public static Matrix ROTATE180 = new Matrix(-1, 0, 0, -1);
	/**
	 * 270 Degree counterclockwise rotation
	 */
	public static Matrix ROTATE270 = new Matrix(0, -1, 1, 0);

	/**
	 * Creates a transformation matrix describing a rotation of the given angle
	 * @param angle angle in radians
	 */
	public static Matrix fromRotation(double angle) {
		double sinA = Math.sin(angle);
		double cosA = Math.cos(angle);
		return new Matrix(cosA, sinA, -sinA, cosA);
	}

	public final double a, b, c, d;
	private Matrix inverseMatrix; //Store for cheap future usage

	/**
	 * Construct a square 2x2 Matrix of the form
	 * 
	 * <pre>
	 * [ a b ]
	 * [ c d ]
	 * </pre>
	 */
	public Matrix(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	/**
	 * Construct a square 2x2 Matrix representing the transformation onto a new
	 * pair of coordinate axes
	 */
	public Matrix(Vector xAxis, Vector yAxis) {
		this(xAxis.x, yAxis.x, xAxis.y, yAxis.y);
	}

	/**
	 * Multiply each cell of the Matrix by a constant
	 * 
	 * @param k
	 *            constant to multiply by
	 */
	public Matrix multiply(double k) {
		return new Matrix(a * k, b * k, c * k, d * k);
	}

	/**
	 * Divide each cell of the Matrix by a constant
	 * 
	 * @param k
	 *            constant to divide by
	 */
	public Matrix divide(double k) {
		return new Matrix(a / k, b / k, c / k, d / k);
	}

	/**
	 * Transforms the vector using the Matrix
	 * 
	 * @param v
	 *            vector to transform
	 */
	public Vector multiply(Vector v) {
		return new Vector(a * v.x + b * v.y, c * v.x + d * v.y);
	}

	/**
	 * Creates a compound transformation by combining matrices
	 * 
	 * @param that
	 *            matrix to multiply by
	 */
	public Matrix multiply(Matrix that) {
		return new Matrix(a * that.a + b * that.c, a * that.b + b * that.d, c
				* that.a + d * that.c, c * that.b + d * that.d);
	}

	/**
	 * The determinant of the matrix, <code>ad - bc</code>
	 */
	public double determinant() {
		return a * d - b * c;
	}

	/**
	 * The inverse of the matrix, representing the reverse transformation
	 */
	public Matrix inverse() {
		if(inverseMatrix == null)
		{
			inverseMatrix = new Matrix(d, -c, -b, a).divide(determinant());
			inverseMatrix.inverseMatrix = this;
		}
		return inverseMatrix;
	}

	public String toString() {
		return "Matrix: [" + a + "," + b + "][" + c + "," + d + "]]";
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Matrix)) {
			return false;
		}

		Matrix that = (Matrix) obj;
		return a == that.a && b == that.b && c == that.c && d == that.d;

	}
}
