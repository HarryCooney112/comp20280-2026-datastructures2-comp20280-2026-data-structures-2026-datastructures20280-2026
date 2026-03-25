package project20280.exercises;

public class recursion {
    public static void main(String[] args) {
        bool(2468);
    }

    public static int fibonacci(int n) {
        if (n == 1 || n == 0) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    public static int trigonacci(int n) {
        if (n == 3) {
            return 1;
        } else if (n <= 2) {
            return 0;
        }
        return trigonacci(n - 1) + trigonacci(n - 2) + trigonacci(n - 3);
    }
    public static int func91(int n) {
        if (n > 100) {
            return n - 10;
        }
        return func91(n + 11);
    }
    public static void bool(int n) {
        if (n / 2 == 0) {
            System.out.print(n);
            return;
        }
        bool(n / 2);
        System.out.print(n % 2);
    }
}
