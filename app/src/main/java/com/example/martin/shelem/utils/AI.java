package com.example.martin.shelem.utils;

public class AI {
    private String[] player;
    private String[] aiTwo;
    private String[] aiThree;
    private String[] aiFour;


    public void setAiTwo(String[] aiTwo){ this.aiTwo = aiTwo;}

    public void setAiThree(String[] aiThree){ this.aiThree = aiThree;}


    public void setAiFour(String[] aiFour){ this.aiFour = aiFour;}

    public String[] getAiTwo(){return aiTwo;}

    public String[] getAiThree(){return aiThree;}

    public String[] getAiFour(){return aiFour;}

    public String[] heuristic(String[] scrinGame){
        return null;
    }

    public String[] play (String[] scrinGame,int playerNum,int cost,int d){
        if (d == 2){
            return heuristic(scrinGame);
        }
        if (playerNum == 2){
            String[] min = new String[3];
            min[3] = String.valueOf(10000000);
            for (int i = 0; i < aiTwo.length; i++) {
                String[] newMatrix = copy(scrinGame);
                String[] x = play(newMatrix,playerNum++,Integer.parseInt(min[1]),d+1);
                if (Integer.parseInt(x[1]) < Integer.parseInt(min[1])){
                    min[1] = x[1];
                    min[0] = player[i];
                }
                if (Integer.parseInt(min[1]) < cost)
                    return min;

            }
        }
        else if (playerNum == 3){
            String[] max = new String[3];
            max[3] = String.valueOf(-10000000);
            for (int i = 0; i < aiThree.length; i++) {
                String[] newMatrix = copy(scrinGame);
                String[] x = play(newMatrix,playerNum++,Integer.parseInt(max[1]),d+1);
                if (Integer.parseInt(x[1]) < Integer.parseInt(max[1])){
                    max[1] = x[1];
                    max[0] = player[i];
                }
                if (Integer.parseInt(max[1]) > cost)
                    return max;

            }
        }
        else if (playerNum == 4){
            String[] min = new String[3];
            min[3] = String.valueOf(10000000);
            for (int i = 0; i < aiFour.length; i++) {
                String[] newMatrix = copy(scrinGame);
                String[] x = play(newMatrix,playerNum++,Integer.parseInt(min[1]),d+1);
                if (Integer.parseInt(x[1]) < Integer.parseInt(min[1])){
                    min[1] = x[1];
                    min[0] = player[i];
                }
                if (Integer.parseInt(min[1]) < cost)
                    return min;

            }
        }
        else{}

        return null;
    }
    private String[] copy(String inter[]) {
        String[] R = new String[inter.length];
        for (int i = 0; i < inter.length; i++) {
            R[i]=inter[i];
        }
        return R;
    }



}
