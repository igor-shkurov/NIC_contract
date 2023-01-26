package com.example.accountingsystem.utility;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.nio.channels.NotYetConnectedException;
import java.time.LocalDateTime;

public class LoginHistoryRecorder {
    private static MongoClient client = null;
    private static MongoCollection<Document> logins;

    public static void record(String username) {
        if (client == null) {
            throw new NotYetConnectedException();
        }

        Document document = new Document();
        document.put("username", username);
        document.put("datetime", LocalDateTime.now());
        logins.insertOne(document);
    }

    public static void initConnection() {
        client = new MongoClient();
        MongoDatabase database = client.getDatabase("login_db");
        logins = database.getCollection("login_history");
    }

    public static void closeConnection() {
        client.close();
    }
}
