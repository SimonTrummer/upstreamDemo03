package at.htlkaindorf;

import java.util.Arrays;

import java.util.Arrays;

public class PrimeNumbers {

    public static void main(String[] args) {
        int[] limits = { 100, 1000, 10000, 100000, 1000000, 10000000, 99999999 };

        for (int limit : limits) {
            runAndCompare(limit);
        }
    }

    public static void runAndCompare(int limit) {
        int numRuns = 10; // Number of runs for statistical analysis
        long[] bruteForceTimes = new long[numRuns];
        long[] sieveTimes = new long[numRuns];

        for (int i = 0; i < numRuns; i++) {
            bruteForceTimes[i] = measureTime(() -> calculatePrimesBruteForce(limit));
            sieveTimes[i] = measureTime(() -> calculatePrimesSieve(limit));
        }

        double bruteForceMean = calculateMean(bruteForceTimes);
        double sieveMean = calculateMean(sieveTimes);

        System.out.println("Limit: " + limit);
        System.out.println("Brute Force Mean Time: " + bruteForceMean + " milliseconds");
        System.out.println("Sieve of Eratosthenes Mean Time: " + sieveMean + " milliseconds");

        double percentageReduction = ((bruteForceMean - sieveMean) / bruteForceMean) * 100;

        System.out.printf("Sieve of Eratosthenes is %.2f%% faster than Brute Force.\n", percentageReduction);

        // Estimation based on time complexity
        double estimationSieve = estimateTimeSieve(limit);
        double estimationBruteForce = estimateTimeBruteForce(limit);

        System.out.println("Estimated time for Sieve of Eratosthenes: " + estimationSieve + " milliseconds");
        System.out.println("Estimated time for Brute Force: " + estimationBruteForce + " milliseconds");

        // Scaling factors
        double scalingFactorSieve = bruteForceMean / estimationSieve;
        double scalingFactorBruteForce = bruteForceMean / estimationBruteForce;

        System.out.println("Scaling factor for Sieve of Eratosthenes: " + scalingFactorSieve);
        System.out.println("Scaling factor for Brute Force: " + scalingFactorBruteForce);

        // Adjusted estimations based on scaling factors
        double adjustedEstimationSieve = estimationSieve * scalingFactorSieve;
        double adjustedEstimationBruteForce = estimationBruteForce * scalingFactorBruteForce;

        System.out.println("Adjusted estimated time for Sieve of Eratosthenes: " + adjustedEstimationSieve + " milliseconds");
        System.out.println("Adjusted estimated time for Brute Force: " + adjustedEstimationBruteForce + " milliseconds");

        System.out.println();
    }

    public static long measureTime(Runnable function) {
        long startTime = System.currentTimeMillis();
        function.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static double calculateMean(long[] times) {
        return Arrays.stream(times).average().orElse(0);
    }

    public static void calculatePrimesBruteForce(int limit) {
        // Brute Force Prime Calculation
        for (int i = 2; i <= limit; i++) {
            isPrime(i);
        }
    }

    public static void calculatePrimesSieve(int limit) {
        // Sieve of Eratosthenes Prime Calculation
        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Estimation based on time complexity
    public static double estimateTimeSieve(int limit) {
        // Adjust k1 based on your system and implementation
        double k1 = 0.000001; // Example constant factor for Sieve of Eratosthenes

        return k1 * limit * Math.log(Math.log(limit));
    }

    public static double estimateTimeBruteForce(int limit) {
        // Adjust k2 based on your system and implementation
        double k2 = 0.00000000001; // Example constant factor for Brute Force

        return k2 * Math.pow(limit, 2);
    }
}
