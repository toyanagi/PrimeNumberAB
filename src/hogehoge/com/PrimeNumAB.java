package hogehoge.com;

public class PrimeNumAB {
	
	//’è”‚Ì’è‹`
	static final int minPrimeNum = 10000;    //Å¬‚Ì‘f”i‚TƒPƒ^j
	
	//•Ï”‚Ì’è‹`
	static int CompositeNumNLength;    //N‚ÌŒ…”
	static int PrimeNumALength;        //A‚ÌŒ…”
	static int PrimeNumBLength;        //B‚ÌŒ…”
	
	static int PrimeNumA;        //A‚ÌŠm’è’l
	static int PrimeNumB;        //B‚ÌŠm’è’l
	static int CompositeNumN;    //N‚ÌŠm’è’l
    
	int PrimeNumAList[];        //A‚ÌŒó•â‚ğŠi”[‚·‚é”—ñ
	int PrimeNumBList[];        //B‚ÌŒó•â‚ğŠi”[‚·‚é”—ñ
	int CompositeNumNList[];    //N‚ÌŒó•â‚ğŠi”[‚·‚é”—ñ

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//“ü—Í’l‚Ìæ“¾
		CompositeNumN = Integer.parseInt(args[0]);
		
		//Å‰‚ÌŒ…”‚ÌŒˆ’è
		PrimeNumALength = (int)Math.ceil(CompositeNumN/2.0);
		PrimeNumBLength = CompositeNumN/2;
		
		System.out.println("PrimeNum A Length = " + PrimeNumALength );
		System.out.println("PrimeNum B Length = " + PrimeNumBLength );
		
		//A‚ğ’Tõ‚·‚é
		PrimeNumA = (int)Math.pow(10, PrimeNumALength) -1;
		
		//ƒ‹[ƒv‚PiA’TõjŠJn
		while(0==0){
			if(isPrimeNum(PrimeNumA,0)==1){
				//A1‚ğŒˆ’è
				break;
			}else{
				PrimeNumA--;
				if(PrimeNumA >= minPrimeNum){
					//ƒ‹[ƒv‚P‚Ìæ“ª‚Ö
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
            if (PrimeNumber % i == 0) // Š„‚èØ‚ê‚é‚Æ‘f”‚Å‚Í‚È‚¢
                return 0;          // ‚»‚êˆÈã‚ÌŒJ•Ô‚µ‚Í•s—v
        }
       // ÅŒã‚Ü‚ÅŠ„‚èØ‚ê‚È‚©‚Á‚½
		return 1;
	}

}
