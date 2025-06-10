package Demo;

import com.zeroc.Ice.*;

import java.util.UUID;

public class WorkerApp {
    public static void main(String[] args) {
        Communicator ic = Util.initialize(args);
        ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints("WorkerAdapter", "default -p 0"); // Puerto aleatorio

        WorkerI servant = new WorkerI();
        String workerId = "worker_" + UUID.randomUUID().toString().substring(0, 8);
        Identity id = Util.stringToIdentity(workerId);
        adapter.add(servant, id);
        adapter.activate();

        MasterPrx master = MasterPrx.checkedCast(
                ic.stringToProxy("master:tcp -h 192.168.131.39 -p 10000")
        );

        // Esperar a que el maestro esté disponible
        int attempts = 0;
        while (master == null && attempts < 10) {
            try {
                Thread.sleep(1000);
                master = MasterPrx.checkedCast(
                        ic.stringToProxy("master:tcp -h 192.168.131.39 -p 10000")
                );
                attempts++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (master == null) {
            System.err.println("No se pudo conectar con el maestro");
            ic.destroy();
            return;
        }

        WorkerPrx workerPrx = WorkerPrx.uncheckedCast(adapter.createProxy(id));
        master.addWorker(workerId, workerPrx);
        System.out.println("Worker registrado: " + workerId);

        ic.waitForShutdown();
    }
}