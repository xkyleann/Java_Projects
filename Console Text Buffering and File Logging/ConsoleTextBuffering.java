import java.io.*;

public class ConsoleTextBuffering {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        
        try {
            File dataDirectory = new File("data");
            if (!dataDirectory.exists()) {
                dataDirectory.mkdir();
            }
            
            File logFile = new File("data/log0.txt");
            
            // If log0.txt exists, shift logs and delete the last log file
            if (logFile.exists()) {
                for (int i = 4; i >= 0; i--) {
                    File source = new File("data/log" + i + ".txt");
                    File target = new File("data/log" + (i + 1) + ".txt");
                    if (target.exists()) {
                        target.delete();
                    }
                    if (source.exists()) {
                        source.renameTo(target);
                    }
                }
                logFile.delete();
            }
            
            logFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
            
            System.out.println("Enter text (Press Enter to finish):");
            
            while ((input = reader.readLine()) != null && !input.isEmpty()) {
                writer.write(input);
                writer.newLine();
            }
            
            writer.close();
            System.out.println("Text stored successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
