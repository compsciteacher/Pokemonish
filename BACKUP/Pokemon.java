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
import java.io.*;
import java.util.*;

public class Pokemon {
    String name;
    String type;
    int health;
    int speed;
    String attack1;
    String attack2;
    int defense;
    int numa2;
    int a1str;
    int a2str;
    static Scanner in=new Scanner(System.in);
    public Pokemon (int choice){
        if (choice<=1){
            this.name="Pikachu";
            this.type="AWESOME!";
            this.health=100;
            this.speed=20;
            this.attack1="Punch";
            this.attack2="Lightning";
            this.defense=10;
            this.numa2=10;
            this.a1str=10;
            this.a2str=25;
                       
        }
        else if (choice<=10){
            this.name="Ekans";
            this.type="Land";
            this.health=75;
            this.speed=5;
            this.attack1="Punch";
            this.attack2="Fire breath";
            this.defense=5;
            this.numa2=3;
            this.a1str=4;
            this.a2str=10;
                       
        }
        else if(choice<=20){
            this.name="Aron";
            this.type="Mountain";
            this.health=90;
            this.speed=4;
            this.attack1="Punch";
            this.attack2="Slam";
            this.defense=7;
            this.numa2=5;
            this.a1str=5;
            this.a2str=7;
        }
        else if (choice<=30){
            this.name="Krabby";
            this.type="Water";
            this.health=60;
            this.speed=8;
            this.attack1="Punch";
            this.attack2="Tail smack";
            this.defense=7;
            this.numa2=5;
            this.a1str=4;
            this.a2str=7;
        }
        else if(choice<=40){
            this.name="Hoppip";
            this.type="Sky";
            this.health=60;
            this.speed=9;
            this.attack1="Punch";
            this.attack2="Dive bomb";
            this.defense=6;
            this.numa2=3;
            this.a1str=4;
            this.a2str=10;
        }
        else{
            this.name="Grimer";
            this.type="Urban";
            this.health=65;
            this.speed=6;
            this.attack1="Punch";
            this.attack2="Roundhouse Kick";
            this.defense=6;
            this.numa2=4;
            this.a1str=6;
            this.a2str=8;
        }
        
    }
    public String toString(){
        return String.format("Pokemon: %s\nType: %s\nHealth: %d\nSpeed: %d\nAttack 1: %s\nAttack 2: %s\n"
                + "Defense: %d\nNumber of secondary attacks: %d\nAttack 1 Strength: %d\nAttack 2 Strength: %d", this.name, this.type, this.health,this.speed,this.attack1,this.attack2,this.defense,this.numa2,this.a1str,this.a2str);
    }
    
}
