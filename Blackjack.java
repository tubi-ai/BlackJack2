
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.BorderUIResource;


public class Blackjack extends JFrame implements ActionListener {
    JButton [] start;
    JButton [] top;
    JButton [] bottom;
    JLabel [] stats;
    JLabel [] playCards;
    JTextField [] display;
    BorderLayout layout;
    JPanel gameField;
    JPanel startOption;
    JPanel topDisplay;
    JPanel sideButton;
    JPanel bottomButton;
    BufferedImage[] pCards;
    BufferedImage[] dCards;
    String[] getCards;
    String[] pCardImg;
    String[] dCardImg;

    int[] dface;
    int[] dvalue;
    int[] dsuit;
    int[] pface;
    int[] pvalue;
    int[] psuit;

    double betTotal;
    double winLoss;
    double cash;
    int total;
    int dtotal;
    int stay;
    int initial;
    int dinitial;

    public static void main (String[] args){
        BlackJack Gui = new BlackJack();
        Gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public BlackJackGame () {
        initial = 0;
        dinitial = 5;
        cash = 50.00;
        winLoss = 0.00;
        total = 0;
        dtotal = 0;
        stay = 0;
        pCardImg = new String[6];
        dCardImg = new String[6];
        dface = new int[6];
        dvalue = new int[6];
        dface = new int[6];
        dsuit = new int[6];
        getCards = new String[6];
        pface = new int[6];
        pvalue = new int[6];
        psuit = new int[6];

        betTotal = 0;
        startOption = new JPanel;
        topDisplay = new JPanel;
        sideButton = new JPanel;
        bottomButton = new JPanel;
        gameField = new JPanel;

        startOption.setLayout(new GridLayout(1, 1));
        topDisplay.setLayout(new GridLayout(1, 1));
        sideButton.setLayout(new GridLayout(5, 1));
        bottomButton.setLayout(new GridLayout(1, 3));
        gameField.setLayout(new GridLayout(2, 5));

        playCards = new JLabel[10];
        playCards[0] = new JLabel("");
        playCards[1] = new JLabel("");
        playCards[2] = new JLabel("");
        playCards[3] = new JLabel("");
        playCards[4] = new JLabel("");
        playCards[5] = new JLabel("");
        playCards[6] = new JLabel("");
        playCards[7] = new JLabel("");
        playCards[8] = new JLabel("");
        playCards[9] = new JLabel("");

        start = new JButton[1];
        start[0] = new JButton("START");

        display = new JTextField[1];
        display[0] = new JTextField("WELCOME");
        display[0].setEditable(false);

        stats = new JLabel[5];
        stats[0] = new JLabel("Hand Total: 0", 10);
        stats[1] = new JLabel("Bet Total: $0.00", 10);
        stats[2] = new JLabel("Cards Left: 52", 10);
        stats[3] = new JLabel("Money Left: $" + cash, 10);
        stats[4] = new JLabel("Total W / L: $" + winLoss, 10);

        bottom = new JButton[3];
        bottom[0] = new JButton("Hit");
        bottom[1] = new JButton("Stay");
        bottom[2] = new JButton("Split");

        layout = new BorderLayout(5, 5);

        startOption.add(start[0]);
        topDisplay.add(display[0]);
        for (int i = 0; i < 5; i++)
            sideButton.add(stats[i]);
        for (int i = 0; i < 3; i++)
            bottomButton.add(bottom[i]);
        for (int i = 0; i < 10; i++)
            gameField.add(playCards[i]);

        add(gameField, BorderLayout.CENTER);
        add(startOption, BorderLayout.WEST);
        add(topDisplay, BorderLayout.NORTH);
        add(sideButton, BorderLayout.EAST);
        add(bottomButton, BorderLayout.SOUTH);

        start[0].addActionListener(this);

        for (int i = 0; i < 3; i++)
            bottom[i].addActionListener(this);

        setTitle("Black Jack");
        setSize(800, 500);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object[] check = {"Yes", "No"};
        Object[] bet = {"$5.00", "$10.00", "$15.00"};
        JButton c = (JButton) (e.getSource());
        if (c.getText().equals("Hit")){
            initial++;
            JOptionPane.showMessageDialog(this,
                    "Dealer delt you a card");
            dealCards(initial);
            stats[0].setText("Hand Total: " +total);
            if(total > 21){
                JOptionPane.showMessageDialog(this,
                        "You lost!");
                total = 0;
                dtotal = 0;
                stats[0].setText("Hand Total: "+total);
                betTotal = 0.00;
                stats[1].setText("Bet Total: $" + betTotal);
                clearHand();
                gotoContinue();
            }
        }
        if(c.getText().equals("Stay")){
            stay = 1;
            JOptionPane.showMessageDialog(this,
                    "You stay");
            if (stay == 1){
                dealerDraws();
                delay(1000);
            }
            if(dtotal < 17 && stay == 1){
                dealerDraws();
                delay(1000);

            }
        }
        if(c.getText().equals("Split")){
            int chk = JOptionPane.showOptionDialog(this,
                    "Are you sure?", "Double Check",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, check, check[1]);

        }
        if(c.getText().equals("START")){
            int b = JOptionPane.showOptionDialog(this,
                    "Bet minimum of higher", "Bet Please",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, bet, bet[1]);
            if(b == 0)
                betTotal += 5.00;
            if(b == 1)
                betTotal +=10.00;
            if(b == 2)
                betTotal +=15.00;
            stats[1].setText("Bet Total: $" + betTotal);
            cash-=betTotal;
            stats[3].setText("Money Left: $" + (cash));
            stay = -1;
            int i = 0;
            dealCards(i);
            System.out.println("TEST");
            stats[0].setText("Hand Total: " + total);
        }
    }
    public void dealCards(int j){
        String[] temp = new String[4];
        if (j==0){
            Shuffle myDealer = new Shuffle();
            myDealer.ShuffleCards();
            for (int i=0; i<4;i++){
                getCards[i] = myDealer.Dealer();
                temp[i] = getCards[i];
                System.out.println(getCards[i]);
            }
            printCards(temp, j);
        } if(j>=1){
            Shuffle myDealer = new Shuffle();
            myDealer.ShuffleCards();
            for (int i=0; i<2; i++){
                getCards[i] = myDealer.Dealer();
                temp[i] = getCards[i];
                System.out.println(getCards[i]);
            }
        } printCards(temp, j);
    }
    public void printCards (String[] temp, int j){
        if (j==0){
            String[] elements0 = temp[0].split(" ");
            String[] elements1 = temp[1].split(" ");
            String[] elements2 = temp[2].split(" ");
            String[] elements3 = temp[3].split(" ");

            pface[0] = Integer.parseInt(elements0[0]);
            pface[1] = Integer.parseInt(elements1[0]);
            psuit[0] = Integer.parseInt(elements0[1]);
            pface[1] = Integer.parseInt(elements1[1]);

            dface[0] = Integer.parseInt(elements2[0]);
            dface[1] = Integer.parseInt(elements3[0]);
            dsuit[0] = Integer.parseInt(elements2[1]);
            dsuit[1] = Integer.parseInt(elements3[1]);

            for (int i=0; i<2; i++){
                if(dface[i]>10)
                    dvalue[i] = 10;
                else
                    dvalue[i] = dface[i];
                if (pface[i] > 10)
                    pvalue[i] = 10;
                else
                    pvalue[i] = pface[i];
                if(pface[i] == 1 && total <= 11)
                    pvalue[i] += 10;
                if(pface[0] == 1 && pface[1] == 1)
                    total = 12;
                if(dface[i] == 1 && dtotal <= 11)
                    dvalue[i] += 10;
                if(dface[0] == 1 && dface[1] == 1)
                    dtotal = 12;
                System.out.println("Player: " + pface[i] + " "
                        + psuit[i] + " "+ pvalue[i]);
                total += pvalue[i];
                System.out.println(total);
                System.out.println("Dealer: " + dface[i] + " "
                        + dsuit[i] + " "+ dvalue[i]);
                dtotal += dvalue[i];
                System.out.println(dtotal);
                getPSuit();
                getDSuit();
            }
            if(total == 21 && dtotal < 21) {
                JOptionPane.showMessageDialog(this,
                        "Black Jack");
                cash += betTotal * 3;
                winLoss += betTotal * 3;
                stats[3].setText("Money Left: $"+(cash));
                stats[4].setText("Total W/L: $" + winLoss);
                total = 0;
                dtotal = 0;
                stats[0].setText("Hand Total: " +total);
                betTotal = 0;
                stats[1].setText("Bet Total: $"+ betTotal);
                clearHand();
                gotoContinue();
            }
        } if (j==1){
            String[] elements0 = temp[0].split(" ");
            pface[2] = Integer.parseInt(elements0[0]);
            psuit[2] = Integer.parseInt(elements0[1]);

            for (int i=2; i<3; i++){
                if (pface[i] > 10)
                    pvalue[i] = 10;
                else
                    pvalue[i] = pface[i];
                if (pface[i] == 1 && total <= 11)
                    pvalue[i] += 10;
                for (int k=0; k<6; k++){
                    if (pface[i] == 1 && total >21)
                        pvalue[k]-= 10;
                }
                System.out.println(pface[i] + " " + psuit[i] + " "
                        +pvalue[i]);
                total += pvalue[i];
                System.out.println(total);
            } getPSuit();
        }
        if(j == 2){
            String[] elements0 = temp[0].split(" ");
            pface[3] = Integer.parseInt(elements0[0]);
            psuit[3] = Integer.parseInt(elements0[1]);
            for (int i=3; i<4; i++){
                if (pface[i] > 10)
                    pvalue[i] = 10;
                else
                    pvalue[i] = pface[i];
                if (pface[i] == 1 && total <= 10)
                    pvalue[i] += 10;
                for (int k=0; k<6; k++){
                    if (pface[i] == 1 && total >21)
                        pvalue[k]-= 10;
                }
                System.out.println(pface[i] + " " + psuit[i] + " "
                        +pvalue[i]);
                total += pvalue[i];
                System.out.println(total);
            } getPSuit();
        }
        if(j == 3){
            String[] elements0 = temp[0].split(" ");
            pface[4] = Integer.parseInt(elements0[0]);
            psuit[4] = Integer.parseInt(elements0[1]);
            for (int i=4; i<5; i++){
                if (pface[i] > 10)
                    pvalue[i] = 10;
                else
                    pvalue[i] = pface[i];
                if (pface[i] == 1 && total <= 10)
                    pvalue[i] += 10;
                for (int k=0; k<6; k++){
                    if (pface[i] == 1 && total >21)
                        pvalue[k]-= 10;
                }
                System.out.println(pface[i] + " " + psuit[i] + " "
                        +pvalue[i]);
                total += pvalue[i];
                System.out.println(total);
            } getPSuit();
        } if(j == 4){
            String[] elements0 = temp[0].split(" ");
            pface[5] = Integer.parseInt(elements0[0]);
            psuit[5] = Integer.parseInt(elements0[1]);
            for (int i=5; i<6; i++){
                if (pface[i] > 10)
                    pvalue[i] = 10;
                else
                    pvalue[i] = pface[i];
                if (pface[i] == 1 && total <= 10)
                    pvalue[i] += 10;
                for (int k=0; k<6; k++){
                    if (pface[i] == 1 && total >21)
                        pvalue[k]-= 10;
                }
                System.out.println(pface[i] + " " + psuit[i] + " "
                        +pvalue[i]);
                total += pvalue[i];
                System.out.println(total);
            } getPSuit();
        } if(j == 5){
            String[] elements0 = temp[0].split(" ");
            pface[2] = Integer.parseInt(elements0[0]);
            psuit[2] = Integer.parseInt(elements0[1]);
            for (int i=2; i<3; i++){
                if (dface[i] > 10)
                    dvalue[i] = 10;
                else
                    dvalue[i] = dface[i];
                if (dface[i] == 1 && dtotal <= 10)
                    dvalue[i] += 10;
                for (int k=0; k<6; k++){
                    if (dface[i] == 1 && dtotal >21)
                        dvalue[k]-= 10;
                }
                System.out.println("Dealer: " + dface[i] + " " +
                        dsuit[i] + " " +dvalue[i]);
                dtotal += dvalue[i];
                System.out.println(dtotal);
            } getDSuit();
        }
        if(j == 6){
            String[] elements3 = temp[0].split(" ");
            pface[3] = Integer.parseInt(elements3[0]);
            psuit[3] = Integer.parseInt(elements3[1]);
            for (int i=3; i<4; i++){
                if (dface[i] > 10)
                    dvalue[i] = 10;
                else
                    dvalue[i] = dface[i];
                if (dface[i] == 1 && dtotal <= 10)
                    dvalue[i] += 10;
                for (int k=0; k<6; k++){
                    if (dface[i] == 1 && dtotal >21)
                        dvalue[k]-= 10;
                }
                System.out.println("Dealer: " + dface[i] + " " +
                        dsuit[i] + " " +dvalue[i]);
                dtotal += dvalue[i];
                System.out.println(dtotal);
            } getDSuit();
        } if(j == 7){
            String[] elements0 = temp[0].split(" ");
            pface[4] = Integer.parseInt(elements0[0]);
            psuit[4] = Integer.parseInt(elements0[1]);
            for (int i=4; i<5; i++){
                if (dface[i] > 10)
                    dvalue[i] = 10;
                else
                    dvalue[i] = dface[i];
                if (dface[i] == 1 && dtotal <= 10)
                    dvalue[i] += 10;
                for (int k=0; k<6; k++){
                    if (dface[i] == 1 && dtotal >21)
                        dvalue[k]-= 10;
                }
                System.out.println("Dealer: " + dface[i] + " " +
                        dsuit[i] + " " +dvalue[i]);
                dtotal += dvalue[i];
                System.out.println(dtotal);
            } getDSuit();
        } if(j == 8){
            String[] elements0 = temp[0].split(" ");
            pface[5] = Integer.parseInt(elements0[0]);
            psuit[5] = Integer.parseInt(elements0[1]);
            for (int i=5; i<6; i++){
                if (dface[i] > 10)
                    dvalue[i] = 10;
                else
                    dvalue[i] = dface[i];
                if (dface[i] == 1 && dtotal <= 10)
                    dvalue[i] += 10;
                for (int k=0; k<6; k++){
                    if (dface[i] == 1 && dtotal >21)
                        dvalue[k]-= 10;
                }
                System.out.println("Dealer: " + dface[i] + " " +
                        dsuit[i] + " " +dvalue[i]);
                dtotal += dvalue[i];
                System.out.println(dtotal);
            } getDSuit();
        }
    }
    public void dealerDraws(){
        if(dtotal < 17 && stay ==1){
            dealCards(dinitial);
            dinitial++;
        }
        if(dtotal >= 17 && dtotal <= 21){
            delay(1000);
            JOptionPane.showMessageDialog(this,
                    "Dealer stays");
            checkHandWin();
        }
        if (dtotal > 21){
            JOptionPane.showMessageDialog(this,
                    "Dealer lost");
            checkHandWin();
        }
    }
    public void checkHandWin(){
        if (total > dtotal || dtotal > 21){
            JOptionPane.showMessageDialog(this,"You Won");

        }
    } public void getPSuit(){
        String[] suit = new String[5];
        for (int i=0; i<5; i++){
            if(psuit[i] == 1)
                suit[i] = "d";
            if(psuit[i] == 1)
                suit[i] = "c";
            if(psuit[i] == 1)
                suit[i] = "h";
            if(psuit[i] == 1)
                suit[i] = "s";
            pCardImg[i] = suit[i] + pface + ".jpg";
        } playerHand();
    }
    public void getDSuit(){
        String[] suit = new String[5];
        for (int i=0; i<5; i++){
            if(dsuit[i] == 1)
                suit[i] = "d";
            if(dsuit[i] == 1)
                suit[i] = "c";
            if(dsuit[i] == 1)
                suit[i] = "h";
            if(dsuit[i] == 1)
                suit[i] = "s";
            dCardImg[i] = suit[i] + pface + ".jpg";
        } playerHand();
    }
    public void playerHand(){
        pCards = new BufferedImage[5];
        for (int i=0; i<5; i++){
            try{
                pCards[i] = ImageIO.read(new File(pCardImg[i]));
                playCards[i] = new JLabel(new ImageIcon(pCards[i]));
                add(playCards[i]);
                repaint();
            } catch (IOException e){

            }
        }
    }
    public void dealerHand(){
        dCards = new BufferedImage[5];
        for (int i=0; i<5; i++){
            try{
                dCards[i] = ImageIO.read(new File(dCardImg[i]));
                playCards[i] = new JLabel(new ImageIcon(dCards[i]));
                add(playCards[i]);
                repaint();
            } catch (IOException e){

            }
        }
    }
    public void delay(long time){
        try{
            Thread.sleep(time);
        } catch(Exception e){

        }
    }
    public void paint(Graphics g){
        super.paint(g);
        if(pCards[0] != null)
            g.drawImage(pCards[0], 100, 300, null);
        if(pCards[0] != null)
            g.drawImage(pCards[1], 150, 325, null);
        if(pCards[0] != null)
            g.drawImage(pCards[2], 200, 300, null);
        if(pCards[0] != null)
            g.drawImage(pCards[3], 250, 325, null);
        if(pCards[0] != null)
            g.drawImage(pCards[4], 300, 300, null);

        if(dCards[0] != null)
            g.drawImage(dCards[0], 500, 55, null);
        if(dCards[0] != null)
            g.drawImage(dCards[1], 450, 80, null);
        if(dCards[0] != null)
            g.drawImage(dCards[2], 400, 55, null);
        if(dCards[0] != null)
            g.drawImage(dCards[3], 350, 80, null);
        if(dCards[0] != null)
            g.drawImage(dCards[4], 300, 55, null);
        if(dCards[0] != null)
            g.drawImage(dCards[5], 250, 80, null);
    }

}
