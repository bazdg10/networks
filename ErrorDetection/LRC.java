package P1.ErrorDetection;
public class LRC {

    static final int LENGTH = 6;
    static final int SEGMENT = 4;


    /**
     * Calculates the parity of a number
     *
     * @param number : receives a number
     */
    int calculate_parity(int number) {

        int parity = 0;
        while (number > 0) {
            parity = ~parity;
            number = (number) & (number - 1);
        }
        if (parity == -1)
            return 0;
        return 1;
    }

    /*
     * Takes in the codeword and returns the codeword with the
     * LRC redundant bits
     *
     * */
    public String prepare_codeword(String dataword) {
        int len = dataword.length();
        int part = 0;
        String LRC = "";

        dataword = padDataword(dataword);
        for (int i = 0; i < SEGMENT; i++) {
            int parity = 0;
            for (part = 0; part + i < dataword.length(); part += SEGMENT) {
                parity ^= (dataword.charAt(i + part) - '0');
            }
            LRC += (char) (parity + '0');
        }

        String codeword = dataword + LRC;
        return codeword;

    }

    String padDataword(String dataword) {
        if (dataword.length() % SEGMENT == 0)
            return dataword;
        int to_pad = SEGMENT - dataword.length() % SEGMENT;
        for (int i = 0; i < to_pad; i++) {
            dataword += '0';
        }
        return dataword;
    }

    public void show(String codeword) {

        for (int part = 0; part < codeword.length(); part += SEGMENT) {
            System.out.println(codeword.substring(part, part + SEGMENT));
        }


    }

    int compute_lrc(String codeword) {


        for (int i = 0; i < SEGMENT; i++) {
            int parity = 0;
            for (int part = 0; part + i < codeword.length(); part += SEGMENT) {
                parity ^= (codeword.charAt(i + part) - '0');
            }
            if (parity == 1) return 1;
        }
        return 0;


    }

    public Boolean detect_error(String framed_codeword) {
        String length_in_binary = framed_codeword.substring(0, LENGTH);
        int length_dataword = convert_into_number(length_in_binary);
        int paddingSize = (length_dataword + LENGTH) % SEGMENT == 0 ? 0 : SEGMENT - (length_dataword + LENGTH) % SEGMENT;
        String codeword = framed_codeword.substring(0, LENGTH + length_dataword + paddingSize + SEGMENT);
        int lrc = compute_lrc(codeword);

        if (lrc == 0)
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