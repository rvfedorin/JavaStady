package quiz_card;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class QuizCardPlayer {
    private JTextArea display;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private QuizCard currentCard;
    private JFrame frame;
    private JButton nextButton;
    private int currentCardIndex;
    private boolean isShowAnswer;

    public static void main(String[] args) {
        QuizCardPlayer player = new QuizCardPlayer();
        player.go();
    }

    private void go() {
        frame = new JFrame("Quiz Card Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        display = new JTextArea(6, 20);
        display.setFont(bigFont);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);

        JScrollPane scroller = new JScrollPane(display);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        nextButton = new JButton("Show Question");
        nextButton.addActionListener(new NextCartListener());

        mainPanel.add(scroller);
        mainPanel.add(nextButton);

        JMenuBar mBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load card set");
        loadMenuItem.addActionListener(new LoadMenuListener());
        menu.add(loadMenuItem);
        mBar.add(menu);

        frame.setJMenuBar(mBar);
        frame.add(mainPanel);
        frame.setSize(640, 500);
        frame.setVisible(true);
    }

    private class NextCartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (isShowAnswer) {
                display.setText(currentCard.getAnswer());
                nextButton.setText("Next Card");
                isShowAnswer = false;
            } else {
                if (currentCardIndex < cardList.size()) {
                    showNextCard();
                } else {
                    display.setText("Это была последняя карточка.");
                    nextButton.setEnabled(false);
                }
            }
        }
    }  // close class NextCartListener

    private class LoadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            JFileChooser openFile = new JFileChooser();
            openFile.showOpenDialog(frame);
            loadFile(openFile.getSelectedFile());
        }
    }  // close class LoadMenuLister

    private void loadFile(File file) {
        cardList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                makeCard(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        showNextCard();  // показываем первую карточку, после загрузки
    }  // close void loadFile

    private void makeCard(String lineToParse) {
        String[] result = lineToParse.split("/");
        if (result.length == 2) {
            QuizCard card = new QuizCard(result[0], result[1]);
            cardList.add(card);
//            System.out.println("Made a card");
        }
    }

    private void showNextCard() {
        currentCard = cardList.get(currentCardIndex);
        currentCardIndex++;
        display.setText(currentCard.getQuestion());
        nextButton.setText("Show Answer");
        isShowAnswer = true;
    }
}
