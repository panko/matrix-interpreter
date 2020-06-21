import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

public class Logicalexpr {
	String result = "";
	Vector v = new Vector<>();
	Vector itemsToRemove = new Vector();
	
	public Logicalexpr(ParseTree tree){
		visit(tree);
		test();
		logical(v);
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
	
	public void logical(Vector v){
		
		int start = 0;
		int end = 0;
		/*
		for (int i = 0; i < v.size(); i++) {
			System.out.println(v.elementAt(i));
		}
		*/
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
		
		for (int j = start; j <= end; j++) {
			//System.out.println(v.elementAt(j));
			itemsToRemove.addElement(v.elementAt(j));
		}
		
		
		
		if (Rationalexpr.alfabet.contains(v.elementAt(start+1).toString())) {
			if (Interp.blocks.elementAt(Interp.block_index).containsKey(v.elementAt(start+1).toString())) {
				FractionMatrix m = Interp.blocks.elementAt(Interp.block_index).get(v.elementAt(start+1).toString());
				String exp = String.valueOf(m.getEntry(0, 0));
				v.setElementAt(exp, start+1);
				
			}else {
				System.out.println("'" + v.elementAt(start+1).toString() + "'" + " not declared yet");
			}
			
		}
		if (Rationalexpr.alfabet.contains(v.elementAt(start+3).toString())) {
			if (Interp.blocks.elementAt(Interp.block_index).containsKey(v.elementAt(start+3).toString())) {
				FractionMatrix m = Interp.blocks.elementAt(Interp.block_index).get(v.elementAt(start+3).toString());
				String exp = String.valueOf(m.getEntry(0, 0));
				v.setElementAt(exp, start+3);
				
			}else {
				System.out.println("'" + v.elementAt(start+3).toString() + "'" + " not declared yet");
			}
			
		}
		
		if (v.elementAt(start+1).toString().indexOf('/') != -1 ) {
			String num = v.elementAt(start+1).toString().substring(0, v.elementAt(start+1).toString().indexOf('/'));
			String den = v.elementAt(start+1).toString().substring(v.elementAt(start+1).toString().indexOf('/')+1, v.elementAt(start+1).toString().length());
			
			double r = Double.parseDouble(num) / Double.parseDouble(den);
			
			String exp = String.valueOf(r);
			v.setElementAt(exp, start+1);
			
			
			
		}
		if (v.elementAt(start+3).toString().indexOf('/') != -1 ) {
			String num = v.elementAt(start+3).toString().substring(0, v.elementAt(start+3).toString().indexOf('/'));
			String den = v.elementAt(start+3).toString().substring(v.elementAt(start+3).toString().indexOf('/')+1, v.elementAt(start+3).toString().length());
			
			double r = Double.parseDouble(num) / Double.parseDouble(den);
			
			String exp = String.valueOf(r);
			v.setElementAt(exp, start+3);
			
			
			
		}
		
		String op = v.elementAt(start+2).toString();
		String v1 = v.elementAt(start+1).toString();
		String v2 = v.elementAt(start+3).toString();
		String r = "";
		
		/*
		for (int i = 0; i < v.size(); i++) {
			System.out.println(v.elementAt(i));
		}
		*/
		
		switch (op) {
		case "<":
			if (Double.parseDouble(v1) < Double.parseDouble(v2)) {
				r = "1.0";
			}else {
				r = "0.0";
			}
			break;
		case ">":
			if (Double.parseDouble(v1) > Double.parseDouble(v2)) {
				r = "1.0";
			}else {
				r = "0.0";
			}
			break;
		case "<=":
			if (Double.parseDouble(v1) <= Double.parseDouble(v2)) {
				r = "1.0";
			}else {
				r = "0.0";
			}
			break;
		case ">=":
			if (Double.parseDouble(v1) >= Double.parseDouble(v2)) {
				r = "1.0";
			}else {
				r = "0.0";
			}
			break;
		case "==":
			if (Double.parseDouble(v1) == Double.parseDouble(v2)) {
				r = "1.0";
			}else {
				r = "0.0";
			}
			break;
		case "!=":
			if (Double.parseDouble(v1) != Double.parseDouble(v2)) {
				r = "1.0";
			}else {
				r = "0.0";
			}
			break;
		
		}
		
		for (int i = start; i <= end; i++) {
			v.remove(start);
		}
		
		
		
		
		v.insertElementAt(r.toString(), start);
		/*
		for (int i = 0; i < v.size(); i++) {
			System.out.println(v.elementAt(i));
		}
		*/
		for (int i = 0; i < v.size(); i++) {
			if (v.elementAt(i).toString().indexOf('(') != -1) {
				logical(v);
			}
		}
		
		
		
		if (v.elementAt(0).toString().equals("0.0")) {
			result = "False";
		}else {
			result = "True";
		}
		
		
	}
	
	
	
}
