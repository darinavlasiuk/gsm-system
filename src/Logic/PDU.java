package Logic;

import java.time.LocalDateTime;

public class PDU {
    public static byte[] toArray(String pdu){
        byte[] res=new byte[pdu.length()/2];
        for(int i=0; i<pdu.length()/2; i++){
            String tmp=""+pdu.charAt(i*2)+pdu.charAt(i*2+1);
            res[i]=(byte)Integer.parseInt(tmp, 16);
        }
        return res;
    }
    public static String toPDUString(byte[] arr){
        StringBuilder sb=new StringBuilder();
        for(int i=0; i<arr.length; i++){
            String tmp=Integer.toHexString(arr[i] & 0xFF);
            while(tmp.length()<2) tmp="0"+tmp;
            sb.append(tmp);
        }
        return sb.toString();
    }
    public static byte[] encode(String sender, String recipient, String message){
        String SMSC=SMSC(sender);
        String FirstOctet="04";
        String TP_OA=TP_OA(recipient);
        String TP_PID="00";
        String TP_DCS="00";
        String TP_SCTS=timeStamp();
        String TP_UD=sevenBitRepresentation(message);
        String TP_UDL=Integer.toHexString(message.length());
        if(TP_UDL.length()<2) TP_UDL="0"+TP_UDL;
        return toArray(SMSC+FirstOctet+TP_OA+TP_PID+TP_DCS+TP_SCTS+TP_UDL+TP_UD);
    }
    public static String decodeRecipient(byte[] arr){
        String SMS=toPDUString(arr);
        int senderLength=Integer.parseInt(SMS.substring(0, 2), 16);
        int shift=2+senderLength*2+2;
        int recipientLength=Integer.parseInt(SMS.substring(shift, shift+2), 16);
        if(recipientLength%2==1) recipientLength++;
        shift+=4;
        String recipient=SMS.substring(shift, shift+recipientLength);
        return fromSemiOctetRepresentation(recipient);
    }
    public static String timeStamp(){
        LocalDateTime now= LocalDateTime.now();
        String year=""+now.getYear()%100;
        if(year.length()<2) year="0"+year;
        String month=""+now.getMonthValue();
        if(month.length()<2) month="0"+month;
        String day=""+now.getDayOfMonth();
        if(day.length()<2) day="0"+day;
        String hour=""+now.getHour();
        if(hour.length()<2) hour="0"+hour;
        String min=""+now.getMinute();
        if(min.length()<2) min="0"+min;
        String sec=""+now.getSecond();
        if(sec.length()<2) sec="0"+sec;
        String zone="08";
        return semiOctetRepresentation(year+month+day+hour+min+sec+zone);
    }
    public static String TP_OA(String number){
        String length=Integer.toHexString(number.length());
        if(length.length()<2) length="0"+length;
        String encodedNumber=semiOctetRepresentation(number);
        String type="91";

        return length+type+encodedNumber;
    }
    public static String SMSC(String number){
        String encodedNumber=semiOctetRepresentation(number);
        String type="91";
        String length=""+(encodedNumber.length()+type.length())/2;
        if(length.length()<2) length="0"+length;
        return length+type+encodedNumber;
    }
    public static String semiOctetRepresentation(String number){
        if(number.length()%2==1) number=number+"f";
        StringBuilder sb=new StringBuilder();
        for(int i=0; i<number.length(); i+=2){
            sb.append(number.charAt(i+1)).append(number.charAt(i));
        }
        return sb.toString();
    }
    public static String sevenBitRepresentation(String s){
        StringBuilder tmp=new StringBuilder();
        for(int i=0; i<s.length(); ++i){
            StringBuilder character=new StringBuilder(Integer.toBinaryString(s.charAt(i)&0xFF));
            while(character.length()<7) character.insert(0, "0");
            tmp.insert(0, character);
        }
        while(tmp.length()%8!=0) tmp.insert(0, "0");

        StringBuilder result=new StringBuilder();
        for(int i=tmp.length(); i>=8; i-=8){
            int c=Integer.parseInt(String.valueOf(tmp.subSequence(i - 8, i)), 2);
            String ss=Integer.toHexString(c);
            if(ss.length()<2) ss="0"+ss;
            result.append(ss);
        }
        return result.toString();
    }
    public static String fromSemiOctetRepresentation(String s) {
        s=semiOctetRepresentation(s);
        if(s.toUpperCase().charAt(s.length()-1)=='F') s=s.substring(0, s.length()-1);

        return s;
    }

}
