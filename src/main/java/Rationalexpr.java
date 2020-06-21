import java.math.BigInteger;
import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

public class Rationalexpr {
	String result = "";
	static String alfabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	Vector v = new Vector<>();
	Vector itemsToRemove = new Vector();
	public Rationalexpr(ParseTree tree){
		visit(tree);
		test();
		rational(v);
	}
	
	
	
	public void visit(ParseTree tree){
		int c = tree.getChildCount();
		
		if (tree.getChildCount()==0) {
			//System.out.println(tree.getText());
			v.addElement(tree.getText());
		}
		
		for (int i = 0; i < c; i++) {
			visit(tree.getChild(i));
			
		}
		
	}
	
	public void test(){
		boolean bool = false;
		for (int i = 0; i < v.size(); i++) {
			if (v.elementAt(i).toString().equals("(")) {
				bool = true;
			}
		}
		if (!bool) {
			v.insertElementAt('(', 0);
			v.insertElementAt(')', v.size());
		}
	}
	
	public String matrix_to_str(FractionMatrix m){
		try {
			String value = String.valueOf(m.getEntry(0, 0));
			if (value.equals("0.0")) {
				//System.out.println("0egy");
				return "0";
			}
			else {
				//System.out.println(value);
				int v1 = Integer.parseInt(value.substring(0,value.indexOf('.')));
				String str_v2 = value.substring(value.indexOf('.')+1,value.length());
				int v2 = Integer.parseInt(value.substring(value.indexOf('.')+1,value.length()));
				
				int k = str_v2.length();
				int d = (int) Math.pow(10, k);
				
				int num = v1 * d + v2;
				int den = d;
				
				//System.out.println(num);
				//System.out.println(den);
				
				String s_num = String.valueOf(num);
				String d_num = String.valueOf(den);
				
				Rational r = new Rational(new BigInteger(s_num), new BigInteger(d_num));
				
				
				
				return r.toString();
			}
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	public void rational(Vector v){
		int start = 0;
		int end = 0;
		
		for (int i = 0; i < v.size(); i++) {
			//System.out.println(v.elementAt(i));
			if (v.elementAt(i).equals("(")) {
				start = i;
				//System.out.println("s " + start);
			}
			if (v.elementAt(i).equals(")")) {
				end = i;
				//System.out.println("end " + end);
				break;
			}
			
		}
		/*
		for (int i = 0; i < v.size(); i++) {
			System.out.println(v.elementAt(i));
		}
		System.out.println("///////////////");
		*/
		for (int j = start; j <= end; j++) {
			//System.out.println(v.elementAt(j));
			itemsToRemove.addElement(v.elementAt(j));
		}
		
		
		String num1 = "";
		String den1 = "";
		String num2 = "";
		String den2 = "";
		String op = "";
		
		
		op = (String) v.elementAt(start+2);
		//System.out.println(op);
		
		if (op.equals("^")) {
			
			if (alfabet.contains(v.elementAt(start+1).toString())) {
				FractionMatrix m = Interp.blocks.elementAt(Interp.block_index).get(v.elementAt(start+1).toString());
				String exp = matrix_to_str(m);
				//System.out.println("asd");
				v.setElementAt(exp, start+1);
				//System.out.println(exp);
				
			}
			if (alfabet.contains(v.elementAt(start+3).toString())) {
				FractionMatrix m = Interp.blocks.elementAt(Interp.block_index).get(v.elementAt(start+3).toString());
				String exp = matrix_to_str(m);
				//System.out.println("asd");
				v.setElementAt(exp, start+3);
				
			}
			
			
			if (v.elementAt(start+1).toString().indexOf('/') != -1 ) {
				
				num1 = v.elementAt(start+1).toString().substring(0, v.elementAt(start+1).toString().indexOf('/'));
				den1 = v.elementAt(start+1).toString().substring(v.elementAt(start+1).toString().indexOf('/')+1, v.elementAt(start+1).toString().length());
			}
			else {
				
				int number = Integer.parseInt(v.elementAt(start+1).toString());
				if (number == 0) {
					num1 = "0";
					den1 = "1";
				}
				else {
					
					if (v.elementAt(start+1).toString().indexOf('-') != -1) {
						num1 = Integer.toString(number*number);
						num1 = "-" + num1;
						den1 = Integer.toString(number);
						den1 = den1.substring(1, den1.length());
					}else {
						num1 = Integer.toString(number*number);
						den1 = Integer.toString(number);
					}
	
				}
			}
			
			if (v.elementAt(start+3).toString().indexOf('/') == -1 ) {
				int n = Integer.parseInt(v.elementAt(start+3).toString().substring(0, v.elementAt(start+3).toString().length()));
				Rational a = new Rational(new BigInteger(num1), new BigInteger(den1));
				
				a = a.pow(n);
				

				for (int i = start; i <= end; i++) {
					v.remove(start);
				}
				
				/*
				for (int i = 0; i < v.size(); i++) {
					System.out.println(v.elementAt(i));
				}
				
				System.out.println("///////////////");
				*/
				
				
				v.insertElementAt(a.toString(), start);
				
				/*
				for (int i = 0; i < v.size(); i++) {
					System.out.println(v.elementAt(i));
				}
				*/
				for (int i = 0; i < v.size(); i++) {
					if (v.elementAt(i).toString().indexOf('(') != -1) {
						rational(v);
					}
				}
				
				
				result = v.elementAt(0).toString();
			}
			else {
				throw new RuntimeException("tört kitevő");
			}
			
			
			
		}else {
			
			if (alfabet.contains(v.elementAt(start+1).toString())) {
				FractionMatrix m = Interp.blocks.elementAt(Interp.block_index).get(v.elementAt(start+1).toString());
				String exp = matrix_to_str(m);
				//System.out.println("asd");
				v.setElementAt(exp, start+1);
				//System.out.println(exp);
				
			}
			if (alfabet.contains(v.elementAt(start+3).toString())) {
				FractionMatrix m = Interp.blocks.elementAt(Interp.block_index).get(v.elementAt(start+3).toString());
				String exp = matrix_to_str(m);
				//System.out.println("asd");
				v.setElementAt(exp, start+3);
				
			}
			
			
			
			if (v.elementAt(start+1).toString().indexOf('/') != -1 ) {
				num1 = v.elementAt(start+1).toString().substring(0, v.elementAt(start+1).toString().indexOf('/'));
				den1 = v.elementAt(start+1).toString().substring(v.elementAt(start+1).toString().indexOf('/')+1, v.elementAt(start+1).toString().length());
			}
			else {
				int number = Integer.parseInt(v.elementAt(start+1).toString());
				
				if (number == 0) {
					num1 = Integer.toString(0);
					den1 = Integer.toString(1);
				}
				else {
					
					if (v.elementAt(start+1).toString().indexOf('-') != -1) {
						num1 = Integer.toString(number*number);
						num1 = "-" + num1;
						den1 = Integer.toString(number);
						den1 = den1.substring(1, den1.length());
					}else {
						num1 = Integer.toString(number*number);
						den1 = Integer.toString(number);
					}
				
				}
			}
			
			if (v.elementAt(start+3).toString().indexOf('/') != -1 ) {
				num2 = v.elementAt(start+3).toString().substring(0, v.elementAt(start+3).toString().indexOf('/'));
				den2 = v.elementAt(start+3).toString().substring(v.elementAt(start+3).toString().indexOf('/')+1, v.elementAt(start+3).toString().length());
			}
			else {
				//System.out.println(v.elementAt(start+3).toString());
				int number = Integer.parseInt(v.elementAt(start+3).toString());
				if (number == 0) {
					num2 = Integer.toString(0);
					den2 = Integer.toString(1);
				}
				else {
					
					if (v.elementAt(start+3).toString().indexOf('-') != -1) {
						num2 = Integer.toString(number*number);
						num2 = "-" + num2;
						den2 = Integer.toString(number);
						den2 = den2.substring(1, den2.length());
					}else {
						num2 = Integer.toString(number*number);
						den2 = Integer.toString(number);
					}
					
					
				}
				
			}
			/*
			System.out.println(num1);
			System.out.println(den1);
			System.out.println(num2);
			System.out.println(den2);
			*/
			
			
			
			Rational a = new Rational(new BigInteger(num1), new BigInteger(den1));
			Rational b = new Rational(new BigInteger(num2), new BigInteger(den2));
			
			//System.out.println(a.toString());
			//System.out.println(b.toString());
			switch (op) {
			case "+":
				a = a.plus(b);
				break;
			case "-":
				a = a.sub(b);
				break;
			case "*":
				a = a.mult(b);
				break;
			case "/":
				a = a.div(b);
				break;
			
			}
			
			
			for (int i = start; i <= end; i++) {
				v.remove(start);
			}
			
			/*
			for (int i = 0; i < v.size(); i++) {
				System.out.println(v.elementAt(i));
			}
			
			System.out.println("///////////////");
			*/
			
			
			v.insertElementAt(a.toString(), start);
			
			/*
			for (int i = 0; i < v.size(); i++) {
				System.out.println(v.elementAt(i));
			}
			*/
			for (int i = 0; i < v.size(); i++) {
				if (v.elementAt(i).toString().indexOf('(') != -1) {
					rational(v);
				}
			}
		
			result = v.elementAt(0).toString();
		}
		
		
		
	}
	
	
	
}
