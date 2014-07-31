package hogehoge.com;

public class PrimeNumAB {
	
	//定数の定義
	static final long minPrimeNum = 10000;    //最小の素数（５ケタ）
	
	//変数の定義
	static int CompositeNumNLength;    //Nの桁数
	static int PrimeNumALength;        //Aの桁数
	static int PrimeNumBLength;        //Bの桁数
	
	static long PrimeNumA;        //Aの確定値
	static long PrimeNumB;        //Bの確定値
	static long PrimeNumB2;        //Bの第２候補
	static long CompositeNumN;    //Nの確定値
    
	int PrimeNumAList[];        //Aの候補を格納する数列
	int PrimeNumBList[];        //Bの候補を格納する数列
	int CompositeNumNList[];    //Nの候補を格納する数列

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
		
		//ループ１（A探索）開始
		while(isPrimeNum(PrimeNumA,0)==0){
			PrimeNumA--;
			if(PrimeNumA < minPrimeNum){
					break;
			}
		}
		System.out.println("PrimeNumA  = " + PrimeNumA );

		//Bを探索する		
		//A、Bの桁数が同じ／異なるケースで場合分け
		if(PrimeNumALength==PrimeNumBLength){
			PrimeNumB = PrimeNumA - 1;                            //AとBの桁数が同じ 場合、(確定したA）-1 をセット
		}else{
			PrimeNumB = (long)Math.pow(10, PrimeNumBLength) - 1;    //AとBの桁数が異なる 場合、B^10-1  をセット
		}		
		
		//ループ２（B探索 初期試行）開始
		while(isPrimeNum(PrimeNumB,0)==0){
			PrimeNumB--;
			if(PrimeNumB < minPrimeNum){
					break;
			}
		}
		System.out.println("PrimeNumB(1st candidate)  = " + PrimeNumB );
		
		//ループ３（B探索 第２候補）開始
		//B2をセット
		PrimeNumB2 = (int)Math.pow(10, PrimeNumBLength) + 1;
		
		//A*B2がN桁以上でないかチェック
		//System.out.println("PrimeNumB(2nd check)  = " + PrimeNumB2 + "   N = " + PrimeNumA * PrimeNumB2 );
		while(PrimeNumA * PrimeNumB2 <= (long)Math.pow(10, CompositeNumNLength)){
			if(isPrimeNum(PrimeNumB2,0)==0){
				System.out.println("PrimeNumB(2nd check　no candidate)  = " + PrimeNumB2 + "   N = " + PrimeNumA * PrimeNumB2 );
				PrimeNumB2++;                   //素数でないならB2に1加算、次へ
			
			}else{
				PrimeNumB = PrimeNumB2;     //素数ならBを更新
				System.out.println("PrimeNumB(2nd candidate)  = " + PrimeNumB + "   N = " + PrimeNumA * PrimeNumB2 );
				PrimeNumB2++;               //らB2に1加算、次を探す
			}
		}
		
		//最終のBを出力
		System.out.println("PrimeNumB(last)  = " + PrimeNumB );
		
		//Nを出力
		CompositeNumN = PrimeNumA * PrimeNumB;
		System.out.println("CompositeNumN = " + CompositeNumN + " = " + PrimeNumA + " * " + PrimeNumB );
		
	}
	
	private static int isPrimeNum(long PrimeNumber,int mode){
		int i=2;
		for ( i = 2; i < PrimeNumber; i++) {
            if (PrimeNumber % i == 0) // 割り切れると素数ではない
                return 0;          // それ以上の繰返しは不要
        }
       // 最後まで割り切れなかった
		return 1;
	}

}
