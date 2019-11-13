import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FrequencyCounter {
    public static void main(String[] args) {
        Map<Character, Integer> frequency = new HashMap<Character, Integer>();

        try (Reader fileReader = new FileReader("./text.txt")) {
            Reader inputStream = new BufferedReader(fileReader);
            char[] input = new char[1];

            while (true) {
                int res = inputStream.read(input);
                char symbol = input[0];
                if (res == -1) {
                    break;
                }
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

        try (Writer fileWriter = new FileWriter("./out.txt")) {
            for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
                fileWriter.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
