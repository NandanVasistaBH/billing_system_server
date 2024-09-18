package com.telstra.billing_system.utils;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
// the spring dev server is running on 10000 on dockerizing we will expose 10000 from container
// again this 10000 port is exposed to the POD which also accepts request @10000
// but pod lives in a k8 cluster so access this pod in the cluster use NODE PORT 30001
public class ApiLoadTest {
    private static final String URL = "http://localhost:30001/subscriptions/prepaid";
    private static final int NUM_THREADS = 10; // Number of concurrent threads
    private static final int REQUESTS_PER_THREAD = 10000;
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < REQUESTS_PER_THREAD; j++) {
                    sendRequest(client);
                }
            });
        }

        // Shutdown executor and wait for all tasks to complete
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static void sendRequest(HttpClient client) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Status: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
        }
    }
}
