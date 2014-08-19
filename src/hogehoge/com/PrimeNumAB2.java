package hogehoge.com;

import java.math.BigInteger;

public class PrimeNumAB2 {
	
	//�萔�̒�`
	static final BigInteger minPrimeNum = new BigInteger("10000");    //�ŏ��̑f���i�T�P�^�j
	static final int minPrimeNumLength = 5;    //�ŏ��̑f���̌����i�T�P�^�j
	static final int searchLimit = 1;    //�e�����Ƃ̒T����
	static final int checkPrimeMode = 1;    //�f�����胂�[�h
	
	//�ϐ��̒�`
	static int CompositeNumNLength;    //N�̌���
	static int PrimeNumALength;        //A�̌���
	static int PrimeNumBLength;        //B�̌���
	
	static BigInteger PrimeNumA;        //A�̌��
	static BigInteger PrimeNumB;        //B�̌��
	static BigInteger CompositeNumN;    //N�̌��
	static BigInteger CompositeNumNMax;    //N�̍ő�l
	static BigInteger PrimeNumALast;        //A�̊m��l
	static BigInteger PrimeNumBLast;        //B�̊m��l
	static BigInteger CompositeNumNLast;    //N�̊m��l
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//���s���Ԍv��
		long startTime = System.currentTimeMillis();
		
		//������
		CompositeNumNLast=BigInteger.ZERO;
		PrimeNumALast =BigInteger.ZERO;
		PrimeNumBLast=BigInteger.ZERO;
		
		//���͒l�̎擾
		CompositeNumNLength = Integer.parseInt(args[0]);
		System.out.println("CompositNum N Length = " + CompositeNumNLength );

		//�ŏ��̌����̌���
		PrimeNumALength =  minPrimeNumLength;
		PrimeNumBLength = CompositeNumNLength - minPrimeNumLength +1;
		
		//�ő��N���Z�b�g
		StringBuffer buf = new StringBuffer();
	    for (int i = 1; i <= CompositeNumNLength; i++) {
	            buf.append("9");
	    }
	    CompositeNumNMax = new BigInteger(buf.toString());
	    System.out.println("CompositeNumNMax = " + CompositeNumNMax );
	    
		//searchAB();
		
		
		//B�̌�����minPrimeNumLength(=5�P�^�j�ȏ�̊ԁAA,B��T��
		while ( PrimeNumALength <=  PrimeNumBLength) {
			searchAB();
			PrimeNumALength++;
			PrimeNumBLength--;
		}
		
		
		//���ʂ��o��
		System.out.println("----------" );
		
		//N���o��
		//CompositeNumN = PrimeNumA * PrimeNumB;
		System.out.print("Decrease Approach CompositeNumN = " + 
		       formatNumber(CompositeNumNLast) + " = " + 
			   formatNumber(PrimeNumALast) + " * "+
		       formatNumber(PrimeNumBLast) + 
		       " searchLimt = " + searchLimit + " checkPrimeMode=" + checkPrimeMode);
		
		//���s���Ԍv��
		long stopTime = System.currentTimeMillis();
		
		//���s���Ԃ��o��
		System.out.println(" Run Time = " + (stopTime - startTime) + " ms " );
		
	}
	
	private static void searchAB(){
		
		System.out.println("----------" );
		System.out.println("PrimeNum A Length = " + PrimeNumALength );
		System.out.println("PrimeNum B Length = " + PrimeNumBLength );
		
		//A��T������
		//A�̏����l���Z�b�g
		StringBuffer buf = new StringBuffer();
		buf.append("1");
	    for (int i = 1; i < PrimeNumALength; i++) {
	            buf.append("0");
	        }
		PrimeNumA = new BigInteger(buf.toString());
				
		//A,B��T������
		for(int j=1;j<=searchLimit;j++){
			
			//System.out.println("PrimeNumA2(initial) = " + PrimeNumA );
			PrimeNumA = nextPrimeNum(PrimeNumA, 0);
		
			//System.out.println("PrimeNumA2  = " + PrimeNumA );
	
			//B��T������		
			//A�AB�̌����������^�قȂ�P�[�X�ŏꍇ����
			/*if(PrimeNumALength==PrimeNumBLength){
				PrimeNumB = PrimeNumA.add(BigInteger.ONE);  //A��B�̌��������� �ꍇ�A(�m�肵��A�j-1 ���Z�b�g
			}else{
				buf = new StringBuffer();
				buf.append("1");
			    for (int i = 1; i < PrimeNumBLength; i++) {
			            buf.append("0");
			    }
				PrimeNumB = new BigInteger(buf.toString());   //A��B�̌������قȂ� �ꍇ�AB^10-1  ���Z�b�g
			}
			System.out.println("PrimeNumB(initial) = " + PrimeNumB );
			*/		
			//B�T���J�n
			//N���ƂȂ�ő��B�Q�����߂�
			PrimeNumB = CompositeNumNMax.divide(PrimeNumA);
			CompositeNumN = PrimeNumA.multiply(PrimeNumB);
			//System.out.println("PrimeNumB(2nd candidate(max))  = " + PrimeNumB +
			//				"   N = " + CompositeNumN );
					
			//���N�����݂�N�ȉ���
			if( CompositeNumN.compareTo(CompositeNumNLast) <=0){
			//�ȉ��̏ꍇ
				//B�AN���m��i�������Ȃ��j
				//System.out.println("PrimeNumB(2nd candidate(max)) is no candidate");
			//�傫���ꍇ
			}else{
				//�P�傫���f����T��
				PrimeNumB = previousPrimeNum(PrimeNumB, 0);
				CompositeNumN = PrimeNumA.multiply(PrimeNumB);
						
				//��₪���݂�N�ȏォ
				if(CompositeNumN.compareTo(CompositeNumNLast) >=0){
				//B2��B�Ƃ��Ċm��
					PrimeNumALast = PrimeNumA;
					PrimeNumBLast = PrimeNumB;
					CompositeNumNLast = CompositeNumN; 
					System.out.println("PrimeNumB(2nd candidate is optimum solution)  = " + PrimeNumBLast +
									"   PrimeNumA2 = " + PrimeNumALast );
					System.out.println("                                            N = " + CompositeNumNLast );
				}else{
					//System.out.println("PrimeNumB(2nd candidate is no optimum solution)  = " + PrimeNumB +
					//				"   N = " + CompositeNumN );
				}
			}
			
			//����A�AB��T��
			//A���P���Z���Ă���
			PrimeNumA = PrimeNumA.add(BigInteger.ONE);
		}
		
	}
	
	private static String formatNumber(BigInteger convNum){
		String tempStr=convNum.toString();
		int len=tempStr.length();
		//System.out.println("String len " + convNum + " = " + len);
		
		StringBuffer buf = new StringBuffer();
		//buf.append("1");
	    for (int i = 0; i < len-1; i++) {
	            buf.append(tempStr.substring(i,i+1));
	            if((len-i-1)%5==0){
	            	buf.append(" ");
	            }
	        }
	    buf.append(tempStr.substring(len-1,len));
	    buf.append("(" + len + ")");
		
		return buf.toString();
	}
	
	//���̑f����T�����郁�\�b�h
	private static BigInteger nextPrimeNum(BigInteger startNumber,int mode){
		while(isPrimeNum(startNumber,checkPrimeMode)==0){
			startNumber = startNumber.add(BigInteger.ONE);
			//System.out.println("PrimeNumB(2nd check no candidate)  = " + startNumber );
			//startNumber.add(startNumber);
		}
		
		//�f����ԋp
		return startNumber;
	}

	
	//�O�̑f����T�����郁�\�b�h
	private static BigInteger previousPrimeNum(BigInteger startNumber,int mode){
		while(isPrimeNum(startNumber,checkPrimeMode)==0){
			startNumber = startNumber.subtract(BigInteger.ONE);
			
			//5���ȉ��ɂȂ�����0��Ԃ�
			if(startNumber.compareTo(minPrimeNum) <= 0){
					return BigInteger.ZERO;
			}
		}
		
		//�f����ԋp
		return startNumber;
	}
	
	//�f�����ǂ����𔻒f���郁�\�b�h
	private static int isPrimeNum(BigInteger PrimeNumber,int mode){
		
		//mode����@0=���߂����A1=isProbablePrime�֐�
		if(mode==0){
			//�^����ꂽ�����̕�������T���̏���Ƃ���
			//long rootPrimeNumber=PrimeNumber;
			BigInteger rootPrimeNumber = sqrt(PrimeNumber);
			for ( BigInteger i = new BigInteger("2"); i.compareTo(rootPrimeNumber) < 0;i = i.add(BigInteger.ONE)) {
	            if (PrimeNumber.remainder(i)  == BigInteger.ZERO) // ����؂��Ƒf���ł͂Ȃ�
	                return 0;          // ����ȏ�̌J�Ԃ��͕s�v
	        }
	       // �Ō�܂Ŋ���؂�Ȃ�����
			return 1;
		}else if(mode==1){
			if(PrimeNumber.isProbablePrime(1)){
				return 1;
			}else{
				return 0;
			}
		}else{
		//mode���w��O
			return -1;
			
		}
		
	}
	
	//���������Z�o
	private static BigInteger sqrt(BigInteger val) {
        BigInteger b1;
        BigInteger b2 = val;
        do {
        	b1 = b2;
            b2 = b1.pow(2).add(val).divide(b1.shiftLeft(1));
        } while(b2.compareTo(b1) < 0);      
            return b1;
    }


}