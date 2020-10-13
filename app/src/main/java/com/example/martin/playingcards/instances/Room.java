package com.example.martin.playingcards.instances;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;



public class Room implements Parcelable {

    private String id;
    private int status, minimumLevel, maxPoints, teamOnePoint, teamTwoPoint;
    private boolean joker;
    public List<Player> playerList = new ArrayList<>();



    public Room() { }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(int minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getTeamOnePoint() {
        return teamOnePoint;
    }

    public void setTeamOnePoint(int teamOnePoint) {
        this.teamOnePoint = teamOnePoint;
    }

    public int getTeamTwoPoint() {
        return teamTwoPoint;
    }

    public void setTeamTwoPoint(int teamTwoPoint) {
        this.teamTwoPoint = teamTwoPoint;
    }

    public boolean isJoker() {
        return joker;
    }

    public void setJoker(boolean joker) {
        this.joker = joker;
    }





    protected Room(Parcel in) {
        id = in.readString();
        status = in.readInt();
        minimumLevel = in.readInt();
        maxPoints = in.readInt();
        teamOnePoint = in.readInt();
        teamTwoPoint = in.readInt();
        joker = in.readByte() != 0;
    }


    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(status);
        dest.writeInt(minimumLevel);
        dest.writeInt(maxPoints);
        dest.writeInt(teamOnePoint);
        dest.writeInt(teamTwoPoint);
        dest.writeByte((byte) (joker ? 1 : 0));
    }
}
