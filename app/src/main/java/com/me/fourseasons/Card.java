package com.me.fourseasons;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by sophielegras on 30/09/2016.
 */
public class Card implements Comparable, Serializable {






    private int value;
    private int suit;
    private Bitmap bitmap;

    public Card(int value, int suit, Bitmap bitmap) {
        this.value = value;
        this.suit = suit;
        this.bitmap = bitmap;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Card) {
            Card card = (Card) o;
            return value==(card.value) && suit==(card.suit);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        final int lessThan = -1;
        final int equal = 0;
        final int greaterThan = 1;

        if (o instanceof Card) {
            Card card = (Card) o;
            if (this.equals(card)) {
                return equal;
            }
            if (this.value < card.value) {
                return lessThan;
            }
            return greaterThan;
        }
        return equal;
    }





    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    public Bitmap getBitmap() {return bitmap; }
}
