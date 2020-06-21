import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Mátrix műveleteket megvalósító osztály.
 * @author prike, johnk
 *
 */
public class Matrix {
	/**
	 * Kiszámolja az összegét az m1 és m2 mátrixnak. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m1 Az első mátrix.
	 * @param m2 A masodik mátrix.
 	 * @return m1 + m2
	 */
	public static FractionMatrix add(FractionMatrix m1, FractionMatrix m2){
		try {
			return m1.add(m2);
		} catch (Exception e) {
			throw e;
		}
	
	}
	/**
	 * Kiszámolja a különbségét az m1 és m2 mátrixnak. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m1 Az első mátrix.
	 * @param m2 A második (kivonandó) mátrix.
 	 * @return m1 - m2
	 */
	public static FractionMatrix sub(FractionMatrix m1, FractionMatrix m2){
		try {
			return m1.subtract(m2);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Kiszámolja a szorzatát az m1 és m2 mátrixnak. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m1 Az első mátrix.
	 * @param m2 A második mátrix.
 	 * @return m1 * m2
	 */
	public static FractionMatrix multiply(FractionMatrix m1, FractionMatrix m2){
		try {
			if (m1.getColumnDimension()==1) {
				m1 = m1.transpose();
			}
			return m1.multiply(m2);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Kiszámolja az m mátrix inverzét. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m Az invertálandó mátrix.
 	 * @return Az m mátrix inverze.
	 */
	public static FractionMatrix inverse(FractionMatrix m){
		
		try {
			RealMatrix r = new LUDecomposition(m.getRealMatrix()).getSolver().getInverse();
			Rational[][] temp = new Rational[r.getRowDimension()][r.getColumnDimension()]; 
			
			for (int i = 0; i < r.getRowDimension(); i++) {
				for (int j = 0; j < r.getColumnDimension(); j++) {
					temp[i][j] = new Rational(String.valueOf(r.getEntry(i, j)));
					
				}
				
			}
		return new FractionMatrix(temp);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Kiszámolja az m mátrix determinánsát. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m A mátrix aminek számoljuk a determinánsát.
 	 * @return A double determináns.
	 */
	public static Rational det(FractionMatrix m) {
		
		
		try {
			//double r = new LUDecomposition(m.getRealMatrix()).getDeterminant();
			
			//FractionMatrix result = new FractionMatrix(new Rational(String.valueOf(r)));
			
			Rational result = m.determinant(m.getData(), m.getColumnDimension());
			return result;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Kiszámolja az m mátrix transzponáltját. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m A mátrix aminek számoljuk a transzponáltját.
 	 * @return a matrix transzponáltja.
	 */
	public static FractionMatrix transpose(FractionMatrix m){
		try {
			return m.transpose();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Megszorozza az m mátrixot egy s skalárral. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m A mátrix amit szorzunk skalárral.
	 * @param s a skalár.
 	 * @return m * s
	 */
	public static FractionMatrix scalar(FractionMatrix m, FractionMatrix s){
		try {
			if (s.getColumnDimension() == 1 && s.getRowDimension()==1) {
				Rational scalar = s.getEntry(0, 0);
				return m.scalarMultiply(scalar);
			}
			else {
				throw new RuntimeException("only 1x1 matrix allowed as scalar variable");
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * A gauss-eliminációt megvalósitő függvény. <br>
	 * Ha a főátlóban nulla található akkor sorcserét végez. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m A mátrix amit gauss-eliminálunk.
 	 * @return m a gauss eliminált matrix.
	 */
	public static FractionMatrix gaussdecomp(FractionMatrix m){
		
		try {
			FractionMatrix result = m.copy();
			Rational[] row = new Rational[result.getColumnDimension()];
			Rational mult = new Rational(BigInteger.ZERO, BigInteger.ONE);
			
			for (int k = 0; k < result.getRowDimension(); k++) {
				
				for (int i = 1; i < result.getRowDimension(); i++) {
					
					if (i>k) {
						
						mult = result.getEntry(i, k).div(result.getEntry(k, k));
						
						for (int j = 0; j < result.getColumnDimension(); j++) {
							
							//System.out.println("mult: " + mult);
							row[j] = result.getEntry(i, j).sub((result.getEntry(k, j).mult(mult)));
							//System.out.println("row[j]: " + row[j]);
							
						}
						
						result.setRow(i, row);
					}
				}
			}
			return result;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * A megadott mátrix sajátértékeit és sajátvektorait írja ki. <br>
	 * Nem csak a valós hanem a komplex számok halmazán is működik. <br>
	 * Ha kivétel generálódik azt továbbdobjuk.  <br>
	 * @param m A mátrix aminek a sajátértékeit és sajátvektorait írjuk ki.
	 */
	public static void EigenDecomp(FractionMatrix m){
		RealMatrix real = m.getRealMatrix();
		
		
		try {
			
			if (m.getColumnDimension() == 2 && m.getRowDimension() == 2) {
				Rational a,b,c;
				FractionMatrix tmp = new FractionMatrix(m.determinant(m.getData(), m.getColumnDimension()));
				a = new Rational(BigInteger.ONE,BigInteger.ONE);
				b = (m.getColumn(0)[0].plus(m.getColumn(1)[1]) ).mult( new Rational(BigInteger.ONE.negate(),BigInteger.ONE)) ;
				c = tmp.getEntry(0, 0);
				
				Rational d = b.pow(2).sub(( a.mult(c.mult(new Rational("4/1"))) ))  ;
				
				Rational x1 =  (b.minus() .plus(d.sqrt())) .div (a.mult(new Rational("2"))) ;
				Rational x2 = (b.minus() .sub(d.sqrt())) .div (a.mult(new Rational("2")));
				

				
				FractionMatrix m1 = m.copy();
				FractionMatrix m2 = m.copy();
				
				m1.setEntry(0, 0, m.getEntry(0, 0).sub(x1));
				m1.setEntry(1, 1, m.getEntry(1, 1).sub(x1));
				m2.setEntry(0, 0, m.getEntry(0, 0).sub(x2));
				m2.setEntry(1, 1, m.getEntry(1, 1).sub(x2));
				
				m1 = Matrix.gaussdecomp(m1);
				m2 = Matrix.gaussdecomp(m2);
				

				
				Rational[] v1 = new Rational[2];
				Rational[] v2 = new Rational[2];
				
				if (m1.getEntry(1, 1).getDouble() == 0 ) {
					v1[0] = (m1.getEntry(0, 1).mult(new Rational(BigInteger.ONE.negate(),BigInteger.ONE)) ) .div(m1.getEntry(0, 0)) ;
					v1[1] = new Rational(BigInteger.ONE,BigInteger.ONE);
					
				}
				else {
					System.out.println("else1 szar"+ m1.getEntry(1, 1).getDouble());
					
					v1[0] = new Rational(BigInteger.ZERO,BigInteger.ONE);
					v1[1] = new Rational(BigInteger.ZERO,BigInteger.ONE);
				}
			
				if (m2.getEntry(1, 1).getDouble() == 0) {
					v2[0] = (m2.getEntry(0, 1) .mult(new Rational(BigInteger.ONE.negate(),BigInteger.ONE)) ) .div(m2.getEntry(0, 0)) ;
					v2[1] = new Rational(BigInteger.ONE,BigInteger.ONE);
					
				}
				else {
					v2[0] = new Rational(BigInteger.ZERO,BigInteger.ONE);
					v2[1] = new Rational(BigInteger.ZERO,BigInteger.ONE);
					System.out.println("else2 szar");
				}
				
				System.out.println(x1 + " t*("  + v1[0] + ", " + v1[1] + ")");
				System.out.println(x2 + " t*("  + v2[0] + ", " + v2[1] + ")");
				
				//System.out.println(LinearSystemSolver(m1, v));
				//System.out.println(LinearSystemSolver(m2, v));
				
				
			}else{//ha nem 2x2es
				double [] se = new EigenDecomposition(real).getRealEigenvalues();
				//double [] si = new EigenDecomposition(m).getImagEigenvalues();
				RealVector sv;
				//RealVector svi;
				
				for (int i = 0; i < se.length; i++) {
					sv = new EigenDecomposition(real).getEigenvector(i);
					System.out.print(se[i] + " " + sv);
					System.out.println('\n');
				}
				
			}
			
			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	private static RealMatrix gaussdecomp(RealMatrix m) {
		
		try {
			RealMatrix result = m.copy();
			double[] row = new double[result.getColumnDimension()];
			double mult = 0;
			
			for (int k = 0; k < result.getRowDimension(); k++) {
				
				for (int i = 1; i < result.getRowDimension(); i++) {
					
					if (i>k) {
						
						mult = result.getEntry(i, k) / result.getEntry(k, k);
						
						for (int j = 0; j < result.getColumnDimension(); j++) {
							
							//System.out.println("mult: " + mult);
							row[j] = result.getEntry(i, j) - result.getEntry(k, j) * mult;
							//System.out.println("row[j]: " + row[j]);
							
						}
						
						result.setRow(i, row);
					}
				}
			}
			return result;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * A megadott m mátrix és a hozzátartozó v vektornak számítja ki az ismeretleneit. <br>
	 * Ha kivétel generálódik azt továbbdobjuk. <br>
	 * @param m A mátrix az egyenletrendszerben.
	 * @param v A mátrixhoz tartozó vektor.
	 * @return solution egy vektorban adjuk vissza a megoldásokat.
	 */
	public static FractionMatrix LinearSystemSolver(FractionMatrix m, FractionMatrix v){
		try {
			
			//////////////////////
			FractionMatrix matrix = m.copy();
			FractionMatrix vector = v.copy();
			
			Rational[][] tmp = new Rational[matrix.getRowDimension()][matrix.getColumnDimension()+1];
			
			int mRows = matrix.getRowDimension();
			int mCols = matrix.getColumnDimension();
			
			for (int i = 0; i < mRows; i++) {
				for (int j = 0; j < mCols; j++) {
					tmp[i][j] = matrix.getEntry(i, j);
				}
			}
			
			for (int i = 0; i < vector.getRowDimension(); i++) {
				tmp[i][m.getColumnDimension()] = vector.getEntry(i, 0); //Row x Cols

			}
			
			
			
			FractionMatrix m2 = new FractionMatrix(tmp);
			
			for (int i = 0; i < mRows+1; i++) {
				for (int j = 0; j < mCols+1; j++) {
					//tmp[i][j] = matrix.getEntry(i, j);
					//System.out.println(tmp[i][j]);
				}
				//System.out.println();
			}
			

			m2 = Matrix.gaussdecomp(m2);
			
			for (int i = 0; i < m2.getRowDimension(); i++) {
				for (int j = 0; j < m2.getColumnDimension(); j++) {
					//System.out.println(m2.getEntry(i, j));
				}
			}
			
			 Rational[] x = new Rational[vector.getRowDimension()];
		     for (int i = vector.getRowDimension() - 1; i >= 0; i--) {
		    	 Rational sum = new Rational(BigInteger.ZERO,BigInteger.ONE);
		         	for (int j = i + 1; j < vector.getRowDimension(); j++) {
		         		sum = sum.plus(m2.getEntry(i,j).mult(x[j]));
		            }
		         	x[i] = (m2.getEntry(i, vector.getRowDimension()).sub(sum) ).div(m2.getEntry(i, i)) ;
		     }
		     
	
			return new FractionMatrix(x);
		} catch (Exception e) {
			throw e;
		}
	}
}