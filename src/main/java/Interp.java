import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.apache.commons.math3.linear.RealVector;

/**
 * Az Interpreter függvényeket megvalósító osztály.
 * @author prike, johnk
 *
 */
public class Interp  {

/**
 * A map nevű hashmapben tároljuk a létrehozott mátrixokat.
 */
public static Vector<Map<String, FractionMatrix>> blocks = new Vector<>();
public static int block_index = 0;
//public static Map<String, FractionMatrix> map = new HashMap<>();

/**
 * Aritmetikai kifejezések feldolgozását végző függvény.<br>
 * A feldolgozott stringet egy javascript engine-nek adjuk át kiértékelésre.<br>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void a_l_expressions(ParseTree tree){
	
	String str = tree.getChild(0).getText();
	try {
		
		Logicalexpr l = new Logicalexpr(tree);
		
		System.out.println(str + " = " + l.result);
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	
	
	
}

public void r_expressions(ParseTree tree){
	try {
	
	String str = tree.getChild(0).getText();
	
	
	
	Rationalexpr r = new Rationalexpr(tree);
	String result = r.result;
	
	
	
	
	
	System.out.println(str + " = " + result);
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	
}

public String r_expr(ParseTree tree){
	try {
	
	
	Rationalexpr r = new Rationalexpr(tree);
	String result = r.result;
	

	return result;
	} catch (Exception e) {
		throw e;
	}
	
	
	
}


/**
 * Logikai kifejezések feldolgozását végző függvény.<br>
 * A feldolgozott stringet egy javascript engine-nek adjuk át kiértékelésre.<br>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void l_b_expressions(ParseTree tree) {
		
	String str = tree.getChild(0).getText();
	
	String l_exp;
	
	try {
		
		if (str.equals("0")) {
			l_exp = "false";
		}
		else
			l_exp = "true";
		
		
		System.out.println(str + " = " + l_exp);
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	
	
	
}

public String l_b_expr(ParseTree tree) {
	
	String str = tree.getChild(0).getText();
	
	String l_exp;
	
	try {
		
		if (str.equals("0")) {
			l_exp = "False";
		}
		else
			l_exp = "True";
		
		
		return l_exp;
	} catch (Exception e) {
		System.out.println(e.getMessage());
		throw e;
		
	}
	
	
	
}

/**
 * Mátrix kifejezések feldolgozását végző függvény.<br>
 * Az egydimenziós mátrixokat dolgozzuk fel és mentjük el a hashmapben. {@link Interp#map}<br>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void m1_expressions(ParseTree tree){

		String str = tree.getChild(0).getText();
		
		try {
			int s = Integer.parseInt(str.substring(str.indexOf('[')+1,str.indexOf(']')));
			
			Rational[] t = new Rational[s];
			
			String[] m_values = str.substring(str.indexOf('{')+1,str.length()-1).split(",");
			
			for (int i = 0; i < m_values.length; i++) {
				if (Rationalexpr.alfabet.contains(m_values[i])) {
					if (blocks.elementAt(block_index).containsKey(m_values[i])) {
						FractionMatrix m = blocks.elementAt(block_index).get(m_values[i]);
						m_values[i] = String.valueOf(m.getEntry(0, 0));
					}else {
						System.out.println("'" + m_values[i] + "'" + " not declared yet");
					}
				}
			}
			
			
			int j = 0;
			for (int i = 0; i < m_values.length;i++) {
				
				t[j] = new Rational(m_values[i]);
				j++;
			}
			
			
			FractionMatrix m = new FractionMatrix(t);
			
			blocks.elementAt(block_index).put(str.substring(0,str.indexOf('[')), m);
			
			
			
			
		}catch (Exception e) {
			System.out.println("matrix initialization error1");
			System.out.println(e.getMessage());
		}
				
		
	}

/**
 * Mátrix kifejezések feldolgozását végző függvény.<br>
 * A kétdimenziós mátrixokat dolgozzuk fel és mentjük el a hashmapben. {@link Interp#map}<br>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void m2_expressions(ParseTree tree){

	String str = tree.getChild(0).getText();
	
	try {
		
		int s = Integer.parseInt(str.substring(str.indexOf('[')+1,str.indexOf(']')));
		int o = Integer.parseInt(str.substring(str.indexOf('[', str.indexOf('[')+1) + 1,str.indexOf(']', str.indexOf(']') + 1)));
		
		String[] m_values = str.substring(str.indexOf('{')+1,str.length()-1).split(",");
		
		Rational[][] t = new Rational[s][o];
		
		for (int i = 0; i < m_values.length; i++) {
			if (Rationalexpr.alfabet.contains(m_values[i])) {
				if (blocks.elementAt(block_index).containsKey(m_values[i])) {
					FractionMatrix m = blocks.elementAt(block_index).get(m_values[i]);
					m_values[i] = String.valueOf(m.getEntry(0, 0));
				}else {
					System.out.println("'" + m_values[i] + "'" + " not declared yet");
				}
			}
		}
		
		
		int j = 0;
		int k = 0;
		for (int i = 0; i < m_values.length; i++) {
			if (k==o) {
				k = 0;
				j++;
			}
			t[j][k] = new Rational(m_values[i]);
			k++;
			
		}
		
		
		FractionMatrix m = new FractionMatrix(t);
		
		blocks.elementAt(block_index).put(str.substring(0,str.indexOf('[')), m);
		
		
		
	}catch (Exception e) {
		System.out.println("matrix initialization error2");
		System.out.println(e.getMessage());
	}
			

}

/**
 * Megvizsgálja hogy a két összeadandó mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrixösszeadó függvényt. {@link Matrix#add(FractionMatrix, FractionMatrix)} <br>
 * Majd eltároljuk a hashmapünkben a mátrixot amennyiben szeretnénk.
 * <pre>
 * {@code
 * FractionMatrix result = Matrix.add(map.get(m1_name), map.get(m2_name));
 * map.put(str.substring(str.length()-1, str.length()), result);
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void add(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		String result_name = str.substring(0, str.indexOf('='));
		String m1_name = str.substring(str.indexOf('=')+1, str.indexOf('.'));
		String m2_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		if (blocks.elementAt(block_index).containsKey(m1_name)) {
			if (blocks.elementAt(block_index).containsKey(m2_name)) {
				
				FractionMatrix result = Matrix.add(blocks.elementAt(block_index).get(m1_name), blocks.elementAt(block_index).get(m2_name));
				blocks.elementAt(block_index).put(result_name, result);
				System.out.println(result_name + " = " + result);		
			}
			else
				System.out.println("'" + m1_name + "'" + " matrix not declared yet");
			}
		else
			System.out.println("'" + m2_name + "'" + " matrix not declared yet");
		
		
		
	}
	else {
		String m1_name = str.substring(0, str.indexOf('.'));
		String m2_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		if (blocks.elementAt(block_index).containsKey(m1_name)) {
			if (blocks.elementAt(block_index).containsKey(m2_name)) {
			
				System.out.println(Matrix.add(blocks.elementAt(block_index).get(m1_name), blocks.elementAt(block_index).get(m2_name)));
			}
			else
				System.out.println("'" + m1_name + "'" + " matrix not declared yet");
			}
		else
			System.out.println("'" + m2_name + "'" + " matrix not declared yet");
	}

}

/**
 * Megvizsgálja hogy a két kivonandó mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrix kivonást végző függvényt. {@link Matrix#sub(FractionMatrix, FractionMatrix)} <br>
 * Majd eltároljuk a hashmapünkben a mátrixot amennyiben szeretnénk.
 * <pre>
 * {@code
 * FractionMatrix result = Matrix.sub(map.get(m1_name), map.get(m2_name));
 * map.put(str.substring(str.length()-1, str.length()), result);
 * 	
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void sub(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		String result_name = str.substring(0, str.indexOf('='));
		String m1_name = str.substring(str.indexOf('=')+1, str.indexOf('.'));
		String m2_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		if (blocks.elementAt(block_index).containsKey(m1_name)) {
			if (blocks.elementAt(block_index).containsKey(m2_name)) {
				
				FractionMatrix result = Matrix.sub(blocks.elementAt(block_index).get(m1_name), blocks.elementAt(block_index).get(m2_name));
				blocks.elementAt(block_index).put(result_name, result);
				System.out.println(result_name + " = " + result);		
			}
			else
				System.out.println("'" + m1_name + "'" + " matrix not declared yet");
			}
		else
			System.out.println("'" + m2_name + "'" + " matrix not declared yet");
		
		
		
	}
	else {
		String m1_name = str.substring(0, str.indexOf('.'));
		String m2_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		if (blocks.elementAt(block_index).containsKey(m1_name)) {
			if (blocks.elementAt(block_index).containsKey(m2_name)) {
			
				System.out.println(Matrix.sub(blocks.elementAt(block_index).get(m1_name), blocks.elementAt(block_index).get(m2_name)));
			}
			else
				System.out.println("'" + m1_name + "'" + " matrix not declared yet");
			}
		else
			System.out.println("'" + m2_name + "'" + " matrix not declared yet");
	}
	
}

/**
 * Megvizsgálja hogy a két összeszorzandó mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrixokat összeszorzó függvényt. {@link Matrix#multiply(FractionMatrix, FractionMatrix)} <br>
 * Majd eltároljuk a hashmapünkben a mátrixot amennyiben szeretnénk.
 * <pre>
 * {@code
 * FractionMatrix result = Matrix.multiply(map.get(m1_name), map.get(m2_name));
 * map.put(str.substring(str.length()-1, str.length()), result);
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void multiply(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		String result_name = str.substring(0, str.indexOf('='));
		String m1_name = str.substring(str.indexOf('=')+1, str.indexOf('.'));
		String m2_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		if (blocks.elementAt(block_index).containsKey(m1_name)) {
			if (blocks.elementAt(block_index).containsKey(m2_name)) {
				
				FractionMatrix result = Matrix.multiply(blocks.elementAt(block_index).get(m1_name), blocks.elementAt(block_index).get(m2_name));
				blocks.elementAt(block_index).put(result_name, result);
				System.out.println(result_name + " = " + result);		
			}
			else
				System.out.println("'" + m1_name + "'" + " matrix not declared yet");
			}
		else
			System.out.println("'" + m2_name + "'" + " matrix not declared yet");
		
		
		
	}
	else {
		String m1_name = str.substring(0, str.indexOf('.'));
		String m2_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		if (blocks.elementAt(block_index).containsKey(m1_name)) {
			if (blocks.elementAt(block_index).containsKey(m2_name)) {
			
				System.out.println(Matrix.multiply(blocks.elementAt(block_index).get(m1_name), blocks.elementAt(block_index).get(m2_name)));
			}
			else
				System.out.println("'" + m1_name + "'" + " matrix not declared yet");
			}
		else
			System.out.println("'" + m2_name + "'" + " matrix not declared yet");
	}	
}

/**
 * Megvizsgálja hogy a mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrix determinánsát kiszámoló függvényt. {@link Matrix#det(FractionMatrix)} <br>
 * A determináns értékét nem tároljuk el csak kiiratjuk.
 * <pre>
 * {@code
 * Matrix.det(map.get(m_name))
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void det(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		String result_name = str.substring(0, str.indexOf('='));
		String m_name = str.substring(str.indexOf('=')+1, str.indexOf('.'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
			
				Rational temp = Matrix.det(blocks.elementAt(block_index).get(m_name));
				FractionMatrix result = new FractionMatrix(temp);
				blocks.elementAt(block_index).put(result_name, result);
				System.out.println(result_name + " = " + result);		
			
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
		
	}
	else {
		String m_name = str.substring(0, str.indexOf('.'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
		
			System.out.println(Matrix.det(blocks.elementAt(block_index).get(m_name)));
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
	}

}

/**
 * Megvizsgálja hogy a mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrix transzponáltját kiszámoló függvényt. {@link Matrix#transpose(FractionMatrix)} <br>
 * Majd eltároljuk a hashmapünkben a mátrixot amennyiben szeretnénk.
 * <pre>
 * {@code
 * FractionMatrix result = Matrix.transpose(map.get(m_name));
 * map.put(str.substring(str.length()-1, str.length()), result);
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void transpose(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		String result_name = str.substring(0, str.indexOf('='));
		String m_name = str.substring(str.indexOf('=')+1, str.indexOf('.'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
			
				FractionMatrix result = Matrix.transpose(blocks.elementAt(block_index).get(m_name));
				blocks.elementAt(block_index).put(result_name, result);
				System.out.println(result_name + " = " + result);		
			
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
		
	}
	else {
		String m_name = str.substring(0, str.indexOf('.'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
		
			System.out.println(Matrix.transpose(blocks.elementAt(block_index).get(m_name)));
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
	}


}

/**
 * Megvizsgálja hogy a mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrix skalárszorosát kiszámoló függvényt. {@link Matrix#scalar(FractionMatrix, double)} <br>
 * Majd eltároljuk a hashmapünkben a mátrixot amennyiben szeretnénk.
 * <pre>
 * {@code
 * FractionMatrix result = Matrix.scalar(map.get(m_name), scalar_name);
 * map.put(str.substring(str.length()-1, str.length()), result);
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void scalar(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		String result_name = str.substring(0, str.indexOf('='));
		String m_name = str.substring(str.indexOf('=')+1, str.indexOf('.'));
		String scalar_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
			
			if (Rationalexpr.alfabet.contains(scalar_name)) {
				if (blocks.elementAt(block_index).containsKey(scalar_name)) {
					FractionMatrix m = blocks.elementAt(block_index).get(scalar_name);
					FractionMatrix result = Matrix.scalar(blocks.elementAt(block_index).get(m_name), m);
					blocks.elementAt(block_index).put(result_name, result);
					System.out.println(result_name + " = " + result);
				}else {
					System.out.println("'" + scalar_name + "'" + " matrix not declared yet");
				}
				
			}else {
				Rational[] t = new Rational[1];
				t[0] = new Rational(scalar_name);
				FractionMatrix m = new FractionMatrix(t);
				FractionMatrix result = Matrix.scalar(blocks.elementAt(block_index).get(m_name), m);
				blocks.elementAt(block_index).put(result_name, result);
				System.out.println(result_name + " = " + result);
			}
			
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
		
	}
	else {
		String m_name = str.substring(0, str.indexOf('.'));
		String scalar_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
			
			if (Rationalexpr.alfabet.contains(scalar_name)) {
				if (blocks.elementAt(block_index).containsKey(scalar_name)) {
					FractionMatrix m = blocks.elementAt(block_index).get(scalar_name);
					FractionMatrix result = Matrix.scalar(blocks.elementAt(block_index).get(m_name), m);
					System.out.println(result);
					
				}else {
					System.out.println("'" + scalar_name + "'" + " matrix not declared yet");
				}
				
			}else {
				Rational[] t = new Rational[1];
				t[0] = new Rational(scalar_name);
				FractionMatrix m = new FractionMatrix(t);
				FractionMatrix result = Matrix.scalar(blocks.elementAt(block_index).get(m_name), m);
				System.out.println(result);
			}
			
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
	}
		
}
/**
 * Megvizsgálja hogy a mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrix sajátérték, sajátvektorát kiszámoló függvényt. {@link Matrix#EigenDecomp(FractionMatrix)} <br>
 * A mátrix sajátértékét és sajátvektorát nem tároljuk el csak kiiratjuk.
 * <pre>
 * {@code
 * Matrix.EigenDecomp(map.get(m_name));
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void eigendecomp(ParseTree tree){
	String str = tree.getChild(0).getText();

	String m_name = str.substring(0, str.indexOf('.'));

	if (blocks.elementAt(block_index).containsKey(m_name)) {
		
		Matrix.EigenDecomp(blocks.elementAt(block_index).get(m_name));
	}
	else
		System.out.println("'" + m_name + "'" + " matrix not declared yet");
	
	
}

/**
 * Megvizsgálja hogy a mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrix inverzét kiszámoló függvényt. {@link Matrix#inverse(FractionMatrix)} <br>
 * Majd eltároljuk a hashmapünkben a mátrixot amennyiben szeretnénk.
 * <pre>
 * {@code
 * FractionMatrix result = Matrix.inverse(map.get(m_name));
 * map.put(str.substring(str.length()-1, str.length()), result);
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void inverse(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		String result_name = str.substring(0, str.indexOf('='));
		String m_name = str.substring(str.indexOf('=')+1, str.indexOf('.'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
			
				FractionMatrix result = Matrix.inverse(blocks.elementAt(block_index).get(m_name));
				blocks.elementAt(block_index).put(result_name, result);
				System.out.println(result_name + " = " + result);		
			
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
		
	}
	else {
		String m_name = str.substring(0, str.indexOf('.'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
		
			System.out.println(Matrix.inverse(blocks.elementAt(block_index).get(m_name)));
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
	}
	
}

/**
 * Megvizsgálja hogy a mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja az egyenletrendszert megoldó függvényt. {@link Matrix#LinearSystemSolver(FractionMatrix, RealVector)} <br>
 * Az eredményt nem tároljuk el csak kiiratjuk.
 * <pre>
 * {@code
 * Matrix.LinearSystemSolver(map.get(m_name), v)
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void lss(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	try {
		String m_name = str.substring(0, str.indexOf('.'));
		String v_name = str.substring(str.indexOf('(')+1, str.indexOf(')'));
		
		//System.out.println(blocks.elementAt(block_index).get(v_name));
		
		FractionMatrix v = blocks.elementAt(block_index).get(v_name).getColumnVector(0);
		
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
			if (blocks.elementAt(block_index).containsKey(v_name)) {
				System.out.println(Matrix.LinearSystemSolver(blocks.elementAt(block_index).get(m_name), v));
			}
			else
				System.out.println("'" + v_name + "'" + " vector not declared yet");
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
		
		
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	
}

/**
 * Megvizsgálja hogy a mátrix létezik e már a rendszerunkben. <br>
 * Ha igen meghivja a mátrixra a gauss eliminációt elvégző függvényt. {@link Matrix#gaussdecomp(FractionMatrix)} <br>
 * Majd eltároljuk a hashmapünkben a mátrixot amennyiben szeretnénk.
 * <pre>
 * {@code
 * FractionMatrix result = Matrix.gaussdecomp(map.get(m_name));
 * map.put(str.substring(str.length()-1, str.length()), result);
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void gauss(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		String result_name = str.substring(0, str.indexOf('='));
		String m_name = str.substring(str.indexOf('=')+1, str.indexOf('.'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
			
				FractionMatrix result = Matrix.gaussdecomp(blocks.elementAt(block_index).get(m_name));
				blocks.elementAt(block_index).put(result_name, result);
				System.out.println(result_name + " = " + result);		
			
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
		
	}
	else {
		String m_name = str.substring(0, str.indexOf('.'));
		
		if (blocks.elementAt(block_index).containsKey(m_name)) {
		
			System.out.println(Matrix.gaussdecomp(blocks.elementAt(block_index).get(m_name)));
			}
		else
			System.out.println("'" + m_name + "'" + " matrix not declared yet");
	}
}

/**
 * Mátrixot kiirató függvény.
 * <pre>
 * {@code
 * for (int i = 0; i < m.getRowDimension(); i++) {
 *	for (int j = 0; j < m.getRow(i).length; j++) {
 *	  System.out.print(m.getRow(i)[j] + " ");
 *	}
 *	  System.out.println();
 * }
 * }
 * </pre>
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void print(ParseTree tree){
	String str = tree.getChild(0).getText();
	
	if (blocks.elementAt(block_index).containsKey(str)) {
		
		FractionMatrix m = blocks.elementAt(block_index).get(str);
		
		System.out.println(m);
		
	}
	else
		System.out.println("'" + str + "'" + " matrix not declared yet");
	
}

/**
 * Az aktív mátrixokat kiirató függvény.<br>
 * Aktív alatt azokat a mátrixokat értjük amelyek bennevannak a hasmapben. {@link Interp#map} <br>
 * <pre>
 * {@code
 * for (Map.Entry entry : map.entrySet()) {
 * 	System.out.println(entry.getKey() + ", " + entry.getValue());
 * }
 * }
 * </pre>
 */
public void active(){
	for (Map.Entry entry : blocks.elementAt(block_index).entrySet()) {
	    System.out.println(entry.getKey() + ": " + entry.getValue());
	}
}

public void str_to_matrix(String name,String str){
	try {
		FractionMatrix m;

		
		if (str.indexOf('/') != -1) {
			BigInteger num = new BigInteger(str.substring(0, str.indexOf('/')));
			BigInteger den = new BigInteger(str.substring(str.indexOf('/')+1, str.length()));
			Rational res = new Rational(num, den);

			m = new FractionMatrix(res);
			blocks.elementAt(block_index).put(name, m);
		}
		else {
			m = new FractionMatrix(new Rational(str));
			blocks.elementAt(block_index).put(name, m);
		}
		
		
	} catch (Exception e) {
		throw e;
	}
	
	
	
}


public void var_expr(ParseTree tree) {
	String str = tree.getChild(0).getText();
	
	if (str.indexOf('=') != -1) {
		ParseTree expr = tree.getChild(0).getChild(2);
		String v_name = str.substring(0, str.indexOf('='));
		
		
		//System.out.println(v_name);
		//System.out.println(expr.getText());
		
		String result = r_expr(expr);
		
		str_to_matrix(v_name, result);
		//System.out.println(v_name + " = " + result);
	}else {
		
		String result = r_expr(tree);
		
		System.out.println(str + " = " + result);
	}
	
	
	
	
}

public void eq_r(ParseTree tree) {
	String str = tree.getChild(0).getText();
	String v_name = str.substring(0, str.indexOf('='));
	String value = str.substring(str.indexOf('=')+1, str.length());
	
	if (Rationalexpr.alfabet.contains(value)) {
		
		if (blocks.elementAt(block_index).containsKey(value)) {
				
			blocks.elementAt(block_index).put(v_name, blocks.elementAt(block_index).get(value));
			}
		else
			System.out.println("'" + value + "'" + " not declared yet");
		
	}else {
		if (value.indexOf('/') != -1) {
			BigInteger num = new BigInteger(value.substring(0, value.indexOf('/')));
			BigInteger den = new BigInteger(value.substring(value.indexOf('/')+1, value.length()));
			Rational r = new Rational(num,den);
			

			FractionMatrix m = new FractionMatrix(r);
			blocks.elementAt(block_index).put(v_name, m);
			
		}else {
			Rational r = new Rational(value);
			FractionMatrix m = new FractionMatrix(r);
			blocks.elementAt(block_index).put(v_name, m);
		}
		
	}
	
}

public void block_copy_back(){
	for (Map.Entry entry : blocks.elementAt(block_index).entrySet()) {
	    if (blocks.elementAt(block_index-1).containsKey(entry.getKey().toString())) {
			blocks.elementAt(block_index-1).put(entry.getKey().toString(),(FractionMatrix)entry.getValue());
		}
	}
	
	blocks.remove(block_index);
	
	Interp.block_index -= 1;
}
public void block_copy_to(){
	Interp.block_index += 1;
	
	for (Map.Entry entry : blocks.elementAt(block_index-1).entrySet()) {
	   
		blocks.elementAt(block_index).put(entry.getKey().toString(),(FractionMatrix)entry.getValue());
		
	}
	
}

public void if_expr(ParseTree tree) {
	String str = tree.getChild(0).getText();
	ParseTree logical_tree = tree.getChild(0).getChild(2);
	String l;
	if (logical_tree.getChild(0).getChildCount() == 0) {
		l = l_b_expr(logical_tree);
	}else {
		Logicalexpr lo = new Logicalexpr(logical_tree);
		l = lo.result;
	}
	
	Interp interp = new Interp();
	
	String inputString;
	ANTLRInputStream in;
	fel8Lexer lex;
	CommonTokenStream tok;
	fel8Parser pars;
	ParseTree iftree;
	boolean els;
	boolean e = true;
	Map<String, FractionMatrix> hmap = new HashMap<>();
	blocks.addElement(hmap);
	block_copy_to();
	
	if(true){
		
		while (true) {
			Scanner sc = new Scanner(System.in);
			inputString = sc.nextLine();
			in = new ANTLRInputStream(inputString);
			lex = new fel8Lexer(in);
			tok = new CommonTokenStream(lex);
			pars = new fel8Parser(tok);
			iftree = pars.r();
			
			if (iftree.getChild(0).getText().equals("}else{") && l.equals("True")) {
				els = false;
				sc = null;
				break;
			}
			if (iftree.getChild(0).getText().equals("}else{") && l.equals("False")) {
				els = true;
				sc = null;
				break;
			}
			
			if (iftree.getChild(0).getText().equals("}")) {
				els = false;
				e = false;
				sc = null;
				break;
			}

			if (pars.getNumberOfSyntaxErrors() == 0 && l.equals("True")) {
				
					interp.interp(pars, iftree);
			}
			
		
		}
	}
	if (e){
		//System.out.println("else ág");
		while (true) {
			Scanner sc = new Scanner(System.in);
			inputString = sc.nextLine();
			in = new ANTLRInputStream(inputString);
			lex = new fel8Lexer(in);
			tok = new CommonTokenStream(lex);
			pars = new fel8Parser(tok);
			iftree = pars.r();
			
			if (pars.getNumberOfSyntaxErrors() == 0 && els) {
				
					interp.interp(pars, iftree);
			}
			if (iftree.getChild(0).getText().equals("}")) {
				sc = null;
				break;
			}
		}
	}
	
	block_copy_back();
	
}

public void while_expr(ParseTree tree){
	String str = tree.getChild(0).getText();
	ParseTree logical_tree = tree.getChild(0).getChild(2);
	String l;
	if (logical_tree.getChild(0).getChildCount() == 0) {
		l = l_b_expr(logical_tree);
	}else {
		Logicalexpr lo = new Logicalexpr(logical_tree);
		l = lo.result;
	}
	
	Interp interp = new Interp();
	Scanner sc = new Scanner(System.in);
	String inputString;
	ANTLRInputStream in;
	fel8Lexer lex;
	CommonTokenStream tok;
	fel8Parser pars;
	ParseTree iftree;
	boolean els;
	boolean e = true;
	Map<String, FractionMatrix> hmap = new HashMap<>();
	blocks.addElement(hmap);
	Vector<String> vec = new Vector<>();
	int counter = 0;
	block_copy_to();
	
	
	while(true){
		inputString = sc.nextLine();
		
		
		
		if(inputString.equals("}")){
			break;
		}
		vec.add(inputString);
		
	}
	
	while (true) {
		
		for (int i = 0; i < vec.size(); i++) {
			in = new ANTLRInputStream(vec.elementAt(i));
			lex = new fel8Lexer(in);
			tok = new CommonTokenStream(lex);
			pars = new fel8Parser(tok);
			iftree = pars.r();
			
			if (pars.getNumberOfSyntaxErrors() == 0) {
				
				interp.interp(pars, iftree);
			}
			Logicalexpr lo = new Logicalexpr(logical_tree);
			l = lo.result;
		}
		
		if (l.equals("False")) {
			break;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	block_copy_back();
	
	
}
/**
 * Megvizsgálja melyik interpreter függvényt kell meghívni majd meghívja.
 * @param parser Az antlr által elkészitett parser.
 * @param tree A fa amit az antlr generált és amiből a feldolgozandó stringet kapjuk.
 */
public void interp(fel8Parser parser,ParseTree tree){
	//System.out.println("intpreter");
	Map<String, FractionMatrix> hmap = new HashMap<>();
	blocks.addElement(hmap);
	
	
	if (tree.toStringTree(parser).indexOf("if_expr") != -1) {
		//System.out.println("if");while_expr
		if_expr(tree);
	}
	else if (tree.toStringTree(parser).indexOf("while_expr") != -1) {
		//System.out.println("l_exp_b");
		while_expr(tree);
	}
	else if (tree.toStringTree(parser).indexOf("l_exp_b") != -1) {
		//System.out.println("l_exp_b");
		l_b_expressions(tree);
	}
	else if (tree.toStringTree(parser).indexOf("l_exp") != -1) {
		//System.out.println("l_exp");
		a_l_expressions(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m1_exp") != -1) {
		//System.out.println("m1");
		m1_expressions(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m2_exp") != -1) {
		//System.out.println("m2");
		m2_expressions(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_add") != -1) {
		//System.out.println("add");
		add(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_sub") != -1) {
		//System.out.println("sub");
		sub(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_mult") != -1) {
		//System.out.println("mult");
		multiply(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_det") != -1) {
		//System.out.println("det");
		det(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_transp") != -1) {
		//System.out.println("trans");
		transpose(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_scalar") != -1) {
		//System.out.println("scalar");
		scalar(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_eigendecomp") != -1) {
		//System.out.println("eigen");
		eigendecomp(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_inverse") != -1) {
		//System.out.println("inverse");
		inverse(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_lss") != -1) {
		//System.out.println("lss");
		lss(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_gauss") != -1) {
		//System.out.println("gauss");
		gauss(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_print") != -1) {
		//System.out.println("print");
		print(tree);
	}
	else if (tree.toStringTree(parser).indexOf("m_active") != -1) {
		active();
	}
	else if (tree.toStringTree(parser).indexOf("var") != -1) {
		//System.out.println("var");
		var_expr(tree);
	}
	else if (tree.toStringTree(parser).indexOf("r_exp") != -1) {
		//System.out.println("r_exp");
		r_expressions(tree);
	}
	else if (tree.toStringTree(parser).indexOf("eq") != -1) {
		//System.out.println("eq");
		eq_r(tree);
	}
	
 }

}
