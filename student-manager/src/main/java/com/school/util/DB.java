package com.school.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    private static Connection conn;

    private static final String PROD_DB = "jdbc:sqlite:data/students.db";
    private static final String TEST_DB = "jdbc:sqlite:data/test_students.db";

    public static Connection get() {

        try {
            if (conn == null || conn.isClosed()) {
                init();
            }
            return conn;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void init() {

        try {

            String url = isTestEnvironment() ? TEST_DB : PROD_DB;

            conn = DriverManager.getConnection(url);

            conn.createStatement().execute("""
                CREATE TABLE IF NOT EXISTS students(
                student_id TEXT PRIMARY KEY,
                full_name TEXT NOT NULL,
                programme TEXT NOT NULL,
                level INTEGER,
                gpa REAL,
                email TEXT,
                phone TEXT,
                date_added TEXT,
                status TEXT
                )
            """);

            System.out.println("Database ready: " + url);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isTestEnvironment() {
        return System.getProperty("test.env") != null;
    }
}