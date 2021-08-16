package P1.ErrorDetection;
public class Checksum {

    static final int LENGTH = 6;
    static final int SEGMENT = 4;


    public String prepare_codeword(String dataword) {
        dataword = prepareDatawordWithPadding(dataword);
        int len = dataword.length();

        int part = 0;
        int checksum = 0;

        while (part < len) {
            String current = dataword.substring(part, part + SEGMENT);
            part += SEGMENT;
            int number = convert_into_number(current);

            checksum += number;
            if (checksum > 15)
                checksum -= 15;
        }

        checksum = 15 - checksum;

        String checksumBinary = Integer.toBinaryString(checksum);
        if (checksumBinary.length() < SEGMENT) {
            int rem = SEGMENT - checksumBinary.length() % SEGMENT;
            String pre = "";
            for (int i = 0; i < rem; i++)
                pre += '0';
            checksumBinary = pre + checksumBinary;
        }


        String codeword = dataword + checksumBinary;
        return codeword;

    }

    String prepareDatawordWithPadding(String dataword) {
        int to_pad = SEGMENT - dataword.length() % SEGMENT;
        for (int i = 0; i < to_pad; i++)
            dataword = dataword + '0';
        return dataword;
    }


    int convert_into_number(String binary_segment) {
        int number = 0;
        int len = binary_segment.length();
        for (int i = 0; i < len; i++) {
            number = number * 2 + (binary_segment.charAt(i) - '0');
        }
        return number;
    }

    int generate_complement(int number) {
        int number_of_bits = (int) (Math.floor((Math.log(number) / Math.log(2)))) + 1;
        int mask = (1 << number_of_bits) - 1;
        return mask ^ number;
    }

    int compute_checksum(String codeword) {
        int part = 0;
        int checksum = 0;
        while (part < codeword.length()) {
            String current = codeword.substring(part, part + SEGMENT);
            part += SEGMENT;
            int number = convert_into_number(current);
//            int complement = generate_complement(number);
            checksum += number;
            if (checksum > 15)
                checksum -= 15;
        }
        checksum = 15 - checksum;
//        System.out.println(generate_complement(checksum));

        return checksum;
    }

    public Boolean detect_error(String codeword) {
        String length_in_binary = codeword.substring(0, LENGTH);
        int len = convert_into_number(length_in_binary);
        int to_pad = SEGMENT - (len + LENGTH) % SEGMENT;
        int checksum = compute_checksum(codeword.substring(0, LENGTH + len + to_pad + SEGMENT));
        if (checksum == 0)
            return true;
        else return false;
    }

    public String extract_dataword(String codeword) {
        String length_in_binary = codeword.substring(0, LENGTH);
        int len = convert_into_number(length_in_binary);
        return codeword.substring(LENGTH, LENGTH + len);
    }


}