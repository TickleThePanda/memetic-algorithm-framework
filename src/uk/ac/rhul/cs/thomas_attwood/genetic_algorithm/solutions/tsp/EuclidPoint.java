package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

public class EuclidPoint implements TspPoint<EuclidPoint> {

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
    EuclidPoint other = (EuclidPoint) obj;
    if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
      return false;
    if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
      return false;
    return true;
  }

  private final double x;
	private final double y;
	
	public EuclidPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int distance(EuclidPoint other) {
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
