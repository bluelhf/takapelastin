package blue.lhf.takapelastin.server;

import blue.lhf.takapelastin.checker.ViolationChecker;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

import static java.util.concurrent.Executors.newWorkStealingPool;

/**
 * An HTTP server with a context for returning the current status of a {@link ViolationChecker}
 * as JSON data. The context is available at <code>/</code>.
 *
 * @see StatusHandler
 * */
public class StatusServer extends Thread implements Closeable {
    private final HttpServer server;

    public StatusServer(final ViolationChecker checker, final int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 10);

        this.server.setExecutor(newWorkStealingPool(16));
        this.server.createContext("/", new StatusHandler(checker));
    }

    @Override
    public void run() {
        this.server.start();
    }

    @Override
    public void close() {
        this.server.stop(1);
    }
}
