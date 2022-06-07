import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**Title: ExtendedEnglishWords
 * This class extends the EnglishWords and is to contain an extended word list.
 * @author Sicheng Shen
 */
public class ExtendedEnglishWords extends EnglishWords{
    /**
     * The method is to read an extended word list from file
     * @throws IOException may throw exceptions when load files.
     */
    public void loadWords() throws IOException {
        File file = new File(StartGame.path+"\\WordleGame\\src\\resources\\extended-dictionary.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        while ((s = br.readLine()) != null) wordsArray.add(s.toUpperCase());
    }
}
