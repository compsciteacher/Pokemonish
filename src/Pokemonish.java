
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author hdavis
 */
class Pokemonish {
static Scanner in=new Scanner(System.in);//simple scanner used throughout
Integer lives1=3;//player lives
Integer lives2=3;
PokemonishUI gui=new PokemonishUI();
    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws IOException, Exception{
//        // TODO code application logic here
//        
//        Random ran=new Random();
//        Deck deck1=new Deck();
//        Deck deck2=new Deck();
//        for(int i=0;i<3;i++){
//            int x=ran.nextInt(49)+1;
//            Pokemon c=new Pokemon(x);
//            deck1.addCard(c);
//        }
//        for(int i=0;i<3;i++){
//            int x=ran.nextInt(49)+1;
//            Pokemon c=new Pokemon(x);
//            deck2.addCard(c);
//        
//        }
//        
//
//        
//     
////        Thread.sleep(5000);
//        play(deck1,deck2);
//    
//    }

    
    public void play(Deck deck1, Deck deck2)throws Exception{
        
        

        System.out.println("Player 1: Which card do you want to play? (Number)");
        int x=in.nextInt()-1;//changes entry -1 to call by array number
        Pokemon p1card=deck1.playCard(x); //calls playcard from Deck class, sends index value to get card
        
        System.out.println("Player 2: Which card do you want to play? (Number)");
        Deck.printNames(deck2);
        int x2=in.nextInt()-1;
        Pokemon p2card=deck2.playCard(x2);
        
        int start=speedCheck(p1card,p2card);//calls speedcheck sending the two speeds
        
        if (start==0){//if speed of card 1 is higher than card 2 player 1 goes first
            start(p1card,p2card,start);
        }
        else if (start==1){
            start(p2card,p1card,start);
        }

    }
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
        
        if (x.health<=0){
            try {
                Win();
            } catch (Exception ex) {
                Logger.getLogger(Pokemonish.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int player=0;
        int player2=1;//for naming
        if(i%2==0){
            player=2;
        }
        else{
            player=1;
            player2=2;
        }
   
        gui.BattleOne.setText(String.format("Player %d's %s Health: %d Type 2 attacks: %d\n",player,x.name,x.health,x.numa2));
        gui.BattleThree.setText(String.format("Player %d's %s Health: %d \n",player2,y.name,y.health));
        System.out.printf("Player %d, which attack to use (1 or 2)? ",player);
        int choice=in.nextInt();
        if (choice==1){//if choice of attack 1
            System.out.printf("%s %s %s\n",x.name,x.attack1,y.name);
            Random ran = new Random();
            int d=ran.nextInt(y.defense);//rolls defense based on other players card
            if (d<x.a1str){//if defense is below attack strength, take attack strength from other players pokemon
                y.health-=x.a1str;
            }
            else{
                System.out.printf("%s stopped your attack!\n",y.name);//if defense is higher than attack strength attack is blocked
            }
        }
        if (choice==2){//attack 2 chosen
            if (x.numa2<1){//checks if they have any type 2 attacks left
                System.out.printf("You have no more %s attacks\n",y.attack2);
                start(x,y,i);
            }
            System.out.printf("%s %s %s\n",x.name,x.attack2,y.name);
            Random ran = new Random();
            int d=ran.nextInt(y.defense);
            if (d<x.a2str){
                y.health-=x.a2str;
                x.numa2--;//takes one of the type 2 attacks away
            }
            else{
                System.out.printf("%s stopped your attack!\n",y.name);
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
    
    
}
