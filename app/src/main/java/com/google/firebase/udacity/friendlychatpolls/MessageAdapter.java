package com.google.firebase.udacity.friendlychatpolls;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);


        RelativeLayout pollRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.pollRelativeLayout);
        Button button1 = (Button) convertView.findViewById(R.id.button1);
        Button button2 = (Button) convertView.findViewById(R.id.button2);
        Button button3 = (Button) convertView.findViewById(R.id.button3);
        Button button4 = (Button) convertView.findViewById(R.id.button4);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        button4.setVisibility(View.GONE);

        FriendlyMessage chatMessage = getItem(position);

        boolean isPhoto = chatMessage.getPhotoUrl() != null;
        boolean isPoll = chatMessage.getPoll() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            pollRelativeLayout.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(chatMessage.getPhotoUrl())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);

            String message = chatMessage.getText();

            // If it's a poll
            if(!isPoll) {
                pollRelativeLayout.setVisibility(View.GONE);
            } else {
                pollRelativeLayout.setVisibility(View.VISIBLE);

                // set and display the buttons as necessary

                for (Map.Entry<Integer, PollAnswer> entry : chatMessage.getPoll().entrySet()) {
                    String answerNumber = String.valueOf(entry.getKey());
                    PollAnswer answer = entry.getValue();
                    String buttonIdString = "button" + answerNumber;
                    try {
                        Class res = R.id.class;
                        Field field = res.getField(buttonIdString);
                        int buttonId = field.getInt(null);
                        Button b = (Button) convertView.findViewById(buttonId);

                        //TODO change this to a formatted string
                        String buttonText = "Choice " + answerNumber + " (" + answer.getVotes().size() + ")";

                        b.setText(buttonText);
                        b.setVisibility(View.VISIBLE);

                    }
                    catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }

                }
            }

            messageTextView.setText(message);






        }
        authorTextView.setText(chatMessage.getName());

        return convertView;
    }
}
