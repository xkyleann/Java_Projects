import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Hamming {
    public int compare(String str1, String str2) throws Exception {
        if (str1.isEmpty() || str2.isEmpty()) {
            throw new Exception("Empty string");
        }
        int distance = 0;
        int minLength = Math.min(str1.length(), str2.length());
        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                distance++;
            }
        }
        distance += Math.abs(str1.length() - str2.length());
        return distance;
    }
}

class HammingCleared extends Hamming {
    private String clear(String str) {
        return str.replaceAll("\\s", "");
    }

    @Override
    public int compare(String str1, String str2) throws Exception {
        String clearedStr1 = clear(str1);
        String clearedStr2 = clear(str2);
        return super.compare(clearedStr1, clearedStr2);
    }
}

class CheckPlagiarism {
    private static final int THRESHOLD = 2;

    public void compareFiles(String file1, String file2) {
        try {
            List<String> lines1 = readLines(file1);
            List<String> lines2 = readLines(file2);
            int identicalLinesCount = 0;
            int minHammingDistanceSum = 0;
            for (String line1 : lines1) {
                int minHammingDistance = Integer.MAX_VALUE;
                for (String line2 : lines2) {
                    int hammingDistance = new HammingCleared().compare(line1, line2);
                    if (hammingDistance < minHammingDistance) {
                        minHammingDistance = hammingDistance;
                    }
                    if (minHammingDistance == 0) {
                        identicalLinesCount++;
                        break;
                    }
                }
                minHammingDistanceSum += minHammingDistance;
            }
            int averageHammingDistance = minHammingDistanceSum / lines1.size();
            if (averageHammingDistance <= THRESHOLD) {
                System.out.println("Plagiarism detected!");
            } else {
                System.out.println("No plagiarism detected.");
            }
            System.out.println("Average Hamming Distance: " + averageHammingDistance);
            System.out.println("Identical lines: " + identicalLinesCount);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the files.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}

public class Main {
    public static void main(String[] args) {
        String workingDirectory = "path/to/working/directory";
        File[] files = new File(workingDirectory).listFiles();
        if (files != null) {
            CheckPlagiarism checker = new CheckPlagiarism();
            for (int i = 0; i < files.length - 1; i++) {
                for (int j = i + 1; j < files.length; j++) {
                    String file1 = files[i].getPath();
                    String file2 = files[j].getPath();
                    System.out.println("Comparing files: " + file1 + " and " + file2);
                    checker.compareFiles(file1, file2);
                    System.out.println("--------------------");
                }
            }
        } else {
            System.out.println("No files found in the working directory.");
        }
    }
}
