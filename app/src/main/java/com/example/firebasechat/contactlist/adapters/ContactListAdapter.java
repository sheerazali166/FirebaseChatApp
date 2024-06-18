package com.example.firebasechat.contactlist.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechat.R;
import com.example.firebasechat.contactlist.entities.User;
import com.example.firebasechat.contactlist.ui.OnItemClickListener;
import com.example.firebasechat.domain.AvatarHelper;
import com.example.firebasechat.lib.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder> {

    private List<User> userList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public ContactListAdapter(List<User> _userList,
                              ImageLoader _imageLoader, OnItemClickListener _onItemClickListener) {

            this.userList = _userList;
            this.imageLoader = _imageLoader;
            this.onItemClickListener = _onItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_contact, viewGroup, false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = userList.get(position);
        holder.setClickListener(user, onItemClickListener);

        String email = user.getEmail();
        boolean online = user.isOnline();
        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        holder.txtUser.setText(email);
        holder.txtStatus.setText(status);
        holder.txtStatus.setTextColor(color);

        imageLoader.load(holder.circleImageView, AvatarHelper.getAvatarUrl(email));

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public int getPositionByUsername(String username) {
        int position = 0;

        for (User user: userList) {

            if (user.getEmail().equals(username)) {
                break;
            }
            position++;
        }
        return position;
    }

    private boolean alreadyInAdapter(User newUser) {

        boolean alreadyInAdapter = false;

        for (User user: this.userList) {

            if (user.getEmail().equals(newUser.getEmail())) {

                alreadyInAdapter = true;
                break;
            }
        }
        return alreadyInAdapter;
    }

    public void add(User user) {

        if (!alreadyInAdapter(user)) {

            this.userList.add(user);
            this.notifyDataSetChanged();
        }
    }

    public void update(User user) {

        int position = getPositionByUsername(user.getEmail());
        userList.set(position, user);
        this.notifyDataSetChanged();

    }

    public void remove(User user) {

        int position = getPositionByUsername(user.getEmail());
        userList.remove(position);
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgAvatar)
        CircleImageView circleImageView;

        @BindView(R.id.txtStatus)
        TextView txtStatus;

        @BindView(R.id.txtUser)
        TextView txtUser;
        View view;

        public MyViewHolder(@NonNull View _view) {
            super(_view);
            view = _view;
            ButterKnife.bind(view);


        }

        public void setClickListener(final User user, OnItemClickListener onItemClickListener) {

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickListener.onItemClick(user);
                }
            });


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    onItemClickListener.onItemLongClick(user);
                    return false;
                }
            });
        }
    }

}
