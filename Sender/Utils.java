package P1.Sender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import P1.ErrorDetection.CRC;
import P1.ErrorDetection.Checksum;
import P1.ErrorDetection.LRC;
import P1.ErrorDetection.VRC;


public class Utils {
    static final int LENGTH = 6;


    public static String readFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String dataword = Files.readAllLines(path).get(0);
        return dataword;
    }

    static String prepareCodewordWithPadding(String codeword, int frameSize) {
        int len = codeword.length();
        if (len == 0) {
            System.out.println("[ERROR] Dataword empty");
            System.exit(0);
        }

        if (len % frameSize != 0) {
            int rem = frameSize - len % frameSize;
            // pad with the remaining zeros
            for (int i = 0; i < rem; i++)
                codeword += '0';
        }

        return codeword;

    }

    static String appendLength(String dataword) {
        int len = dataword.length();
        if (len == 0) {
            System.out.println("[ERROR] Dataword empty");
            System.exit(0);
        }
        String length_in_binary = Integer.toBinaryString(len);
        if (length_in_binary.length() < LENGTH) {
            for (int i = 0; i < LENGTH - length_in_binary.length(); i++) {
                length_in_binary = '0' + length_in_binary;
            }
        }
        dataword = length_in_binary + dataword;
        return dataword;
    }

    public static String prepareCodewordLRC(String dataword, int frameSize) {
        dataword = appendLength(dataword);
        LRC obj = new LRC();
        String codeword = obj.prepare_codeword(dataword);
        codeword = Utils.prepareCodewordWithPadding(codeword, frameSize);
        return codeword;

    }

    public static String prepareCodewordVRC(String dataword, int frameSize) {
        dataword = appendLength(dataword);
        VRC obj = new VRC();
        String codeword = obj.prepare_codeword(dataword);
        codeword = Utils.prepareCodewordWithPadding(codeword, frameSize);
        return codeword;
    }

    static String prepareCodewordChecksum(String dataword, int frameSize) {
        dataword = appendLength(dataword);
        Checksum obj = new Checksum();
        String codeword = obj.prepare_codeword(dataword);
        codeword = Utils.prepareCodewordWithPadding(codeword, frameSize);
        return codeword;
    }

    public static String prepareCodewordCRC(String dataword, int frameSize) {
        dataword = appendLength(dataword);
        CRC obj = new CRC();
        String codeword = obj.prepare_codeword(dataword);
        codeword = Utils.prepareCodewordWithPadding(codeword, frameSize);
        return codeword;
    }

    public static String addError(String codeword) {
        Random obj = new Random();
        char[] code = codeword.toCharArray();
        int[] points = obj.ints(1, 0, codeword.length()).toArray();
        System.out.print("Error introduced at bit numbers : ");
        for (int x : points) {
            System.out.print(Integer.toString(x) + " ");
            code[x] = (char) (((code[x] - '0') ^ 1) + '0');
        }
        codeword = "";
        for (char c : code)
            codeword += c;


        return codeword;
    }

    public static String addErrorAtPos(String codeword, int pos) {
        char[] code = codeword.toCharArray();
        codeword = "";
        code[pos] = (char) (((code[pos] - '0') ^ 1) + '0');
        for (char c : code) codeword += c;
        return codeword;

    }
}