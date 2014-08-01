package hogehoge.com;

public class PrimeNumAB {
	
	//定数の定義
	static final long minPrimeNum = 99999;    //最小の素数（５ケタ）
	
	//変数の定義
	static int CompositeNumNLength;    //Nの桁数
	static int PrimeNumALength;        //Aの桁数
	static int PrimeNumBLength;        //Bの桁数
	
	static long PrimeNumA;        //Aの確定値
	static long PrimeNumB;        //Bの確定値
	static long PrimeNumB2;       //Bの第２候補
	static long CompositeNumN;    //Nの確定値
	static long CompositeNumN2;   //Nの第２候補
	
	int PrimeNumAList[];        //Aの候補を格納する数列
	int PrimeNumBList[];        //Bの候補を格納する数列
	int CompositeNumNList[];    //Nの候補を格納する数列

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//実行時間計測
		long startTime = System.currentTimeMillis();
		
		//入力値の取得
		CompositeNumNLength = Integer.parseInt(args[0]);
		
		//最初の桁数の決定
		PrimeNumALength = (int)Math.ceil(CompositeNumNLength/2.0);
		PrimeNumBLength = CompositeNumNLength/2;
		
		System.out.println("CompositNum N Length = " + CompositeNumNLength );
		System.out.println("PrimeNum A Length = " + PrimeNumALength );
		System.out.println("PrimeNum B Length = " + PrimeNumBLength );
		
		//Aを探索する
		PrimeNumA = (long)Math.pow(10, PrimeNumALength) -1;
		PrimeNumA = previousPrimeNum(PrimeNumA, 0);
		
		System.out.println("PrimeNumA  = " + PrimeNumA );

		//Bを探索する		
		//A、Bの桁数が同じ／異なるケースで場合分け
		if(PrimeNumALength==PrimeNumBLength){
			PrimeNumB = PrimeNumA - 1;                            //AとBの桁数が同じ 場合、(確定したA）-1 をセット
		}else{
			PrimeNumB = (long)Math.pow(10, PrimeNumBLength) - 1;    //AとBの桁数が異なる 場合、B^10-1  をセット
		}		
		
		//次に小さい素数を探す
		PrimeNumB = previousPrimeNum(PrimeNumB, 0);
		
		//第一候補とする
		CompositeNumN = PrimeNumA * PrimeNumB;
		System.out.println("PrimeNumB(1st candidate)  = " + PrimeNumB 
		           		 + "   N = " + CompositeNumN);
		
		//ループ３（B探索 第２候補）開始
		//N桁となる最大のB２を求める
		PrimeNumB2= ((long)Math.pow(10, CompositeNumNLength)-1) / PrimeNumA;
		CompositeNumN2 = PrimeNumA * PrimeNumB2;
		System.out.println("PrimeNumB(2nd candidate(max))  = " + PrimeNumB2 +
				"   N = " + CompositeNumN2 );
		
		//候補N2が現在のN以下か
		if( CompositeNumN2 <= CompositeNumN){
		//以下の場合
			//B、Nを確定（何もしない）
			System.out.println("PrimeNumB(2nd candidate(max)) is no candidate");
		//大きい場合
		}else{
			//１つ小さい素数を探す
			PrimeNumB2 = previousPrimeNum(PrimeNumB2, 0);
			CompositeNumN2 = PrimeNumA * PrimeNumB2;
			
			//候補が現在のN以上か
			if(CompositeNumN2 >= CompositeNumN){
				//B2をBとして確定
				PrimeNumB = PrimeNumB2;
				CompositeNumN = CompositeNumN2; 
				System.out.println("PrimeNumB(2nd candidate is optimum solution)  = " + PrimeNumB +
						"   N = " + CompositeNumN );
			}else{
				System.out.println("PrimeNumB(2nd candidate is no optimum solution)  = " + PrimeNumB2 +
						"   N = " + CompositeNumN2 );
			}
		}
		
		//結果を出力
		System.out.println("----------" );
		
		//Nを出力
		CompositeNumN = PrimeNumA * PrimeNumB;
		System.out.println("CompositeNumN = " + CompositeNumN + " = " + PrimeNumA + " * " + PrimeNumB );
		
		//実行時間計測
		long stopTime = System.currentTimeMillis();
		
		//実行時間を出力
		System.out.println("Run Time = " + (stopTime - startTime) + " ms " );
		
	}
	
	//次の素数を探索するメソッド
	private static long nextPrimeNum(long startNumber,int mode){
		while(isPrimeNum(startNumber,0)==0){
			System.out.println("PrimeNumB(2nd check no candidate)  = " + startNumber );
			startNumber++;
		}
		
		//素数を返却
		return startNumber;
	}

	
	//前の素数を探索するメソッド
	private static long previousPrimeNum(long startNumber,int mode){
		while(isPrimeNum(startNumber,0)==0){
			startNumber--;
			
			//5桁以下になったら0を返す
			if(startNumber <= minPrimeNum){
					return 0;
			}
		}
		
		//素数を返却
		return startNumber;
	}
	
	//素数かどうかを判断するメソッド
	private static int isPrimeNum(long PrimeNumber,int mode){
		int i=2;
		//与えられた整数の平方根を探索の上限とする
		//long rootPrimeNumber=PrimeNumber;
		long rootPrimeNumber=(long)Math.sqrt(PrimeNumber);
		for ( i = 2; i < rootPrimeNumber; i++) {
            if (PrimeNumber % i == 0) // 割り切れると素数ではない
                return 0;          // それ以上の繰返しは不要
        }
       // 最後まで割り切れなかった
		return 1;
	}

}
