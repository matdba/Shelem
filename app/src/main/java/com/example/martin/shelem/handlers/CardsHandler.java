package com.example.martin.shelem.handlers;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CardsHandler {

    private String[] cards;
    private int[] cardsArrange;


    public CardsHandler() {
        setCards();
    }



    @SuppressLint("DefaultLocale")
    private void setCards() {

        cards = new String[54];
        cardsArrange = new int[54];
        String preName = "card_";

        for (int i = 0; i < cards.length; i++)
            if (i < 13)
                cards[i] = preName + "c" + String.format("%02d", i + 2);
            else if (i < 26)
                cards[i] = preName + "d" + String.format("%02d", i - 11);
            else if (i < 39)
                cards[i] = preName + "h" + String.format("%02d", i - 24);
            else if (i < 52)
                cards[i] = preName + "s" + String.format("%02d", i - 37);
            else
                cards[i] = preName + "j" + String.format("%02d", i - 51);


        for (int i = 0; i < cardsArrange.length; i++) {
            cardsArrange[i] = i;
        }

        shuffleArray(cardsArrange);

    }


    private static void shuffleArray(int[] array) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }






    public String[] getPlayersCard(int playerNumber) {
        String[] playerCards = new String[12];
        int startingCardPosition = 12 * playerNumber - 12;

        for (int i = 0; i < playerCards.length; i++) {
            playerCards[i] = cards[cardsArrange[startingCardPosition + i]];
        }

        return playerCards;
    }



    public String[] getLeftOvercards() {
        String[] playerCards = new String[6];

        for (int i = 0; i < playerCards.length; i++) {
            playerCards[i] = cards[cardsArrange[i + 48]];
        }

        return playerCards;
    }







    public String[] sortCards(String[] cards){

        String[] sortedCards = new String[cards.length];

        List<String> clubs = new ArrayList<>();
        List<String> diamonds = new ArrayList<>();
        List<String> hearts = new ArrayList<>();
        List<String> spades = new ArrayList<>();
        List<String> jokers = new ArrayList<>();


        for (String card : cards)
            if (card.split("_")[1].charAt(0) == 'c') clubs.add(card);
            else if (card.split("_")[1].charAt(0) == 'd') diamonds.add(card);
            else if (card.split("_")[1].charAt(0) == 'h') hearts.add(card);
            else if (card.split("_")[1].charAt(0) == 's') spades.add(card);
            else jokers.add(card);


        Collections.sort(clubs);
        Collections.sort(diamonds);
        Collections.sort(hearts);
        Collections.sort(spades);
        Collections.sort(jokers);


        for (int i = 0; i < diamonds.size(); i++) sortedCards[i] = diamonds.get(i);
        for (int i = 0; i < clubs.size(); i++) sortedCards[i + diamonds.size()] = clubs.get(i);
        for (int i = 0; i < hearts.size(); i++) sortedCards[i + diamonds.size() + clubs.size()] = hearts.get(i);
        for (int i = 0; i < spades.size(); i++) sortedCards[i + diamonds.size() + clubs.size() + hearts.size()] = spades.get(i);
        for (int i = 0; i < jokers.size(); i++) sortedCards[i + diamonds.size() + clubs.size() + hearts.size() + spades.size()] = jokers.get(i);


        return sortedCards;
    }


}