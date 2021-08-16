package P1.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import P1.ErrorDetection.CRC;

public class Gen {


    public static void main(String[] args) throws IOException {
        Path path = Paths.get(FILE);
//        if (!Files.exists(path)) {
//            Files.createFile(path);
//        }
//        Files.write(path, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
//        Path path1 = Paths.get(Data);
//        String dataword = Files.readAllLines(path1).get(0);
//        String codeword = Utils.prepareCodewordCRC(dataword, 8);
//        for (int i = 0; i < codeword.length(); i++) {
//            String noisy = Utils.addErrorAtPos(codeword, i);
//            Files.write(path, (Integer.toString(i) + "," + noisy + "\n").getBytes(), StandardOpenOption.APPEND);
//        }

        List<String> content = Files.readAllLines(path);
        CRC obj = new CRC();
        System.out.println(content.get(0));
        for (String line : content) {
            String[] ss = line.split(",");

            try {

                if (obj.detect_error(ss[1])) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                continue;
            }
        }

    }

}