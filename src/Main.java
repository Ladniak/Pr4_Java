import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        processCharArray();
        processRealNumbers();
    }

    private static void processCharArray() {
        System.out.println("\nЗадача 1: Обробка символьного масиву\n");
        long start = System.nanoTime();

        CompletableFuture.supplyAsync(() -> {
            char[] array = new char[20];
            Random random = new Random();
            for (int i = 0; i < array.length; i++) {
                array[i] = (char) (random.nextInt(95) + 32); // Генерація друкованих ASCII символів
            }
            System.out.println("Згенерований масив: " + Arrays.toString(array));
            return array;
        }).thenApplyAsync(array -> {
            char[] alphabets = IntStream.range(0, array.length)
                    .mapToObj(i -> array[i])
                    .filter(Character::isLetter)
                    .map(String::valueOf)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString()
                    .toCharArray();
            System.out.println("Алфавітні символи: " + Arrays.toString(alphabets));
            return array;
        }).thenApplyAsync(array -> {
            char[] spaces = IntStream.range(0, array.length)
                    .mapToObj(i -> array[i])
                    .filter(ch -> ch == ' ' || ch == '\t')
                    .map(String::valueOf)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString()
                    .toCharArray();
            System.out.println("Пробіли та табуляції: " + Arrays.toString(spaces));
            return array;
        }).thenApplyAsync(array -> {
            char[] others = IntStream.range(0, array.length)
                    .mapToObj(i -> array[i])
                    .filter(ch -> !Character.isLetter(ch) && ch != ' ' && ch != '\t')
                    .map(String::valueOf)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString()
                    .toCharArray();
            System.out.println("Інші символи: " + Arrays.toString(others));
            return array;
        }).thenRunAsync(() -> {
            long end = System.nanoTime();
            System.out.printf("Задача 1 завершена за %d мс.%n", TimeUnit.NANOSECONDS.toMillis(end - start));
        }).join();
    }

    private static void processRealNumbers() {
        System.out.println("\nЗадача 2: Обробка дійсних чисел\n");
        long start = System.nanoTime();

        CompletableFuture.supplyAsync(() -> {
            double[] numbers = new Random().doubles(20, -100, 100).toArray();
            System.out.println("Згенеровані числа: " + Arrays.toString(numbers));
            return numbers;
        }).thenApplyAsync(numbers -> {
            double maxDiff = 0;
            for (int i = 1; i < numbers.length; i++) {
                maxDiff = Math.max(maxDiff, Math.abs(numbers[i] - numbers[i - 1]));
            }
            return maxDiff;
        }).thenAcceptAsync(maxDiff -> {
            System.out.println("Максимальна абсолютна різниця: " + maxDiff);
        }).thenRunAsync(() -> {
            long end = System.nanoTime();
            System.out.printf("Задача 2 завершена за %d мс.%n", TimeUnit.NANOSECONDS.toMillis(end - start));
        }).join();
    }
}
