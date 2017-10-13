package com.example.formulae;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


class TopicsRecyclerViewAdapter extends RecyclerView.Adapter<TopicsRecyclerViewAdapter.ViewHolder> {

    private List<TopicsItem> TopicsItems;
    private Context context;

    TopicsRecyclerViewAdapter(List<TopicsItem> TopicsItems, Context context) {
        this.TopicsItems = TopicsItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_topics_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TopicsItem currentTopicsItem = TopicsItems.get(position);

        holder.textView_topicName.setText(currentTopicsItem.getTopicName());

        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "Lets go to " + TopicsItems.get(position).getTopicName() + "!", Toast.LENGTH_SHORT).show();

                /*Bundle userInfo = new Bundle();
                userInfo.putString("topicName", contactsItems.get(position).getTopicName());*/

                Intent DetailsActivityIntent = new Intent(context, DetailsActivity.class);
                //MessageAreaActivityIntent.putExtras(userInfo);
                DetailsActivityIntent.putExtra("topicName", TopicsItems.get(position).getTopicName());
                context.startActivity(DetailsActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return TopicsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_topicName;
        private RecyclerViewItemClickListener itemClickListener;

        ViewHolder(View itemView) {
            super(itemView);

            textView_topicName = (TextView) itemView.findViewById(R.id.textView_topicName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }

        void setItemClickListener(RecyclerViewItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
