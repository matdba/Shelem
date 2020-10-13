package com.example.martin.shelem.instances;

public class RecentGameRooms {
    private Player person1,person2,person3,person4;
    private String id,type,game;
    private int teemWiner,maxPont,roomLvl,pointPlay,pointTeemOne,pointTeemTwo;





    public Player getPerson1() {
        return person1;
    }

    public Player getPerson2() {
        return person2;
    }

    public Player getPerson3() {
        return person3;
    }

    public Player getPerson4() {
        return person4;
    }

    public String getId(){return  id;}

    public String getGame(){return  game;}

    public String getType(){return  type;}

    public int getTeemWiner(){return  teemWiner;}

    public int getMaxPont(){return  maxPont;}

    public int getRoomLvl(){return  roomLvl;}

    public int getPointPlay(){return  pointPlay;}

    public int getPointTeemOne(){return  pointTeemOne;}

    public int getPointTeemTwo(){return  pointTeemTwo;}

    public void setPerson1(Player person1) {
        this.person1 = person1;
    }

    public void setPerson2(Player person2) {
        this.person2 = person2;
    }

    public void setPerson3(Player person3) {
        this.person3 = person3;
    }

    public void setPerson4(Player person4) {
        this.person4 = person4;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaxPoints(int intMaxPoints) {
        this.maxPont = intMaxPoints;
    }

    public void setRoomLevel(int intRoomLevel) {
        this.roomLvl = intRoomLevel;
    }

    public void setTeemWiner(int intTeemWiner) {
        this.teemWiner = intTeemWiner;
    }

    public void setPointPlay(int intPointPlay) {
        this.pointPlay = intPointPlay;
    }

    public void setPointTeemOne(int intPointTeemOne) {
        this.pointTeemOne = intPointTeemOne;
    }

    public void setPointTeemTwo(int intPointTeemTwo) {
        this.pointTeemTwo = intPointTeemTwo;
    }




}
