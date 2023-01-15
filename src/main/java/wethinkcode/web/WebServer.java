package wethinkcode.web;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

/**
 * DO NOT MODIFY THIS CODE
 */
public class WebServer {
    private static final String PAGES_DIR = "/html";
    private static final int DEFAULT_PORT = 5000;
    private final Javalin javalin;

    /**
     * Configure the server
     */
    public WebServer() {
        this.javalin = Javalin.create(config -> {
            config.addStaticFiles(PAGES_DIR, Location.CLASSPATH);
        });
    }

    /**
     * Run the server for manual testing
     * @param args ignored, no command line args
     */
    public static void main(String[] args) {
        WebServer server = new WebServer();
        server.start(DEFAULT_PORT);
    }

    /**
     * Start the server
     * @param port the port on which to start the server
     */
    public void start(int port) {
        this.javalin.start(port);
    }

    /**
     * Stop the server
     */
    public void stop() {
        this.javalin.close();
        this.javalin.stop();
    }
}
