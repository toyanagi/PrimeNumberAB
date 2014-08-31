package hogehoge.com;

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class S1GP {
	//�萔�̒�`
		static final BigNumber minPrimeNum = new BigNumber("10000");    //�ŏ��̑f���i�T�P�^�j
		static final int minPrimeNumLength = 5;    //�ŏ��̑f���̌����i�T�P�^�j
		static int searchLimit = 1;    //�e�����Ƃ̒T����
		static final int checkPrimeMode = 1;    //�f�����胂�[�h
		
		//�ϐ��̒�`
		static int CompositeNumNLength;    //N�̌���
		static int PrimeNumALength;        //A�̌���
		static int PrimeNumBLength;        //B�̌���
		
		static BigNumber PrimeNumA;        //A�̌��
		static BigNumber PrimeNumB;        //B�̌��
		static BigNumber CompositeNumN;    //N�̌��
		static BigNumber CompositeNumNMax;    //N�̍ő�l
		static BigNumber PrimeNumALast;        //A�̊m��l
		static BigNumber PrimeNumBLast;        //B�̊m��l
		static BigNumber CompositeNumNLast;    //N�̊m��l
		
		//�ł��؂蔻��p
		static boolean isClose=false;
		static long startTime=-1;
		static long maxTime=0;
		static long limitTIme=50*1000;
		static long previousTime=0;
		static long nowTime=0;
		

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			//���s���Ԍv��
			long startTime = System.currentTimeMillis();
			
			//������
			CompositeNumNLast=BigNumber.ZERO;
			PrimeNumALast =BigNumber.ZERO;
			PrimeNumBLast=BigNumber.ZERO;
			
			//���͒l�̎擾
			// java S1GP Y�̌��� �ł��؂蔻�莞�� �T�����x�� �����𔻒肷��ő包��
			CompositeNumNLength = Integer.parseInt(args[0]);
			
			//�t�@�C���o�͐�
			String outfile = "C:\\Users\\tomo\\output_"+ CompositeNumNLength + ".txt";
			
			//�f�t�H���g�l��ݒ�
			limitTIme=50*1000;  //�ł��؂蔻�莞��(�f�t�H���g50�b)
			searchLimit = 1;    //�T�����x�� (�f�t�H���g1)
			int useExactSol = 15;    //�����𔻒肷��ő包�� (�f�t�H���g15)
			
			if(args.length>=2) limitTIme = Integer.parseInt(args[1]) * 1000;
			if(args.length>=3) searchLimit = Integer.parseInt(args[2]);
			if(args.length>=4) useExactSol = Integer.parseInt(args[3]);

			//�ݒ�l�̕\��
			System.out.println("CompositNum N Length = " + CompositeNumNLength );
			System.out.println("Limit Time = " + limitTIme + "msec" );
			System.out.println("Serach Level = " + searchLimit  );
			System.out.println("Use Exact Solution Method = " + useExactSol  );
			
			//���������Z�o
			if(CompositeNumNLength <= useExactSol){
				long result[] = ExactSolution.exactSol(CompositeNumNLength);
				
				//�t�@�C���o��
				PrintWriter pw = null;
		        try {
		            // �o�͐�t�@�C��
		            File file = new File(outfile);
		            
		            pw = new PrintWriter(file);
		            pw.println(""  + result[0] + "," 
		            		       + result[2] + ","
		            		       + result[2]);
		            
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            if (pw != null) {
		                // �X�g���[���͕K�� finally �� close ���܂��B
		                pw.close();
		            }
		        }
		      //���s���Ԍv��
				long stopTime = System.currentTimeMillis();
				
				//���s���Ԃ��o��
				System.out.println(" Run Time = " + (stopTime - startTime) + " ms " );
				return;
			}
			
			//�œK�����Z�o
			//BigNumber�N���X�p�f�����X�g�̍쐬
			BigNumber.initPrimeList();
			
			//�f���T���p�}�X�N�쐬
			BigNumber.makePrimeMask();
						
			//�ŏ��̌����̌���
			PrimeNumALength =  minPrimeNumLength;
			PrimeNumBLength = CompositeNumNLength - minPrimeNumLength +1;
			
			//�ő��N���Z�b�g
			StringBuffer buf = new StringBuffer();
		    for (int i = 1; i <= CompositeNumNLength; i++) {
		            buf.append("9");
		    }
		    CompositeNumNMax = new BigNumber(buf.toString());
		    System.out.println("CompositeNumNMax = " + CompositeNumNMax );
		    
			//searchAB();
			
			/// ���ꂽ�������߂������@ex)100�P�^�Ȃ�5�E95���ˁE�E�E��49�E51
			//B�̌�����minPrimeNumLength(=5�P�^�j�ȏ�̊ԁAA,B��T��
			while ( PrimeNumALength <=  PrimeNumBLength) {
				long temp1 = System.currentTimeMillis();
				searchAB();
				judgeClose();
				if(isClose) break;
				long temp2 = System.currentTimeMillis();
				System.out.println(" Run Time = " + (temp2 - temp1) + " ms " );
				PrimeNumALength++;
				PrimeNumBLength--;
			}
			
		    
		    // �߂����������ꂽ�����@ex)100�P�^�Ȃ�49�E51���ˁE�E�E��5�E95
			//B�̌�����minPrimeNumLength(=5�P�^�j�ȏ�̊ԁAA,B��T��
			while ( PrimeNumALength >=  minPrimeNumLength) {
				//searchAB();
				PrimeNumALength--;
				PrimeNumBLength++;
			}
			
			
			
			//���ʂ��o��
			System.out.println("----------" );
			
			PrintWriter pw = null;
	        try {
	            // �o�͐�t�@�C��
	            File file = new File(outfile);
	            
	            pw = new PrintWriter(file);
	            pw.println(""  + PrimeNumALast.getWordsRaw() + "," 
	            		       + PrimeNumBLast.getWordsRaw() + ","
	            		       + CompositeNumNLast.getWordsRaw());
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (pw != null) {
	                // �X�g���[���͕K�� finally �� close ���܂��B
	                pw.close();
	            }
	        }
			
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
		
		private static void judgeClose(){
			if(startTime <0)
				startTime = System.currentTimeMillis();
			nowTime = System.currentTimeMillis() - startTime;
			
			//�ł��؂蔻��
			if(limitTIme - nowTime < maxTime * 3)
				isClose =true;
			
			//�ő厞�Ԃ̍X�V
			if(	nowTime - previousTime > maxTime)
				maxTime=nowTime - previousTime;
			
			//���񎞍����L��
			previousTime = nowTime;
			//����
			
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
			PrimeNumA = new BigNumber(buf.toString());
					
			//A,B��T������
			for(int j=1;j<=searchLimit;j++){
				
				//System.out.println("PrimeNumA2(initial) = " + PrimeNumA );
				PrimeNumA = nextPrimeNum(PrimeNumA, 0);
			
				//System.out.println("PrimeNumA2  = " + PrimeNumA );
		
				//B��T������		
				//A�AB�̌����������^�قȂ�P�[�X�ŏꍇ����
				/*if(PrimeNumALength==PrimeNumBLength){
					PrimeNumB = PrimeNumA.add(BigNumber.ONE);  //A��B�̌��������� �ꍇ�A(�m�肵��A�j-1 ���Z�b�g
				}else{
					buf = new StringBuffer();
					buf.append("1");
				    for (int i = 1; i < PrimeNumBLength; i++) {
				            buf.append("0");
				    }
					PrimeNumB = new BigNumber(buf.toString());   //A��B�̌������قȂ� �ꍇ�AB^10-1  ���Z�b�g
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
				PrimeNumA = PrimeNumA.add(BigNumber.ONE);
			}
			
		}
		
		private static String formatNumber(BigNumber convNum){
			return convNum.toString();
			/*String tempStr=convNum.toString();
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
			*/
		}
		
		//���̑f����T�����郁�\�b�h
		private static BigNumber nextPrimeNum(BigNumber startNumber,int mode){
			//System.out.println("NextP");
			//�f���}�X�N�̃`�F�b�N
			int maskoffset = startNumber.remainder(BigNumber.maskLenBN).words[0];
			while(BigNumber.primeMask[maskoffset]){
				maskoffset = (maskoffset == BigNumber.maskLen-1 ? 0 : maskoffset+1 );				
			}
			//if(!BigNumber.primeMask[maskoffset]) return 0;
			
			while(isPrimeNum(startNumber,checkPrimeMode)==0){
				startNumber = startNumber.add(BigNumber.ONE);
				//System.out.println("PrimeNumB(2nd check no candidate)  = " + startNumber );
				//startNumber.add(startNumber);
			}
			
			//�f����ԋp
			return startNumber;
		}

		
		//�O�̑f����T�����郁�\�b�h
		private static BigNumber previousPrimeNum(BigNumber startNumber,int mode){
			//System.out.println("PreviousP");
			//�f���}�X�N�̃`�F�b�N
			int maskoffset = startNumber.remainder(BigNumber.maskLenBN).words[0];
			while(BigNumber.primeMask[maskoffset]){
				maskoffset = (maskoffset == 0 ? BigNumber.maskLen-1 : maskoffset-1 );				
			}
			while(isPrimeNum(startNumber,checkPrimeMode)==0){
				startNumber = startNumber.subtract(BigNumber.ONE);
				
				//5���ȉ��ɂȂ�����0��Ԃ�
				if(startNumber.compareTo(minPrimeNum) <= 0){
						return BigNumber.ZERO;
				}
			}
			
			//�f����ԋp
			return startNumber;
		}
		
		//�f�����ǂ����𔻒f���郁�\�b�h
		private static int isPrimeNum(BigNumber PrimeNumber,int mode){
			//System.out.print("isP : " + PrimeNumber);
			//mode����@0=���߂����A1=isProbablePrime�֐�
			if(mode==0){
				//�^����ꂽ�����̕�������T���̏���Ƃ���
				//long rootPrimeNumber=PrimeNumber;
				/* igNumber rootPrimeNumber = sqrt(PrimeNumber);
				for ( BigNumber i = new BigNumber("2"); i.compareTo(rootPrimeNumber) < 0;i = i.add(BigNumber.ONE)) {
		            if (PrimeNumber.remainder(i)  == BigNumber.ZERO) // ����؂��Ƒf���ł͂Ȃ�
		                return 0;          // ����ȏ�̌J�Ԃ��͕s�v
		        } */
		       // �Ō�܂Ŋ���؂�Ȃ�����
				return 1; 
			}else if(mode==1){
				if(PrimeNumber.isProbablePrime(50)){
					//System.out.println("true");
					return 1;
				}else{
					//System.out.println("false");
					return 0;
				}
			}else{
			//mode���w��O
				return -1;
				
			}
			
		}
		
		//�f�����X�g���쐬
		public static int[] sosuList(int max,int num){
			//�ӂ邢���쐬(max�܂Ń`�F�b�N�j
			boolean sieve[]=makeSieve(max);
			
			//�f�����X�g�쐬
			int list[]=new int[num];
			
			//�[���ŏ�����
			for (int i=0; i<list.length ; i++){
				list[i] = 0;
			}
			
			//�f���̂݃Z�b�g
			int count=0;
			
			for (int i=2; i<sieve.length ; i++){
				if(sieve[i]){
					list[count] = i;
					count++;
					if(count>=num) break;
				}
			}		
			System.out.println("Last Prime Number = " +  list[count-1]);
			return list;
		}
		
		//�f���̂ӂ邢�����{ (num�܂ł̃G���g�X�l�e�X�̂ӂ邢�j
		private static boolean[] makeSieve(int num){
			//true�ŏ�����
			boolean sieve[]= new boolean[num+1];
			for (int i=0; i<sieve.length ; i++){
				sieve[i] = true;
			}
			
			//�O�ƂP�͏��O
			sieve[0] = false;
			sieve[1] = false;
			
			int max = (int)Math.sqrt(sieve.length);
					
			for (int p=2; p<=max ; p++){
				if(sieve[p]){
					for (int i=p*2; i<sieve.length ; i += p ){
						sieve[i] = false;
					}
				}
			}
			return sieve;
		}
}
