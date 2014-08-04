package hogehoge.com;

import java.math.BigInteger;

public class PrimeNumAB {
	
	//定数の定義
	static final BigInteger minPrimeNum = new BigInteger("10000");    //最小の素数（５ケタ）
	static final long minPrimeNumLength = 5;    //最小の素数の桁数（５ケタ）
	
	//変数の定義
	static int CompositeNumNLength;    //Nの桁数
	static int PrimeNumALength;        //Aの桁数
	static int PrimeNumBLength;        //Bの桁数
	
	static BigInteger PrimeNumA;        //Aの候補
	static BigInteger PrimeNumB;        //Bの候補
	static BigInteger CompositeNumN;    //Nの候補
	static BigInteger CompositeNumNMax;    //Nの最大値
	static BigInteger PrimeNumALast;        //Aの確定値
	static BigInteger PrimeNumBLast;        //Bの確定値
	static BigInteger CompositeNumNLast;    //Nの確定値
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//実行時間計測
		long startTime = System.currentTimeMillis();
		
		//初期化
		CompositeNumNLast=BigInteger.ZERO;
		PrimeNumALast =BigInteger.ZERO;
		PrimeNumBLast=BigInteger.ZERO;
		
		//入力値の取得
		CompositeNumNLength = Integer.parseInt(args[0]);
		System.out.println("CompositNum N Length = " + CompositeNumNLength );

		//最初の桁数の決定
		PrimeNumALength = (int)Math.ceil(CompositeNumNLength/2.0);
		PrimeNumBLength = CompositeNumNLength/2;
		
		//最大のNをセット
		StringBuffer buf = new StringBuffer();
	    for (int i = 1; i <= CompositeNumNLength; i++) {
	            buf.append("9");
	    }
	    CompositeNumNMax = new BigInteger(buf.toString());
	    System.out.println("CompositeNumNMax = " + CompositeNumNMax );
	    
		//searchAB();
		
		
		//Bの桁数がminPrimeNumLength(=5ケタ）以上の間、A,Bを探索
		while ( PrimeNumBLength >=  minPrimeNumLength) {
			searchAB();
			PrimeNumALength++;
			PrimeNumBLength--;
		}
		
		
		//結果を出力
		System.out.println("----------" );
		
		//Nを出力
		//CompositeNumN = PrimeNumA * PrimeNumB;
		System.out.println("CompositeNumN = " + CompositeNumNLast + " = " + PrimeNumALast + 
				" * " + PrimeNumBLast );
		
		//実行時間計測
		long stopTime = System.currentTimeMillis();
		
		//実行時間を出力
		System.out.println("Run Time = " + (stopTime - startTime) + " ms " );
		
	}
	
	private static void searchAB(){
		
		System.out.println("----------" );
		System.out.println("PrimeNum A Length = " + PrimeNumALength );
		System.out.println("PrimeNum B Length = " + PrimeNumBLength );
		
		//Aを探索する
		//Aの初期値をセット
		StringBuffer buf = new StringBuffer();
	    for (int i = 1; i <= PrimeNumALength; i++) {
	            buf.append("9");
	        }
		PrimeNumA = new BigInteger(buf.toString());
				
		//A,Bを探索する
		for(int j=1;j<=100;j++){
			
			//System.out.println("PrimeNumA2(initial) = " + PrimeNumA );
			PrimeNumA = previousPrimeNum(PrimeNumA, 0);
		
			//System.out.println("PrimeNumA2  = " + PrimeNumA );
	
			//Bを探索する		
			//A、Bの桁数が同じ／異なるケースで場合分け
			if(PrimeNumALength==PrimeNumBLength){
				PrimeNumB = PrimeNumA.subtract(BigInteger.ONE);  //AとBの桁数が同じ 場合、(確定したA）-1 をセット
			}else{
				buf = new StringBuffer();
			    for (int i = 1; i <= PrimeNumBLength; i++) {
			            buf.append("9");
			    }
				PrimeNumB = new BigInteger(buf.toString());   //AとBの桁数が異なる 場合、B^10-1  をセット
			}
			//System.out.println("PrimeNumB(initial) = " + PrimeNumB );
					
			//B探索開始
			//N桁となる最大のB２を求める
			PrimeNumB = CompositeNumNMax.divide(PrimeNumA);
			CompositeNumN = PrimeNumA.multiply(PrimeNumB);
			//System.out.println("PrimeNumB(2nd candidate(max))  = " + PrimeNumB +
			//				"   N = " + CompositeNumN );
					
			//候補Nが現在のN以下か
			if( CompositeNumN.compareTo(CompositeNumNLast) <=0){
			//以下の場合
				//B、Nを確定（何もしない）
				//System.out.println("PrimeNumB(2nd candidate(max)) is no candidate");
			//大きい場合
			}else{
				//１つ小さい素数を探す
				PrimeNumB = previousPrimeNum(PrimeNumB, 0);
				CompositeNumN = PrimeNumA.multiply(PrimeNumB);
						
				//候補が現在のN以上か
				if(CompositeNumN.compareTo(CompositeNumNLast) >=0){
				//B2をBとして確定
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
			
			//次のA、Bを探す
			//Aを１減算しておく
			PrimeNumA = PrimeNumA.subtract(BigInteger.ONE);
		}
		
	}
	
	//次の素数を探索するメソッド
	private static BigInteger nextPrimeNum(BigInteger startNumber,int mode){
		while(isPrimeNum(startNumber,0)==0){
			System.out.println("PrimeNumB(2nd check no candidate)  = " + startNumber );
			startNumber.add(startNumber);
		}
		
		//素数を返却
		return startNumber;
	}

	
	//前の素数を探索するメソッド
	private static BigInteger previousPrimeNum(BigInteger startNumber,int mode){
		while(isPrimeNum(startNumber,1)==0){
			startNumber = startNumber.subtract(BigInteger.ONE);;
			
			//5桁以下になったら0を返す
			if(startNumber.compareTo(minPrimeNum) <= 0){
					return BigInteger.ZERO;
			}
		}
		
		//素数を返却
		return startNumber;
	}
	
	//素数かどうかを判断するメソッド
	private static int isPrimeNum(BigInteger PrimeNumber,int mode){
		
		//mode判定　0=ためし割、1=isProbablePrime関数
		if(mode==0){
			//与えられた整数の平方根を探索の上限とする
			//long rootPrimeNumber=PrimeNumber;
			BigInteger rootPrimeNumber = sqrt(PrimeNumber);
			for ( BigInteger i = new BigInteger("2"); i.compareTo(rootPrimeNumber) < 0;i = i.add(BigInteger.ONE)) {
	            if (PrimeNumber.remainder(i)  == BigInteger.ZERO) // 割り切れると素数ではない
	                return 0;          // それ以上の繰返しは不要
	        }
	       // 最後まで割り切れなかった
			return 1;
		}else if(mode==1){
			if(PrimeNumber.isProbablePrime(1)){
				return 1;
			}else{
				return 0;
			}
		}else{
		//modeが指定外
			return -1;
			
		}
		
	}
	
	//平方根を算出
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
