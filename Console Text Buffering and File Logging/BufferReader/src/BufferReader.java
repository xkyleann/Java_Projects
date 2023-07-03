import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BufferReader {
	public static void main(String[] args) {
		Scanner scanner= new Scanner(System.in);
		BufferedReader br= null;
		String line;
		
		System.out.println("Please enter the name to be read:");
		
		try {
			br=new BufferedReader(new FileReader("C:\\Users\\User\\Documents\\myProjects\\files\\"+scanner.next()));
		}
		catch(FileNotFoundException fnfex) {
			System.out.println(fnfex.getMessage()+"the file was not found");
			System.exit(0);
		}
		
		try {
			while((line=br.readLine())!=null ) {
				System.out.println(line);
			}
		} catch (IOException ioex) {
			// TODO Auto-generated catch block
			System.out.println(ioex.getMessage()+"error reading file");

		}finally {
			System.exit(0);
		}
		
	}
}
