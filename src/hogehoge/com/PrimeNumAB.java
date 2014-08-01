package hogehoge.com;

public class PrimeNumAB {
	
	//�萔�̒�`
	static final long minPrimeNum = 99999;    //�ŏ��̑f���i�T�P�^�j
	
	//�ϐ��̒�`
	static int CompositeNumNLength;    //N�̌���
	static int PrimeNumALength;        //A�̌���
	static int PrimeNumBLength;        //B�̌���
	
	static long PrimeNumA;        //A�̊m��l
	static long PrimeNumB;        //B�̊m��l
	static long PrimeNumB2;       //B�̑�Q���
	static long CompositeNumN;    //N�̊m��l
	static long CompositeNumN2;   //N�̑�Q���
	
	int PrimeNumAList[];        //A�̌����i�[���鐔��
	int PrimeNumBList[];        //B�̌����i�[���鐔��
	int CompositeNumNList[];    //N�̌����i�[���鐔��

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//���s���Ԍv��
		long startTime = System.currentTimeMillis();
		
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
		PrimeNumA = previousPrimeNum(PrimeNumA, 0);
		
		System.out.println("PrimeNumA  = " + PrimeNumA );

		//B��T������		
		//A�AB�̌����������^�قȂ�P�[�X�ŏꍇ����
		if(PrimeNumALength==PrimeNumBLength){
			PrimeNumB = PrimeNumA - 1;                            //A��B�̌��������� �ꍇ�A(�m�肵��A�j-1 ���Z�b�g
		}else{
			PrimeNumB = (long)Math.pow(10, PrimeNumBLength) - 1;    //A��B�̌������قȂ� �ꍇ�AB^10-1  ���Z�b�g
		}		
		
		//���ɏ������f����T��
		PrimeNumB = previousPrimeNum(PrimeNumB, 0);
		
		//�����Ƃ���
		CompositeNumN = PrimeNumA * PrimeNumB;
		System.out.println("PrimeNumB(1st candidate)  = " + PrimeNumB 
		           		 + "   N = " + CompositeNumN);
		
		//���[�v�R�iB�T�� ��Q���j�J�n
		//N���ƂȂ�ő��B�Q�����߂�
		PrimeNumB2= ((long)Math.pow(10, CompositeNumNLength)-1) / PrimeNumA;
		CompositeNumN2 = PrimeNumA * PrimeNumB2;
		System.out.println("PrimeNumB(2nd candidate(max))  = " + PrimeNumB2 +
				"   N = " + CompositeNumN2 );
		
		//���N2�����݂�N�ȉ���
		if( CompositeNumN2 <= CompositeNumN){
		//�ȉ��̏ꍇ
			//B�AN���m��i�������Ȃ��j
			System.out.println("PrimeNumB(2nd candidate(max)) is no candidate");
		//�傫���ꍇ
		}else{
			//�P�������f����T��
			PrimeNumB2 = previousPrimeNum(PrimeNumB2, 0);
			CompositeNumN2 = PrimeNumA * PrimeNumB2;
			
			//��₪���݂�N�ȏォ
			if(CompositeNumN2 >= CompositeNumN){
				//B2��B�Ƃ��Ċm��
				PrimeNumB = PrimeNumB2;
				CompositeNumN = CompositeNumN2; 
				System.out.println("PrimeNumB(2nd candidate is optimum solution)  = " + PrimeNumB +
						"   N = " + CompositeNumN );
			}else{
				System.out.println("PrimeNumB(2nd candidate is no optimum solution)  = " + PrimeNumB2 +
						"   N = " + CompositeNumN2 );
			}
		}
		
		//���ʂ��o��
		System.out.println("----------" );
		
		//N���o��
		CompositeNumN = PrimeNumA * PrimeNumB;
		System.out.println("CompositeNumN = " + CompositeNumN + " = " + PrimeNumA + " * " + PrimeNumB );
		
		//���s���Ԍv��
		long stopTime = System.currentTimeMillis();
		
		//���s���Ԃ��o��
		System.out.println("Run Time = " + (stopTime - startTime) + " ms " );
		
	}
	
	//���̑f����T�����郁�\�b�h
	private static long nextPrimeNum(long startNumber,int mode){
		while(isPrimeNum(startNumber,0)==0){
			System.out.println("PrimeNumB(2nd check no candidate)  = " + startNumber );
			startNumber++;
		}
		
		//�f����ԋp
		return startNumber;
	}

	
	//�O�̑f����T�����郁�\�b�h
	private static long previousPrimeNum(long startNumber,int mode){
		while(isPrimeNum(startNumber,0)==0){
			startNumber--;
			
			//5���ȉ��ɂȂ�����0��Ԃ�
			if(startNumber <= minPrimeNum){
					return 0;
			}
		}
		
		//�f����ԋp
		return startNumber;
	}
	
	//�f�����ǂ����𔻒f���郁�\�b�h
	private static int isPrimeNum(long PrimeNumber,int mode){
		int i=2;
		//�^����ꂽ�����̕�������T���̏���Ƃ���
		//long rootPrimeNumber=PrimeNumber;
		long rootPrimeNumber=(long)Math.sqrt(PrimeNumber);
		for ( i = 2; i < rootPrimeNumber; i++) {
            if (PrimeNumber % i == 0) // ����؂��Ƒf���ł͂Ȃ�
                return 0;          // ����ȏ�̌J�Ԃ��͕s�v
        }
       // �Ō�܂Ŋ���؂�Ȃ�����
		return 1;
	}

}
