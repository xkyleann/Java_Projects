import java.util.Scanner;

public class GetNumbers implements Runnable {
    private int[] array;
    private boolean filled;

    public GetNumbers(int[] array) {
        this.array = array;
        this.filled = false;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!filled) {
                System.out.println("Enter numbers to fill the array:");
                for (int i = 0; i < array.length; i++) {
                    array[i] = scanner.nextInt();
                }
                filled = true;
            }
        }
    }

    public static void main(String[] args) {
        int N = 5;
        int[] array = new int[N];

        GetNumbers getNumbers = new GetNumbers(array);
        Thread numbersThread = new Thread(getNumbers);
        numbersThread.start();

        GetSum getSum = new GetSum(array);
        Thread sumThread = new Thread(getSum);
        sumThread.start();
    }
}

class GetSum implements Runnable {
    private int[] array;
    private boolean filled;

    public GetSum(int[] array) {
        this.array = array;
        this.filled = false;
    }

    @Override
    public void run() {
        while (true) {
            if (filled) {
                int sum = 0;
                for (int i = 0; i < array.length; i++) {
                    sum += array[i];
                }
                System.out.println("Sum: " + sum);
                filled = false;

                // Artificial delay to simulate some processing time
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Fill the array with zeros
                for (int i = 0; i < array.length; i++) {
                    array[i] = 0;
                }
            }
        }
    }
}
