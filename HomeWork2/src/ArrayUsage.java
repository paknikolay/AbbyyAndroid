import java.util.Arrays;
import java.util.Random;

public class ArrayUsage {
    public static void main(String[] args) {
        int[][] array = new int[6][7];
        Random random = new Random();
        for (int i = 0; i < 6; i++ ) {
            for (int j = 0; j < 7; j++) {
                array[i][j] = random.nextInt(10);
            }
        }
        System.out.println(Arrays.deepToString(array));

        for (int i = 0; i < 6; i++ ) {
            //отсортируем по возрастанию
            Arrays.sort(array[i]);
            //поменяем наибольший с наименьшим
            int tmp = array[i][0];
            array[i][0] = array[i][6];
            array[i][6] = tmp;
        }
        System.out.println(Arrays.deepToString(array));
    }
}
