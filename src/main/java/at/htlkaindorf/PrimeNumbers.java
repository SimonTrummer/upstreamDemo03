package at.htlkaindorf;

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
}


