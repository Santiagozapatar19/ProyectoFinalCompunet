package Demo;

import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.List;

public class WorkerI implements Worker {
    @Override
    public long[] findPerfectNumbers(long start, long end, Current __current) {
        System.out.println("Worker procesando rango: " + start + " a " + end);
        List<Long> perfectNumbers = new ArrayList<>();

        for (long num = start; num <= end; num++) {
            if (isPerfect(num)) {
                perfectNumbers.add(num);
            }
        }

        System.out.println("Worker encontrados: " + perfectNumbers.size());
        return perfectNumbers.stream().mapToLong(Long::longValue).toArray();
    }

    private boolean isPerfect(long number) {
        if (number <= 1) return false;
        if (number % 2 != 0) return false; // Los números perfectos conocidos son pares

        long sum = 1;
        long sqrt = (long) Math.sqrt(number);

        for (long i = 2; i <= sqrt; i++) {
            if (number % i == 0) {
                sum += i;
                long complement = number / i;
                if (complement != i) {
                    sum += complement;
                }
            }
        }

        return sum == number;
    }
}