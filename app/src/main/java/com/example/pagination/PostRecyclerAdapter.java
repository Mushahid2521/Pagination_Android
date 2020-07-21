package com.example.pagination;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class PostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private List<PostItem> mPostItems;
    public PostRecyclerAdapter(List<PostItem> postItems) {
        this.mPostItems = postItems;
    }
    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            PostItem postItem = getItem(position);

            ((ViewHolder) holder).tite.setText(postItem.getTitle());
            ((ViewHolder) holder).description.setText(postItem.getDescription());
        }
        else {
            ((ProgressHolder) holder).progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPostItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
    @Override
    public int getItemCount() {
        return mPostItems == null ? 0 : mPostItems.size();
    }
    public void addItems(List<PostItem> postItems) {
        mPostItems.addAll(postItems);
        notifyDataSetChanged();
    }
    public void addLoading() {
        isLoaderVisible = true;
        mPostItems.add(new PostItem());
        notifyItemInserted(mPostItems.size() - 1);
    }
    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPostItems.size() - 1;
        PostItem item = getItem(position);
        if (item != null) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void clear() {
        mPostItems.clear();
        notifyDataSetChanged();
    }
    PostItem getItem(int position) {
        return mPostItems.get(position);
    }


    // get the UI object from the layout
    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        TextView tite, description;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tite = mView.findViewById(R.id.textViewTitle);
            description = mView.findViewById(R.id.textViewDescription);

        }
    }

    public class ProgressHolder extends RecyclerView.ViewHolder {

        private View mView;
        private ProgressBar progressBar;
        private TextView error_msg;

        public  ProgressHolder(View itemView) {
            super(itemView);
            mView = itemView;
            progressBar = mView.findViewById(R.id.progressBar);
        }
    }

}
