public class RectHandler {
    private RectHandler(){
    }

    public static int getArea(Rect rect) {
        return (rect.topLeft.y - rect.bottomRight.y) * (rect.bottomRight.x - rect.topLeft.x);
    }

    public static int getPerimeter(Rect rect) {
        return 2 * (rect.topLeft.y - rect.bottomRight.y) + 2 * (rect.bottomRight.x - rect.topLeft.x);
    }

    /**
     * Возвразает истину, если в прямоугольнике содержится начало координат
     * @param rect прямоугольник
     */
    public static boolean hasOrigin(Rect rect) {
        return rect.topLeft.x <= 0 && rect.topLeft.y >= 0 &&
               rect.bottomRight.x >= 0 && rect.bottomRight.y <= 0;
    }


}
