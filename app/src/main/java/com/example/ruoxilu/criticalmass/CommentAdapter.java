package com.example.ruoxilu.criticalmass;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by tingyu on 4/18/15.
 */
public class CommentAdapter extends ParseQueryAdapter<Comment> {

    String mUsername;
    String mComment;

    public CommentAdapter(Context c, final String eventObjectId) {
        super(c, new ParseQueryAdapter.QueryFactory<Comment>() {
            public ParseQuery<Comment> create() {
                ParseQuery queryEventComment = new ParseQuery("EventComment");
                queryEventComment.whereEqualTo("EventId", eventObjectId);
                queryEventComment.orderByDescending("updatedAt");
                return queryEventComment;
            }
        });

        mUsername = "";
        mComment = "";

    }

    @Override
    public View getItemView(Comment comment,
                            View v,
                            ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.comment_item, null);
        }

        super.getItemView(comment, v, parent);

        TextView usernameText = (TextView) v.findViewById(R.id.comment_username);
        if (comment.getUserName() == null) {
            usernameText.setText("Nobody: ");
        } else {
            usernameText.setText(comment.getUserName() + ": ");
        }

        TextView commentText = (TextView) v
                .findViewById(R.id.comment_content);
        commentText.setText(comment.getUserComment());

        TextView timeText = (TextView) v.findViewById(R.id.comment_time);
        timeText.setText(comment.getUpdateTime());
        Log.d(Settings.APPTAG, "getUpdateTime returns " + comment.getUpdateTime());

        return v;

    }


}
