/***
 * Layout Class
 * @Author Benjamin Bascom
 * @Date 11/8/12
 * @Class CIT591
 */

package layout;

public class Layout {
	private int[][] array;
	
	/**
	 * Constructor for Layout
	 * @param arrayToCopy A 1 dimensional array to be copied into an instance variable
	 */
	public Layout(int[] array) {
		int[][] temp_arr = new int[1][array.length];
		
		for (int i=0; i < array.length; i++) {
			temp_arr[0][i] = array[i];
		}
		this.array = temp_arr;
	}
	
	/**
	 * Constructor for Layout
	 * @param arrayToCopy A 2 dimensional array to be copied into an instance variable
	 */
	public Layout(int[][] array) {
		this.array = copy(array);
	}
	
	/**
	 * Constructor for Layout
	 * @param arrayToCopy A integer to be copied into an instance variable
	 * Copied as a range from 1 to length inclusive
	 */
	public Layout(int length) {
		if (length < 0) {
		    throw new IllegalArgumentException();
		}
		
		int[][] temp_arr = new int[1][length];
		for (int i=0; i<length-1; i++) {
			temp_arr[0][i] = i + 1;
		}
		this.array = temp_arr;
	}
	
	/**
	 * Getter for rowcount
	 * @return integer of size array.length
	 */
	public int rowCount() {
		return array.length;
	}
	
	/**
	 * Getter for columncount
	 * @return integer of size array[0].length
	 * Assumes no ragged arrays by returning the column length for the first 
	 * row as the default for the array
	 */
	public int columnCount() {
		return array[0].length;
	}
	
	/**
	 * Array copy method
	 * @param 2 dimensional integer array
	 * @return deep copy of @param from
	 * 
	 */
	private static int[][] copy(int[][] from) {
		int rows = from.length;
		int cols = from[0].length;
		
		int[][] to = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				to[i][j] = from[i][j];
			}
		}
		return to;
	}
	/**
	 * Getter for layout, returns flattened if layout is 2 dimensional
	 * @return copy of one dimensional array of layout
	 */
	public int[] toArray1D() {
		int[] array1D;

		array1D = new int[this.rowCount() * this.columnCount()];
		
		int m = 0;
		for (int i = 0; i < this.rowCount(); i++) {
			for (int j = 0; j < this.columnCount(); j++) {
				array1D[m] = this.array[i][j];
				m += 1;
			}
		}
		return array1D;
	}
	/**
	 * Getter for layout, returns copy
	 * @return copy of 2 dimensional array of layout
	 */
	public int[][] toArray2D() {
		return copy(array);
	}
	
	/**
	 * Reverses a layout by row
	 * @return a new Layout with each row inverted
	 */
	public Layout reverse() {
		Layout reversedLayout;
		int[][] constr;
		
		constr = new int[this.rowCount()][this.columnCount()];
		for (int l = 0; l < this.rowCount(); l++) {
			int[] reverse_row = new int[this.columnCount()];
			int j = 0;
			for (int i = this.columnCount() - 1; i >= 0; i--) {
				reverse_row[i] = array[l][j];
				j += 1;
			}
			constr[l] = reverse_row;
		}
		reversedLayout = new Layout(constr);
		return reversedLayout;
	}
	
	/**
	 * Rotates a layout right, that is, puts the first row in the last column, last row
	 * in the first column, etc
	 * @return a new Layout with the same values but rotated right
	 */
	public Layout rotateRight() {
		Layout rotatedLayout;
		
		if (this.columnCount()==0) {
			int[][] constr = copy(this.toArray2D());
			rotatedLayout = new Layout(constr);
			return rotatedLayout;
		}
		
		int[][] right_shifted_array = new int[this.columnCount()][this.rowCount()];
		
		for (int i=0; i < this.rowCount(); i++) {
			for (int j=0; j < this.columnCount(); j++) {
				right_shifted_array[j][i] = array[i][j];
			}
		}
		rotatedLayout = new Layout(right_shifted_array);
		rotatedLayout = rotatedLayout.reverse();
		return rotatedLayout;
	}
	
	/**
	 * Rotates a layout left, that is, puts the first row into the first column, etc.
	 * @return a new Layout with the same values but rotated left
	 */
	public Layout rotateLeft() {
		Layout rotatedLayout;
		int[][] constr;
		
		if (this.columnCount()==0) {
			constr = copy(this.toArray2D());
			rotatedLayout = new Layout(constr);
			return rotatedLayout;
		} else {
			constr = new int[this.columnCount()][this.rowCount()];
		}
		
		int m = 0;
		for (int i = this.columnCount()-1; i >= 0; i--) {
			for (int j = 0; j < this.rowCount(); j++) {
				constr[m][j] = array[j][i];
			}
			m += 1;
		}
	
		rotatedLayout = new Layout(constr);
		return rotatedLayout;
	}
	
	/**
	 * Transposes a layout, that is a layout of m, n rows/columns becomes
	 * a layout of n, m columns
	 * @return
	 */
	public Layout transpose() {
		Layout transposedLayout;
		int[][] constr = new int[this.columnCount()][this.rowCount()];
		
		if (this.columnCount()==0) {
		    throw new IllegalArgumentException();
		}
		
		for (int i=0; i < this.rowCount(); i++) {
			for (int j=0; j < this.columnCount(); j++) {
				constr[j][i] = array[i][j];
			}
		}
		transposedLayout = new Layout(constr);
		return transposedLayout;
	}
	
	/**
	 * Returns a layout of m, n row/columns
	 * if m is not evenly divisible by n, throws an exception
	 * @param n number of columns in new layout
	 * @return layout with dimension m x n
	 */
	public Layout ravel(int n) {
		Layout raveledLayout, unraveledLayout;
		int m, newRowCount;
		int[][] constr;
		int[] temp_array;
		
		//Normalize 2D arrays
		if (array.length>1) {
			unraveledLayout = this.unravel();
			this.array = copy(unraveledLayout.toArray2D());
		}
		//Get Column Count
		m = this.columnCount();
		
		//throw IllegalArgumentException if m%n==0;
		if (!(m%n==0)) {
		    throw new IllegalArgumentException();
		}
		
		newRowCount = m/n;
		
		constr = new int[newRowCount][n];
		int r = 0;
		
		int[] array1d = this.toArray1D();
		
		for (int i = 0; i < newRowCount; i++) {
			for (int j = 0; j < n; j++) {
				constr[i][j] = array1d[r];
				r += 1;
			}
		}
		
		raveledLayout = new Layout(constr);
		return raveledLayout;
	}
	
	/**
	 * Returns a layout of 1 row with m*n columns where m*n is the original column/row count
	 * @return a layout of 1 row with m*n columns
	 */
	public Layout unravel() {
		Layout unraveledLayout;
		int m = 0;
		int[] arr = new int[this.columnCount() * this.rowCount()];
		
		for (int i = 0; i < this.rowCount(); i++) {
			for (int j = 0; j < this.columnCount(); j++) {
				arr[m] = this.array[i][j];
				m += 1;
			}
		}
		unraveledLayout = new Layout(arr);
		return unraveledLayout;
	}
	
	/**
	 * Returns a layout of m/n rows and n columns
	 * @param n, new column count
	 * @return layout object with n columns and oldRow*oldColumn/n rows
	 */
	public Layout reshape(int n) {
		Layout reshaped;
		int[] array1D;
		
		//Throw error if r*c is not divided evenly by n
		if (!((this.rowCount()*this.columnCount())%n==0)) {
		    throw new IllegalArgumentException();
		}
		
		//Start with a 1D array for easily looping
		array1D = this.toArray1D();
		
		//Index for where we are in our 1D array as we step through the rows/columns
		//of our new array
		int m = 0;
		
		int[][] constr = new int[((this.rowCount()*this.columnCount())/n)][n];
		
		for (int i = 0; i < ((this.rowCount()*this.columnCount())/n); i++) {
			for (int j = 0; j < n; j++) {
				constr[i][j] = array1D[m];
				m += 1;
			}

		}
		reshaped = new Layout(constr);
		return reshaped;
	}
	
	/**
	 * Combines two layouts by appending the rows of the layout input to the
	 * corresponding rows of the layout object
	 * @param layout object to add in
	 * @return new layout object with the same number of rows and column
	 * length equal to the sum of the column length of the original layout +
	 * the column length of the layout parameter
	 */
	public Layout join(Layout layout) {
		Layout joined;
		int[][] constr, layarray;
		
		//Illegal if rowCount() of the layout object input is not equal to rowCount() of our
		//internal array representation
		if (!(layout.rowCount() == this.rowCount())) {
			throw new IllegalArgumentException();
		}
		
		layarray = layout.toArray2D(); 
		constr = new int[this.rowCount()][this.columnCount() + layout.columnCount()];

		//Fill in what we've already got
		for (int i = 0; i < this.rowCount(); i++) {
			for (int j = 0; j < this.columnCount(); j++) {
				constr[i][j] = this.array[i][j];
			}
		}
		
		//Add the new columns from our input layout (by starting at j+this.columnCount())
		for (int i = 0; i < this.rowCount(); i++) {
			for (int j = 0; j < layout.columnCount(); j++) {
				constr[i][j+this.columnCount()] = layarray[i][j];
			}
		}
		
	joined = new Layout(constr);
	return joined;
	}
	
	/**
	 * Adds a layout with equal column length by appending it as a (series of) new rows
	 * @param layout to stack onto (append to) our layout
	 * @return Stacked layout object
	 */
	public Layout stack(Layout layout) {
		Layout stacked;
		int[][] constr, layarray;
		
		//initialize new Array to stick our items in
		constr = new int[this.rowCount() + layout.rowCount()][this.columnCount()];
		
		//Get the internal representation of our input layout as a 2 dimensional array
		layarray = layout.toArray2D();
		
		//Illegal if the columnCount() of the input layout is not equal to the columnCount()
		//of our internal array
		if (!(layout.columnCount() == this.columnCount())) {
			throw new IllegalArgumentException();
		}
		
		//Copy what's already in our array
		for (int i = 0; i < this.rowCount(); i++) {
			for (int j = 0; j < this.columnCount(); j++) {
				constr[i][j] = this.array[i][j];
			}
		}
		
		//Now add the information from layout as new rows (by starting at i+this.rowCount())
		for (int i = 0; i < layout.rowCount(); i++) {
			for (int j = 0; j < this.columnCount(); j++) {
				constr[i+this.rowCount()][j] = layarray[i][j];
			}
		}
		
	stacked = new Layout(constr);
	return stacked;	
	}
	
	/**
	 * Returns a new layout from firstRow to lastRow inclusive
	 * @param firstRow first row index 
	 * @param lastRow last row index
	 * @return a new row-wise slice of layout from firstRow to lastRow
	 */
	public Layout rows(int firstRow, int lastRow) {
		Layout rowLayout;
		int[][] constr;
		
		if (!(lastRow<=this.rowCount()) || firstRow > lastRow) {
			throw new IllegalArgumentException();
		}
		
		constr = new int[(lastRow - firstRow)+1][this.columnCount()];
		
		for (int i = firstRow; i <= lastRow; i++) {
			for (int j = 0; j < this.columnCount(); j++) {
				constr[i-firstRow][j] = array[i][j];
			}
		}
		rowLayout = new Layout(constr);
		return rowLayout;
		
	}
	
	/**
	 * Returns a new layout from firstColumn to lastColumn inclusive
	 * @param firstColumn first row index 
	 * @param lastColumn last row index
	 * @return a new column-wise slice of layout from firstColumn to lastColumn
	 */
	public Layout columns(int firstColumn, int lastColumn) {
		Layout columnLayout;
		int[][] constr;
		
		if (!(lastColumn<=this.columnCount()) || firstColumn > lastColumn) {
			throw new IllegalArgumentException();
		}
		
		constr = new int[this.rowCount()][(lastColumn-firstColumn)+1];

		for (int i = 0; i < this.rowCount(); i++) {
			for (int j = firstColumn; j <= lastColumn; j++) {
				constr[i][j-(firstColumn)] = array[i][j];
				
			}

		}
		columnLayout = new Layout(constr);
		return columnLayout;
		
	}
	/**
	 * Returns a row-wise and column-wise slice of a layout
	 * @param firstRow Index of first row in slice
	 * @param lastRow Index of last row in slice
	 * @param firstColumn Index of first column in slice
	 * @param lastColumn Index of last column in slice
	 * @return layout of size (lastRow - firstRow) x (lastColumn - firstColumn)
	 */
	public Layout slice(int firstRow, int lastRow, int firstColumn, int lastColumn) {
		Layout slicedLayout;
		int[][] constr;
		
		if (!(lastColumn<=this.columnCount()) || firstColumn > lastColumn) {
			throw new IllegalArgumentException();
		}
		
		if (!(lastRow<=this.rowCount()) || firstRow > lastRow) {
			throw new IllegalArgumentException();
		}
		
		constr = new int[(lastRow - firstRow)+1][(lastColumn - firstColumn)+1];
		
		for (int i = firstRow; i <= lastRow; i++) {
			for (int j = firstColumn; j <= lastColumn; j++) {
				constr[i-(firstRow)][j-(firstColumn)] = this.array[i][j];
			}
		}
		slicedLayout = new Layout(constr);
		return slicedLayout;
	}
	
	/**
	 * Returns a layout with elements row through column replaced by the elements in layout
	 * @param layout layout to sub into this layout object
	 * @param row first row to start the substitution
	 * @param column first column to start the substitution
	 * @return Layout with elements (lastRow - row) x (lastColumn - column) 
	 * substituted with items from the layout input
	 */
	public Layout replace(Layout layout, int row, int column) {
		Layout replacedLayout;
		int[][] constr, layarray;
		int m, n;

		constr = new int[this.rowCount()][this.columnCount()];
		
		//
		if (!(layout.columnCount() <= this.columnCount() || (layout.rowCount() <= this.columnCount()))) {
			throw new IllegalArgumentException();
		}
		
		//First populate with old values
		for (int i = 0; i < this.rowCount(); i++) {
			for (int j = 0; j < this.columnCount(); j++) {
				constr[i][j] = array[i][j];
			}
		}
		
		layarray = layout.toArray2D();
		//Then populate relevant indices with our local array
		for (int i = 0; i < layout.rowCount(); i++) {
			for (int j = 0; j < layout.columnCount(); j++) {
				constr[row+i][column+j] = layarray[i][j];
			}
		}
		replacedLayout = new Layout(constr);
		return replacedLayout;
	}
	
	@Override
    public boolean equals(Object o) {
        if (! (o instanceof Layout)) return false;
        Layout that = (Layout)o;
        if (this.rowCount() != that.rowCount()) return false;
        if (this.columnCount() != that.columnCount()) return false;
        return java.util.Arrays.equals(this.toArray1D(), that.toArray1D());
    }
	
	/**
	 * Getter for item at index [row][column]
	 * @param row
	 * @param column
	 * @return
	 */
	public int at(int row, int column) {
		if (!(row < this.rowCount() && column < this.columnCount())) {
			throw new IllegalArgumentException();
		}
		
		return this.array[row][column];
	}
}
