package com.example.formulae;


import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.kexanie.library.MathView;


class FormulasRecyclerViewAdapter extends RecyclerView.Adapter<FormulasRecyclerViewAdapter.ViewHolder> {

    private List<FormulasItem> formulasItems;
    private Context context;
    private TabLayout tabhost;

    FormulasRecyclerViewAdapter(List<FormulasItem> formulasItems, Context context, Activity activity) {
        this.formulasItems = formulasItems;
        this.context = context;
        tabhost = (TabLayout) activity.findViewById(R.id.tabs);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_formulas_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FormulasItem currentFormulasItem = formulasItems.get(position);

        holder.textView_shortDescription.setText(currentFormulasItem.getTitle());
        holder.mathView_singleFormula.setText(currentFormulasItem.getFormula());
        holder.textView_notations.setText(currentFormulasItem.getNotations());

        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "Lets calculate something!", Toast.LENGTH_SHORT).show();

                DetailsActivity.selectedFormulaItem = formulasItems.get(position);
                //Toast.makeText(context, DetailsActivity.selectedFormulaItem.getTitle(), Toast.LENGTH_SHORT).show();
                tabhost.setTag(formulasItems.get(position));
                tabhost.getTabAt(0).select();
                tabhost.getTabAt(2).select();

                /*Bundle formulaItemInfo = new Bundle();
                formulaItemInfo.putString("title", formulasItems.get(position).getTitle());
                formulaItemInfo.putInt("id", position);

                Intent DetailsActivityIntent = new Intent(context, DetailsActivity.class);
                MessageAreaActivityIntent.putExtras(userInfo);*/

                //DetailsActivity.mSectionsPagerAdapter.getItem(3);
                //DetailsActivity.PlaceholderFragment.newInstance(3);
                //context.getTabHost().setCurrentTab(2);
                
                //DetailsActivityIntent.putExtra("topicName", formulasItems.get(position).getTopicName());
                //context.startActivity(DetailsActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return formulasItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_shortDescription;
        MathView mathView_singleFormula;
        MathView textView_notations;
        private RecyclerViewItemClickListener itemClickListener;

        ViewHolder(View itemView) {
            super(itemView);

            textView_shortDescription = (TextView) itemView.findViewById(R.id.textView_shortDescription);
            mathView_singleFormula = (MathView) itemView.findViewById(R.id.mathView_singleFormula);
            textView_notations = (MathView) itemView.findViewById(R.id.textView_notations);

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
