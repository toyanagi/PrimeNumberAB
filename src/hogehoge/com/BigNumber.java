package hogehoge.com;

import java.math.BigInteger;

public class BigNumber {
	//Long.MAX_VALUE: 9223 37203 68547 75807
	//                 999 99999 80000 00001
	//Integer.MAX_VALUE:         21474 83647
	
	
	//long型に収まる場合
	private long lval;
	
	//long型に収まらない場合はword配列に格納（１つの配列には１０桁）
	private int words[];
	
	//桁数を格納
	private int length;
	
	//trueなら負数
	private boolean isnegative;
	
	//定数
	//private final int longTypelen=9;
	private final int intTypelen=9;
	private final int intMax=999999999;
	public static final BigNumber ZERO=new BigNumber("0");
	public static final BigNumber ONE=new BigNumber("1");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String str= "12345678901234567890123456789";
		String str=   "11111111111";
		String str2=  "11111111111";
		
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
		
	}
	
	public BigNumber(){
		
	}
	
	public BigNumber(String s){
		int len=s.length();
		int start;
		
		//桁数、正負の設定
		if(s.charAt(0)=='-'){
			isnegative=true;
			length=len-1;
			start=1;
		}else{
			isnegative=false;
			length=len;
			start=0;
		}
		
		//long型に収まる桁数ならlong型で保持
		/*if(len-start <= 18){
			lval=(long)Math.abs(Long.parseLong(s));
			words=null;
			return;
		}*/
		
		//long型に収まらない場合は10ケタずつに区切って保持
		int countLong=(int)Math.ceil((len-start)/(double)intTypelen);
		words=new int[countLong];
		for(int i=0; i<words.length; i++){
			int startIndex=( len - intTypelen * (i+1)<0 ? start : len - intTypelen*(i+1)   );		
			int endIndex=( len - intTypelen*i  );	
			words[i]=Integer.parseInt(s.substring(startIndex, endIndex));
			
		}
	}
	
	//正負を逆にする
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
	
	//加算する
	public BigNumber add(BigNumber num){
		
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
	
	public BigNumber subtract(BigNumber num){
		
		//正負のパターンチェック	
		if( (!this.isnegative) && num.isnegative){
			return this.add(num.negate());			
		}else if( this.isnegative  && !(num.isnegative)){
			return this.add(num.negate());
		}else if ( this.isnegative  && num.isnegative){
			return (num.negate()).subtract(this.negate());
		}
		
		//大小比較
		int comp=this.compareTo(num);
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

	public BigNumber multiply(BigNumber num){
		
		if(this.compareTo(ZERO)==0 || num.compareTo(ZERO)==0){
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
					newnum.words[x+y+1] += incDigit;
					newnum.words[x+y] += (templ - incDigit * (intMax+1));
				}else{
					newnum.words[x+y] += templ;
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
	public int compareTo(BigNumber num){
		
		if( this.isnegative ^ num.isnegative ){
			return (this.isnegative ? -1 : 1 );
		}
		
		if(this.length>num.length){
			return (this.isnegative ? -1 : 1 );
		}else if(this.length<num.length){
			return (this.isnegative ? 1 : -1 );
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
	
	//数値部分のみを返す（符号なし）
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
	
}
