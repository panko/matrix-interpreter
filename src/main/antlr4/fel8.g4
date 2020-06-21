grammar fel8;

r: l_exp EOF | l_exp_b EOF | m1_exp EOF | eq EOF | ESLE_BLOCK EOF 
 | m2_exp EOF | m_add EOF | m_sub EOF | m_mult EOF | END_BLOCK EOF
 | m_det EOF | m_transp EOF | m_scalar EOF | m_eigendecomp EOF 
 | m_inverse EOF | m_lss EOF | m_gauss EOF | if_expr EOF
 | m_print EOF | m_active EOF | r_exp EOF | var EOF | while_expr | EOF;
	



r_exp: '(' r_exp* ')'               
	 | NUMS a_op NUMS
	 | a_op
	 | a_op NUMS;	
	 
a_op: '+' | '-' | '*' | '/' | '^';       //arithmetic operators


	    
NUMS: REAL_NUMS
	| INT_NUMS
	| RAC_NUMS;

var_r: '(' var_r* ')'
	 | M_VAR a_op M_VAR
	 | a_op
	 | a_op M_VAR
	 | NUMS a_op NUMS
	 | a_op
	 | a_op NUMS
	 | NUMS a_op M_VAR
	 | M_VAR a_op NUMS;

var: M_VAR '=' var_r | var_r; 

eq_r: M_VAR | NUMS;

eq: M_VAR '=' eq_r;

		    
l_exp:'(' l_exp* ')'               //logical expressions
	 | eq_r l_op eq_r
	 | l_op
	 | l_op eq_r;

l_exp_b: NUMS;                 //basic logical exp (0 false others true)	     
	    
	    
l_op: '<' | '>' | '<=' | '>=' | '==' | '!=';

if_expr: 'if' '(' (l_exp | l_exp_b) ')' '{' ELSE_BLOCK?;
while_expr: 'while' '(' (l_exp | l_exp_b) ')' '{';

	    
m1_exp:M_VAR '[' NUMS ']' '=' '{' (eq_r ',')* eq_r  '}';
m2_exp:M_VAR '[' NUMS ']' '[' NUMS ']' '=' '{' (eq_r ',')* eq_r  '}';

m_add: M_VAR '.add' '(' M_VAR ')' | M_VAR '=' M_VAR '.add' '(' M_VAR ')';
m_sub: M_VAR '.sub' '(' M_VAR ')' | M_VAR '=' M_VAR '.sub' '(' M_VAR ')';
m_mult: M_VAR '.mult' '(' M_VAR ')' | M_VAR '=' M_VAR '.mult' '(' M_VAR ')';
m_det: M_VAR '.det' '('')'| M_VAR '=' M_VAR '.det' '('')';
m_transp: M_VAR '.transp' '('')' | M_VAR '=' M_VAR '.transp' '('')';
m_scalar: M_VAR '.scalar' '(' eq_r ')' | M_VAR '=' M_VAR '.scalar' '(' eq_r ')';
m_eigendecomp: M_VAR '.eigendecomp' '('')';
m_inverse: M_VAR '.inv' '('')' | M_VAR '=' M_VAR '.inv' '('')';
m_lss : M_VAR '.lss' '(' M_VAR ')';
m_gauss: M_VAR '.gauss' '('')' | M_VAR '=' M_VAR '.gauss' '('')';
m_print: M_VAR;
m_active:'.';

END_BLOCK: '}';
ESLE_BLOCK: '}else{';					  
M_VAR: [a-zA-Z]+;      			  	   
INT_NUMS: '-'?[0-9]+;
REAL_NUMS :'-'?[0-9]+ '.' [0-9]+;      
RAC_NUMS : '-'?[1-9]+ [0-9]* '/' [1-9]+ [0-9]*; 	  	  
WS : [ \t\r\n]+ -> skip ;		      

