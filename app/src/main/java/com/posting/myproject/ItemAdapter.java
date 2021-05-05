package com.posting.myproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.posting.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by delaroy on 3/22/17.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> items;
    private Context context;



    public ItemAdapter(Context applicationContext, List<Item> itemArrayList) {
        this.context = applicationContext;
        this.items = itemArrayList;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_user, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(items.get(i).getName());
        viewHolder.description.setText(items.get(i).getDescription());
    //    viewHolder.githublink1.setText(items.get(i).getHooksUrl());

        Picasso.get()
                .load(items.get(i).getAvatarUrl())
                .placeholder(R.drawable.load)
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description,githublink1;
         CircleImageView imageView;


        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);

            imageView = view.findViewById(R.id.cover);

            //on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (!items.get(pos).getHtmlUrl().equals("check")) {
                            Item clickedDataItem = items.get(pos);
                            Intent intent = new Intent(context, DetailActivity.class);
                            intent.putExtra("login", items.get(pos).getName());
                            intent.putExtra("html_url", items.get(pos).getHtmlUrl());
                            intent.putExtra("forks_url", items.get(pos).getForksUrl());
                            intent.putExtra("keys_url", items.get(pos).getKeysUrl());
                            intent.putExtra("hooks_url", items.get(pos).getHooksUrl());
                            intent.putExtra("avatar_url", items.get(pos).getAvatarUrl());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,items.get(pos).getHtmlUrl() , Toast.LENGTH_SHORT).show();

                        }
                    }
                }

            });
        }
    }
}
