package com.google.firebase.udacity.friendlychatpolls;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class MessageAdapter extends FirebaseRecyclerAdapter<FriendlyMessage, MessageAdapter.FriendlyMessageHolder> {

    VoteOnClickHandler mVoteClickHandler;

    /** Interface for clicks**/
    public interface VoteOnClickHandler {
        void onClick(String voteNumber, String messageId);
    }

    public MessageAdapter(Class<FriendlyMessage> modelClass, int modelLayout,
                          Class<FriendlyMessageHolder> viewHolderClass, Query ref,
                          VoteOnClickHandler handler) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mVoteClickHandler = handler;
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
        } else if (isPoll) {
            viewHolder.messageTextView.setVisibility(View.VISIBLE);
            viewHolder.photoImageView.setVisibility(View.GONE);
            viewHolder.bindPollMessage(chatMessage, mVoteClickHandler, getRef(position).getKey());

        } else {
            viewHolder.messageTextView.setVisibility(View.VISIBLE);
            viewHolder.photoImageView.setVisibility(View.GONE);
            viewHolder.messageTextView.setText(chatMessage.getText());
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

        private VoteOnClickHandler mVoteClickHandler;
        private String mKey;

        public FriendlyMessageHolder(View itemView) {
            super(itemView);
            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            authorTextView = (TextView) itemView.findViewById(R.id.nameTextView);

            button1 = (Button) itemView.findViewById(R.id.button1);
            button2 = (Button) itemView.findViewById(R.id.button2);
            button3 = (Button) itemView.findViewById(R.id.button3);
            button4 = (Button) itemView.findViewById(R.id.button4);

            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            button3.setOnClickListener(this);
            button4.setOnClickListener(this);
        }

        public void bindPollMessage(FriendlyMessage chatMessage, VoteOnClickHandler handler, String key) {
            int answerNumber = 1;
            String question = chatMessage.getText();
            // set and display the buttons as necessary
            for (PollAnswer answer : chatMessage.getPoll()) {
                String answerNumberString = String.valueOf(answerNumber);
                question += "\n" + answerNumberString + ") " + answer.getText();

                Button b = null;
                switch (answerNumber) {
                    case 1:
                        b = button1;
                        break;
                    case 2:
                        b = button2;
                        break;
                    case 3:
                        b = button3;
                        break;
                    case 4:
                        b = button4;
                        break;
                    default:
                        b = button1;
                }

                int voteCount = 0;
                if (answer.getVotes() != null) voteCount = answer.getVotes().size();

                String buttonText = "Vote " + answerNumberString + " (" + voteCount + ")";

                b.setText(buttonText);
                b.setVisibility(View.VISIBLE);

                answerNumber++;
            }

            messageTextView.setText(question);
            mVoteClickHandler = handler;
            this.mKey = key;
        }

        @Override
        public void onClick(View v) {

            if (mVoteClickHandler != null) {
                switch (v.getId()) {
                    case R.id.button1:
                        mVoteClickHandler.onClick("0", mKey);
                        break;
                    case R.id.button2:
                        mVoteClickHandler.onClick("1", mKey);
                        break;
                    case R.id.button3:
                        mVoteClickHandler.onClick("2", mKey);
                        break;
                    case R.id.button4:
                        mVoteClickHandler.onClick("3", mKey);
                        break;
                }
            }

        }
    }
}
