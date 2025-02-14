package io.github.pedromartinsl.libraryapi.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigCredential {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String GOOGLE_CLIENT_ID = dotenv.get("GOOGLE_CLIENT_ID");
    public static final String GOOGLE_CLIENT_SECRET = dotenv.get("GOOGLE_CLIENT_SECRET");
}
