package P1.Receiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import P1.ErrorDetection.CRC;

public class Receiver {


    public static void main(String[] args) throws FileNotFoundException, IOException {


        Path path = Paths.get(MAPPED_FILE);
        // Clearing off existing data
        Files.write(path, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        FileChannel fc = new RandomAccessFile(new File(MAPPED_FILE), "rw").getChannel();
        long bufferSize = 8 * 1000;
        MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY, 0, 100);
        mem.clear();
        long oldSize = fc.size();
        long currentPos = 0;
        long xx = currentPos;
        long start = System.currentTimeMillis();
        System.out.println("Waiting for data ...");
        Boolean received = false;
        while (true) {
            if (received) {
                break;
            }
            String codeword = "";
            long current = System.currentTimeMillis() - start;
            if (current > 10 * 1000) {
                start = System.currentTimeMillis();

//                System.out.print(" Current position : " + Integer.toString(mem.position()));
                System.out.println(" Current limit  , 10 seconds passed more : " + Integer.toString(mem.limit()));

                mem = fc.map(FileChannel.MapMode.READ_ONLY, 0, 100);

            }

            if (mem.hasRemaining()) {
                char st = (char) mem.get();

                if (st == '$') {
                    char en = (char) mem.get();
                    while (en != '$') {
                        codeword += en;
                        en = (char) mem.get();
                    }
                    System.out.println("DATA RECEIVED");
                    System.out.println("DATA : " + codeword);
//                    VRC obj = new VRC();
//                    LRC obj = new LRC();
//                    Checksum obj = new Checksum();
                    CRC obj = new CRC();

                    Boolean correct = obj.detect_error(codeword);


                    System.out.println("CORRECT : " + Boolean.toString(correct));
                    if (correct) {
                        String dataword = obj.extract_dataword(codeword);
                        System.out.println("Extracted dataword : " + dataword);
                    }
                    break;
                }
            }


        }


    }
}