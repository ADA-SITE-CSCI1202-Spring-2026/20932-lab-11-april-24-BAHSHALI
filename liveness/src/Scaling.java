class MathTask implements Runnable {
    private int id;

    MathTask(int id) {
        this.id = id;
    }

    public void run() {
        long sum = 0;

        for (long i = 0; i < 10_000_000; i++) {
            sum += i * i * i + i * id;
        }

        System.out.println("Thread " + id + " finish. Sum = " + sum);
    }
}

public class Scaling {
    public static void main(String[] args) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();

        System.out.println("Logical processors: " + cores);

        runTest(1);
        runTest(cores);
    }

    public static void runTest(int threadCount) throws InterruptedException {
        Thread[] threads = new Thread[threadCount];

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new MathTask(i));
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }

        long end = System.currentTimeMillis();

        System.out.println("Time for " + threadCount + " thread: " + (end - start) + " ms");
        System.out.println();
    }
}