package com.google.firebase.udacity.friendlychatpolls;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lyla on 1/2/17.
 */

public class MessageUtilities {


    public static boolean isPoll (String pollMessage) {
        return pollMessage.contains("/poll");
    }

    public static FriendlyMessage parsePoll(String pollMessage, String author, Context c){

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

        //Error case, less than one answer
        if (answers.size() < 2) {
            showError(c, "You need to provide as least two answers for your poll. " +
                    "The poll format is /poll \"question\" \"answer 1\" \"answer 2\" ");
            return null;
        }

        //Error case more than four answers
        if (answers.size() > 4) {
            showError(c, "You can have a maximum of four answers for your poll");
            return null;
        }

        return new FriendlyMessage(question, author, answers);
    }

    private static void showError(Context c, String errorMessage) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(c, errorMessage, duration);
        toast.show();
    }

}
