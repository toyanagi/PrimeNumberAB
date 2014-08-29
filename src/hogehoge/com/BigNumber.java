package hogehoge.com;

import java.math.BigInteger;

public class BigNumber {
	//Long.MAX_VALUE: 9223 37203 68547 75807
	//                 999 99999 80000 00001
	//Integer.MAX_VALUE:         21474 83647
	
	
	//longŒ^‚Éû‚Ü‚éê‡
	private long lval;
	
	//longŒ^‚Éû‚Ü‚ç‚È‚¢ê‡‚Íword”z—ñ‚ÉŠi”[i‚P‚Â‚Ì”z—ñ‚É‚Í‚P‚OŒ…j
	private int words[];
	
	//Œ…”‚ğŠi”[
	private int length;
	
	//true‚È‚ç•‰”
	private boolean isnegative;
	
	//’è”
	//private final int longTypelen=9;
	private final int intTypelen=9;
	private final int longTypelen=18;
	private final int intMax=999999999;
	public static final BigNumber ZERO=new BigNumber("0");
	public static final BigNumber ONE=new BigNumber("1");
	public static final BigNumber TWO=new BigNumber("2");
	public static final BigNumber MinusONE=new BigNumber("-1");
	private static final int[] k = 
		{100,150,200,250,300,350,400,5000,600,800,1250,Integer.MAX_VALUE};
	private static final int[] t = 
		{27,18,15,12,9,8,7,6,5,4,3,2};
	private static final int[] primes=
		{2,3,5,7,11,13,17,19,23,29,31,37,41,43,
		47,53,59,61,67,71,73,79,83,89,97,101,103,107,
		109,113,127,131,137,139,149,151,157,163,167,173,179,181,
		191,193,197,199,211,223,227,229,233,239,241,251};
	/*static{
		int check=2;
		int div=2;
		for(int i=0;i<54;i++){
			
			loop1:
			while( ( check % div ) ==0){
				if(check==div){
					primes[i]=check;
					System.out.println("primes[" + i + "]=" +check);
					check++;
					div=2;
					break loop1;
				}else{
					check++;
					div=2;
				}
				div++;
			}
		}
	}*/
	private static final int minFixNum = -100;
	private static final int maxFixNum = 1024;
	private static final int numFixNum = maxFixNum-minFixNum+1;
	private static final BigNumber[] smallFixNums = new BigNumber[numFixNum];
	static
	{
	     for (int i = numFixNum;  --i >= 0; )
	       smallFixNums[i] = new BigNumber(i + minFixNum);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String str= "12345678901234567890123456789";
		String str=   "100000000000000000000000";
		String str2= "-99999999999999999";
		//String str3= "320989741";
		String str3= "97";
		//String str2=   "-12345678987654321";
		
		BigNumber num=new BigNumber(str3);
		BigInteger numi=new BigInteger(str3);
		System.out.println(num);
		
		System.out.println("comp:" + TWO.compareTo(TWO));
		
		//ÀsŠÔŒv‘ª
		long startTime = System.currentTimeMillis();		
		long stopTime = System.currentTimeMillis();
		/*
		System.out.println("BI getLowestBitSet: " + numi.getLowestSetBit());
		long stopTime = System.currentTimeMillis();
		System.out.println(" Run Time = " + (stopTime - startTime) + " ms " );
		
		startTime = System.currentTimeMillis();
		System.out.println("BN getLowestBitSet: " + num.getLowestSetBit());
		stopTime = System.currentTimeMillis();
		System.out.println(" Run Time = " + (stopTime - startTime) + " ms " );
		
		startTime = System.currentTimeMillis();
		System.out.println("BI getBitLen: " + numi.bitLength());
		stopTime = System.currentTimeMillis();
		System.out.println(" Run Time = " + (stopTime - startTime) + " ms " );
		
		startTime = System.currentTimeMillis();
		System.out.println("BN getBitLen: " + num.bitLength());
		stopTime = System.currentTimeMillis();
		System.out.println(" Run Time = " + (stopTime - startTime) + " ms " );
		
		*/
		
		startTime = System.currentTimeMillis();
		System.out.println("BI isPrime: " + numi.isProbablePrime(50));
		stopTime = System.currentTimeMillis();
		System.out.println(" Run Time = " + (stopTime - startTime) + " ms " );
		
		startTime = System.currentTimeMillis();
		System.out.println("BN isPrime: " + num.isProbablePrime(50));
		stopTime = System.currentTimeMillis();
		System.out.println(" Run Time = " + (stopTime - startTime) + " ms " );
		
		/*
		BigNumber num=new BigNumber(str);
		BigInteger numi=new BigInteger(str);
		System.out.println(num);
		BigNumber num2=new BigNumber(str2);
		BigInteger numi2=new BigInteger(str2);
		System.out.println(num2);
		
		System.out.println(num.add(num2));
		System.out.println(numi.add(numi2));
		System.out.println(num.subtract(num2));
		System.out.println(numi.subtract(numi2));
		System.out.println(num.multiply(num2));
		System.out.println(numi.multiply(numi2));
		System.out.println(numi.divide(numi2));
		System.out.println(num.divide(num2));
		System.out.println(numi.remainder(numi2));
		System.out.println(num.remainder(num2));
		*/
		
	}
	
	public BigNumber(){
		
	}
	
	public BigNumber(String s){
		int len=s.length();
		int start;
		
		//Œ…”A³•‰‚Ìİ’è
		if(s.charAt(0)=='-'){
			isnegative=true;
			length=len-1;
			start=1;
		}else{
			isnegative=false;
			length=len;
			start=0;
		}
		
		while(s.charAt(start)=='0'){
			start++;
			if(start==len){
				length=1;
				words=new int[1];
				words[0]=0;
				isnegative=false;
				return;
			}
			length--;
		}
		
		//longŒ^‚Éû‚Ü‚éŒ…”‚È‚çlongŒ^‚Å•Û
		/*if(len-start <= 18){
			lval=(long)Math.abs(Long.parseLong(s));
			words=null;
			return;
		}*/
		
		//longŒ^‚Éû‚Ü‚ç‚È‚¢ê‡‚Í10ƒPƒ^‚¸‚Â‚É‹æØ‚Á‚Ä•Û
		int countLong=(int)Math.ceil((len-start)/(double)intTypelen);
		words=new int[countLong];
		for(int i=0; i<words.length; i++){
			int startIndex=( len - intTypelen * (i+1)-start<0 ? start : len - intTypelen*(i+1)   );		
			int endIndex=( len - intTypelen*i  );	
			words[i]=Integer.parseInt(s.substring(startIndex, endIndex));
			
		}
	}
	
	public BigNumber(long l){
		new BigNumber(Long.toString(l));
	}
	
	public BigNumber(int i){
		i = i<0 ? i * (-1) : i ;
		int len = Integer.toString(i).length();
		length = i<0 ? len -1 : len ;
		isnegative = i<0 ? true : false ;
		
		words=new int[1];
		words[0]= i<0 ? i * (-1) : i ;
		isnegative=false;
	}
	
	//³•‰‚ğ‹t‚É‚·‚é
	public BigNumber negate(){
		if((this.length==1) & (this.words[0]==0)){
			return new BigNumber("0");
		}
		BigNumber newnum = new BigNumber();
		newnum.words=this.words;
		newnum.length=this.length;
		newnum.isnegative = !this.isnegative;
			
		return newnum;
	}
	
	public boolean isZero(){
		if((this.length==1) & (this.words[0]==0)){
			return true;
		}
		return false;
	}
	
	public boolean isOne(){
		if((this.length==1) & (this.words[0]==1)){
			return true;
		}
		return false;
	}
	
	public BigNumber copy(){
		BigNumber newnum = new BigNumber();
		newnum.words=this.words;
		newnum.length=this.length;		
		newnum.isnegative = this.isnegative;
		return newnum;
	}
	
	//â‘Î’l‚ğ•Ô‚·
	public BigNumber abs(){
		if((this.length==1) & (this.words[0]==0)){
			return new BigNumber("0");
		}
		
		BigNumber newnum = new BigNumber();
		newnum.words=this.words;
		newnum.length=this.length;		
		newnum.isnegative = ( this.isnegative ? false : false );
			
		return newnum;
	}
	
	//‰ÁZ‚·‚é
	public BigNumber add(BigNumber num){
		
		//‚Ç‚¿‚ç‚©‚ªƒ[ƒ‚Ìê‡
		if(this.isZero()) 
			return num.copy();
		if(num.isZero()) 
			return this.copy();	
		
		if( this.isnegative ^ num.isnegative){
			if(num.isnegative){
				return this.subtract(num.negate());
			}else{
				return num.subtract(this.negate());
			}
		}
		
		BigNumber newnum = new BigNumber();
		
		int thisCount = (int)Math.ceil(this.length/(double)intTypelen);
		int numCount = (int)Math.ceil(num.length/(double)intTypelen);
		//int resultLen = (this.length >= num.length ? this.length : num.length);
		int countInt= (thisCount >= numCount ? thisCount : numCount );
		newnum.words=new int[countInt];
				
		int thisTemp;
		int numTemp;
		int incDigit=0;
		
		for(int i=0;i<countInt;i++){
			thisTemp = ( i<thisCount ? this.words[i] : 0);
			numTemp = ( i<numCount ? num.words[i] : 0);
			newnum.words[i]=thisTemp+numTemp+incDigit;
			if(newnum.words[i]>intMax){
				newnum.words[i]=newnum.words[i]-(intMax+1);
				incDigit=1;
			}else{
				incDigit=0;
			}			
		}
		
		newnum.length=(this.length >= num.length ? this.length : num.length);
		newnum.isnegative=this.isnegative;
		
		if(incDigit==1){
			int tempwords[]=new int[countInt];
			tempwords=newnum.words;
			newnum.words=new int[countInt+1];
			for(int i=0;i<tempwords.length;i++){
				newnum.words[i]=tempwords[i];
			}
			newnum.words[countInt]=1;
			newnum.length+=1;
		}
		
		newnum.length=newnum.getWordsRaw().length();
		
		return newnum;
	}
	
	//Œ¸Z‚·‚é
	public BigNumber subtract(BigNumber num){
		
		//‚Ç‚¿‚ç‚©‚ªƒ[ƒ‚Ìê‡
		if(this.isZero()) 
			return num.negate();
		if(num.isZero()) 
			return this.copy();			
		
		//³•‰‚Ìƒpƒ^[ƒ“ƒ`ƒFƒbƒN	
		if( (!this.isnegative) && num.isnegative){
			return this.add(num.negate());			
		}else if( this.isnegative  && !(num.isnegative)){
			return this.add(num.negate());
		}else if ( this.isnegative  && num.isnegative){
			return (num.negate()).subtract(this.negate());
		}
		
		//‘å¬”äŠr
		int comp=this.compareTo(num);
		//comp=comp+0;
		if(comp==0){
			return new BigNumber("0");
		}else if(comp<0){
			return num.subtract(this).negate();
		}
		
		//
		BigNumber newnum = new BigNumber();
		
		int thisCount = (int)Math.ceil(this.length/(double)intTypelen);
		int numCount = (int)Math.ceil(num.length/(double)intTypelen);
		int countInt= (thisCount >= numCount ? thisCount : numCount );
		newnum.words=new int[countInt];
				
		int thisTemp;
		int numTemp;
		int decDigit=0;
		
		for(int i=0;i<countInt-1;i++){
			thisTemp = ( i<thisCount ? this.words[i] : 0);
			numTemp = ( i<numCount ? num.words[i] : 0);
			newnum.words[i]=thisTemp-numTemp-decDigit;
			if(newnum.words[i]<0){
				newnum.words[i]=newnum.words[i]+(intMax+1);
				decDigit=1;
			}else{
				decDigit=0;
			}			
		}
		 
		thisTemp = ( countInt-1<thisCount ? this.words[countInt-1] : 0);
		numTemp = ( countInt-1<numCount ? num.words[countInt-1] : 0);
		newnum.words[countInt-1]=thisTemp-numTemp-decDigit;
		
		if(newnum.words[countInt-1]<0){
			newnum.isnegative=true;
			newnum.words[countInt-1]=newnum.words[countInt-1]*(-1);
		}else{
			newnum.isnegative=false;
		}
		
		
		int newCountInt=countInt;
		
		for(int i=countInt-1;i>=0;i--){
			if(newnum.words[i]==0){
				newCountInt--;
			}
		}
		
		int tempwords[]=new int[countInt];
		tempwords=newnum.words;
		newnum.words=new int[newCountInt];
		for(int i=0;i<newCountInt;i++){
			newnum.words[i]=tempwords[i];
		}
		
		newnum.length=newnum.getWordsRaw().length();
		
		return newnum;
	}

	//Ï‚ğ‹‚ß‚é
	public BigNumber multiply(BigNumber num){
		
		if(this.isZero() || num.isZero()){
			return new BigNumber("0");
		}
		
		BigNumber newnum = new BigNumber();
		int tempx=this.words.length;
		int tempy=num.words.length;
		long matrix[][]=new long[tempx][tempy];
		long incDigit=0;
		long templ;
		newnum.words=new int[tempx+tempy];
				
		for(int y=0;y<tempy;y++){
			for(int x=0;x<tempx;x++){
				incDigit=0;
				matrix[x][y]=(long)(this.words[x])*(long)(num.words[y]); 
				templ = (long)(newnum.words[x+y]) + matrix[x][y];
				if(templ>intMax){
					incDigit =  templ / (intMax+1);
					newnum.words[x+y] = (int)(templ - incDigit * (intMax+1));
					if(newnum.words[x+y+1] + incDigit > intMax){
						templ = newnum.words[x+y+1] + incDigit;
						incDigit =  templ / (intMax+1);
						newnum.words[x+y+1] = (int)(templ - incDigit * (intMax+1));
						newnum.words[x+y+2] += incDigit;
					}else{
						newnum.words[x+y+1] += incDigit;
					}				
				}else{
					newnum.words[x+y] = (int)templ;
				}
			}				
		}
		
		if(newnum.words[tempx+tempy-1]==0){
			int tempwords[]=new int[tempx+tempy];
			tempwords=newnum.words;
			newnum.words=new int[tempx+tempy-1];
			for(int i=0;i<tempx+tempy-1;i++){
				newnum.words[i]=tempwords[i];
			}
		}
		
		newnum.length=newnum.getWordsRaw().length();
		newnum.isnegative = ( this.isnegative ^ num.isnegative );
				
		return newnum;
	}
	
	//¤œ‚ğ‹‚ß‚é(¬”“_ˆÈ‰º‚ÍØ‚è‰º‚°‚éj
	public BigNumber[] divideandremainder(BigNumber num){
		
		BigNumber[] temp= new BigNumber[2];
		
		//‚Ç‚¿‚ç‚©‚ªƒ[ƒ‚Ìê‡
		if(this.isZero()){
			temp[0]=ZERO;
			temp[1]=ZERO;
			return temp;
		}
		if(num.isZero()) //ƒ[ƒœZ‚Ìê‡‚Í—áŠO‚ğ•Ô‚·
			throw new ArithmeticException("ZERO Division");	
		
		BigNumber tempthis= this.abs();
		BigNumber tempnum= num.abs();
		
		//Š„‚é”‚Ì•û‚ª‘å‚«‚¢ê‡‚Íƒ[ƒ‚ğ•Ô‚·
		if(tempthis.compareTo(tempnum.abs()) < 0 ){
			temp[0]=ZERO;
			temp[1]=new BigNumber(this.getWordsRaw());
			temp[1].isnegative=this.isnegative;
			return temp;
		}
		
		StringBuffer buf = new StringBuffer();
		
		//Š„‚é”‚ÅŒ…”ŒÅ’è
		BigNumber tempnumx= new BigNumber();
		BigNumber tempnummul= new BigNumber();
		BigNumber tempnumrem= new BigNumber();
		int tempx;
		int tempy;
		int tempdiv;
		boolean existSol=false;
		
		String tempxstr=tempthis.getWordsRaw();
		String tempystr=tempnum.getWordsRaw();
		
		if(tempnum.length > intTypelen-1){
			tempx = Integer.parseInt(tempxstr.substring(0, intTypelen-1));
		}else{
			tempx = Integer.parseInt(tempxstr.substring(0, tempnum.length));
		}
		
		if(tempnum.length > intTypelen-1){
			tempy = Integer.parseInt(tempystr.substring(0, intTypelen-1));
		}else{
			tempy = Integer.parseInt(tempystr);
		}
		tempdiv = tempx/tempy;  //‰¼‚Ì¤
		
		//tempnumx = new BigNumber(tempxstr.substring(0, num.length-1));
		tempnumx = new BigNumber(tempxstr.substring(0, tempnum.length));
		
		//for(int i=0;i<= this.length - num.length +1 ;i++){
		for(int i=0;i<= tempthis.length - tempnum.length  ;i++){
			
			tempnummul = tempnum.multiply(new BigNumber(Integer.toString(tempdiv)));
			tempnumrem = tempnumx.subtract(tempnummul);
			
			loopWhile:
			while(tempdiv>0){
				
				if(tempnumx.compareTo(tempnummul) < 0){
					tempdiv--;
					tempnummul = tempnum.multiply(new BigNumber(Integer.toString(tempdiv)));
					tempnumrem = tempnumx.subtract(tempnummul);
					continue loopWhile;
				}
				if(tempnumrem.compareTo(ZERO) < 0){
					tempdiv++;
					tempnummul = tempnum.multiply(new BigNumber(Integer.toString(tempdiv)));
					tempnumrem = tempnumx.subtract(tempnummul);
					continue loopWhile;
				}
				break loopWhile;
			}
			
			if(tempdiv!=0)
				existSol=true;
			
			//¤‚ğŠm’èi‚PŒ…•ªj
			if(existSol)
				buf.append(tempdiv);
			
			//if(i==this.length - num.length+1)
			if(i==tempthis.length - tempnum.length)
				break;
			
			tempnumx = new BigNumber(tempnumrem.getWordsRaw() +
					tempxstr.substring(i+tempnum.length, i+tempnum.length+1));
			//		tempxstr.substring(i+num.length-1, i+num.length));
			
			if(tempnumx.length > intTypelen){
				tempx = Integer.parseInt(tempnumx.getWordsRaw().substring(0, intTypelen));
			}else{
				tempx = Integer.parseInt(tempnumx.getWordsRaw());
			}
			tempdiv = tempx/tempy;  //‰¼‚Ì¤
		}
				
		BigNumber newnum = new BigNumber(buf.toString());
		newnum.isnegative = ( this.isnegative ^ num.isnegative );
		
		temp[0]=newnum;
		if(this.isnegative & !num.isnegative){
			temp[1]=tempnumrem.negate();
		}else if(this.isnegative & num.isnegative){
			temp[1]=tempnumrem.negate();
		}else if(!this.isnegative & num.isnegative){
			temp[1]=tempnumrem;
		}else{
			temp[1]=tempnumrem;
		}
		
		
		return temp;
	}
	
	public BigNumber divide(BigNumber num){
		return this.divideandremainder(num)[0];
	}
	
	public BigNumber remainder(BigNumber num){
		return this.divideandremainder(num)[1];
	}
	
	//‚Q‚Â‚Ì”’l‚ğ”äŠr‚·‚é
	public int compareTo(BigNumber num){
		
		//•Ğ•û‚ª•‰”‚Ìê‡
		if( this.isnegative ^ num.isnegative ){
			return (this.isnegative ? -1 : 1 );
		}
		
		//Œ…”‚ªˆá‚¤ê‡
		if(this.length>num.length){
			return (this.isnegative ? -1 : 1 );
		}else if(this.length<num.length){
			return (this.isnegative ? 1 : -1 );
			
		//Œ…”‚ª“¯‚¶ê‡
		}else{
			for(int i=this.words.length-1;i>=0;i--){
				if(this.words[i]>num.words[i]){
					return (this.isnegative ? -1 : 1 );
				}else if(this.words[i]<num.words[i]){
					return (this.isnegative ? 1 : -1 );
				}
			}
		}
		return 0;
	}
	
	//”’l•”•ª‚Ì‚İ‚ğ•Ô‚·i•„†‚È‚µj
	private String getWordsRaw(){
		String temp;
		StringBuffer buf = new StringBuffer();
		buf.append(Integer.toString(words[words.length-1]));
		for(int i=words.length-2; i>=0; i--){
			temp=Integer.toString(words[i]);
			for(int j=0;j<intTypelen-temp.length();j++){
				buf.append("0");				
			}
			buf.append(temp);
		}
		return buf.toString();		
	}
	
	public String toString(){
		
		String temp;
		StringBuffer buf = new StringBuffer();
				
		if(words==null){
			buf.append(Long.toString((long)Math.abs(lval)));
		}else{
			buf.append(Integer.toString(words[words.length-1]));
			
			for(int i=words.length-2; i>=0; i--){
				temp=Integer.toString(words[i]);
				for(int j=0;j<intTypelen-temp.length();j++){
					buf.append("0");				
				}
				buf.append(temp);
			}
			
			
		}
		
		temp=buf.toString();
		buf = new StringBuffer();
		int len=temp.length();
		if(isnegative) buf.append("-");
		//int start=( isnegative ? 1 : 0 );
		
		for (int i = 0; i <len-1; i++) {
            buf.append(temp.substring(i,i+1));
            if((len-i-1)%5==0){
            	buf.append(" ");
            }
        }
		buf.append(temp.substring(len-1,len));
		buf.append("(" + length + ")");
	
		return buf.toString();
	}
	
	public boolean isProbablePrime(int cert){
		int i;
		//‘f”‚ÌŠÈˆÕƒ`ƒFƒbƒN
		this.checkPrimeFirst();
		
		//ƒ‰ƒrƒ“Eƒ~ƒ‰[–@‚Å”»’è
		BigNumber pMinus1=this.add(MinusONE);
		int b=pMinus1.getLowestSetBit();
		System.out.println( (2L << b - 1) );
		BigNumber divtemp = new BigNumber(Long.toString(2L << b - 1));
		BigNumber m = pMinus1.divide(divtemp);
		
		int bits=this.bitLength();
		
		for(i=0;i<k.length;i++)
			if(bits <= k[i])
				break;
		int trials=t[i];
		
		if(cert>80)
			trials *= 2;
		BigNumber z;
		
		for(int t=0;t<trials;t++){
			z=smallFixNums[primes[t]-minFixNum].modPow(m,this);
			if(z.isOne() || z.compareTo(pMinus1)==0)
				continue;
			
			for(i=0;i<b;){
				if(z.isOne())
					return false;
				i++;
				if( z.compareTo(pMinus1)==0)
					break;
				z=z.modPow(TWO,this);
			}
			if(i==b && !(z.compareTo(pMinus1)==0))
				return false;
		}
		return true;
	}
	
	public BigNumber modPow(BigNumber exp,BigNumber m){
		if(m.isnegative || m.isZero())
			throw new ArithmeticException("non positive modulo");
		
		if(exp.isnegative)
			throw new ArithmeticException("non positive exp");
		
		if(exp.isOne())
			return mod(m);
		
		BigNumber s=ONE;
		BigNumber t=this;
		BigNumber u =exp;
		
		while(!u.isZero()){
			//if(u.and(ONE).isOne){
			if(u.words[0] % 2 == 1){
				s=s.multiply(t).mod(m);
			}
			//u=u.shiftRight(1);
			u=u.divide(TWO);
			s=t.multiply(t).mod(m);
		}
			
		return s;
	}
	
	public BigNumber mod(BigNumber m){
		if(m.isnegative || m.isZero())
			throw new ArithmeticException("non positive modulus");
		
		return this.remainder(m);
	}
	
	public boolean checkPrimeFirst(){
		
		return true;
	}

	
	public int getLowestSetBit(){
		
		if(this.isZero())
			return -1;
		
		int i=0;
		BigNumber temp[] = this.divideandremainder(TWO);
		while(temp[1].compareTo(ZERO)==0){
			i++;
			temp=temp[0].divideandremainder(TWO);
		}
		return i;
	}
	
	//2i”•\Œ»‚Ìƒrƒbƒg”‚ğ•Ô‹p
	// log2(A)=log10(A)/log10(2)‚Å‹ß—’l‚ğ‹‚ß‚é
	public int bitLength(){
		//log10(2)=0.30102999
		return (int)((this.length-0.5)/0.30102999);
	}
	
	
}
