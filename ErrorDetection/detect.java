package P1.ErrorDetection;

public class detect {

    public static void main(String[] args) {
        LRC obj = new LRC();
        VRC obj2 = new VRC();
        String dataword = "00110010";
        String codeword = obj.prepare_codeword(dataword);
        System.out.println("dataword : 00110010");
        System.out.println(codeword);
        System.out.println(obj.detect_error(codeword));
        System.out.println("Extracted  " + obj.extract_dataword(codeword));
        codeword = obj2.prepare_codeword(dataword);
        System.out.println("VRC :" + codeword);
        System.out.println(obj2.detect_error(codeword));
        System.out.println(obj2.extract_dataword(codeword));
        int number = 37;
        System.out.println(Integer.toBinaryString(number));
        Checksum obj3 = new Checksum();
        codeword = obj3.prepare_codeword(dataword);
        System.out.println(codeword);
        System.out.println(obj3.detect_error(codeword));

    }
}