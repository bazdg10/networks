package P1.ErrorDetection;

public class VRC {

    static final int LENGTH = 6;


    /*
     * Takes in the codeword and returns the codeword with the
     * LRC redundant bits
     *
     * */
    public String prepare_codeword(String dataword) {
        int parity = 0;
        for (char c : dataword.toCharArray()) {
            parity ^= (c - '0');
        }
        String codeword = dataword + (char) (parity + '0');
        return codeword;
    }

    int compute_vrc(String codeword) {
        int parity = 0;
        for (char c : codeword.toCharArray()) {
            parity ^= (c - '0');
        }
        return parity;
    }

    public Boolean detect_error(String framed_codeword) {
        String length_in_binary = framed_codeword.substring(0, LENGTH);
        int length_dataword = convert_into_number(length_in_binary);
        //Only one redundant bit
        String codeword = framed_codeword.substring(0, LENGTH + length_dataword + 1);


        int vrc = compute_vrc(codeword);
        if (vrc == 0)
            return true;
        else return false;
    }

    public String extract_dataword(String codeword) {
        String length_in_binary = codeword.substring(0, LENGTH);
        int len = convert_into_number(length_in_binary);
        return codeword.substring(LENGTH, LENGTH + len);
    }

    int convert_into_number(String binary_segment) {
        int number = 0;
        int len = binary_segment.length();
        for (int i = 0; i < len; i++) {
            number = number * 2 + (binary_segment.charAt(i) - '0');
        }
        return number;
    }
}