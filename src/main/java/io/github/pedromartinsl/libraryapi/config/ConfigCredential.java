package io.github.pedromartinsl.libraryapi.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigCredential {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String GOOGLE_CLIENT_ID = dotenv.get("GOOGLE_CLIENT_ID");
    public static final String GOOGLE_CLIENT_SECRET = dotenv.get("GOOGLE_CLIENT_SECRET");
    public static final String DATASOURCE_URL = "(jdbc:postgresql://localhost:5433/library)";
    public static final String DATASOURCE_USERNAME = "(postgresprod)";
    public static final String DATASOURCE_PASSWORD = "(postgresprod)";
}
