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
import java.util.*;

public class Deck {
    String deck="";
    private ArrayList<Pokemon> cards;
    int addressNo;
    public Deck(){
    this.cards=new ArrayList<Pokemon>();

    
}

    public void addCard(Pokemon c){
        this.cards.add(c);
    } 
    public String printDeck(){
        for(Pokemon r : cards){
            
            deck+=r+"\n";
            deck+=("--------------------"+"\n");
        }
    return deck;
        
    }
    public static void printNames(Deck x){
        int i=1;
            for(Pokemon r : x.cards){
                System.out.println("Card "+i);
            System.out.println(r.name);
            System.out.println("--------------------");
            i++;
        }

    }
    public Pokemon playCard(int num){
        
        Pokemon x= cards.get(num);
        return x;
    }
}
