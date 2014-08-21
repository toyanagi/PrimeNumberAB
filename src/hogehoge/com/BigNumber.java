package hogehoge.com;

public class BigNumber {
	//Long.MAX_VALUE: 9223 37203 68547 75807
	//Integer.MAX_VALUE:         21474 83647
	
	
	//long�^�Ɏ��܂�ꍇ
	private long lval;
	
	//long�^�Ɏ��܂�Ȃ��ꍇ��word�z��Ɋi�[�i�P�̔z��ɂ͂P�W���j
	private long words[];
	
	//�������i�[
	private int length;
	
	//true�Ȃ畉��
	private boolean isnegative;
	
	//�萔
	private final int longTypelen=18;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="-1234567890123456789";
		
		BigNumber num=new BigNumber(str);
		System.out.println(num);
		
	}
	
	public BigNumber(String s){
		int len=s.length();
		int start;
		
		//�����A�����̐ݒ�
		if(s.charAt(0)=='-'){
			isnegative=true;
			length=len-1;
			start=1;
		}else{
			isnegative=false;
			length=len;
			start=0;
		}
		
		//long�^�Ɏ��܂錅���Ȃ�long�^�ŕێ�
		if(len-start <= 18){
			lval=(long)Math.abs(Long.parseLong(s));
			words=null;
			return;
		}
		
		//long�^�Ɏ��܂�Ȃ��ꍇ��18�P�^���ɋ�؂��ĕێ�
		int countLong=(int)Math.ceil((len-start)/(double)longTypelen);
		words=new long[countLong];
		for(int i=0; i<words.length; i++){
			int startIndex=( len - longTypelen * (i+1)<0 ? start : len - longTypelen*(i+1)   );		
			int endIndex=( len - longTypelen*i  );	
			words[i]=Long.parseLong(s.substring(startIndex, endIndex));
			
		}
	}
	
	public String toString(){
		
		String temp;
		StringBuffer buf = new StringBuffer();
		if(isnegative) buf.append("-");
		
		if(words==null){
			buf.append(Long.toString(lval));
		}else{
			buf.append(Long.toString(words[words.length-1]));
			
			for(int i=words.length-2; i>=0; i--){
				temp=Long.toString(words[i]);
				for(int j=0;j<longTypelen-temp.length();j++){
					buf.append("0");				
				}
				buf.append(temp);
			}
			
			
		}
		
		temp=buf.toString();
		buf = new StringBuffer();
		int len=temp.length();
		
		for (int i = 0; i <len-1; i++) {
            buf.append(temp.substring(i,i+1));
            if((len-i-1)%5==0){
            	buf.append(" ");
            }
        }
		buf.append(temp.substring(len-1,len));
		buf.append("(" + length + ")");
	
		return buf.toString();
	}
	
}
