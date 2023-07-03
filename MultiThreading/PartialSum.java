import java.util.Random;

public class PartialSum implements Runnable {
    private int[] array;
    private int start;
    private int end;
    private int partialSum;

    public PartialSum(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.partialSum = 0;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            partialSum += array[i];
        }
    }

    public int getPartialSum() {
        return partialSum;
    }

    public static void main(String[] args) throws InterruptedException {
        int N = 10000;
        int K = 4;

        // Create and fill the array with pseudorandom numbers
        int[] array = new int[N];
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            array[i] = random.nextInt(100);
        }

        // Create an array of threads
        Thread[] threads = new Thread[K];
        PartialSum[] partialSums = new PartialSum[K];

        int m = N / K; // Size of each partial sum

        // Create and start the threads
        for (int i = 0; i < K; i++) {
            partialSums[i] = new PartialSum(array, i * m, (i + 1) * m);
            threads[i] = new Thread(partialSums[i]);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < K; i++) {
            threads[i].join();
        }

        // Calculate the total sum from partial sums
        int totalSum = 0;
        for (int i = 0; i < K; i++) {
            totalSum += partialSums[i].getPartialSum();
        }

        // Calculate the sum in the conventional way
        int conventionalSum = 0;
        for (int i = 0; i < N; i++) {
            conventionalSum += array[i];
        }

        // Compare the results
        System.out.println("Total Sum (from partial sums): " + totalSum);
        System.out.println("Conventional Sum: " + conventionalSum);
    }
}
