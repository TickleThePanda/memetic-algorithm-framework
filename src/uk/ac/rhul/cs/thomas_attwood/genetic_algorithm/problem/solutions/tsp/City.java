package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp;

public interface City<E extends City<E>> {
  
  public class Euclidian implements City<Euclidian> {

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      long temp;
      temp = Double.doubleToLongBits(x);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(y);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Euclidian other = (Euclidian) obj;
      if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
        return false;
      if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
        return false;
      return true;
    }

    private final double x;
    private final double y;
    
    public Euclidian(double x, double y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int distance(Euclidian other) {
      double xd = (this.x - other.x);
      double yd = (this.y - other.y);
      int dist = (int) Math.round(Math.sqrt(xd*xd + yd*yd));
      return dist;
    }

    public double getX() {
      return this.x;
    }

    @Override
    public double getY() {
      return this.y;
    }
    
  }
  
  public class Geo implements City<Geo> {

    public static final double RRR = 6378.388;
    public static final double PI = 3.141592;
    private double lon;
    private double lat;

    public Geo(double lat, double lon) {
      this.lat = lat;
      this.lon = lon;
    }

    public static double toGeographical(double n) {
      int deg = (int) n;

      double min = n - deg;
      return PI * (deg + 5.0 * min / 3.0) / 180.0;
    }

    public int distance(Geo other) {
      double q1 = Math.cos(this.lon - other.lon);
      double q2 = Math.cos(this.lat - other.lat);
      double q3 = Math.cos(this.lat + other.lat);
      int dist = (int) (RRR
          * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
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

	double getX();	
	double getY();
	
	int distance(E city);
}
