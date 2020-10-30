package com.example.martin.shelem.handlers;

import com.example.martin.shelem.instances.Player;

public class XPHandler {


    public static void setWin(Player player,Boolean isWin){

        player.setTotalMatches(player.getTotalMatches() + 1);
        if (isWin) {
            player.setMmr(player.getMmr() + 25);
            player.setTotalXp(player.getTotalXp() + 15);
            player.setWonMatches(player.getWonMatches() + 1);
        } else {
            player.setMmr(player.getMmr() - 10);
            player.setTotalXp(player.getTotalXp() + 5);
            player.setWonMatches(player.getWonMatches() - 1);
        }
        APIHandler.updatePlayer(player, new APIHandler.ResponseListenerUpdatePlayer() {
            @Override
            public void onCaptionRecived(String response) {

            }

            @Override
            public void onStatusRecived(Boolean response) {

            }
        });
    }

    public static void setLeave(Player player) {

        player.setMmr(player.getMmr() - 30);
        player.setTotalXp(player.getTotalXp() + 5);
        player.setWonMatches(player.getWonMatches() - 1);
        APIHandler.updatePlayer(player, new APIHandler.ResponseListenerUpdatePlayer() {
            @Override
            public void onCaptionRecived(String response) {

            }

            @Override
            public void onStatusRecived(Boolean response) {

            }
        });
    }

    public static String getCup(int mmr){

        String cup = "none";

        if (mmr < 1150) cup = "none";

        else if ((mmr > 1200) && (mmr < 1350)) cup = "11_1";
        else if ((mmr > 1350) && (mmr < 1500)) cup = "11_2";
        else if ((mmr > 1500) && (mmr < 1650)) cup = "11_3";
        else if ((mmr > 1650) && (mmr < 1800)) cup = "11_4";

        else if ((mmr > 1800) && (mmr < 1950)) cup = "12_1";
        else if ((mmr > 1950) && (mmr < 2100)) cup = "12_2";
        else if ((mmr > 2100) && (mmr < 2250)) cup = "12_3";
        else if ((mmr > 2250) && (mmr < 2400)) cup = "12_4";

        else if ((mmr > 2400) && (mmr < 2550)) cup = "13_1";
        else if ((mmr > 2550) && (mmr < 2700)) cup = "13_2";
        else if ((mmr > 2700) && (mmr < 2850)) cup = "13_3";
        else if ((mmr > 2850) && (mmr < 3000)) cup = "13_4";

        else if ((mmr > 3000) && (mmr < 3150)) cup = "14_1";
        else if ((mmr > 3150) && (mmr < 3300)) cup = "14_2";
        else if ((mmr > 3300) && (mmr < 3450)) cup = "14_3";
        else if ((mmr > 3450) && (mmr < 3600)) cup = "14_4";

        else if ((mmr > 3600) && (mmr < 3750)) cup = "15_1";
        else if ((mmr > 3750) && (mmr < 3900)) cup = "15_2";
        else if ((mmr > 3900) && (mmr < 4050)) cup = "15_3";
        else if ((mmr > 4050) && (mmr < 4200)) cup = "15_4";


        return cup;
    }



}
