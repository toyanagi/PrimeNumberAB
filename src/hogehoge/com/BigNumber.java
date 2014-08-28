package hogehoge.com;

import java.math.BigInteger;

public class BigNumber {
	//Long.MAX_VALUE: 9223 37203 68547 75807
	//                 999 99999 80000 00001
	//Integer.MAX_VALUE:         21474 83647
	
	
	//long�^�Ɏ��܂�ꍇ
	private long lval;
	
	//long�^�Ɏ��܂�Ȃ��ꍇ��word�z��Ɋi�[�i�P�̔z��ɂ͂P�O���j
	private int words[];
	
	//�������i�[
	private int length;
	
	//true�Ȃ畉��
	private boolean isnegative;
	
	//�萔
	//private final int longTypelen=9;
	private final int intTypelen=9;
	private final int longTypelen=18;
	private final int intMax=999999999;
	public static final BigNumber ZERO=new BigNumber("0");
	public static final BigNumber ONE=new BigNumber("1");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String str= "12345678901234567890123456789";
		String str=   "100000000000000000000000";
		String str2= "-99999999999999999";
		//String str2=   "-12345678987654321";
		
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
		
		
	}
	
	public BigNumber(){
		
	}
	
	public BigNumber(String s){
		int len=s.length();
		int start;
		
		//�����A�����̐ݒ�
		if(s.charAt(0)=='-'){
			isnegative=true;
			length=len-1;
			start=1;
		}else{
			isnegative=false;
			length=len;
			start=0;
		}
		
		//long�^�Ɏ��܂錅���Ȃ�long�^�ŕێ�
		/*if(len-start <= 18){
			lval=(long)Math.abs(Long.parseLong(s));
			words=null;
			return;
		}*/
		
		//long�^�Ɏ��܂�Ȃ��ꍇ��10�P�^���ɋ�؂��ĕێ�
		int countLong=(int)Math.ceil((len-start)/(double)intTypelen);
		words=new int[countLong];
		for(int i=0; i<words.length; i++){
			int startIndex=( len - intTypelen * (i+1)-start<0 ? start : len - intTypelen*(i+1)   );		
			int endIndex=( len - intTypelen*i  );	
			words[i]=Integer.parseInt(s.substring(startIndex, endIndex));
			
		}
	}
	
	//�������t�ɂ���
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
	
	public BigNumber copy(){
		BigNumber newnum = new BigNumber();
		newnum.words=this.words;
		newnum.length=this.length;		
		newnum.isnegative = this.isnegative;
		return newnum;
	}
	
	//��Βl��Ԃ�
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
	
	//���Z����
	public BigNumber add(BigNumber num){
		
		//�ǂ��炩���[���̏ꍇ
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
	
	//���Z����
	public BigNumber subtract(BigNumber num){
		
		//�ǂ��炩���[���̏ꍇ
		if(this.isZero()) 
			return num.negate();
		if(num.isZero()) 
			return this.copy();			
		
		//�����̃p�^�[���`�F�b�N	
		if( (!this.isnegative) && num.isnegative){
			return this.add(num.negate());			
		}else if( this.isnegative  && !(num.isnegative)){
			return this.add(num.negate());
		}else if ( this.isnegative  && num.isnegative){
			return (num.negate()).subtract(this.negate());
		}
		
		//�召��r
		int comp=this.compareTo(num);
		comp=comp+0;
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

	//�ς����߂�
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
	
	//���������߂�(�����_�ȉ��͐؂艺����j
	public BigNumber[] divideandremainder(BigNumber num){
		
		BigNumber[] temp= new BigNumber[2];
		
		//�ǂ��炩���[���̏ꍇ
		if(this.isZero()){
			temp[0]=ZERO;
			temp[1]=ZERO;
			return temp;
		}
		if(num.isZero()) //�[�����Z�̏ꍇ�͗�O��Ԃ�
			throw new ArithmeticException("ZERO Division");	
		
		BigNumber tempthis= this.abs();
		BigNumber tempnum= num.abs();
		
		//���鐔�̕����傫���ꍇ�̓[����Ԃ�
		if(tempthis.compareTo(tempnum.abs()) < 0 ){
			temp[0]=ZERO;
			temp[1]=new BigNumber(this.getWordsRaw());
			temp[1].isnegative=this.isnegative;
			return temp;
		}
		
		StringBuffer buf = new StringBuffer();
		
		//���鐔�Ō����Œ�
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
		tempdiv = tempx/tempy;  //���̏�
		
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
			
			//�����m��i�P�����j
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
			tempdiv = tempx/tempy;  //���̏�
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
	
	//�Q�̐��l���r����
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
	
	//���l�����݂̂�Ԃ��i�����Ȃ��j
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
