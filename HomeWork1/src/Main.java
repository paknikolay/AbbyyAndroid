public class Main {

    public static double CalculateDistance(Point f, Point s) {
        return Math.sqrt((f.x - s.x) * (f.x - s.x) + (f.y - s.y) * (f.y - s.y));
    }

    public static void main(String[] args) {
        Point f = new Point(0,0);
        Point s = new Point(3,4);
        System.out.println(CalculateDistance(f,s));
    }
}
