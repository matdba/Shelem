package com.example.martin.shelem.instances;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Room {

    @SerializedName("roomID")
    @Expose
    private String roomID;
    @SerializedName("roomStatus")
    @Expose
    private Integer roomStatus;
    @SerializedName("isJoker")
    @Expose
    private Boolean isJoker;
    @SerializedName("maxPoint")
    @Expose
    private Integer maxPoint;
    @SerializedName("teamOnePoints")
    @Expose
    private Integer teamOnePoints;
    @SerializedName("teamTwoPoints")
    @Expose
    private Integer teamTwoPoints;
    @SerializedName("players")
    @Expose
    private List<Player> players = null;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Boolean getIsJoker() {
        return isJoker;
    }

    public void setIsJoker(Boolean isJoker) {
        this.isJoker = isJoker;
    }

    public Integer getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(Integer maxPoint) {
        this.maxPoint = maxPoint;
    }

    public Integer getTeamOnePoints() {
        return teamOnePoints;
    }

    public void setTeamOnePoints(Integer teamOnePoints) {
        this.teamOnePoints = teamOnePoints;
    }

    public Integer getTeamTwoPoints() {
        return teamTwoPoints;
    }

    public void setTeamTwoPoints(Integer teamTwoPoints) {
        this.teamTwoPoints = teamTwoPoints;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


}