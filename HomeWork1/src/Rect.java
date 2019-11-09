public class Rect {
    Point topLeft;
    Point bottomRight;

    public Rect(Point topLeft, Point bottomRight) {
        if (topLeft.x > bottomRight.x || topLeft.y < bottomRight.y) {
            throw new IllegalArgumentException();
        }

        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
}
