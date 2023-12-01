// Movie Recommendation System
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// class User start
class User {
    String username;
    Map<String, Double> preferences;
    Map<String, Double> numRatings;

    public User(String username) {
        this.username = username;
        this.preferences = new HashMap<>();
        this.numRatings = new HashMap<>();
    }

    public void updatePreferences(String genre, double weight) {
        preferences.put(genre, weight);
    }

    public void rateMovie(String movieTitle, double rating) {
        numRatings.put(movieTitle, rating);
    }

    public User get(String key) {
        return null;
    }
}
// class User ends

// class Moie start

class Movie {
    String title;
    List<String> genres;
    Map<String, Double> numRatings;

    public Movie(String title, List<String> genres, Map<String, Double> ratings) {
        this.title = title;
        this.genres = genres;
        this.numRatings = new HashMap<>();
    }

    public double getAverageRating() {
        double sum = 0.0;
        for (double rating : numRatings.values()) {
            sum += rating;
        }
        return sum / numRatings.size();
    }

    public void addRating(String username, double rating) {
        numRatings.put(username, rating);
    }
}

// class Moie ends
// class MovieRecommendationSystem start

class MovieRecommendationSystem {
    List<Movie> movies;
    Map<String, User> users;

    public MovieRecommendationSystem() {
        this.movies = new ArrayList<>();
        this.users = new HashMap<>();
    }

    public void registerUser(String username) {
        users.put(username, new User(username));
    }

    public void addMovie(String title, List<String> genres) {
        movies.add(new Movie(title, genres, null));
    }

    public void rateMovie(String username, String movieTitle, double rating) {
        User user = users.get(username);
        if (user != null) {
            user.rateMovie(movieTitle, rating);
        }
    }

    public List<Movie> searchMoviesByGenre(String genre) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.genres.contains(genre)) {
                result.add(movie);
            }
        }
        return result;
    }

    public List<Movie> getRecommendedMovies(String username) {
        User user = users.get(username);
        if (user == null) {
            return new ArrayList<>();
        }

        Map<Movie, Double> movieScores = new HashMap<>();
        for (Movie movie : movies) {
            double score = 0.0;
            int count = 0;
            for (Map.Entry<String, Double> entry : movie.numRatings.entrySet()) {
                User otherUser = user.get(entry.getKey());
                if (otherUser != null) {
                    Double otherRating = otherUser.numRatings.get(movie.title);
                    if (otherRating != null) {
                        score += entry.getValue() * otherRating;
                        count++;
                    }
                }
            }

            if (count > 0) {
                movieScores.put(movie, score / count);
            }
        }

        List<Movie> recommendedMovies = new ArrayList<>();
        movieScores.entrySet().stream().sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .limit(5).forEach(entry -> recommendedMovies.add(entry.getKey()));

        return recommendedMovies;
    }
}

// class MovieRecommendationSystem ends

public class MovieRecommendationApplication {


    public static void main(String[] args) {
        MovieRecommendationSystem recommendationSystem = new MovieRecommendationSystem();


        // Movies in all genres
        recommendationSystem.addMovie("Avatar", Arrays.asList("Action"));
        recommendationSystem.addMovie("Old", Arrays.asList("Horror"));
        recommendationSystem.addMovie("OMG2 ", Arrays.asList("Comedy"));
        recommendationSystem.addMovie("SpiderMan", Arrays.asList("Action"));
        recommendationSystem.addMovie("RiverWild ", Arrays.asList("Thriller"));
        recommendationSystem.addMovie("Nun", Arrays.asList("Horror"));
        recommendationSystem.addMovie("GrandMasti", Arrays.asList("Romance"));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username : ");
        String username = scanner.nextLine();
        scanner.nextLine();


        while (true) {
            System.out.println("******************************");
            System.out.println("|       Movie Section        |");
            System.out.println("******************************");
            System.out.println("*  1. Search Movies by genre *");
            System.out.println("*  2. Get recommended Movies *");
            System.out.println("*  3. Rate a movie           *");
            System.out.println("*  4. Exit                   *");
            System.out.println("******************************");

            System.out.println("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter the genre name you want to search : ");
                    String genre = scanner.nextLine();
                    List<Movie> genreMovies = recommendationSystem.searchMoviesByGenre(genre);
                    System.out.println("Movies in " + genre + " genre :");
                    System.out.println();
                    for (Movie movie : genreMovies) {
                        System.out.println("**" + movie.title + "**");
                    }
                    System.out.println();
                    break;
                case 2:
                    List<Movie> recommendedMovies = recommendationSystem.getRecommendedMovies(username);
                    System.out.println("Recommended Movies :");
                    for (Movie movie : recommendedMovies) {
                        System.out.println(movie.title + " (Average Rating : " + movie.getAverageRating());
                    }
                    break;
                case 3:
                    System.out.println("Enter the movie title you want to rate : ");
                    String movieTitle = scanner.nextLine();
                    System.out.println("Enter your rating (0.0 to 5.0)");
                    double rating = scanner.nextDouble();
                    recommendationSystem.rateMovie(username, movieTitle, rating);
                    System.out.println(" Movie rated Successfully");
                    System.out.println();
                    break;

                case 4:
                    System.out.println("Exit..");
                    return;

                default:
                    System.out.println("Invalid choice .Please try again");
            }
        }
    }
}
