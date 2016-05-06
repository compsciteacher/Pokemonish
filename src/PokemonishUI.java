
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/*
 * The MIT License
 *
 * Copyright 2016 hdavis.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 *
 * @author hdavis
 */

public class PokemonishUI extends javax.swing.JFrame {
        Deck deck1=new Deck();
        Deck deck2=new Deck();
        Pokemon p1card;
        Pokemon p2card;
        Scanner in=new Scanner(System.in);
    /**
     * Creates new form PokemonishUI
     */

    public PokemonishUI() {
        initComponents();
        
    }
    public void askPlayer(){
        PlayerOneChoose.setVisible(true);
        
    }
//    public void play(Deck deck1, Deck deck2)throws Exception{
//        
//        
//
//        System.out.println("Player 1: Which card do you want to play? (Number)");
//        int x=in.nextInt()-1;//changes entry -1 to call by array number
//        Pokemon p1card=deck1.playCard(x); //calls playcard from Deck class, sends index value to get card
//        
//        System.out.println("Player 2: Which card do you want to play? (Number)");
//        Deck.printNames(deck2);
//        int x2=in.nextInt()-1;
//        Pokemon p2card=deck2.playCard(x2);
//        
//        int start=speedCheck(p1card,p2card);//calls speedcheck sending the two speeds
//        
//        if (start==0){//if speed of card 1 is higher than card 2 player 1 goes first
//            start(p1card,p2card,start);
//        }
//        else if (start==1){
//            start(p2card,p1card,start);
//        }
//
//    }
    public static Integer speedCheck(Pokemon x, Pokemon y){//checks the speeds and returns 1 if player 1 is higher
        if (x.speed>=y.speed){
            return 0;
        }
        return 1;
        
    }
//    this is the actual playing of the hands;
//    x and y are determined on speed first, and then just flip after that (you can see that at end of method)
//    i is to determine which play it is (starts at 0,1 based on speedcheck)
//    then gives prompts for which attack to use
    
    public void start(Pokemon x,Pokemon y,int i){
        Notice.toFront();
        NoticeLabel.setText(String.valueOf(i));
        
        if (x.health<=0){
            try {
                Win();
                Thread.sleep(10000);
            } catch (Exception ex) {
                Logger.getLogger(Pokemonish.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.exit(0);
        }
        int player=0;
        int player2=1;//for naming
        if(i%2==0){
            player=1;
        }
        else{
            player=2;
            player2=1;
        }
   
        BattleOne.setText(String.format("Player %d's %s Health: %d \nType 2 attacks: %d\n",player,x.name,x.health,x.numa2));
        BattleTwo.setText(String.format("Player %d's %s Health: %d \n",player2,y.name,y.health));
        
        Object[] options = {x.attack1,
                    x.attack2};
int n = OptionPane.showOptionDialog(OptionPane,
    "Attack Type One or Two?",
    String.format("Player %d, which attack to use (1 or 2)? ",player),
    OptionPane.YES_NO_CANCEL_OPTION,
    OptionPane.QUESTION_MESSAGE,
    null,
    options,
    options[1]);
        int choice=n+1;
        if (choice==1){//if choice of attack 1
            BattleThree.setText(String.format("%s %ss %s for %d damage\n",x.name,x.attack1,y.name,x.a1str));
            Random ran = new Random();
            int d=ran.nextInt(y.defense);//rolls defense based on other players card
            if (d<x.a1str){//if defense is below attack strength, take attack strength from other players pokemon
                y.health-=x.a1str;
            }
            else{
                BattleThree.setText(String.format("%s stopped your attack!\n",y.name));//if defense is higher than attack strength attack is blocked
            }
        }
        if (choice==2){//attack 2 chosen
            if (x.numa2<1){//checks if they have any type 2 attacks left
                BattleThree.setText(String.format("You have no more %s attacks\n",x.attack2));
                start(x,y,i);
            }
            BattleThree.setText(String.format("%s %s %s\n for %d damage",x.name,x.attack2,y.name,x.a2str));
            Random ran = new Random();
            int d=ran.nextInt(y.defense);
            if (d<x.a2str){
                y.health-=x.a2str;
                x.numa2--;//takes one of the type 2 attacks away
            }
            else{
                BattleThree.setText(String.format("%s stopped your attack!\n",y.name));
                x.numa2--;
            }
        }
        i++;//next play
        start(y,x,i);//switch players
    }
  
    
    
    public static void Win() throws Exception
  {
    // open the sound file as a Java input stream
    String gongFile = "chokeslam.au";
    InputStream in = new FileInputStream(gongFile);
 
    // create an audiostream from the inputstream
    AudioStream audioStream = new AudioStream(in);
 
    // play the audio clip with the audioplayer class
    AudioPlayer.player.start(audioStream);
  }
    public final static void clearConsole()
{
    try
    {
        final String os = System.getProperty("os.name");

        if (os.contains("Windows"))
        {
            Runtime.getRuntime().exec("cls");
        }
        else
        {
            Runtime.getRuntime().exec("clear");
        }
    }
    catch (final Exception e)
    {
        //  Handle any exceptions.
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Dialogue = new javax.swing.JDialog();
        OptionPane = new javax.swing.JOptionPane();
        Notice = new javax.swing.JDialog();
        NoticeLabel = new javax.swing.JLabel();
        DEAL = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        DeckOne = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        DeckTwo = new javax.swing.JTextArea();
        PlayerOneChoose = new javax.swing.JLabel();
        PlayerTwoChoose = new javax.swing.JLabel();
        CardTwo = new javax.swing.JButton();
        CardOne = new javax.swing.JButton();
        CardThree = new javax.swing.JButton();
        CardFour = new javax.swing.JButton();
        CardFive = new javax.swing.JButton();
        CardSix = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        BattleOne = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        BattleThree = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        BattleTwo = new javax.swing.JTextPane();

        javax.swing.GroupLayout DialogueLayout = new javax.swing.GroupLayout(Dialogue.getContentPane());
        Dialogue.getContentPane().setLayout(DialogueLayout);
        DialogueLayout.setHorizontalGroup(
            DialogueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogueLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(OptionPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        DialogueLayout.setVerticalGroup(
            DialogueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogueLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(OptionPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );

        NoticeLabel.setText("Huh?");

        javax.swing.GroupLayout NoticeLayout = new javax.swing.GroupLayout(Notice.getContentPane());
        Notice.getContentPane().setLayout(NoticeLayout);
        NoticeLayout.setHorizontalGroup(
            NoticeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NoticeLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(NoticeLabel)
                .addContainerGap(226, Short.MAX_VALUE))
        );
        NoticeLayout.setVerticalGroup(
            NoticeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NoticeLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(NoticeLabel)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        DEAL.setText("DEAL");
        DEAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEALActionPerformed(evt);
            }
        });

        DeckOne.setColumns(20);
        DeckOne.setRows(5);
        jScrollPane1.setViewportView(DeckOne);

        DeckTwo.setColumns(20);
        DeckTwo.setRows(5);
        jScrollPane2.setViewportView(DeckTwo);

        PlayerOneChoose.setText("PLAYER ONE");

        PlayerTwoChoose.setText("PLAYER TWO");

        CardTwo.setText("Card Two");
        CardTwo.setEnabled(false);
        CardTwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CardTwoActionPerformed(evt);
            }
        });

        CardOne.setText("Card One");
        CardOne.setEnabled(false);
        CardOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CardOneActionPerformed(evt);
            }
        });

        CardThree.setText("Card Three");
        CardThree.setEnabled(false);
        CardThree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CardThreeActionPerformed(evt);
            }
        });

        CardFour.setText("Card One");
        CardFour.setEnabled(false);
        CardFour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CardFourActionPerformed(evt);
            }
        });

        CardFive.setText("Card Two");
        CardFive.setEnabled(false);
        CardFive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CardFiveActionPerformed(evt);
            }
        });

        CardSix.setText("Card Three");
        CardSix.setEnabled(false);
        CardSix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CardSixActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(BattleOne);

        jScrollPane4.setViewportView(BattleThree);

        jScrollPane5.setViewportView(BattleTwo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(PlayerOneChoose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CardTwo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CardOne, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CardThree, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CardFive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CardFour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CardSix, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane4)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane5))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(DEAL, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PlayerTwoChoose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PlayerOneChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PlayerTwoChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DEAL, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CardFour, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CardFive, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CardSix, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CardOne, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CardTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CardThree, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DEALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEALActionPerformed
        // TODO add your handling code here:
                Random ran=new Random();

        for(int i=0;i<3;i++){
            int x=ran.nextInt(49)+1;
            Pokemon c=new Pokemon(x);
            deck1.addCard(c);
        }
        for(int i=0;i<3;i++){
            int x=ran.nextInt(49)+1;
            Pokemon c=new Pokemon(x);
            deck2.addCard(c);}
        DeckOne.setText((deck1.printDeck()));
        DeckTwo.setText((deck2.printDeck()));
        CardOne.setEnabled(true);
        CardTwo.setEnabled(true);
        CardThree.setEnabled(true);

        askPlayer();

    }//GEN-LAST:event_DEALActionPerformed

    private void CardOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CardOneActionPerformed
        // TODO add your handling code here:
        p1card=deck1.playCard(0);
        BattleOne.setText(p1card.toString());
        CardOne.setEnabled(false);
        CardTwo.setEnabled(false);
        CardThree.setEnabled(false);
        CardFour.setEnabled(true);
        CardFive.setEnabled(true);
        CardSix.setEnabled(true);
    }//GEN-LAST:event_CardOneActionPerformed

    private void CardTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CardTwoActionPerformed
        p1card=deck1.playCard(1);
        BattleOne.setText(p1card.toString());
        CardOne.setEnabled(false);
        CardTwo.setEnabled(false);
        CardThree.setEnabled(false);
        CardFour.setEnabled(true);
        CardFive.setEnabled(true);
        CardSix.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_CardTwoActionPerformed

    private void CardThreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CardThreeActionPerformed
        p1card=deck1.playCard(2);
        BattleOne.setText(p1card.toString());
        CardOne.setEnabled(false);
        CardTwo.setEnabled(false);
        CardThree.setEnabled(false);
        CardFour.setEnabled(true);
        CardFive.setEnabled(true);
        CardSix.setEnabled(true);             // TODO add your handling code here:
    }//GEN-LAST:event_CardThreeActionPerformed

    private void CardFourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CardFourActionPerformed
        // TODO add your handling code here:
        p2card=deck2.playCard(0);
        BattleTwo.setText(p2card.toString());
        CardOne.setEnabled(false);
        CardTwo.setEnabled(false);
        CardThree.setEnabled(false);
        CardFour.setEnabled(false);
        CardFive.setEnabled(false);
        CardSix.setEnabled(false); 
        int start=speedCheck(p1card,p2card);//calls speedcheck sending the two speeds
        
        if (start==0){//if speed of card 1 is higher than card 2 player 1 goes first
            start(p1card,p2card,start);
        }
        else if (start==1){
            start(p2card,p1card,start);
        }
    }//GEN-LAST:event_CardFourActionPerformed

    private void CardFiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CardFiveActionPerformed
        p2card=deck2.playCard(1);
        BattleTwo.setText(p2card.toString());
        CardOne.setEnabled(false);
        CardTwo.setEnabled(false);
        CardThree.setEnabled(false);
        CardFour.setEnabled(false);
        CardFive.setEnabled(false);
        CardSix.setEnabled(false);         // TODO add your handling code here:
        int start=speedCheck(p1card,p2card);//calls speedcheck sending the two speeds
        
        if (start==0){//if speed of card 1 is higher than card 2 player 1 goes first
            start(p1card,p2card,start);
        }
        else if (start==1){
            start(p2card,p1card,start);
        }
    }//GEN-LAST:event_CardFiveActionPerformed

    private void CardSixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CardSixActionPerformed
        // TODO add your handling code here:
        p2card=deck2.playCard(2);
        BattleTwo.setText(p2card.toString());
        CardOne.setEnabled(false);
        CardTwo.setEnabled(false);
        CardThree.setEnabled(false);
        CardFour.setEnabled(false);
        CardFive.setEnabled(false);
        CardSix.setEnabled(false); 
        int start=speedCheck(p1card,p2card);//calls speedcheck sending the two speeds
        
        if (start==0){//if speed of card 1 is higher than card 2 player 1 goes first
            start(p1card,p2card,start);
        }
        else if (start==1){
            start(p2card,p1card,start);
        }
    }//GEN-LAST:event_CardSixActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PokemonishUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PokemonishUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PokemonishUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PokemonishUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PokemonishUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextPane BattleOne;
    public javax.swing.JTextPane BattleThree;
    public javax.swing.JTextPane BattleTwo;
    public javax.swing.JButton CardFive;
    public javax.swing.JButton CardFour;
    public javax.swing.JButton CardOne;
    public javax.swing.JButton CardSix;
    public javax.swing.JButton CardThree;
    public javax.swing.JButton CardTwo;
    public javax.swing.JButton DEAL;
    private javax.swing.JTextArea DeckOne;
    private javax.swing.JTextArea DeckTwo;
    private javax.swing.JDialog Dialogue;
    private javax.swing.JDialog Notice;
    private javax.swing.JLabel NoticeLabel;
    private javax.swing.JOptionPane OptionPane;
    private javax.swing.JLabel PlayerOneChoose;
    private javax.swing.JLabel PlayerTwoChoose;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
