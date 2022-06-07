import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**Title: EnglishWords
 * This class is to create the word list and save corresponding methods which are about words processing
 * @author  Sicheng Shen
 */
public class EnglishWords{
    ArrayList<String> wordsArray = new ArrayList<String>();//to save the word list
    ArrayList<ImageIcon> character = new ArrayList<ImageIcon>();//to save the letter button array

    /*
     * The method is to read the letter button images from file
     * and save them in an arraylist.
     */
    public void loadchar() throws IOException{
        String[] file = new String[27];
        file[0] = StartGame.path+"\\WordleGame\\src\\resources\\character\\A.jpg";
        file[1] = StartGame.path+"\\WordleGame\\src\\resources\\character\\B.jpg";
        file[2] = StartGame.path+"\\WordleGame\\src\\resources\\character\\C.jpg";
        file[3] = StartGame.path+"\\WordleGame\\src\\resources\\character\\D.jpg";
        file[4] = StartGame.path+"\\WordleGame\\src\\resources\\character\\E.jpg";
        file[5] = StartGame.path+"\\WordleGame\\src\\resources\\character\\F.jpg";
        file[6] = StartGame.path+"\\WordleGame\\src\\resources\\character\\G.jpg";
        file[7] = StartGame.path+"\\WordleGame\\src\\resources\\character\\H.jpg";
        file[8] = StartGame.path+"\\WordleGame\\src\\resources\\character\\I.jpg";
        file[9] = StartGame.path+"\\WordleGame\\src\\resources\\character\\J.jpg";
        file[10] = StartGame.path+"\\WordleGame\\src\\resources\\character\\K.jpg";
        file[11] = StartGame.path+"\\WordleGame\\src\\resources\\character\\L.jpg";
        file[12] = StartGame.path+"\\WordleGame\\src\\resources\\character\\M.jpg";
        file[13] = StartGame.path+"\\WordleGame\\src\\resources\\character\\N.jpg";
        file[14] = StartGame.path+"\\WordleGame\\src\\resources\\character\\O.jpg";
        file[15] = StartGame.path+"\\WordleGame\\src\\resources\\character\\P.jpg";
        file[16] = StartGame.path+"\\WordleGame\\src\\resources\\character\\Q.jpg";
        file[17] = StartGame.path+"\\WordleGame\\src\\resources\\character\\R.jpg";
        file[18] = StartGame.path+"\\WordleGame\\src\\resources\\character\\S.jpg";
        file[19] = StartGame.path+"\\WordleGame\\src\\resources\\character\\T.jpg";
        file[20] = StartGame.path+"\\WordleGame\\src\\resources\\character\\U.jpg";
        file[21] = StartGame.path+"\\WordleGame\\src\\resources\\character\\V.jpg";
        file[22] = StartGame.path+"\\WordleGame\\src\\resources\\character\\W.jpg";
        file[23] = StartGame.path+"\\WordleGame\\src\\resources\\character\\X.jpg";
        file[24] = StartGame.path+"\\WordleGame\\src\\resources\\character\\Y.jpg";
        file[25] = StartGame.path+"\\WordleGame\\src\\resources\\character\\Z.jpg";
        file[26] = StartGame.path+"\\WordleGame\\src\\resources\\button.jpg";
        for(int i =0; i<27;i++) {
            ImageIcon imageIcon = new ImageIcon(file[i]);
            Image img = imageIcon.getImage().getScaledInstance(50, 50, 1);
            imageIcon.setImage(img);
            character.add(imageIcon);
        }
    }

    /**
     * This method is to read word list from file
     * @throws IOException may throw exceptions when load files.
     */
    public void loadWords() throws IOException {
        File file = new File(StartGame.path+"\\WordleGame\\src\\resources\\dictionary.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        while ((s = br.readLine()) != null)
           wordsArray.add(s.toUpperCase());
    }

    /**
     * The method is used to check whether the entered text is written in Enlish
     * @param word the word entered by user
     * @return a true or false depends on the input
     */
    private boolean checkWordIfEnglish(String word){
        word = word.toLowerCase();
        for(int i=0;i<word.length();i++){
            if(word.charAt(i)<'a'||word.charAt(i)>'z') {
                return false;
            }
        }
        return true;
    }

    /**
     * The method is to check if the # of letters is 5
     * @param word the word entered by user
     * @return true if the length is 5, otherwise false
     */
    private boolean checkWordLength(String word){
        if(word.length()==5) return true;
        else return false;
    }

    /**
     * The method is to check the if it is an Englishm word(if it is in the list)
     * @param word the word entered by user
     * @return true if it is in the list, otherwise false
     */
    private boolean checkIfWordInList(String word){
        int position = this.wordsArray.indexOf(word);
        if(position>=0) return true;
        else return false;
    }

    /**
     * A integrated method by three method above to check if the word is valid
     * @param word the word entered by user
     * @return true if valid, other wise false
     */
    public boolean wordCheck(String word){
        if(!checkWordLength(word)){ return false;}
        if(!checkWordIfEnglish(word)){ return false;}
        if(!checkIfWordInList(word)){ return false;}
        else return true;
    }

    /**
     * The function of the method is the similar as above but return a string to display on the panel to users
     * @param word the word entered by user
     * @return the correspongding string describe which error it is.
     */
    public String checkWord(String word){
        if(!checkWordLength(word)){return "Only 5-letters English word permitted!";}
        else if(!checkWordIfEnglish(word)){return "Only English letter permitted!"; }
        else if(!checkIfWordInList(word)){return "The word isn't in the word list!";}
        else return "";
    }

    /**
     * Randomly choose a word from the list as the answer of the game
     * @return the word chose randomly
     */
    public String chooseWord(){
        return wordsArray.get((int)(Math.random()*wordsArray.size()));
    }
}