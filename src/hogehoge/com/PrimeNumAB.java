package hogehoge.com;

public class PrimeNumAB {
	
	//�萔�̒�`
	static final int minPrimeNum = 10000;    //�ŏ��̑f���i�T�P�^�j
	
	//�ϐ��̒�`
	static int CompositeNumNLength;    //N�̌���
	static int PrimeNumALength;        //A�̌���
	static int PrimeNumBLength;        //B�̌���
	
	static int PrimeNumA;        //A�̊m��l
	static int PrimeNumB;        //B�̊m��l
	static int CompositeNumN;    //N�̊m��l
    
	int PrimeNumAList[];        //A�̌����i�[���鐔��
	int PrimeNumBList[];        //B�̌����i�[���鐔��
	int CompositeNumNList[];    //N�̌����i�[���鐔��

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//���͒l�̎擾
		CompositeNumN = Integer.parseInt(args[0]);
		
		//�ŏ��̌����̌���
		PrimeNumALength = (int)Math.ceil(CompositeNumN/2.0);
		PrimeNumBLength = CompositeNumN/2;
		
		System.out.println("PrimeNum A Length = " + PrimeNumALength );
		System.out.println("PrimeNum B Length = " + PrimeNumBLength );
		
		//A��T������
		PrimeNumA = (int)Math.pow(10, PrimeNumALength) -1;
		
		//���[�v�P�iA�T���j�J�n
		while(0==0){
			if(isPrimeNum(PrimeNumA,0)==1){
				//A1������
				break;
			}else{
				PrimeNumA--;
				if(PrimeNumA >= minPrimeNum){
					//���[�v�P�̐擪��
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
            if (PrimeNumber % i == 0) // ����؂��Ƒf���ł͂Ȃ�
                return 0;          // ����ȏ�̌J�Ԃ��͕s�v
        }
       // �Ō�܂Ŋ���؂�Ȃ�����
		return 1;
	}

}
