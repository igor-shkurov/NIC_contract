package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.user.User;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.nio.channels.NotYetConnectedException;
import java.time.LocalDateTime;

public class LoginHistoryRecorder {
    private static MongoClient client = null;
    private static MongoCollection<Document> logins;

    public static void record(User user) {
        if (client == null) {
            throw new NotYetConnectedException();
        }

        Document document = new Document();
        document.put("username", user.getUsername());
        document.put("role", user.getRole().toString());
        document.put("datetime", LocalDateTime.now());
        logins.insertOne(document);
    }

    public static void initConnection() {
        String uri = "mongodb://account:contract@mongodb:27017/admin";
        client = MongoClients.create(uri);

        MongoDatabase database = client.getDatabase("mongo");
        logins = database.getCollection("login_history");
    }

    public static void closeConnection() {
        client.close();
    }
}
