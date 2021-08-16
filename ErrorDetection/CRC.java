package P1.ErrorDetection;
public class CRC {
    static final int LENGTH = 6;
    //CRC-8 GENERAL
    final int[] divisorCRC8 = {1, 0, 1, 0, 1, 0, 1, 0, 1};
    final int[] divisorCRC4 = {1, 0, 0, 1, 1};
    //CRC-16 USB
    final int[] divisorCRC16 = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1};
    final static int CRC16 = 17;
    final static int CRC8 = 9;
    final static int CRC4 = 5;

    public String prepare_codeword(String dataword) {
        int total_length = dataword.length() + CRC16 - 1;
        dataword = padDataword(dataword, total_length);
        int[] div = new int[total_length];
        int[] rem = new int[total_length];
        int[] crc = new int[total_length];
        for (int i = 0; i < dataword.length(); i++) {
            div[i] = dataword.charAt(i) - '0';
            rem[i] = dataword.charAt(i) - '0';
        }
        rem = divide(div, divisorCRC16, rem);
        for (int i = 0; i < div.length; i++) {
            crc[i] = (div[i] ^ rem[i]);
        }
        String codeword = "";
        for (int bit : crc) {
            codeword += (char) (bit + '0');
        }
        return codeword;

    }

    String padDataword(String dataword, int total) {
        int to_pad = total - dataword.length();
        for (int i = 0; i < to_pad; i++)
            dataword += '0';
        return dataword;
    }

    int[] divide(int div[], int divisor[], int rem[]) {
        int cur = 0;
        while (true) {
            for (int i = 0; i < divisor.length; i++)
                rem[cur + i] = (rem[cur + i] ^ divisor[i]);

            while (rem[cur] == 0 && cur != rem.length - 1)
                cur++;

            if ((rem.length - cur) < divisor.length)
                break;
        }
        return rem;
    }

    public Boolean detect_error(String framed_codeword) {
        String length_in_binary = framed_codeword.substring(0, LENGTH);
        int length = convert_into_number(length_in_binary);
        String codeword = framed_codeword.substring(0, LENGTH + length + CRC16 - 1);
        int[] crc = new int[codeword.length()];
        int[] rem = new int[codeword.length()];
        for (int i = 0; i < codeword.length(); i++) {
            crc[i] = codeword.charAt(i) - '0';
            rem[i] = codeword.charAt(i) - '0';
        }

        rem = divide(crc, divisorCRC16, rem);

        for (int i = 0; i < rem.length; i++) {
            if (rem[i] != 0) return false;
        }
        return true;

    }

    int convert_into_number(String binary_segment) {
        int number = 0;
        int len = binary_segment.length();
        for (int i = 0; i < len; i++) {
            number = number * 2 + (binary_segment.charAt(i) - '0');
        }
        return number;
    }

    public String extract_dataword(String codeword) {
        String length_in_binary = codeword.substring(0, LENGTH);
        int length = convert_into_number(length_in_binary);
        return codeword.substring(LENGTH, LENGTH + length);

    }
}