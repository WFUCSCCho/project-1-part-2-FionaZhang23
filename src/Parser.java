/***********************************************************
 * @file: Parser.java
 * @Description: This file contains the implementation of a parser class where it reads command from the input and put it in to the binary search tree
 * @Author: Fiona Zhang
 * @Date: September 26, 2024
 ***********************************************************/
import java.io.*;
import java.util.Scanner;

public class Parser {

    // Create a BST tree of your class type (Note: Replace "Object" with your class type)
    private BST<Song> mybst = new BST<Song>();

    public Parser(String filename) throws FileNotFoundException {
        process(new File(filename));
    }

    // Implement the process method
    // Remove redundant spaces for each input command
    public void process(File input) throws FileNotFoundException {

        Scanner scanner = new Scanner(input);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim(); // Remove redundant spaces
            if (line.isEmpty()) continue;  // Ignore blank lines

            String[] command = line.split("\\s+");
            operate_BST(command);
        }

        scanner.close();
    }

    // Implement the operate_BST method
    // Determine the incoming command and operate on the BST
    public void operate_BST(String[] command) {
        switch (command[0]) {
            case "insert":
                // Example command: insert "Song Name" Artist Year Streams BPM Danceability
                String trackName = command[1].replace("_", " ");
                String artistName = command[2].replace("_", " ");
                int releaseYear = Integer.parseInt(command[3]);
                long streams = Long.parseLong(command[4]);
                int bpm = Integer.parseInt(command[5]);
                int danceability = Integer.parseInt(command[6]);
                mybst.insert(new Song(trackName, artistName, releaseYear, streams, bpm, danceability));
                writeToFile("Inserted: " + trackName, "./result.txt");
                break;

            case "search":
                trackName = command[1].replace("_", " ").trim().toLowerCase();
                boolean found = false;

                // Iterate through the BST to search for a matching song
                for (Song song : mybst) {
                    if (song.getTrackName().toLowerCase().equals(trackName)) {
                        found = true;
                        writeToFile("Found: " + song.toString(), "./result.txt");
                        break;
                    }
                }

                if (!found) {
                    writeToFile("Not Found: " + trackName, "./result.txt");
                }
                break;

            case "remove":
                // Example command: remove "Song Name"
                trackName = command[1].replace("_", " ");
                mybst.remove(new Song(trackName, "", 0, 0, 0, 0));
                writeToFile("Removed: " + trackName, "./result.txt");
                break;

            case "print":
                // Print the BST in ascending order using an iterator
                StringBuilder output = new StringBuilder();
                for (Song value : mybst) {
                    output.append(value).append(" ");
                }
                // Write the result to the file
                writeToFile(output.toString().trim(), "./result.txt");
                break;
            // default case for Invalid Command
            default:
                writeToFile("Invalid Command", "./result.txt");
                break;
        }
    }

    private Song findSongByTrackName(String trackName) {
        for (Song song : mybst) {
            if (song.getTrackName().equalsIgnoreCase(trackName)) {
                return song;
            }
        }
        return null;  // Song not found
    }

    // Implement the writeToFile method
    // Generate the result file
    public void writeToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
