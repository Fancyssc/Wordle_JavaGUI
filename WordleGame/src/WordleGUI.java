import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**Title:WordleGUI
 * This class is created to build GUI of wordle
 * @author Sicheng Shen
 */
public class WordleGUI extends JFrame {
    EnglishWords words = new EnglishWords();//create the Englishword object.
    ExtendedEnglishWords ewords = new ExtendedEnglishWords();//create the Englishword object.
    FranchWords fwords = new FranchWords();//create the Franchword object.
    int chances = 6,echances=6,fchances=6;//count the chances left.
    ArrayList<JButton[]> buttonArray,ebuttonArray,fbuttonArray;//to display the letters

    JTextArea eliminatedLetters,eeliminatedLetters,feliminatedLetters;//to store eliminated letters

    ArrayList<Character> eliminatedArray = new ArrayList<Character>();//to save eliminated letters
    ArrayList<Character> eeliminatedArray = new ArrayList<Character>();//to save eliminated letters

    ArrayList<Character> feliminatedArray = new ArrayList<Character>();//to save eliminated letters

    JButton enterButton,eenterButton,fenterButton;//click to enter thge word
    String wordSelected,ewordSelected,fwordSelected;//to store the word select from the list
    String currentWord,ecurrentWord,fcurrentWord;//to store the word entered
    JTextField text,etext,ftext;//Textfield to enter the word
    Color[] colors,ecolors,fcolors;//store the corresponding color

    JTextArea invalidEnter,einvalidEnter,finvalidEnter;//store invalid information
    Container container = this.getContentPane();

    JButton jb1,jb2,jb3,jb4 ;//The 4 main buttons on the start page.

    /**
     * Constructor, to set up the game and build the initial window.
     */
    public void initInterface(){
        this.setBounds(520,30,630,850);
        this.setTitle("wordle");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
        this.setIconImage(im);//set icon
        container.setLayout(null);


        //set initial background
        container.setBackground(Color.WHITE);
        JLabel backgroundLabel = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\background.jpg"));
        JPanel backjp=new JPanel();
        backjp.setSize(630,850);backjp.setLocation(0,-5);
        backjp.add(backgroundLabel);
        this.setVisible(true);
        //initial background set over

        //set button panel
        JPanel buttonjp=new JPanel();buttonjp.setLayout(null); container.add(buttonjp);
        buttonjp.setSize(600,100);buttonjp.setLocation(10,670);buttonjp.setBackground(Color.white);
        buttonjp.setOpaque(false);
        //button panel set over

        //set button
        Font font = new Font("Comic Sans MS",Font.BOLD,30);
        jb1 =new JButton();jb1.setSize(82,40);jb1.setBorder(null);jb1.setFont(font);jb1.setForeground(new Color(0xFCDFB3));
        jb2 =new JButton();jb2.setSize(82,40);jb2.setBorder(null);jb2.setFont(font);jb2.setForeground(new Color(0xFCDFB3));
        jb3 =new JButton();jb3.setSize(82,40);jb3.setBorder(null);jb3.setFont(font);jb3.setForeground(new Color(0xFCDFB3));
        jb4 =new JButton();jb4.setSize(100,40);jb4.setBorder(null);jb4.setFont(font);jb4.setForeground(new Color(0xFCDFB3));
        jb1.setText("Basic");jb2.setText("Hard");jb3.setText("Help");jb4.setText("French");
        jb1.setContentAreaFilled(false);jb2.setContentAreaFilled(false);
        jb3.setContentAreaFilled(false);jb4.setContentAreaFilled(false);
        buttonjp.add(jb1);jb1.setLocation(50,30);
        buttonjp.add(jb2);jb2.setLocation(175,30);
        buttonjp.add(jb3);jb3.setLocation(450,30);
        buttonjp.add(jb4);jb4.setLocation(300,30);
        //button set over
        container.add(backjp);
        //set game buttons(basic&hard)
        jb1.addActionListener(new BasicButtonListener());
        jb2.addActionListener(new HardButtonListener());
        jb4.addActionListener(new FranchButtonListener());

        //set game buttons(basic&hard) over

        //set help button action
        jb3.addActionListener(new HelpButtonListener());
        //set help button action over

    }

    /**
     * This class is to generate an ActionList when click "Help"
     */
    class HelpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Help Dialog
            new HelpDialog();
        }
    }

    /**
     * These 2 classes are to generate a game when clickm "Basic" or "Harder"
     */
    class  BasicButtonListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            jb1.setEnabled(false);
            new BasicDialog();
        }
    }
    class  HardButtonListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            jb2.setEnabled(false);
            new HardDialog();
        }
    }
    /**
     * The class is to generate a game when clickm "Franch"
     */
    class  FranchButtonListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            jb4.setEnabled(false);
            new FranchDialog();
        }
    }


    /**
     * This method is to compare if the word entered is the same as the word from list
     * @param guess the word entered by user
     * @param target the word selected from the list
     */
    public void wordCompare(String guess, String target,Color[] c,ArrayList<Character> a){
        for(int i=0;i<5;i++){
            if(guess.charAt(i)==target.charAt(i)) {c[i]=Color.GREEN;continue;}
            if(target.indexOf(guess.charAt(i))!=-1){c[i]=Color.YELLOW;continue;}
            else{c[i]=new Color(0xFF706E6E, true);
                if(!a.contains(guess.charAt(i))){
                    a.add(guess.charAt(i));
                }
            }
        }
    }

    /**
     * The method set the color to display-button
     * @param number the index of rows of buttons
     */
    public void colorset(int number,Color[] c,ArrayList<JButton[]> a){
        for(int i =0;i<5;i++){
            a.get(number)[i].setBackground(c[i]);
        }
    }

    /**
     * The program is to display the chars which have been entered
     * @param number the index of rows of buttons
     */
    public void charset(int number,ArrayList<JButton[]> arrayList,String s,EnglishWords e){
        for(int i =0;i<5;i++){
            int chardif = s.charAt(i) - 'A';
            arrayList.get(number)[i].setIcon(e.character.get(chardif));
        }
    }
    public void charset(ArrayList<JButton[]> arrayList,EnglishWords e){
        for(int j=0;j<6;j++) {
            for (int i = 0; i < 5; i++) {
                arrayList.get(j)[i].setIcon(e.character.get(26));
            }
        }
    }

    /**
     * The method is used to count how many greens there are
     * @param colors color array
     * @return the number of greens
     */
    public int colorcount(Color[] colors){
        int number=0;
        for(int i = 0;i<5;i++ ){
            if(colors[i]==Color.GREEN) number++;
        }
        return  number;
    }

    /**
     * This class is to generate a Dialog when click "Help"
     */
    class HelpDialog extends JDialog {
        public HelpDialog() {
            this.setBounds(415, 200, 800, 600);
            this.setResizable(false);
            this.setTitle("Rules For Wordle");
            this.setLayout(null);
            Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
            this.setIconImage(im);//set icon
            JLabel helpLabel = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\helpDialog.jpg"));
            this.getContentPane().add(helpLabel);
            helpLabel.setLocation(-6,-10);helpLabel.setSize(800,600);
            this.setVisible(true);
        }
    }

    /**
     * These 3 classes are the dialog for game itself
     */
    class BasicDialog extends JDialog{
        public BasicDialog() {
            text=new JTextField();
            text.setEditable(true);
            chances= 6;
            text.setText("");
            /*
             * load words from dictionary
             */
            try {
                words.loadWords();
                words.loadchar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }//load over
            wordSelected = words.chooseWord().toUpperCase();//word choosed
            colors = new Color[5];
            this.setBounds(415, 200, 800, 600);this.setResizable(false);
            this.setTitle("Wordle-Basic");
            Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
            this.setIconImage(im);//set icon
            JLabel dialogBack = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\basicDialogBack.jpg"));
            dialogBack.setLocation(0,0);dialogBack.setSize(800,600);
            Container container =  this.getContentPane();container.setLayout(null);

            //set panel background
            container.setBackground(new Color(0xFFF2F2));
            //set over
            //add display buttons
            ImageIcon imageIcon = new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\button.jpg");
            Image img = imageIcon.getImage().getScaledInstance(50, 50, 1);
            imageIcon.setImage(img);
            buttonArray =new ArrayList<JButton[]>();
            for(int j =0;j<6;j++) {
                JButton[] jb=new JButton[5];
                buttonArray.add(jb);
                for (int i = 0; i < 5; i++) {
                    jb[i] = new JButton();
                    container.add(jb[i]);
                    jb[i].setSize(55, 55);
                    jb[i].setLocation(450+i * 65, 50+j*65);
                    jb[i].setIcon(imageIcon);
                    jb[i].setBorder(null);
                    jb[i].setBackground(Color.white);
                    this.setVisible(true);
                }
            }//display buttons add over
            this.setVisible(true);
            //add textfield and enter button
            text.setSize(220,60);text.setLocation(450,450);
            container.add(text);text.setHorizontalAlignment(JTextField.CENTER);
            text.setFont(new Font("Comic Sans MS",Font.BOLD,25));text.setBorder(null);
            enterButton = new JButton();
            enterButton.setFont(new Font("Comic Sans MS",Font.BOLD,18));
            enterButton.setText("Enter");enterButton.setBackground(Color.WHITE);enterButton.setBorder(null);
            container.add(enterButton);enterButton.setSize(88,52);enterButton.setLocation(680,455);
            enterButton.addActionListener(new BasicGameAction());
            //add over
            //add text area to store eliminated letters
            eliminatedLetters = new JTextArea();eliminatedLetters.setSize(350,150);
            eliminatedLetters.setLocation(55,300);eliminatedLetters.setOpaque(false);
            eliminatedLetters.setFont(new Font("Comic Sans MS",Font.BOLD,25));eliminatedLetters.setForeground(new Color(0x7E7EF1));
            eliminatedLetters.setEditable(false);
            this.add(eliminatedLetters);
            //add over
            //add text to tell the user invalid input
            invalidEnter =new JTextArea();invalidEnter.setSize(350,150);
            invalidEnter.setLocation(50,420);invalidEnter.setOpaque(false);
            invalidEnter.setForeground(new Color(0xFFFD3939, true));
            invalidEnter.setFont(new Font("Comic Sans MS",Font.BOLD,18));
            //add over
            this.add(invalidEnter);
            this.add(dialogBack);
            this.setVisible(true);
        }
    }

    class HardDialog extends JDialog{

        public HardDialog() {
            etext=new JTextField();
            etext.setEditable(true);
            echances= 6;
            etext.setText("");
            ewords = new ExtendedEnglishWords();
            try {
                ewords.loadWords();
                ewords.loadchar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ewordSelected = ewords.chooseWord().toUpperCase();//word choosed
            this.setBounds(415, 200, 800, 600);this.setResizable(false);
            this.setTitle("Wordle-Hard");
            Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
            this.setIconImage(im);//set icon
            JLabel dialogBack = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\hardDialogBack.jpg"));
            dialogBack.setLocation(0,0);dialogBack.setSize(800,600);
            Container container =  this.getContentPane();container.setLayout(null);
            //set panel background
            container.setBackground(new Color(0xFFF2F2));
            //set over
            //add display buttons
            ImageIcon imageIcon = new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\button.jpg");
            Image img = imageIcon.getImage().getScaledInstance(50, 50, 1);
            imageIcon.setImage(img);
            ebuttonArray =new ArrayList<JButton[]>();
            ecolors =new Color[5];
            for(int j =0;j<6;j++) {
                JButton[] ejb=new JButton[5];
                ebuttonArray.add(ejb);
                for (int i = 0; i < 5; i++) {
                    ejb[i] = new JButton();
                    container.add(ejb[i]);
                    ejb[i].setSize(55, 55);
                    ejb[i].setLocation(450+i * 65, 50+j*65);
                    ejb[i].setIcon(imageIcon);
                    ejb[i].setBorder(null);
                    ejb[i].setBackground(Color.white);
                    this.setVisible(true);
                }
            }//display buttons add over
            this.setVisible(true);
            //add textfield and enter button
            etext.setSize(220,60);etext.setLocation(450,450);
            container.add(etext);etext.setHorizontalAlignment(JTextField.CENTER);
            etext.setFont(new Font("Comic Sans MS",Font.BOLD,25));etext.setBorder(null);
            eenterButton = new JButton();
            eenterButton.setFont(new Font("Comic Sans MS",Font.BOLD,18));
            eenterButton.setText("Enter");eenterButton.setBackground(Color.WHITE);eenterButton.setBorder(null);
            container.add(eenterButton);eenterButton.setSize(88,52);eenterButton.setLocation(680,455);
            eenterButton.addActionListener(new HardGameAction());
            //add over
            //add text area to store eliminated letters
            eeliminatedLetters = new JTextArea();eeliminatedLetters.setSize(350,150);
            eeliminatedLetters.setLocation(55,300);eeliminatedLetters.setOpaque(false);
            eeliminatedLetters.setFont(new Font("Comic Sans MS",Font.BOLD,25));eeliminatedLetters.setForeground(Color.ORANGE);
            eeliminatedLetters.setEditable(false);
            this.add(eeliminatedLetters);
            //add over
            //add text to tell the user invalid input
            einvalidEnter =new JTextArea();einvalidEnter.setSize(350,150);
            einvalidEnter.setLocation(55,450);einvalidEnter.setOpaque(false);
            einvalidEnter.setForeground(new Color(0xCDF84E12, true));
            einvalidEnter.setFont(new Font("Comic Sans MS",Font.BOLD,18));
            //add over
            this.add(einvalidEnter);
            this.add(dialogBack);
            this.setVisible(true);
        }

    }

    /**
     * Franch game class
     */
    class FranchDialog extends JDialog{

        public FranchDialog() {
            ftext=new JTextField();
            ftext.setEditable(true);
            fchances= 6;
            ftext.setText("");
            fwords = new FranchWords();
            try {
                fwords.loadWords();
                fwords.loadchar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fwordSelected = fwords.chooseWord().toUpperCase();//word choosed
            this.setBounds(415, 200, 800, 600);this.setResizable(false);
            this.setTitle("Wordle-French");
            Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
            this.setIconImage(im);//set icon
            JLabel dialogBack = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\frenchDialogBack.jpg"));
            dialogBack.setLocation(0,0);dialogBack.setSize(800,600);
            Container container =  this.getContentPane();container.setLayout(null);
            //set panel background
            container.setBackground(new Color(0xFFF2F2));
            //set over
            //add display buttons
            ImageIcon imageIcon = new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\button.jpg");
            Image img = imageIcon.getImage().getScaledInstance(50, 50, 1);
            imageIcon.setImage(img);
            fbuttonArray =new ArrayList<JButton[]>();
            fcolors =new Color[5];
            for(int j =0;j<6;j++) {
                JButton[] fjb=new JButton[5];
                fbuttonArray.add(fjb);
                for (int i = 0; i < 5; i++) {
                    fjb[i] = new JButton();
                    container.add(fjb[i]);
                    fjb[i].setSize(55, 55);
                    fjb[i].setLocation(450+i * 65, 50+j*65);
                    fjb[i].setIcon(imageIcon);
                    fjb[i].setBorder(null);
                    fjb[i].setBackground(Color.white);
                    this.setVisible(true);
                }
            }//display buttons add over
            this.setVisible(true);
            //add textfield and enter button
            ftext.setSize(220,60);ftext.setLocation(450,450);
            container.add(ftext);ftext.setHorizontalAlignment(JTextField.CENTER);
            ftext.setFont(new Font("Comic Sans MS",Font.BOLD,25));ftext.setBorder(null);
            fenterButton = new JButton();
            fenterButton.setFont(new Font("Comic Sans MS",Font.BOLD,18));
            fenterButton.setText("Enter");fenterButton.setBackground(Color.WHITE);fenterButton.setBorder(null);
            container.add(fenterButton);fenterButton.setSize(88,52);fenterButton.setLocation(680,455);
            fenterButton.addActionListener(new FranchGameAction());
            //add over
            //add text area to store eliminated letters
            feliminatedLetters = new JTextArea();feliminatedLetters.setSize(350,150);
            feliminatedLetters.setLocation(55,320);feliminatedLetters.setOpaque(false);
            feliminatedLetters.setFont(new Font("Comic Sans MS",Font.BOLD,25));feliminatedLetters.setForeground(Color.ORANGE);
            feliminatedLetters.setEditable(false);
            this.add(feliminatedLetters);
            //add over
            //add text to tell the user invalid input
            finvalidEnter =new JTextArea();finvalidEnter.setSize(350,150);
            finvalidEnter.setLocation(55,450);finvalidEnter.setOpaque(false);
            finvalidEnter.setForeground(new Color(0xCDF84E12, true));
            finvalidEnter.setFont(new Font("Comic Sans MS",Font.BOLD,18));
            //add over
            this.add(finvalidEnter);
            this.add(dialogBack);
            this.setVisible(true);
        }

    }

    /**
     * This class create the actionListener for "enter" button in basic level.
     */
    class BasicGameAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //initial game panel over
            invalidEnter.setText("");
            currentWord=text.getText();
            currentWord=currentWord.toUpperCase();
            //initial game panel over
            //check if entered word valid
            if (!words.wordCheck(currentWord)) {
                text.setText("");
                String s =words.checkWord(currentWord);
                invalidEnter.setText(s);
            }
            //check over
            else{
                text.setText("");
                //display corresponding word,color and eliminated letters.
                wordCompare(currentWord, wordSelected,colors,eliminatedArray);
                colorset(6-chances,colors,buttonArray);
                charset(6-chances,buttonArray,currentWord,words);
                int count = colorcount(colors);
                eliminatedArray.sort(Comparator.naturalOrder());
                String tmp="";
                for(int i = 0;i<eliminatedArray.size();i++){
                    tmp=tmp+eliminatedArray.get(i)+" ";
                }
                eliminatedLetters.setText(tmp);
                //display over
                //check if the answer is correct
                if(count == 5){
                    //create a "success" dialog
                    JDialog successDialog = new JDialog();
                    successDialog.setTitle("SUCCESS!");
                    successDialog.setBounds(415,280,800,450);
                    Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
                    successDialog.setIconImage(im);//set icon
                    JLabel success = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\success.jpg"));
                    JPanel back=new JPanel();
                    back.setSize(800,444);back.setLocation(0,0);
                    JTextArea correctAnswer = new JTextArea();correctAnswer.setLocation(570,90);
                    correctAnswer.setSize(200,50);correctAnswer.setFont(new Font("Comic Sans MS",Font.BOLD,25));
                    correctAnswer.setOpaque(false);correctAnswer.setEditable(false);correctAnswer.setForeground(Color.ORANGE);
                    correctAnswer.setText(wordSelected);
                    successDialog.add(correctAnswer);
                    back.add(success);
                    successDialog.add(back);
                    successDialog.setVisible(true);
                    successDialog.setResizable(false);
                    //create a "success" dialog over
                    //reset the game
                    chances = 6;
                    wordSelected=words.chooseWord();
                    text.setText("");
                    for(int i =0;i<5;i++){
                        colors[i]=Color.white;
                    }
                    for(int i =0 ;i<6;i++){
                        colorset(i,colors,buttonArray);
                        charset(buttonArray,words);
                    }
                    charset(buttonArray,words);
                    eliminatedArray.clear();
                    eliminatedLetters.setText("");
                    //reset game over
                }
                //check over
                else chances--;
                //check if all chances used up
                if(chances==0){
                    //create the "fail" dialog
                    JDialog failDialog = new JDialog();
                    failDialog.setTitle("CHANCES USED UP!");failDialog.setLayout(null);
                    failDialog.setBounds(415,280,800,444);
                    JLabel fail = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\fail.jpg"));
                    JPanel back=new JPanel();back.add(fail);
                    back.setSize(800,444);back.setLocation(0,0);
                    Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
                    failDialog.setIconImage(im);//set icon
                    JTextArea correctAnswer = new JTextArea();correctAnswer.setLocation(570,100);
                    correctAnswer.setSize(200,50);correctAnswer.setFont(new Font("Comic Sans MS",Font.BOLD,30));
                    correctAnswer.setOpaque(false);correctAnswer.setEditable(false);correctAnswer.setForeground(Color.ORANGE);
                    correctAnswer.setText(wordSelected);
                    failDialog.add(correctAnswer);
                    failDialog.setResizable(false);
                    //create the "fail" dialog over
                    //display the correct answer
                    chances = 6;
                    wordSelected=words.chooseWord();
                    text.setText("");
                    for(int i =0;i<5;i++){
                        colors[i]=Color.white;
                    }
                    for(int i =0 ;i<6;i++){
                        colorset(i,colors,buttonArray);
                        charset(buttonArray,words);
                    }
                    failDialog.add(back);
                    failDialog.setVisible(true);
                    //display the correct answer over
                    //reset game
                    charset(buttonArray,words);
                    eliminatedArray.clear();
                    eliminatedLetters.setText("");
                    text.setText("");
                    //reset game over
                }
                //check over
            }
        }
    }

    /**
     * This class create the actionListener for "enter" button in hard level.
     */
    class HardGameAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //initial game panel over
            einvalidEnter.setText("");
            ecurrentWord=etext.getText();
            ecurrentWord=ecurrentWord.toUpperCase();
            //initial game panel over
            //check if entered word valid
            if (!ewords.wordCheck(ecurrentWord)) {
                etext.setText("");
                String s =ewords.checkWord(ecurrentWord);
                einvalidEnter.setText(s);
            }
            //check over
            else{
                etext.setText("");
                //display corresponding word,color and eliminated letters.
                wordCompare(ecurrentWord, ewordSelected,ecolors,eeliminatedArray);
                colorset(6-echances,ecolors,ebuttonArray);
                charset(6-echances,ebuttonArray,ecurrentWord,ewords);
                int count = colorcount(ecolors);
                eeliminatedArray.sort(Comparator.naturalOrder());
                String tmp="";
                for(int i = 0;i<eeliminatedArray.size();i++){
                    tmp=tmp+eeliminatedArray.get(i)+" ";
                }
                eeliminatedLetters.setText(tmp);
                //display over
                //check if the answer is correct
                if(count == 5){
                    //create a "success" dialog
                    JDialog successDialog = new JDialog();
                    successDialog.setTitle("SUCCESS!");
                    successDialog.setBounds(415,280,800,450);
                    JLabel success = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\hardsuccess.jpg"));
                    Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
                    successDialog.setIconImage(im);//set icon
                    JTextArea ecorrectAnswer = new JTextArea();ecorrectAnswer.setLocation(570,90);
                    ecorrectAnswer.setSize(200,50);ecorrectAnswer.setFont(new Font("Comic Sans MS",Font.BOLD,25));
                    ecorrectAnswer.setOpaque(false);ecorrectAnswer.setEditable(false);ecorrectAnswer.setForeground(Color.ORANGE);
                    ecorrectAnswer.setText(ewordSelected);
                    successDialog.add(ecorrectAnswer);
                    JPanel back=new JPanel();successDialog.add(back);
                    back.setSize(800,444);back.setLocation(10,0);
                    back.add(success);
                    successDialog.setVisible(true);
                    successDialog.setResizable(false);
                    //create a "success" dialog over
                    //reset the game
                    echances = 6;
                    ewordSelected=ewords.chooseWord();
                    etext.setText("");
                    for(int i =0;i<5;i++){
                        ecolors[i]=Color.white;
                    }
                    for(int i =0 ;i<6;i++){
                        colorset(i,ecolors,ebuttonArray);
                        charset(ebuttonArray,ewords);
                    }
                    charset(ebuttonArray,ewords);
                    eeliminatedArray.clear();
                    eeliminatedLetters.setText("");
                    //reset game over
                }
                //check over
                else echances--;
                //check if all chances used up
                if(echances==0){
                    //create the "fail" dialog
                    JDialog failDialog = new JDialog();
                    failDialog.setTitle("CHANCES USED UP!");failDialog.setLayout(null);
                    failDialog.setBounds(415,280,800,444);
                    JLabel fail = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\fail.jpg"));
                    JPanel back=new JPanel();back.add(fail);
                    back.setSize(800,444);back.setLocation(0,0);
                    Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
                    failDialog.setIconImage(im);//set icon
                    JTextArea ecorrectAnswer = new JTextArea();ecorrectAnswer.setLocation(570,100);
                    ecorrectAnswer.setSize(200,50);ecorrectAnswer.setFont(new Font("Comic Sans MS",Font.BOLD,30));
                    ecorrectAnswer.setOpaque(false);ecorrectAnswer.setEditable(false);ecorrectAnswer.setForeground(Color.ORANGE);
                    ecorrectAnswer.setText(ewordSelected);
                    failDialog.add(ecorrectAnswer);
                    failDialog.setResizable(false);
                    //create the "fail" dialog over
                    //display the correct answer
                    echances = 6;
                    ewordSelected=ewords.chooseWord();
                    etext.setText("");
                    for(int i =0;i<5;i++){
                        ecolors[i]=Color.white;
                    }
                    for(int i =0 ;i<6;i++){
                        colorset(i,ecolors,ebuttonArray);
                        charset(ebuttonArray,ewords);
                    }
                    //display the correct answer over
                    //reset game
                    charset(ebuttonArray,ewords);
                    failDialog.add(back);
                    failDialog.setVisible(true);
                    eeliminatedArray.clear();
                    eeliminatedLetters.setText("");
                    etext.setText("");
                    //reset game over
                }
                //check over
            }
        }
    }

    /**
     * This class create the actionListener for "enter" button in Franch edition.
     */
    class FranchGameAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //initial game panel over
            finvalidEnter.setText("");
            fcurrentWord=ftext.getText();
            fcurrentWord=fcurrentWord.toUpperCase();
            //initial game panel over
            //check if entered word valid
            if (!fwords.wordCheck(fcurrentWord)) {
                ftext.setText("");
                String s =fwords.checkWord(fcurrentWord);
                finvalidEnter.setText(s);
            }
            //check over
            else{
                ftext.setText("");
                //display corresponding word,color and eliminated letters.
                wordCompare(fcurrentWord, fwordSelected,fcolors,feliminatedArray);
                colorset(6-fchances,fcolors,fbuttonArray);
                charset(6-fchances,fbuttonArray,fcurrentWord,fwords);
                int count = colorcount(fcolors);
                feliminatedArray.sort(Comparator.naturalOrder());
                String tmp="";
                for(int i = 0;i<feliminatedArray.size();i++){
                    tmp=tmp+feliminatedArray.get(i)+" ";
                }
                feliminatedLetters.setText(tmp);
                //display over
                //check if the answer is correct
                if(count == 5){
                    //create a "success" dialog
                    JDialog successDialog = new JDialog();
                    successDialog.setTitle("SUCCESS!");
                    successDialog.setBounds(415,280,800,450);
                    JLabel success = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\frenchsuccess.jpg"));
                    Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
                    successDialog.setIconImage(im);//set icon
                    JTextArea fcorrectAnswer = new JTextArea();fcorrectAnswer.setLocation(570,90);
                    fcorrectAnswer.setSize(200,50);fcorrectAnswer.setFont(new Font("Comic Sans MS",Font.BOLD,25));
                    fcorrectAnswer.setOpaque(false);fcorrectAnswer.setEditable(false);fcorrectAnswer.setForeground(Color.ORANGE);
                    fcorrectAnswer.setText(fwordSelected);
                    successDialog.add(fcorrectAnswer);
                    JPanel back=new JPanel();successDialog.add(back);
                    back.setSize(800,444);back.setLocation(10,0);
                    back.add(success);
                    successDialog.setVisible(true);
                    successDialog.setResizable(false);
                    //create a "success" dialog over
                    //reset the game
                    fchances = 6;
                    fwordSelected=fwords.chooseWord();
                    ftext.setText("");
                    for(int i =0;i<5;i++){
                        fcolors[i]=Color.white;
                    }
                    for(int i =0 ;i<6;i++){
                        colorset(i,fcolors,fbuttonArray);
                        charset(fbuttonArray,fwords);
                    }
                    charset(fbuttonArray,fwords);
                    feliminatedArray.clear();
                    feliminatedLetters.setText("");
                    //reset game over
                }
                //check over
                else fchances--;
                //check if all chances used up
                if(fchances==0){
                    //create the "fail" dialog
                    JDialog failDialog = new JDialog();
                    failDialog.setTitle("CHANCES USED UP!");failDialog.setLayout(null);
                    failDialog.setBounds(415,280,800,444);
                    JLabel fail = new JLabel(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\fail.jpg"));
                    JPanel back=new JPanel();back.add(fail);
                    back.setSize(800,444);back.setLocation(0,0);
                    Image im=(new ImageIcon(StartGame.path+"\\WordleGame\\src\\resources\\title.jpg")).getImage();
                    failDialog.setIconImage(im);//set icon
                    JTextArea fcorrectAnswer = new JTextArea();fcorrectAnswer.setLocation(570,100);
                    fcorrectAnswer.setSize(200,50);fcorrectAnswer.setFont(new Font("Comic Sans MS",Font.BOLD,30));
                    fcorrectAnswer.setOpaque(false);fcorrectAnswer.setEditable(false);fcorrectAnswer.setForeground(Color.ORANGE);
                    fcorrectAnswer.setText(fwordSelected);
                    failDialog.add(fcorrectAnswer);
                    failDialog.setResizable(false);
                    //create the "fail" dialog over
                    //display the correct answer
                    ftext.setText("");
                    for(int i =0;i<5;i++){
                        fcolors[i]=Color.white;
                    }
                    for(int i =0 ;i<6;i++){
                        colorset(i,fcolors,fbuttonArray);
                        charset(fbuttonArray,fwords);
                    }
                    //display the correct answer over
                    //reset game
                    charset(fbuttonArray,fwords);
                    failDialog.add(back);
                    failDialog.setVisible(true);
                    feliminatedArray.clear();
                    feliminatedLetters.setText("");
                    ftext.setText("");
                    fchances = 6;
                    fwordSelected=fwords.chooseWord();
                    //reset game over
                }
                //check over
            }
        }
    }

}