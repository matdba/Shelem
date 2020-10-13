package com.example.martin.shelem.instances;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
     private String username, userID, roomID;
     private int avatarNumber, playerStatus, playerNumber;


    public Player() { }


    protected Player(Parcel in) {
        username = in.readString();
        userID = in.readString();
        roomID = in.readString();
        avatarNumber = in.readInt();
        playerStatus = in.readInt();
        playerNumber = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }


    public int getAvatarNumber() {
        return avatarNumber;
    }

    public void setAvatarNumber(int avatarNumber) {
        this.avatarNumber = avatarNumber;
    }

    public int getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(int playerStatus) {
        this.playerStatus = playerStatus;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(userID);
        dest.writeString(roomID);
        dest.writeInt(avatarNumber);
        dest.writeInt(playerStatus);
        dest.writeInt(playerNumber);
    }
}
