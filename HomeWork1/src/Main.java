public class Main {

    public static double CalculateDistance(Point f, Point s) {
        return Math.sqrt((f.x - s.x) * (f.x - s.x) + (f.y - s.y) * (f.y - s.y));
    }

    public static void main(String[] args) {
        Point f = new Point(0,4);
        Point s = new Point(3,0);
        System.out.println(CalculateDistance(f,s));

        Rect rect = new Rect(f, s);

        System.out.println(RectHandler.GetArea(rect));
        System.out.println(RectHandler.GetPerimeter(rect));
        System.out.println(RectHandler.HasOrigin(rect));
    }
}
