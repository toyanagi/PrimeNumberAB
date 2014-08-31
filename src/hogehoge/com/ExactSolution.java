package hogehoge.com;

import java.math.BigInteger;

//�����������߂邽�߂̃N���X
public class ExactSolution {
	static int NumLen = 10;
	static final int NumLenA = 5;
	static int UpLimit =99999;
	static boolean[] sieve ;
	static int[] sosuList ;
	
	public static void main(String[] args){
		NumLen = Integer.parseInt(args[0]);
		exactSol(NumLen);
	}
	
	public static long[] exactSol(int num){
		//�f����T���͈͂��w��
		if(num % 2 == 1){  //��Ȃ�
			UpLimit=(int)Math.pow(10, ( (num+1)/2) ) / 3 ;
		}else{  //�����Ȃ�
			UpLimit=(int)Math.pow(10, num/2 );
		}
		
		sieve = new boolean[UpLimit];
		sosuList = new int[UpLimit/5];
		
		System.out.println("UpLimit = " + UpLimit );
		
		//���s���Ԍv��
		long startTime = System.currentTimeMillis();
		
		//�f���T���p�}�X�N�쐬
		BigNumber.initPrimeList();
		BigNumber.makePrimeMask();
		
		//�f�����X�g�̍쐬
		sieve();
		sosuList();
		//���s���Ԍv��
		long stopTime = System.currentTimeMillis();
				
		//���s���Ԃ��o��
		System.out.println("Run Time(Prime Number search) = " + (stopTime - startTime) + " ms " );
		
		int n=0;
		for (int i=0; i<sosuList.length ; i++){
			if(sosuList[i] != 0){
			//	System.out.print(sosuList[i]);
				n++;
			//	if(n%100 ==0){
			//		System.out.println();
			//	}else{
			//		System.out.print(" ");
			//	}
			}
		}
		System.out.println();
		System.out.println("The number of Prime Number = " + n);
		
		//���������Z�o
		long candidate[] = {0,0};
		long max=(long)Math.pow(10, num)-1;  //999999999
		long min=(long)Math.pow(10, NumLenA-1); //10000
		int count=0;
		
		System.out.println("The number of max = " + max);
		System.out.println("The number of min = " + min);
		
		
		A:for (long N=max; N>=min ; N -= 1){
			/* /�}�X�N���g���Ĕ���
			int maskoffset = (int)(max % (long)BigNumber.maskLen) ;
			if(!BigNumber.primeMask[maskoffset]){
			int maskoffset = (int)(max % (long)BigNumber.maskLen) ;
				maskoffset = (maskoffset == 0 ? BigNumber.maskLen-1 : maskoffset -1 );
				continue;
			}
			maskoffset = (maskoffset == 0 ? BigNumber.maskLen-1 : maskoffset-1 );
			*/
			//System.out.println(" candidate = N :" + N);
			B:for (int i=0; i<sosuList.length ; i++){
				if(sosuList[i] != 0){
					if( N % sosuList[i] == 0 ){
						count++;
						//System.out.println(" candidate = " + count + ":" + sosuList[i]);
						if(count>=3){
							count=0;
							break B;
						}
					
						if(sosuList[i] < min){
							count=0;
							break B;
						}
						
						candidate[count-1]=sosuList[i];	
						
						if(count==1){
							if( N % candidate[0] != 0){
								count=0;
								break B;
							}
							candidate[1] = N / candidate[0];
							if(isPrimeNum(candidate[1],0)==1){
								break A;
							}
							
						}
						/*
						if(count==2){
							System.out.println(" candidate = N :" + candidate[0] * candidate[1] +
			                        " A=" + candidate[0] +
			                        " B=" + candidate[1]); 
							if(candidate[0] * candidate[1] == N){
								
							
								break A;
							}else{
								count=0;
								break B;
							}
						}
						*/
					}
				}else if(sosuList[i] == 0){
					System.out.println(" N is Prime = " + N);
					count=0;
					break B;
				}
			}			
		}
		
		//���ʂ��o��
		if(count==1){
			System.out.println("Exact Solution exsits : "+
		            "N="  + formatNumber(candidate[0]*candidate[1]) +
					" A=" + formatNumber(candidate[0]) +
				    " B=" + formatNumber(candidate[1]));
			//System.out.println(formatNumber(candidate[0]*candidate[1]));
			//System.out.println(formatNumber(candidate[0]));
			//System.out.println(formatNumber(candidate[1]));
			
		}else{
			System.out.println("Exact Solution does not exists");
		}
		//���s���Ԍv��
		stopTime = System.currentTimeMillis();
						
		//���s���Ԃ��o��
		System.out.println("Run Time = " + (stopTime - startTime) + " ms " );
		
		long result[] = new long[3];
		
		result[0]=candidate[0];
		result[1]=candidate[1];
		result[2]=candidate[0] * candidate[1];
		
		return result;
		
	}
	
	private static void sosuList(){
		//�[���ŏ�����
		for (int i=0; i<sosuList.length ; i++){
			sosuList[i] = 0;
		}
		
		//�f���̂݃Z�b�g
		int count=0;
		
		for (int i=2; i<sieve.length ; i++){
			if(sieve[i]){
				sosuList[count] = i;
				count++;
			}
		}		
		System.out.println("Last Prime Number = " +  sosuList[count-1]);
		System.out.println(" count = " +  Integer.toString(count));
	}
	
	private static String formatNumber(long convNum){
		String tempStr=Long.toString(convNum);
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
	
	private static int isPrimeNum(long PrimeNumber,int mode){
		System.out.println("Prime Number check start : " +  PrimeNumber);
		
		//�f���}�X�N�ŉ�����
		//int maskoffset = (int)(PrimeNumber % BigNumber.maskLen) ;
		//if(!BigNumber.primeMask[maskoffset]) return 0;
		
		//�^����ꂽ�����̕�������T���̏���Ƃ���
		 long rootPrimeNumber = (long)Math.sqrt(PrimeNumber);
		 for (int i=0; i<sosuList.length ; i++){
			 if(sosuList[i] != 0){
					if( PrimeNumber % sosuList[i] == 0 ){
						//System.out.println("Prime Number check  : not prime");
						return 0;
					}
					if(sosuList[i]>rootPrimeNumber){
						
						System.out.println("Prime Number check  : prime");
						return 1;
					}
	                //return 0;          // ����ȏ�̌J�Ԃ��͕s�v
	        }else{
	        	return 1;
	        }

		 }
		 return -1;
		
	}
	
	private static void sieve(){
		//true�ŏ�����
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
	}
}
