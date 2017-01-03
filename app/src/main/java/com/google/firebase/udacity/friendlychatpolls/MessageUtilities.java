package com.google.firebase.udacity.friendlychatpolls;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lyla on 1/2/17.
 */

public class MessageUtilities {


    public static boolean isPoll (String pollMessage) {
        return pollMessage.contains("/poll");
    }

    public static FriendlyMessage parsePoll(String pollMessage, String author){

        String[] parts = pollMessage.split("\"");
        String question = null;
        ArrayList<PollAnswer> answers = new ArrayList<PollAnswer>();
        for (String s : parts) {
            if (isPoll(s)) continue;
            if (s.trim().length() > 0) {
                if (question == null) {
                    question = s;
                } else {
                    answers.add(new PollAnswer(s));
                }
            }
        }

        return new FriendlyMessage(question, author, answers);
    }

}
