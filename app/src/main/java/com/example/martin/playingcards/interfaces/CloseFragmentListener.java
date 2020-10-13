package com.example.martin.playingcards.interfaces;

import com.example.martin.playingcards.instances.Room;

public interface CloseFragmentListener {
    void onFragmentClosed();
    void onFragmentClosedAction(Room room);

}
