package layout;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LayoutTest {
	Layout example, example2d, empty;
	
	@Before
	public void setUp() throws Exception {
		example = new Layout(new int[] {1,2,3,4});
		example2d = new Layout(new int[][] { { 1, 2, 3 }, { 4, 5, 6 } });
		empty = new Layout(0);

	}
	
	@Test 
	public final void testLayoutConstructor() {
		Layout example_loc = new Layout(new int[] {1,2,3,4});
		Layout example_len = new Layout(12);
		int[][] array1 = this.example.toArray2D();
		int[][] array2 = example_loc.toArray2D();
		assertArrayEquals(array1, array2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testLayoutConstructorException() {
		Layout example_len_neg = new Layout(-1);
	}
	
	@Test
	public final void testReverse() {
		Layout reversedLayout;
		reversedLayout = this.example.reverse();
		int[][] array = reversedLayout.toArray2D();
		int[][] test = new int[][] { {4,3,2,1} };
		assertArrayEquals(array, test);
		
		Layout reversedLayout2D;
		reversedLayout2D = this.example2d.reverse();
		int[][] array2d = reversedLayout2D.toArray2D();
		int[][] test2d = new int[][] { {3, 2, 1}, {6, 5, 4} };
		assertArrayEquals(array2d, test2d);
		
		Layout emptyReverse;
		emptyReverse = empty.reverse();
		assertEquals(empty, emptyReverse);
	}
	
	@Test
	public final void testToArray1D() {
		int[] array, test, array_empty, test_empty;

		array = new int[] { 1, 2, 3, 4, 5, 6 };
		test = example2d.toArray1D();
		assertArrayEquals(test, array);
		
		array_empty = new int[] {};
		test_empty = empty.toArray1D();
		assertArrayEquals(test_empty, array_empty);
	}

	@Test
	public final void testToArray2D() {
		int[][] example_arr;
		int[][] test_arr = new int[][] { {1,2,3}, {4,5,6} };
		example_arr = example2d.toArray2D();
		assertArrayEquals(example_arr, test_arr);
		
	}
	
	@Test
	public final void testRowCount() {
		int oned_row, twod_row, empty_row;
		empty_row = empty.rowCount();
		oned_row = example.rowCount();
		twod_row = example2d.rowCount();
		assertEquals(empty_row, 1);
		assertEquals(oned_row, 1);
		assertEquals(twod_row, 2);
	}

	@Test
	public final void testColumnCount() {
		int oned_col, twod_col, empty_col;
		empty_col = empty.columnCount();
		oned_col = example.columnCount();
		twod_col = example2d.columnCount();
		assertEquals(empty_col, 0);
		assertEquals(oned_col, 4);
		assertEquals(twod_col, 3);
		
	}
	
	@Test
	public final void testRotateRight() {
		Layout rotate, rotate1d, example1d, empty_test, empty_layout;
		int[][] array;
		int[][] test = new int[][] { {4,1}, {5,2}, {6,3} };
		rotate = example2d.rotateRight();
		array = rotate.toArray2D();
		assertArrayEquals(array, test);
		
		example1d = example.rotateRight();
		int[][] constr = new int[][] { {1}, {2}, {3}, {4} };
		rotate1d = new Layout(constr);
		assertEquals(example1d, rotate1d);
		
		empty_layout = empty.rotateRight();
		empty_test = new Layout(0);
		assertEquals(empty_layout, empty_test);
		
	}

	@Test
	public final void testRotateLeft() {
		Layout rotate, rotate1d, example1d, empty_test, empty_layout;
		int[][] array;
		int[][] test = new int[][] { {3,6}, {2,5}, {1,4} };
		rotate = example2d.rotateLeft();
		array = rotate.toArray2D();
		assertArrayEquals(array, test);
		
		example1d = example.rotateLeft();
		int[][] constr = new int[][] { {4}, {3}, {2}, {1} };
		rotate1d = new Layout(constr);
		assertEquals(example1d, rotate1d);
		
		empty_layout = empty.rotateLeft();
		empty_test = new Layout(0);
		assertEquals(empty_layout, empty_test);
	}
	
		

	@Test
	public final void testTranspose() {
		Layout transpose, transpose1D, transpose1DTest, emptyTest;
		int[][] array, constr, testTranspose1D;
		int[][] test = new int[][] { {1,4}, {2,5}, {3,6} };
		transpose = example2d.transpose();
		array = transpose.toArray2D();
		assertArrayEquals(array, test);
		
		transpose1D = example.transpose();
		testTranspose1D = new int[][] { {1}, {2}, {3}, {4} };
		transpose1DTest = new Layout(testTranspose1D);
		assertEquals(transpose1DTest, transpose1D);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testTransposeException() {
		empty.transpose();
	}
	
	@Test
	public final void testRavel() {
		Layout raveled, raveled2D;
		int[][] array, raveledArray, array2D;
		int[][] test = new int[][] { {1,2}, {3,4} };
		
		raveled = example.ravel(2);
		array = raveled.toArray2D();
		assertArrayEquals(array, test);
		
		raveled2D = example2d.ravel(3);
		raveledArray = new int[][] { {1,2,3}, {4,5,6} };
		array2D = raveled2D.toArray2D();
		assertArrayEquals(raveledArray, array2D);	
	}
	

	@Test
	public final void testUnravel() {
		Layout unraveled, testEmpty, layoutBig;
		int[] test, array;
		int[][] testBig, arrayBig;
		
		test = new int[] { 1,2,3,4,5,6 };
		unraveled = example2d.unravel();
		array = unraveled.toArray1D();
		assertArrayEquals(array, test);
		
		testEmpty = new Layout(0);
		testEmpty = testEmpty.unravel();
		assertEquals(empty, testEmpty);
		
		int[][] constr;
		constr = new int[][] { {1,2,3,4}, {5,6,7,8}, {9,10,11,12} };
		layoutBig = new Layout(constr);
		layoutBig = layoutBig.unravel();
		testBig = new int[][] { { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 } };
		arrayBig = layoutBig.toArray2D();
		assertArrayEquals(arrayBig, testBig);
		
	}

	@Test
	public final void testReshape() {
		Layout reshaped, to_reshape;
		int[][] array, test, constr;
		constr = new int[][] { {1,2}, {3,4}, {5,6} };
		to_reshape = new Layout(constr);
		
		test = example2d.toArray2D();
		reshaped = to_reshape.reshape(3);
		array = reshaped.toArray2D();
		assertArrayEquals(array, test);
		
		Layout reshapedBig, to_reshapeBig, exampleBig;
		int[][] arrayBig, testBig, constrBig, exampleBigConstr;
		constrBig = new int[][] { {1,2,3,4}, {5,6,7,8}, {9,10,11,12} };
		to_reshapeBig = new Layout(constrBig);
		
		exampleBigConstr = new int[][] { {1,2,3,4,5,6}, {7,8,9,10,11,12} };
		exampleBig = new Layout(exampleBigConstr);
		testBig = exampleBig.toArray2D();
		reshapedBig = to_reshapeBig.reshape(6);
		arrayBig = reshapedBig.toArray2D();
		assertArrayEquals(arrayBig, testBig);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testReshapeException() {
		example2d.reshape(4);
	}

	@Test
	public final void testJoin() {
		Layout joined, to_join;
		int[][] array, test, constr;
		constr = new int[][] { {10, 20, 30}, {40, 50, 60} };
		to_join = new Layout(constr);
		test = new int[][] { {1,2,3,10,20,30}, {4,5,6,40,50,60} };
		
		joined = example2d.join(to_join);
		array = joined.toArray2D();
		assertArrayEquals(array, test);
		
		int[][] constr_empty_arr;
		Layout to_join_empty, join_empty;
		constr_empty_arr = new int[][] { {}, {} };
		to_join_empty = new Layout(constr_empty_arr);
		join_empty = example2d.join(to_join_empty);
		assertEquals(example2d, join_empty);
		
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testJoinException() {
		example.join(example2d);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testJoinEmptyException() {
		example2d.join(empty);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyJoinException() {
		empty.join(example2d);
	}
	
	@Test
	public final void testStack() {
		Layout stacked, to_stack, stack_empty, empty_list;
		int[][] array, test, constr;
		constr = new int[][] { {7,8,9} };
		to_stack = new Layout(constr);
		stacked = example2d.stack(to_stack);
		array = stacked.toArray2D();
		test = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
		assertArrayEquals(array, test);
		
		stack_empty = new Layout(0);
		stack_empty = stack_empty.stack(empty);
		int[][] empty_constr = { {}, {} };
		empty_list = new Layout(empty_constr);
		assertEquals(stack_empty, empty_list);
		
		Layout single_stack, to_single_stack;
		int[][] constr_single_stack, constr_single, test_single_stack, array_single_stack;
		constr_single_stack = new int[][] { {2} };
		constr_single = new int[][] { {1} };
		single_stack = new Layout(constr_single);
		to_single_stack = new Layout(constr_single_stack);
		single_stack = single_stack.stack(to_single_stack);
		test_single_stack = new int[][] { {1}, {2} };
		array_single_stack = single_stack.toArray2D();
		assertArrayEquals(array_single_stack, test_single_stack);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testStackException() {
		example.stack(example2d);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testStackEmptyException() {
		example.stack(empty);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyStackException() {
		empty.stack(example);
	}
	@Test
	public final void testRows() {
		Layout testRowsLeft;
		int[][] arrayLeft, testLeft, constrLeft;
		constrLeft = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
		testRowsLeft = new Layout(constrLeft);
		testLeft = new int[][] { {1,2,3} };
		testRowsLeft = testRowsLeft.rows(0,0);
		arrayLeft = testRowsLeft.toArray2D();
		assertArrayEquals(arrayLeft, testLeft);
		
		Layout testRows;
		int[][] array, test, constr;
		constr = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
		testRows = new Layout(constr);
		test = new int[][] { {4,5,6}, {7,8,9} };
		testRows = testRows.rows(1,2);
		array = testRows.toArray2D();
		assertArrayEquals(array, test);
		
		Layout testRowsEmpty;
		int[][] arrayEmpty, testEmpty, constrEmpty;
		constrEmpty = new int[][] { {}, {}, {} };
		testRowsEmpty = new Layout(constrEmpty);
		testEmpty = new int[][] { {}, {} };
		testRowsEmpty = testRowsEmpty.rows(1,2);
		arrayEmpty = testRowsEmpty.toArray2D();
		assertArrayEquals(arrayEmpty, testEmpty);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testRowsException() {
		example.rows(2,1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testRowsExceptionOutOfBounds() {
		example2d.rows(2,3);
	}

	@Test
	public final void testColumns() {
		Layout testColsLeft;
		int[] arrayLeft, testLeft;
		int[][] constrLeft;
		constrLeft = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
		testColsLeft = new Layout(constrLeft);
		testLeft = new int[] { 1, 4, 7 };
		testColsLeft = testColsLeft.columns(0,0);
		arrayLeft = testColsLeft.toArray1D();
		assertArrayEquals(arrayLeft, testLeft);
		
		Layout testCols;
		int[][] array, test, constr;
		constr = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
		testCols = new Layout(constr);
		test = new int[][] { {1,2}, {4,5}, {7,8} };
		testCols = testCols.columns(0,1);
		array = testCols.toArray2D();
		assertArrayEquals(array, test);
		
		Layout testColsRight;
		int[][] arrayRight, testRight, constrRight;
		constrRight = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
		testColsRight = new Layout(constrRight);
		testRight = new int[][] { {2,3}, {5,6}, {8,9} };
		testColsRight = testColsRight.columns(1,2);
		arrayRight = testColsRight.toArray2D();
		assertArrayEquals(arrayRight, testRight);
	
		
	}

	
	@Test(expected = IllegalArgumentException.class)
	public final void testColsException() {
		example.columns(2,1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testColsExceptionOutOfBounds() {
		example2d.columns(4,5);
	}

	
	@Test
	public final void testSlice() {
		Layout testSliced;
		int[][] array, test, constr;
		constr = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
		testSliced = new Layout(constr);
		test = new int[][] { {5,6}, {8,9} };

		testSliced = testSliced.slice(1,2,1,2);
		array = testSliced.toArray2D();
		
		assertArrayEquals(array, test);
		
		Layout testSingleSlice;
		int[][] arraySingle, testSingle, constrSingle;
		constrSingle = new int[][] { {1,2,3}, {4,5,6}, {7,8,9} };
		testSingleSlice = new Layout(constrSingle);
		testSingle = new int[][] { {5} };

		testSingleSlice = testSingleSlice.slice(1,1,1,1);
		arraySingle = testSingleSlice.toArray2D();
		
		assertArrayEquals(arraySingle, testSingle);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testRowSliceException() {
		example2d.slice(2,1,0,1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testRowSliceExceptionOutOfBounds() {
		example2d.slice(3,4,0,1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testColSliceException() {
		example2d.slice(0,1,2,1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testColSliceExceptionOutOfBounds() {
		example2d.slice(0,1,7,8);
	}

	@Test
	public final void testReplace() {
		Layout testReplace, testLayout;
		int[][] array, test, constr;
		constr = new int[][] { {6,7}, {8,9} };
		testLayout = new Layout(constr);
		
		testReplace = example2d.replace(testLayout, 0, 1);
		array = testReplace.toArray2D();
		test = new int[][] { {1, 6, 7}, {4, 8, 9} };
		assertArrayEquals(array, test);
		
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testReplaceException() {
		empty.replace(example, 0, 0);
	}
	
    @Test
    public final void testEqualsObject() {
        Layout l0 = new Layout(0);
        Layout l1 = new Layout(5);
        Layout l2 = new Layout(new int[] { 1, 2, 3 });
        Layout l3 = new Layout(new int[][] { {2, 3}, {4, 5}, {6, 7}, {8, 1} });
        Layout m0 = new Layout(0);
        Layout m1 = new Layout(5);
        Layout m2 = new Layout(new int[] { 1, 2, 3 });
        Layout m3 = new Layout(new int[][] { {2, 3}, {4, 5}, {6, 7}, {8, 1} });
        assertEquals(l0, m0);
        assertEquals(l1, m1);
        assertEquals(l2, m2);
        assertEquals(l3, m3);
        assertFalse(l0.equals(l1));
        assertFalse(l1.equals(l0));
        assertFalse(l1.equals(l2));
        assertFalse(l2.equals(l3));
        assertFalse(l3.equals(l1));
        assertFalse(l2.equals(new int[] {1, 3, 2}));
        assertFalse(l3.equals(new int[] { 2, 3, 4, 5, 6, 7, 8, 1 }));
        assertFalse(l0.equals(null));
   }


	@Test
	public final void testAt() {
		int test1d, test2d;
		
		assertEquals(1, example.at(0,0));
		assertEquals(6, example2d.at(1,2));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAtException() {
		example.at(0, 7);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAtEmptyException() {
		empty.at(0, 0);
	}

}
