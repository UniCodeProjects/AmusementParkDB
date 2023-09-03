package org.apdb4j;

import io.github.cdimascio.dotenv.Dotenv;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.apdb4j.db.Tables.*;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        var url = dotenv.get("DB_URL");
        var user = dotenv.get("DB_USERNAME");
        var password = dotenv.get("DB_PASSWORD");
        // DB connection sample.
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DSLContext db = DSL.using(connection);
            Result<Record> result = db.select().from(PRICE_LISTS).fetch();
            for (Record r : result) {
                var year = r.getValue(PRICE_LISTS.YEAR);
                System.out.println("YEAR: " + year);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
