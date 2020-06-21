import java.math.BigInteger;

public class Rational {
	private BigInteger num;   // the numerator
	private BigInteger den;   // the denominator

	
    // create and initialize a new Rational object
    public Rational(BigInteger numerator, BigInteger denominator) {
        if (denominator == BigInteger.ZERO) {
            throw new RuntimeException("Denominator is zero");
        }

        if(denominator.compareTo(BigInteger.ZERO) == -1){
        	denominator = denominator.abs();
        	numerator = numerator.negate();
        }
        
        
        BigInteger g = lnko(numerator, denominator);
        
        num = numerator.divide(g);
        den = denominator.divide(g);

        
    }
    
    

    public Rational(String string) {
    	//System.out.println("class.Rational.contruct" + string);

		if (string.contains(".")) {
			String v1s = string.substring(0,string.indexOf('.'));
			String v2s = string.substring(string.indexOf('.')+1,string.length());
			int k = v2s.length();
			BigInteger d = BigInteger.valueOf((long) Math.pow(10, k));
			if(string.contains("-")){
				v2s = "-" + v2s;
			}
			BigInteger v1 = new BigInteger(v1s);
			BigInteger v2 = new BigInteger(v2s);
			
			num = (v1.multiply(d)).add(v2) ;
			den = d;
		}else if (string.contains("/")) {
			String v1s = string.substring(0,string.indexOf('/'));
			String v2s = string.substring(string.indexOf('/')+1,string.length());
			num = new BigInteger(v1s);
			den = new BigInteger(v2s);
		}else{
			num = new BigInteger(string);
			den = BigInteger.ONE;
		}
		BigInteger g = lnko(num, den);
        num = num.divide(g);
        den = den.divide(g);

	}



	// return string representation of (this)
    @Override
	public String toString() {
    	//System.out.println(num);
    	//System.out.println(den);
        if (den.equals(BigInteger.ONE)) 
        	return num + "";
        else          
        	return num + "/" + den;
    }

    // return (this * b)
    public Rational mult(Rational other) {
        return new Rational(this.num.multiply(other.num) , this.den.multiply(other.den));
    }


    // return (this + b)
    public Rational plus(Rational other) {
        BigInteger numerator   = this.num .multiply(other.den).add(this.den.multiply(other.num));
        BigInteger denominator = this.den.multiply(other.den);
        return new Rational(numerator, denominator);
    }
    
    public Rational sub(Rational other) {
        BigInteger numerator   = this.num .multiply(other.den).subtract(this.den.multiply(other.num));
        BigInteger denominator = this.den.multiply(other.den);
        return new Rational(numerator, denominator);
    }
    public Rational pow(int n){
    	BigInteger numerator   = this.num.pow(n);
        BigInteger denominator = this.den.pow(n);
    	return new Rational(numerator, denominator);
    }

    public Rational sqrt() {

    	BigInteger numres, denres;

    	BigInteger num = BigInteger.ZERO.setBit(this.num.bitLength()/2);
    	BigInteger num2 = num;

    	BigInteger den = BigInteger.ZERO.setBit(this.den.bitLength()/2);
    	BigInteger den2 = den;


    	// Loop until we hit the same value twice in a row, or wind
    	// up alternating.
    	for(;;) {
    		BigInteger y = num.add(this.num.divide(num)).shiftRight(1);
    		if (y.equals(num) || y.equals(num2)){
    			numres = y ;
    			break;
    		}
    			
    		num2 = num;
    		num = y;
    	}
    	for(;;) {
    		BigInteger y = den.add(this.den.divide(den)).shiftRight(1);
    		if (y.equals(den) || y.equals(den2)){
    			denres = y ;
    			break;
    		}
    			
    		den2 = den;
    		den = y;
    	}
    	return new Rational(numres,denres);
    }

    // return (1 / this)
    public Rational reciprocal() { 
    	if(num.equals(BigInteger.ZERO)){
    		return new Rational(num, den);
    	}
    	return new Rational(den, num);
    	}

    // return (this / b)
    public Rational div(Rational other) {
        return this.mult(other.reciprocal());
    }
    
   
    public BigInteger getNum() {
		return num;
	}


	public BigInteger getDen() {
		return den;
	}
	public double getDouble(){
		return num.doubleValue()/den.doubleValue();
	}
   /***************************************************************************
    *  Helper functions
    ***************************************************************************/

    // lnko(m, n)
    private static BigInteger lnko(BigInteger m, BigInteger n) {
        if (BigInteger.ZERO == n) return m;
        else return lnko(n, m.mod(n));
    }


public Rational minus() {
	return new Rational(num.multiply(BigInteger.valueOf(-1)),den);
}
    
}
