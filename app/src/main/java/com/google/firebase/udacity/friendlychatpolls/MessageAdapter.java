package com.google.firebase.udacity.friendlychatpolls;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class MessageAdapter extends FirebaseRecyclerAdapter<FriendlyMessage, MessageAdapter.FriendlyMessageHolder> {


    public MessageAdapter(Class<FriendlyMessage> modelClass, int modelLayout, Class<FriendlyMessageHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(FriendlyMessageHolder viewHolder, FriendlyMessage model, int position) {

        viewHolder.button1.setVisibility(View.GONE);
        viewHolder.button2.setVisibility(View.GONE);
        viewHolder.button3.setVisibility(View.GONE);
        viewHolder.button4.setVisibility(View.GONE);

        FriendlyMessage chatMessage = getItem(position);

        boolean isPhoto = chatMessage.getPhotoUrl() != null;
        boolean isPoll = chatMessage.getPoll() != null;
        if (isPhoto) {
            viewHolder.messageTextView.setVisibility(View.GONE);
            viewHolder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.photoImageView.getContext())
                    .load(chatMessage.getPhotoUrl())
                    .into(viewHolder.photoImageView);
        } else {
            viewHolder.messageTextView.setVisibility(View.VISIBLE);
            viewHolder.photoImageView.setVisibility(View.GONE);

            String message = chatMessage.getText();

            // If it's a poll
            if (!isPoll) {
            } else {
                int answerNumber = 1;
                // set and display the buttons as necessary
                for (PollAnswer answer : chatMessage.getPoll()) {

                    String answerNumberString = String.valueOf(answerNumber);

                    message += "\n" + answerNumberString + ") " + answer.getText();

                    String buttonIdString = "button" + String.valueOf(answerNumber);


                    Button b = null;
                    switch (answerNumber) {
                        case 1:
                            b = viewHolder.button1;
                            break;
                        case 2:
                            b = viewHolder.button2;
                            break;
                        case 3:
                            b = viewHolder.button3;
                            break;
                        case 4:
                            b = viewHolder.button4;
                            break;
                    }

                    int voteCount = 0;
                    if (answer.getVotes() != null) voteCount = answer.getVotes().size();

                    String buttonText = "Vote " + answerNumberString + " (" + voteCount + ")";

                    b.setText(buttonText);
                    b.setVisibility(View.VISIBLE);

                    answerNumber++;
                }
            }

            viewHolder.messageTextView.setText(message);

        }
        viewHolder.authorTextView.setText(chatMessage.getName());
    }

    public static class FriendlyMessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView photoImageView;
        TextView messageTextView;
        TextView authorTextView;
        Button button1;
        Button button2;
        Button button3;
        Button button4;

        public FriendlyMessageHolder(View itemView) {
            super(itemView);
            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            authorTextView = (TextView) itemView.findViewById(R.id.nameTextView);

            button1 = (Button) itemView.findViewById(R.id.button1);
            button2 = (Button) itemView.findViewById(R.id.button2);
            button3 = (Button) itemView.findViewById(R.id.button3);
            button4 = (Button) itemView.findViewById(R.id.button4);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
