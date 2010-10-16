package huffmancoding;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            String option = args[0];
            String stringToEncode, fileName;
            stringToEncode = fileName = "";
            if (option.equals("-s")) stringToEncode = args[1];
            else if (option.equals("-f")) {
                fileName = args[1];
                try {
                    //Read in the file into a single String
                    Scanner scanner = new Scanner(new File(fileName));
                    while (scanner.hasNextLine()) {
                        stringToEncode += scanner.nextLine() + " ";
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("File could not be found.");
                    System.exit(0);
                }
            }
            else {
                System.out.println("Please include the String to encode (or " +
                    "the name of a file to read) as a command line argument.");
                System.out.println("Examples: \"-s [a String]\" or \"-f [a filename]\"");
                System.exit(0);
            }

            System.out.println("String: " + stringToEncode);
            //Create the HuffmanTree from the command line argument String
            HuffmanTree huffman = new HuffmanTree(stringToEncode);
            
            //Get the list of encodings, sort in ascending order by frequency
            ArrayList<Code> codes = huffman.getCodeList();
            ArrayList<Code> sorted = new ArrayList<Code>();
            sorted.add(codes.get(0));
            for (int a=1; a<codes.size(); a++) {
                Code code = codes.get(a);
                boolean flag = false;
                for (int b = 0; b<sorted.size(); b++) {
                    Code checker = sorted.get(b);
                    //Insert into sorted list by ascending frequency
                    if (code.getFrequency() < checker.getFrequency()) {
                        sorted.add(b, code);
                        flag = true;
                        break;
                    }
                    //If frequency equal, sort by char length of encoding
                    else if(code.getFrequency() == checker.getFrequency()) {
                        if (code.getEncoding().length() >=
                                checker.getEncoding().length()) {
                            sorted.add(b, code);
                            flag = true;
                            break;
                        }

                    }
                }
                if (!flag) sorted.add(code);
            }

            //Print results for each character
            for (int i=0; i<sorted.size(); i++) {
                Code code = sorted.get(i);
                System.out.println(code.getCharacter() + " " +
                        code.getEncoding() + " " + code.getFrequency());
            }
            //Print the fully encoded String
            System.out.println("Encoded String: " + huffman.getEncodedString());
        }
        else {
            System.out.println("Please include the String to encode (or a " +
                    "the name of a file to read)\nas a command line argument.");
            System.out.println("Examples: \"-s [a String]\",\"-f [a filename]\"");
        }
        System.exit(0);
    }
}

