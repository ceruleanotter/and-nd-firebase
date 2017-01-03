package com.google.firebase.udacity.friendlychatpolls;

import java.util.HashMap;

/**
 * Created by lyla on 1/2/17.
 */

public class PollAnswer {
    private String text;
    private HashMap<String, Boolean> votes;

    public PollAnswer() {
    }

    public PollAnswer(String text) {
        this.text = text;
        this.votes = new HashMap<String, Boolean>();
    }

    public String getText() {
        return text;
    }

    public HashMap<String, Boolean> getVotes() {
        return votes;
    }
}
