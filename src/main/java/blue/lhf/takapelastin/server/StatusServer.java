package blue.lhf.takapelastin.server;

import blue.lhf.takapelastin.checker.*;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class StatusServer extends Thread implements Closeable {
    private final HttpServer server;

    public StatusServer(final ViolationChecker sourceTask, final int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 10);

        final ExecutorService executor = Executors.newWorkStealingPool(16);
        this.server.setExecutor(executor);

        this.server.createContext("/status/",
            new StatusHandler(sourceTask));
    }

    @Override
    public void run() {
        this.server.start();
    }

    @Override
    public void close() {
        this.server.stop(3);
    }
}
