import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.*;
import java.util.*;

class ReadLines
{

    public static void main(String[] args) {

        if (args.length != 1)
        {
            System.out.println("You didn't provide an input file name.");
        }
        else
        {
            //Generic ArrayList to hold string objects containing each line of text
            List<String> strands = new ArrayList<String>();

            try
            {
                //Scanner class to read file input
                Scanner sc = new Scanner(new File(args[0]));

                //Iterate through text file, adding each line as an ArrayList object
                while (sc.hasNextLine())
                {

                    String line = sc.nextLine();
                    try
                    {
                        char [] charLine = line.toCharArray();
                        for (char c : charLine) {

                            if ((c != 'A') && (c != 'C') && (c != 'G') && (c != 'U')) {

                                throw new InvalidInputException(c);
                            }
                        }
                        strands.add(line);
                    }
                    catch (InvalidInputException e2)
                    {
                        System.out.println(e2.getMessage());
                    }
                }
            }
            catch (FileNotFoundException e1) {

                e1.printStackTrace();
            }

            //Array list to store matching substrands   
            List<String> strandMatches = new ArrayList<String>();

            //Starting subsequence size         
            int subSeqSize = 15;

            //Reference strand (for comparison) 
            String testStrand = strands.get(0);


            //Sets length of substrand to search for 
            for (int i = subSeqSize; i > 4; i--) {

                int testSize = testStrand.length();
                int difference = testSize - i;

                //Sets index range of the substrand 
                for (int j = 0; j <= difference; j++) {

                    int strandMatch = 1;

                    //Substrand calculated based on necessary indexes
                    String subStrand = testStrand.substring(j, j + i);

                    for (int k = 1; k < strands.size(); k++) {

                        if (strands.get(k).contains(subStrand))
                        {
                            strandMatch++;
                        }
                    }

                    if (strandMatch == strands.size())
                    {
                        if (strandMatches.isEmpty())
                        {
                            strandMatches.add(subStrand);
                        }
                        else
                        {
                            int counter = 0;
                            for (String match : strandMatches) {

                                if (!match.contains(subStrand))
                                {
                                    counter++;
                                }
                            }
                            if (counter == strandMatches.size())
                            {
                                strandMatches.add(subStrand);
                            }
                        }
                    }
                }
            }

            //Print out matching sequences
            System.out.println("Found the following DNA sequences in all strands:");
            for (String match : strandMatches) {

                System.out.println(match);
            }
        }
    }
}


public class InvalidInputException extends Exception {

    public InvalidInputException() {
        super("Warning! Invalid data was found.");
    }

    public InvalidInputException(char c) {
        super("Warning! Invalid base found: \""  + c + "\" ");

    }

    public InvalidInputException(String message) {
        super(message);
    }
}