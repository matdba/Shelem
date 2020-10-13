package com.example.martin.shelem.utils;

import java.util.Arrays;

public class PlayersMatchingHandler {

    private static final Integer[][] numberArrangements = {{1, 2, 3, 4}, {2, 1, 3, 4}, {3, 4, 1, 2}, {4, 3, 1, 2}};


    public static int handle(int currentPlayerNumber, int playerNumber) {
        return Arrays.asList(numberArrangements[currentPlayerNumber - 1]).indexOf(playerNumber);
    }
}
