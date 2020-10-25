package com.example.martin.shelem.instances;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player  {

    @SerializedName("roomID")
    @Expose
    private Integer roomID;
    @SerializedName("userID")
    @Expose
    private Integer userID;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profilePictureNum")
    @Expose
    private Integer profilePictureNum;
    @SerializedName("totalxp")
    @Expose
    private Integer totalxp;
    @SerializedName("totalMatches")
    @Expose
    private Integer totalMatches;
    @SerializedName("wonMatches")
    @Expose
    private Integer wonMatches;
    @SerializedName("playedTime")
    @Expose
    private Integer playedTime;
    @SerializedName("playerStatus")
    @Expose
    private Integer playerStatus;
    @SerializedName("playerNumber")
    @Expose
    private Integer playerNumber;

    public Integer getRoomID() {
        return roomID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProfilePictureNum() {
        return profilePictureNum;
    }

    public void setProfilePictureNum(Integer profilePictureNum) {
        this.profilePictureNum = profilePictureNum;
    }

    public Integer getTotalxp() {
        return totalxp;
    }

    public void setTotalxp(Integer totalxp) {
        this.totalxp = totalxp;
    }

    public Integer getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(Integer totalMatches) {
        this.totalMatches = totalMatches;
    }

    public Integer getWonMatches() {
        return wonMatches;
    }

    public void setWonMatches(Integer wonMatches) {
        this.wonMatches = wonMatches;
    }

    public Integer getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(Integer playedTime) {
        this.playedTime = playedTime;
    }

    public Integer getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(Integer playerStatus) {
        this.playerStatus = playerStatus;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }


}
