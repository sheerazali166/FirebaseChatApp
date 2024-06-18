package com.example.firebasechat.chat.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechat.R;
import com.example.firebasechat.chat.entities.ChatMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context _context, List<ChatMessage> _chatMessages) {

        this.context = _context;
        this.chatMessages = _chatMessages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_chat, viewGroup, false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ChatMessage chatMessage = chatMessages.get(position);

        String message = chatMessage.getMessage();
        holder.txtMessage.setText(message);

        int color = fetchColor(androidx.appcompat.R.attr.colorPrimary);
        int gravity = Gravity.LEFT;

        if (!chatMessage.isSentByMe()) {

            gravity = Gravity.RIGHT;
            color = fetchColor(androidx.appcompat.R.attr.colorAccent);
        }

        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        layoutParams.gravity = gravity;
        holder.txtMessage.setLayoutParams(layoutParams);

    }

    private int fetchColor(int color) {

        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data, new int[] { color } );

        int returnColor = typedArray.getColor(0, 0);
        typedArray.recycle();

        return returnColor;
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    private boolean alreadyInAdapter(ChatMessage newMessage) {

        boolean alreadyInAdapter = false;

        for (ChatMessage chatMessage: this.chatMessages) {

            if (chatMessage.getMessage().equals(newMessage.getMessage()) && chatMessage.getSender().equals(newMessage.getSender())) {
                alreadyInAdapter = true;
                break;
            }
        }

        return alreadyInAdapter;
    }

    public void add(ChatMessage _chatMessage) {

        if (!alreadyInAdapter(_chatMessage)) {

            this.chatMessages.add(_chatMessage);
            this.notifyDataSetChanged();

        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMessage)
        TextView txtMessage;

        public MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
