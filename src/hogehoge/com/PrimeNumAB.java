package hogehoge.com;

public class PrimeNumAB {
	
	//定数の定義
	static final int minPrimeNum = 10000;    //最小の素数（５ケタ）
	
	//変数の定義
	static int CompositeNumNLength;    //Nの桁数
	static int PrimeNumALength;        //Aの桁数
	static int PrimeNumBLength;        //Bの桁数
	
	static int PrimeNumA;        //Aの確定値
	static int PrimeNumB;        //Bの確定値
	static int CompositeNumN;    //Nの確定値
    
	int PrimeNumAList[];        //Aの候補を格納する数列
	int PrimeNumBList[];        //Bの候補を格納する数列
	int CompositeNumNList[];    //Nの候補を格納する数列

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//入力値の取得
		CompositeNumN = Integer.parseInt(args[0]);
		
		//最初の桁数の決定
		PrimeNumALength = (int)Math.ceil(CompositeNumN/2.0);
		PrimeNumBLength = CompositeNumN/2;
		
		System.out.println("PrimeNum A Length = " + PrimeNumALength );
		System.out.println("PrimeNum B Length = " + PrimeNumBLength );
		
		//Aを探索する
		PrimeNumA = (int)Math.pow(10, PrimeNumALength) -1;
		
		//ループ１（A探索）開始
		while(0==0){
			if(isPrimeNum(PrimeNumA,0)==1){
				//A1を決定
				break;
			}else{
				PrimeNumA--;
				if(PrimeNumA >= minPrimeNum){
					//ループ１の先頭へ
				}else{
					break;
				}
			}
		}
		System.out.println("PrimeNum  = " + PrimeNumA );

	}
	
	private static int isPrimeNum(int PrimeNumber,int mode){
		int i=2;
		for ( i = 2; i < PrimeNumber; i++) {
            if (PrimeNumber % i == 0) // 割り切れると素数ではない
                return 0;          // それ以上の繰返しは不要
        }
       // 最後まで割り切れなかった
		return 1;
	}

}
