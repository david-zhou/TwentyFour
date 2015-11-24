package com.dzt.twentyfour.Class;

import com.dzt.twentyfour.R;

public class Card {
    int number;
    String suit;
    int id, imageId;

    public Card(int number, String suit, int id) {
        this.number = number;
        this.suit = suit;
        this.id = id;
        calculateImageId();
    }

    private void calculateImageId() {
        switch(id){
            case 0:
                imageId = R.drawable.ace_of_diamonds;
                break;
            case 1:
                imageId = R.drawable._2_of_diamonds;
                break;
            case 2:
                imageId = R.drawable._3_of_diamonds;
                break;
            case 3:
                imageId = R.drawable._4_of_diamonds;
                break;
            case 4:
                imageId = R.drawable._5_of_diamonds;
                break;
            case 5:
                imageId = R.drawable._6_of_diamonds;
                break;
            case 6:
                imageId = R.drawable._7_of_diamonds;
                break;
            case 7:
                imageId = R.drawable._8_of_diamonds;
                break;
            case 8:
                imageId = R.drawable._9_of_diamonds;
                break;
            case 9:
                imageId = R.drawable._10_of_diamonds;
                break;
            case 10:
                imageId = R.drawable.jack_of_diamonds;
                break;
            case 11:
                imageId = R.drawable.queen_of_diamonds;
                break;
            case 12:
                imageId = R.drawable.king_of_diamonds;
                break;
            case 13:
                imageId = R.drawable.ace_of_clubs;
                break;
            case 14:
                imageId = R.drawable._2_of_clubs;
                break;
            case 15:
                imageId = R.drawable._3_of_clubs;
                break;
            case 16:
                imageId = R.drawable._4_of_clubs;
                break;
            case 17:
                imageId = R.drawable._5_of_clubs;
                break;
            case 18:
                imageId = R.drawable._6_of_clubs;
                break;
            case 19:
                imageId = R.drawable._7_of_clubs;
                break;
            case 20:
                imageId = R.drawable._8_of_clubs;
                break;
            case 21:
                imageId = R.drawable._9_of_clubs;
                break;
            case 22:
                imageId = R.drawable._10_of_clubs;
                break;
            case 23:
                imageId = R.drawable.jack_of_clubs;
                break;
            case 24:
                imageId = R.drawable.queen_of_clubs;
                break;
            case 25:
                imageId = R.drawable.king_of_clubs;
                break;
            case 26:
                imageId = R.drawable.ace_of_hearts;
                break;
            case 27:
                imageId = R.drawable._2_of_hearts;
                break;
            case 28:
                imageId = R.drawable._3_of_hearts;
                break;
            case 29:
                imageId = R.drawable._4_of_hearts;
                break;
            case 30:
                imageId = R.drawable._5_of_hearts;
                break;
            case 31:
                imageId = R.drawable._6_of_hearts;
                break;
            case 32:
                imageId = R.drawable._7_of_hearts;
                break;
            case 33:
                imageId = R.drawable._8_of_hearts;
                break;
            case 34:
                imageId = R.drawable._9_of_hearts;
                break;
            case 35:
                imageId = R.drawable._10_of_hearts;
                break;
            case 36:
                imageId = R.drawable.jack_of_hearts;
                break;
            case 37:
                imageId = R.drawable.queen_of_hearts;
                break;
            case 38:
                imageId = R.drawable.king_of_hearts;
                break;
            case 39:
                imageId = R.drawable.ace_of_spades;
                break;
            case 40:
                imageId = R.drawable._2_of_spades;
                break;
            case 41:
                imageId = R.drawable._3_of_spades;
                break;
            case 42:
                imageId = R.drawable._4_of_spades;
                break;
            case 43:
                imageId = R.drawable._5_of_spades;
                break;
            case 44:
                imageId = R.drawable._6_of_spades;
                break;
            case 45:
                imageId = R.drawable._7_of_spades;
                break;
            case 46:
                imageId = R.drawable._8_of_spades;
                break;
            case 47:
                imageId = R.drawable._9_of_spades;
                break;
            case 48:
                imageId = R.drawable._10_of_spades;
                break;
            case 49:
                imageId = R.drawable.jack_of_spades;
                break;
            case 50:
                imageId = R.drawable.queen_of_spades;
                break;
            case 51:
                imageId = R.drawable.king_of_spades;
                break;
            default:
                imageId = R.drawable._10_of_clubs;
                break;
        }
    }

    public String getCardName()  {
        String name;
        switch(number) {
            case 1:
                name = "A";
                break;
            case 11:
                name = "J";
                break;
            case 12:
                name = "Q";
                break;
            case 13:
                name = "K";
                break;
            default:
                name = number + "";
                break;
        }
        name += " of " + suit;
        return name;
    }

    public int getNumber(){
        return number;
    }

    public int getId() { return id; }

    public int getImageId() { return imageId; }
}
