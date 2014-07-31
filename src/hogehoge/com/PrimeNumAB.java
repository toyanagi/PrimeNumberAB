package hogehoge.com;

public class PrimeNumAB {
	
	//�萔�̒�`
	static final long minPrimeNum = 10000;    //�ŏ��̑f���i�T�P�^�j
	
	//�ϐ��̒�`
	static int CompositeNumNLength;    //N�̌���
	static int PrimeNumALength;        //A�̌���
	static int PrimeNumBLength;        //B�̌���
	
	static long PrimeNumA;        //A�̊m��l
	static long PrimeNumB;        //B�̊m��l
	static long PrimeNumB2;        //B�̑�Q���
	static long CompositeNumN;    //N�̊m��l
    
	int PrimeNumAList[];        //A�̌����i�[���鐔��
	int PrimeNumBList[];        //B�̌����i�[���鐔��
	int CompositeNumNList[];    //N�̌����i�[���鐔��

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//���͒l�̎擾
		CompositeNumNLength = Integer.parseInt(args[0]);
		
		//�ŏ��̌����̌���
		PrimeNumALength = (int)Math.ceil(CompositeNumNLength/2.0);
		PrimeNumBLength = CompositeNumNLength/2;
		
		System.out.println("CompositNum N Length = " + CompositeNumNLength );
		System.out.println("PrimeNum A Length = " + PrimeNumALength );
		System.out.println("PrimeNum B Length = " + PrimeNumBLength );
		
		//A��T������
		PrimeNumA = (long)Math.pow(10, PrimeNumALength) -1;
		
		//���[�v�P�iA�T���j�J�n
		while(isPrimeNum(PrimeNumA,0)==0){
			PrimeNumA--;
			if(PrimeNumA < minPrimeNum){
					break;
			}
		}
		System.out.println("PrimeNumA  = " + PrimeNumA );

		//B��T������		
		//A�AB�̌����������^�قȂ�P�[�X�ŏꍇ����
		if(PrimeNumALength==PrimeNumBLength){
			PrimeNumB = PrimeNumA - 1;                            //A��B�̌��������� �ꍇ�A(�m�肵��A�j-1 ���Z�b�g
		}else{
			PrimeNumB = (long)Math.pow(10, PrimeNumBLength) - 1;    //A��B�̌������قȂ� �ꍇ�AB^10-1  ���Z�b�g
		}		
		
		//���[�v�Q�iB�T�� �������s�j�J�n
		while(isPrimeNum(PrimeNumB,0)==0){
			PrimeNumB--;
			if(PrimeNumB < minPrimeNum){
					break;
			}
		}
		System.out.println("PrimeNumB(1st candidate)  = " + PrimeNumB );
		
		//���[�v�R�iB�T�� ��Q���j�J�n
		//B2���Z�b�g
		PrimeNumB2 = (int)Math.pow(10, PrimeNumBLength) + 1;
		
		//A*B2��N���ȏ�łȂ����`�F�b�N
		//System.out.println("PrimeNumB(2nd check)  = " + PrimeNumB2 + "   N = " + PrimeNumA * PrimeNumB2 );
		while(PrimeNumA * PrimeNumB2 <= (long)Math.pow(10, CompositeNumNLength)){
			if(isPrimeNum(PrimeNumB2,0)==0){
				System.out.println("PrimeNumB(2nd check�@no candidate)  = " + PrimeNumB2 + "   N = " + PrimeNumA * PrimeNumB2 );
				PrimeNumB2++;                   //�f���łȂ��Ȃ�B2��1���Z�A����
			
			}else{
				PrimeNumB = PrimeNumB2;     //�f���Ȃ�B���X�V
				System.out.println("PrimeNumB(2nd candidate)  = " + PrimeNumB + "   N = " + PrimeNumA * PrimeNumB2 );
				PrimeNumB2++;               //��B2��1���Z�A����T��
			}
		}
		
		//�ŏI��B���o��
		System.out.println("PrimeNumB(last)  = " + PrimeNumB );
		
		//N���o��
		CompositeNumN = PrimeNumA * PrimeNumB;
		System.out.println("CompositeNumN = " + CompositeNumN + " = " + PrimeNumA + " * " + PrimeNumB );
		
	}
	
	private static int isPrimeNum(long PrimeNumber,int mode){
		int i=2;
		for ( i = 2; i < PrimeNumber; i++) {
            if (PrimeNumber % i == 0) // ����؂��Ƒf���ł͂Ȃ�
                return 0;          // ����ȏ�̌J�Ԃ��͕s�v
        }
       // �Ō�܂Ŋ���؂�Ȃ�����
		return 1;
	}

}
