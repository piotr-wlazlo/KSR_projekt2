package pl.ksr;

import pl.ksr.db.DB;
import java.sql.SQLException;


public class DbTest {
    public static void main(String[] args) {
        try (var connection =  DB.connect()){
            System.out.println("Connected to the PostgreSQL database.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
