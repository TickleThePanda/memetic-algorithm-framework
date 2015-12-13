package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

public interface City<E extends City<E>> {

  public class Euclidian implements City<Euclidian> {

    private final double x;

    private final double y;

    public Euclidian(final double x, final double y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int distance(final Euclidian other) {
      final double xd = this.x - other.x;
      final double yd = this.y - other.y;
      final int dist = (int) Math.round(Math.sqrt(xd * xd + yd * yd));
      return dist;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Euclidian other = (Euclidian) obj;
      if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
        return false;
      }
      if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
        return false;
      }
      return true;
    }

    @Override
    public double getX() {
      return this.x;
    }

    @Override
    public double getY() {
      return this.y;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      long temp;
      temp = Double.doubleToLongBits(x);
      result = prime * result + (int) (temp ^ temp >>> 32);
      temp = Double.doubleToLongBits(y);
      result = prime * result + (int) (temp ^ temp >>> 32);
      return result;
    }

  }

  public class Geo implements City<Geo> {

    public static final double RRR = 6378.388;
    public static final double PI = 3.141592;

    public static double toGeographical(final double n) {
      final int deg = (int) n;

      final double min = n - deg;
      return PI * (deg + 5.0 * min / 3.0) / 180.0;
    }

    private final double lon;

    private final double lat;

    public Geo(final double lat, final double lon) {
      this.lat = lat;
      this.lon = lon;
    }

    @Override
    public int distance(final Geo other) {
      final double q1 = Math.cos(this.lon - other.lon);
      final double q2 = Math.cos(this.lat - other.lat);
      final double q3 = Math.cos(this.lat + other.lat);
      final int dist = (int) (RRR * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
      return dist;
    }

    @Override
    public double getX() {
      return lat;
    }

    @Override
    public double getY() {
      return lon;
    }
  }

  int distance(E city);

  double getX();

  double getY();
}
