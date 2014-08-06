package hogehoge.com;

//Œµ–§‰ğ‚ğ‹‚ß‚é‚½‚ß‚ÌƒNƒ‰ƒX
public class ExactSolution {
	static final int NumLen = 12;
	static final int NumLenA = 6;
	static final int NumLenB = 6;
	static final int UpLimitLen =100000;
	static final int UpLimit =100000;
	static boolean[] sieve = new boolean[UpLimit];
	static int[] sosuList = new int[UpLimit/5];
	
	public static void main(String[] args){
		sieve();
		sosuList();
		
		int n=0;
		for (int i=0; i<sosuList.length ; i++){
			if(sosuList[i] != 0){
				System.out.print(sosuList[i]);
				n++;
				if(n%100 ==0){
					System.out.println();
				}else{
					System.out.print(" ");
				}
			}
		}
		System.out.println();
		System.out.println("The number of Prime Number = " + n);
		
		//Œµ–§‰ğ‚ğZo
		long candidate[] = {0,0};
		long max=(long)Math.pow(10, NumLen)-1;
		long min=(long)Math.pow(10, NumLenA-1);
		int count=0;
		
		System.out.println("The number of max = " + max);
		System.out.println("The number of min = " + min);
				
		A:for (long N=max; N>=min ; N -= 2){
			B:for (int i=0; i<sosuList.length ; i++){
				if(sosuList[i] != 0){
					if( N % sosuList[i] == 0 ){
						count++;
						if(count>=3){
							count=0;
							break B;
						}
					
						if(sosuList[i] < min){
							count=0;
							break B;
						}
						
						candidate[count-1]=sosuList[i];	
						if(count==2){
							break A;
						}
					}
				}else if(sosuList[i] == 0){
					break B;
				}
			}			
		}
		
		//Œ‹‰Ê‚ğo—Í
		if(count==2){
			System.out.println("Exact Solution exsits : N="  + candidate[0]*candidate[1] +
					                                    " A=" + candidate[0] +
					                                    " B=" + candidate[1]);
		}else{
			System.out.println("Exact Solution does not exists");
		}
	}
	
	private static void sosuList(){
		//ƒ[ƒ‚Å‰Šú‰»
		for (int i=0; i<sosuList.length ; i++){
			sosuList[i] = 0;
		}
		
		//‘f”‚Ì‚İƒZƒbƒg
		int count=0;
		
		for (int i=2; i<sieve.length ; i++){
			if(sieve[i]){
				sosuList[count] = i;
				count++;
			}
		}		
	}
	
	private static void sieve(){
		//true‚Å‰Šú‰»
		for (int i=0; i<sieve.length ; i++){
			sieve[i] = true;
		}
		
		//‚O‚Æ‚P‚ÍœŠO
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
