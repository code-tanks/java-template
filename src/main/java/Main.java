import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.URI;
// import org.json.JSONObject;
import java.util.HashMap;

// Replace these with the actual classes
// import lib.api;
// import tanks.my_tank;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import api.BaseTank;
import tanks.MyTank;

public class Main {

    static BaseTank tank = MyTank.createTank();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/request_commands", new RequestCommandsHandler());
        server.createContext("/request_commands_by_event", new RequestCommandsByEventHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Java HTTP server is running on port 8080..");
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "pong";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class RequestCommandsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            tank.run();
            String response = toJSON(tank.commands);
            tank.commands.clear();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class RequestCommandsByEventHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStreamReader isr =  new InputStreamReader(t.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);

            // From now on, the right way of moving from bytes to utf-8 characters:

            int b;
            StringBuilder buf = new StringBuilder(512);
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }

            br.close();
            isr.close();
            String body = buf.toString(); // This should actually read the body as a stream, similar to req.on('data') in Node.js
            System.out.println("body");
            System.out.println(body);
            
            // Parse body to JSON here and store in 'data'
            // Let's assume parseJSON is a function that handles this.
            HashMap<String, Object> data = parseJSON(body);
            tank.onEvent(data);
            String response = toJSON(tank.commands);
            tank.commands.clear();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static String toJSON(Object commands) {
        Gson gson = new Gson();
        // You'll need to convert the commands object to JSON here.
        // Let's assume toJSON is a function that handles this.
        return gson.toJson(commands);
    }
    
    static HashMap<String, Object> parseJSON(String body) {
        // You'll need to convert the body string to a JSON object here.
        // Let's assume parseJSON is a function that handles this.
        Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
        Gson gson = new Gson();

        HashMap<String, Object> map = gson.fromJson(body, type);

        return map;
    }
}
