import java.math.BigInteger;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.linear.AnyMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DefaultRealMatrixPreservingVisitor;
import org.apache.commons.math3.linear.MatrixDimensionMismatchException;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.MathUtils;

import edu.emory.mathcs.backport.java.util.Arrays;

public class FractionMatrix implements AnyMatrix{

	private Rational data[][];
	private int rowDimension;//Row x Col - Sor x Oszlop
	private int colDimension;

	
	public FractionMatrix() {}
	public FractionMatrix(int rows, int cols) {
		rowDimension = rows;
		colDimension = cols;
		data = new Rational[rows][cols];
	}
	public FractionMatrix(final Rational[][] d) {
		rowDimension = d.length;
		colDimension = d[0].length;
		data = d;
		
	}
	
    public FractionMatrix(final Rational[] v) {
        rowDimension = v.length;
        colDimension = 1;
        data = new Rational[rowDimension][colDimension];
        for (int row = 0; row < rowDimension; row++) {
            data[row][0] = v[row];
        }
    }
    
    public FractionMatrix(Rational rat) {
    	colDimension = 1;
    	rowDimension = 1;
    	data = new Rational[rowDimension][colDimension];
    	data[0][0] = rat;
	}
    
    @Override
    public String toString() {
    	StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < rowDimension; i++) {
			for (int j = 0; j < colDimension; j++) {
				if (colDimension < 2){
					strBuilder.append(data[i][j].toString() );
				}else{
					strBuilder.append("\t" + data[i][j].toString() + "\t");
				}
				
			}
			if(i != rowDimension-1)strBuilder.append(System.getProperty("line.separator"));
		}
		return strBuilder.toString();

    }
	public int getColumnDimension(){
    	return colDimension;
    }
    
    public int getRowDimension(){
    	return rowDimension;
    }
    
    public void print(){
		for (int i = 0; i < rowDimension; i++) {
			for (int j = 0; j < colDimension; j++) {
				
				System.out.print(data[i][j].toString() + " ");
			}
			System.out.println();
		}
    }
    
    public Rational getEntry(final int row, final int column)
            throws OutOfRangeException {
            MatrixUtils.checkMatrixIndex(this, row, column);
            return data[row][column];
        }

	
    
    public Rational determinant(Rational[][] data, int N)
    {
    	
    	//int N = rowDimension;
        Rational det = new Rational(BigInteger.ZERO, BigInteger.ONE );
        if(N == 1)
        {
            det = data[0][0];
        }
        else if (N == 2)
        {
            det = data[0][0].mult(data[1][1]).sub(data[1][0].mult(data[0][1])) ;
        }
        else
        {
            det= new Rational(BigInteger.ZERO, BigInteger.ONE );
            for(int j1=0;j1<N;j1++)
            {
                Rational[][] m = new Rational[N-1][];
                for(int k=0;k<(N-1);k++)
                {
                    m[k] = new Rational[N-1];
                }
                for(int i=1;i<N;i++)
                {
                    int j2=0;
                    for(int j=0;j<N;j++)
                    {
                        if(j == j1)
                            continue;
                        m[i-1][j2] = data[i][j];
                        j2++;
                    }
                }
                det = det.plus(new Rational(BigInteger.ONE.negate().pow(1+j1+1),BigInteger.ONE).mult(data[0][j1].mult(determinant(m,N-1)))  )  ;
            }
        }
        return det;
    }
 
    
    
    public Rational[][] getData() {
		return data;
	}
	public void setData(Rational[][] data) {
		this.data = data;
	}
	public FractionMatrix getColumnVector(final int column)
            throws OutOfRangeException {
            return new FractionMatrix(getColumn(column));
        }

    public Rational[] getColumn(final int column) throws OutOfRangeException {
        MatrixUtils.checkColumnIndex(this, column);
        final int nRows = getRowDimension();
        final Rational[] out = new Rational[nRows];
        for (int i = 0; i < nRows; ++i) {
            out[i] = getEntry(i, column);
        }

        return out;
    }
	
	public FractionMatrix add(FractionMatrix other) throws IllegalArgumentException{
		if(colDimension!=other.getColumnDimension() || rowDimension!=other.getRowDimension()){
			throw new IllegalArgumentException("You have to add an n x n matrix to each other.");
		}
		
		Rational[][] temp = new Rational[rowDimension][colDimension];
		for (int i = 0; i < rowDimension; i++) {
			for (int j = 0; j < colDimension; j++) {
				temp[i][j] = data[i][j].plus(other.getEntry(i, j));
			}
		}
		
		return new FractionMatrix(temp);
	}
	
	public FractionMatrix subtract(FractionMatrix other) throws IllegalArgumentException{
		if(colDimension!=other.getColumnDimension() || rowDimension!=other.getRowDimension()){
			throw new IllegalArgumentException("You have to add an n x n matrix to each other.");
		}
		
		Rational[][] temp = new Rational[rowDimension][colDimension];
		for (int i = 0; i < rowDimension; i++) {
			for (int j = 0; j < colDimension; j++) {
				temp[i][j] = data[i][j].sub(other.getEntry(i, j));
			}
		}
		
		return new FractionMatrix(temp);
	}
	
	public FractionMatrix multiply(FractionMatrix other) throws DimensionMismatchException{
		checkMultiplicationCompatible(this, other);
		
        final int nRows = this.getRowDimension();
        final int nCols = other.getColumnDimension();
        final int nSum = this.getColumnDimension();
        
        final Rational[][] outData = new Rational[nRows][nCols];
        // Will hold a column of "m".
        final Rational[] mCol = new Rational[nSum];
        final Rational[][] mData = other.data;
        
        
        // Multiply.
        for (int col = 0; col < nCols; col++) {
            // Copy all elements of column "col" of "m" so that
            // will be in contiguous memory.
            for (int mRow = 0; mRow < nSum; mRow++) {
                mCol[mRow] = mData[mRow][col];
            }

            for (int row = 0; row < nRows; row++) {
                final Rational[] dataRow = data[row];
                Rational sum =  new Rational(BigInteger.ZERO,BigInteger.ONE);
                for (int i = 0; i < nSum; i++) {
                    sum = sum.plus(dataRow[i].mult(mCol[i])) ;
                }
                outData[row][col] = sum;
            }
        }


        return new FractionMatrix(outData);
	}

	public static void checkMatrixIndex(final AnyMatrix m,
			final int row, final int column)
					throws OutOfRangeException {
		checkRowIndex(m, row);
		checkColumnIndex(m, column);
	}
    public static void checkRowIndex(final AnyMatrix m, final int row)
            throws OutOfRangeException {
            if (row < 0 ||
                row >= m.getRowDimension()) {
                throw new OutOfRangeException(LocalizedFormats.ROW_INDEX,
                                              row, 0, m.getRowDimension() - 1);
            }
        }

        /**
         * Check if a column index is valid.
         *
         * @param m Matrix.
         * @param column Column index to check.
         * @throws OutOfRangeException if {@code column} is not a valid index.
         */
        public static void checkColumnIndex(final AnyMatrix m, final int column)
            throws OutOfRangeException {
            if (column < 0 || column >= m.getColumnDimension()) {
                throw new OutOfRangeException(LocalizedFormats.COLUMN_INDEX,
                                               column, 0, m.getColumnDimension() - 1);
            }
        }

	public void checkMultiplicationCompatible(final FractionMatrix left, final FractionMatrix right)
			throws DimensionMismatchException {

		if (left.getColumnDimension() != right.getRowDimension()) {
			throw new DimensionMismatchException(left.getColumnDimension(),
					right.getRowDimension());
		}
	}


	public FractionMatrix scalarMultiply(Rational scalar) throws IllegalArgumentException{


		Rational[][] temp = new Rational[rowDimension][colDimension];
		for (int i = 0; i < rowDimension; i++) {
			for (int j = 0; j < colDimension; j++) {
				temp[i][j] = data[i][j].mult(scalar);
			}
		}
		
		return new FractionMatrix(temp);
	}
	
	public FractionMatrix transpose() {
		Rational[][] temp = new Rational[colDimension][rowDimension];
		for (int i = 0; i < colDimension; i++)
			for (int j = 0; j < rowDimension; j++)
				temp[j][i] = data[i][j];
		return new FractionMatrix(temp);
	}
	
    public FractionMatrix copy() {
        return new FractionMatrix(copyOut());
    }
	
    private Rational[][] copyOut() {
        final int nRows = this.getRowDimension();
        final Rational[][] out = new Rational[nRows][this.getColumnDimension()];
        // can't copy 2-d array in one shot, otherwise get row references
        for (int i = 0; i < nRows; i++) {
            System.arraycopy(data[i], 0, out[i], 0, data[i].length);
        }
        return out;
    }
	public RealMatrix getRealMatrix(){
		double[][] temp = new double[rowDimension][colDimension];
		for (int i = 0; i < rowDimension; i++) {
			for (int j = 0; j < colDimension; j++) {
				temp[i][j] = data[i][j].getDouble();
			}
		}
		
		return new Array2DRowRealMatrix(temp);
	}
	public void setRow(final int row, final Rational[] array)
			throws OutOfRangeException, MatrixDimensionMismatchException {
		//MatrixUtils.checkRowIndex(this, row);
		final int nCols = getColumnDimension();
		if (array.length != nCols) {
			throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
		}
		for (int i = 0; i < nCols; ++i) {
			setEntry(row, i, array[i]);
		}
	}
	public void setEntry(final int row, final int column, final Rational value)
			throws OutOfRangeException {
		MatrixUtils.checkMatrixIndex(this, row, column);
		data[row][column] = value;
	}
	public void setEntryVector(final int row, final Rational value)
			throws OutOfRangeException {
		int column = 0;
		MatrixUtils.checkMatrixIndex(this, row, column);
		data[row][column] = value;
	}
	@Override
	public boolean isSquare() {
		if(colDimension == rowDimension){
			return true;
		}else{
			return false;
		}
	}



}

