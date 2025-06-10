package Demo;

import com.zeroc.Ice.Current;
import java.util.*;
import java.util.concurrent.*;

public class MasterI implements Master {
    private final Map<String, WorkerPrx> workers = new ConcurrentHashMap<>();

    @Override
    public void addWorker(String workerId, WorkerPrx worker, Current __current) {
        workers.put(workerId, worker);
        System.out.println("Worker registrado: " + workerId);
    }

    @Override
    public long[] arrangeAnswer(long start, long end, Current __current) {
        System.out.println("Solicitud recibida para rango: " + start + " a " + end);

        int numWorkers = workers.size();
        if (numWorkers == 0) {
            System.out.println("Advertencia: No hay workers registrados");
            return new long[0];
        }

        List<Future<long[]>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);

        long chunkSize = (end - start + 1) / numWorkers;
        long remainder = (end - start + 1) % numWorkers;
        long currentStart = start;

        for (WorkerPrx worker : workers.values()) {
            long currentEnd = currentStart + chunkSize - 1;
            if (remainder > 0) {
                currentEnd++;
                remainder--;
            }

            if (currentEnd > end) currentEnd = end;

            final long workerStart = currentStart;
            final long workerEnd = currentEnd;

            futures.add(executor.submit(() ->
                    worker.findPerfectNumbers(workerStart, workerEnd)
            ));

            currentStart = currentEnd + 1;
            if (currentStart > end) break;
        }

        List<Long> allPerfects = new ArrayList<>();
        for (Future<long[]> future : futures) {
            try {
                long[] partial = future.get(5, TimeUnit.MINUTES);
                for (long num : partial) {
                    allPerfects.add(num);
                }
            } catch (Exception e) {
                System.err.println("Error obteniendo resultados de worker: " + e.getMessage());
                e.printStackTrace();
            }
        }

        executor.shutdown();

        // Convertir a array primitivo
        long[] result = new long[allPerfects.size()];
        for (int i = 0; i < allPerfects.size(); i++) {
            result[i] = allPerfects.get(i);
        }

        System.out.println("Resultados consolidados: " + result.length + " números");
        return result;
    }
}