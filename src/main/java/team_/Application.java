package team_;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4_team_2");

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
