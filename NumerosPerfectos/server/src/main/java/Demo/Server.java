// ruta física: server/src/main/java/Demo/Server.java
package Demo;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Server {
    public static void main(String[] args) {
        // Inicializa ICE
        Communicator ic = Util.initialize(args);

        // Crea un ObjectAdapter en el puerto 10000
        ObjectAdapter adapter =
                ic.createObjectAdapterWithEndpoints("MasterAdapter", "tcp -h 192.168.131.39 -p 10000");

        // Instancia y publica el servant
        MasterI servant = new MasterI();
        adapter.add(servant, Util.stringToIdentity("master"));

        adapter.activate();
        System.out.println("Master listo en puerto 10000");

        ic.waitForShutdown();
    }
}
