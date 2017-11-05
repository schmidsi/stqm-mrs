package ch.fhnw.swc.mrs.datagenerator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

public class GeneratingDataloader implements Dataloader {
    private static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final LocalDate TODAY = LocalDate.now();
    private static final int CURRENT_YEAR = TODAY.getYear();
    
    private static final String MOVIE_SQL = 
            "INSERT INTO movies (title, isrented, releasedate, pricecategory, agerating)"
            + "  VALUES (?, ?, ?, ?, ?)";
    private static final String CLIENT_SQL = "INSERT INTO clients ( firstname, name, birthdate ) VALUES ( ?, ?, ? )";
    private static final String RENTAL_SQL = "INSERT INTO rentals ( movieid, clientid, rentaldate )"
            + "  VALUES ( ?, ?, ? )";

    private Connection connection;
    private int maxUsers, maxMovies, maxRentals;
    private double rentedRatio;
    private Random rnd = new Random();
    private int[] usersAge; 

    /**
     * Generates random data for MRS database.
     * @param nofusers that shall be generated.
     * @param nofmovies that shall be generated.
     * @param nofrentals that shall be generated.
     */
    public GeneratingDataloader(int nofusers, int nofmovies, int nofrentals) {
        maxUsers = nofusers;
        maxMovies = nofmovies;
        maxRentals = nofrentals;
        usersAge = new int[nofusers];
        rentedRatio = (double) maxRentals / (double) maxMovies;
    }
        
    
    @Override
    public void load(Connection connection) throws Exception {
        this.connection = connection;
        
        System.out.println("Generating data to load...");

        generateUsers();
        generateMovies();
    }

    private void generateRental(int movieid, int ageRating) throws Exception {
        int userid = rnd.nextInt(maxUsers);
        while (usersAge[userid] < ageRating) {
            userid = (userid + 1) % maxUsers;
        }
        LocalDate rentalDate = TODAY.minusDays(rnd.nextInt(8));
        writeRentalToDb(userid + 100, movieid, rentalDate);
    }


    private void generateMovies() throws Exception {
        int countMovies = 0;
        int countRentals = 0;
        for (int i = 0; i < maxMovies; i++) {
            String title = generateString(40);
            LocalDate releasedAt = generateDate(70);
            String priceCategory;
            switch (rnd.nextInt(3)) {
            case 1: priceCategory = "New Release"; break;
            case 2: priceCategory = "Children"; break;
            default: priceCategory = "Regular";
            }
            int rating = rnd.nextInt(19);
            boolean rented = rnd.nextDouble() < rentedRatio;
            writeMovieToDb(title, releasedAt, priceCategory, rating, rented);
            if (rented) {
                generateRental(i + 100, rating);
                countRentals++;
            }
            countMovies++;
            if (countMovies % 100 == 0) {
                System.out.println("" + countMovies + " movies and " + countRentals + " rentals");
            }
        }
        System.out.println("" + countMovies + " movies generated");
        System.out.println("" + countRentals + " rentals generated");
    }


    private void generateUsers() throws Exception {
        int count = 0;
        for (int i = 0; i < maxUsers; i++) {
            String firstname = generateString(40);
            String lastname = generateString(40);
            LocalDate birthdate = generateDate(40);
            writeUserToDb(lastname, firstname, birthdate);
            int age = Period.between(birthdate, TODAY).getYears();
            usersAge[i] = age;
            count++;
            if (count % 100 == 0) {
                System.out.println("" + count + " users");
            }
        }
        System.out.println("" + count + " users generated");
    }

    private void writeMovieToDb(String title, LocalDate releasedAt, String priceCategory, int rating,
                               boolean rented) throws Exception {
        PreparedStatement writeStmt = connection.prepareStatement(MOVIE_SQL, Statement.RETURN_GENERATED_KEYS);
        writeStmt.setString(1, title);
        writeStmt.setBoolean(2, rented);
        writeStmt.setDate(3, Date.valueOf(releasedAt));
        writeStmt.setString(4, priceCategory);
        writeStmt.setInt(5, rating);
        writeStmt.execute();
        writeStmt.close();
    }

    private void writeUserToDb(String name, String firstname, LocalDate birthdate) throws Exception {
        PreparedStatement writeStmt = connection.prepareStatement(CLIENT_SQL);
        writeStmt.setString(1, firstname);
        writeStmt.setString(2, name);
        writeStmt.setDate(3, Date.valueOf(birthdate));
        writeStmt.execute();
        writeStmt.close();
    }

    private void writeRentalToDb(int userId, int movieId, LocalDate rentalDate) throws Exception {
        PreparedStatement writeStmt = connection.prepareStatement(RENTAL_SQL);
        writeStmt.setInt(1, movieId);
        writeStmt.setInt(2, userId);
        writeStmt.setDate(3, Date.valueOf(rentalDate));
        writeStmt.execute();
        writeStmt.close();
    }
    
    private String generateString(int length) {
        int len = randBetween(4, length);     
        char[] s = new char[len];
        for (int i = 0; i < len; i++) {
            s[i] = CHARS[rnd.nextInt(CHARS.length)];
        }
        return new String(s);
    }

    private LocalDate generateDate(int maxAge) {
        int year = randBetween(CURRENT_YEAR - maxAge, CURRENT_YEAR);
        int dayOfYear = randBetween(1, 365);

        return LocalDate.ofYearDay(year, dayOfYear);
    }
    
    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
