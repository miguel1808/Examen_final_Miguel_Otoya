import java.util.HashMap;

public class RentalInfo {
  private static final String REGULAR = "regular";
  final private HashMap<String, Movie> movies;

  public RentalInfo() {
    movies = new HashMap<>();
    movies.put("F001", new Movie("You've Got Mail", REGULAR));
    movies.put("F002", new Movie("Matrix", REGULAR));
    movies.put("F003", new Movie("Cars", "childrens"));
    movies.put("F004", new Movie("Fast & Furious X", "new"));
  }

  public String statement(Customer customer) {
    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");

    for (MovieRental rental : customer.getRentals()) {
      double thisAmount = calculateAmount(rental);
      frequentEnterPoints += calculateFrequentEnterPoints(rental);
      result.append("\t").append(getMovieTitle(rental)).append("\t").append(thisAmount).append("\n");
      totalAmount += thisAmount;
    }

    result.append("Amount owed is ").append(totalAmount).append("\n");
    result.append("You earned ").append(frequentEnterPoints).append(" frequent renter points");

    return result.toString();
  }

  private double calculateAmount(MovieRental rental) {
    Movie movie = movies.get(rental.getMovieId());
    double thisAmount = 0;

    if (movie.getCode().equals(REGULAR)) {
      thisAmount = 2;
      if (rental.getDays() > 2) {
        thisAmount += (rental.getDays() - 2) * 1.5;
      }
    } else if (movie.getCode().equals("new")) {
      thisAmount = rental.getDays() * 3;
    } else if (movie.getCode().equals("childrens")) {
      thisAmount = 1.5;
      if (rental.getDays() > 3) {
        thisAmount += (rental.getDays() - 3) * 1.5;
      }
    }
    return thisAmount;
  }

  private int calculateFrequentEnterPoints(MovieRental rental) {
    Movie movie = movies.get(rental.getMovieId());
    int frequentEnterPoints = 1;

    if (movie.getCode().equals("new") && rental.getDays() > 2) {
      frequentEnterPoints++;
    }
    return frequentEnterPoints;
  }

  private String getMovieTitle(MovieRental rental) {
    return movies.get(rental.getMovieId()).getTitle();
  }
}