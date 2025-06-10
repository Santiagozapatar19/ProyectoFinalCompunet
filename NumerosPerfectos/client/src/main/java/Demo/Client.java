package Demo;

import com.zeroc.Ice.*;

public class Client {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: Client <inicio> <fin>");
            return;
        }

        long start = Long.parseLong(args[0]);
        long end = Long.parseLong(args[1]);

        Communicator ic = Util.initialize(args);
        MasterPrx master = MasterPrx.checkedCast(
                ic.stringToProxy("master:tcp -h 192.168.131.39 -p 10000")
        );

        long startTime = System.currentTimeMillis();
        long[] results = master.arrangeAnswer(start, end);
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("\nNúmeros perfectos encontrados:");
        for (long num : results) {
            System.out.println(num);
        }
        System.out.printf("\nTiempo total: %.3f segundos\n", duration / 1000.0);

        ic.destroy();
    }
}