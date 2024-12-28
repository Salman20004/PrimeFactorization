import java.util.*;


public class PrimeFactorization {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a number as a command-line argument.");
            return;
        }

        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }

        if (n <= 1) {
            System.out.println("Cannot factorize numbers less than or equal to 1.");
            return;
        } else if (isPrime(n)) {
            System.out.println(n + " is a prime number.");
            return;
        }

        System.out.print("Prime Factorization for " + n + " is: ");
        while (!isPrime(n)) {
            int factor = Factorization(n);
                 System.out.print(factor + " x "); 
            n /= factor;
        }
        System.out.println(n);
    }

    public static int findGCD(int x, int y) {
        while (y != 0) {
            int tmp = y;
            y = x % y;
            x = tmp;
        }
        return Math.abs(x);
    }

    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2 || number == 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) return false;
        }
        return true;
    }

    
    public static int Factorization(int n) {
        if (n % 2 == 0) return 2; // Quick check for even numbers

        Random rand = new Random();
        int x = 2, y = 2, gcd = 1;
        int c = 1; 

        while (gcd == 1 || gcd == n || !isPrime(gcd)) {
            x = (x * x + c) % n;
            y = (y * y + c) % n;
            y = (y * y + c) % n;
            gcd = findGCD(Math.abs(x - y), n);

            if (x==y && !isPrime(gcd)) {
                c = rand.nextInt(n - 1) + 1;
                x = y = 2;
                gcd = 1;
            }
        }

        return gcd;
    } }
