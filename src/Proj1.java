/***********************************************************
 * @file: BST.java
 * @Description: This file includes the main method of this project which reads the command line argument.
 * @Author: Fiona Zhang
 * @Date: September 26, 2024
 ***********************************************************/
import java.io.FileNotFoundException;

public class Proj1 {
    public static void main(String[] args) throws FileNotFoundException{
        if(args.length != 1){
            System.err.println("Argument count is invalid: " + args.length);
            System.exit(0);
        }
        new Parser(args[0]);
    }
}