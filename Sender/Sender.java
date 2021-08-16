package P1.Sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Sender {

    /**
     * NOTE : CRC : 39 37 38
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        if (args.length <= 0) {
            System.out.println("[ERROR] File path should be provided as an argument");
            System.exit(0);
        }
        String filePath = args[0];
        String dataword = Utils.readFromFile(filePath);
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the frame size : ");
        int frameSize = in.nextInt();
        System.out.println("Raw dataword : " + dataword);

        FileChannel fc = new RandomAccessFile(new File(MAPPED_FILE), "rw").getChannel();

        String codeword = Utils.prepareCodewordCRC(dataword, frameSize);
        System.out.println("Codeword formed: " + codeword);
//        codeword = Utils.addError(codeword);
//        System.out.println("Codeword formed: " + codeword);
        MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_WRITE, 0, codeword.length() + 2);


        codeword = '$' + codeword + '$';
        mem.put(codeword.getBytes());


    }
}