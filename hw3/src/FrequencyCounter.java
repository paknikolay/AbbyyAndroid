import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class FrequencyCounter {
    public static void main(String[] args) {
        HashMap<Character, Integer> frequency = new HashMap<Character, Integer>();

        try (FileInputStream inputStream = new FileInputStream("./text.txt")) {

            while (true) {
                int input = inputStream.read();
                if (input == -1) {
                    break;
                }
                char symbol = (char) input;
                if (frequency.containsKey(symbol)) {
                    Integer value = Integer.sum(frequency.get(symbol), 1);
                    frequency.put(symbol, value);
                } else {
                    frequency.put(symbol, 0);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (HashMap.Entry<Character, Integer> entry : frequency.entrySet()) {
            System.out.println("" + entry.getKey() + ": " + entry.getValue());
        }
    }
}
