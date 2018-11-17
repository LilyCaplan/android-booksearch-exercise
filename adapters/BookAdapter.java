package com.codepath.android.booksearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.android.booksearch.GlideApp;
import com.codepath.android.booksearch.MyAppGlideModule;
import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.activities.BookDetailActivity;
import com.codepath.android.booksearch.models.Book;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> mBooks;
    private Context mContext;


    // View lookup cache
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
        public TextView tvAuthor;
        RelativeLayout mll;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ivCover = (ImageView)itemView.findViewById(R.id.ivBookCover);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvAuthor = (TextView)itemView.findViewById(R.id.tvAuthor);
            mll = (RelativeLayout) itemView.findViewById(R.id.container);

        }

        public void bind(final Book book) {
            // Populate data into the template view using the data object
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            GlideApp.with(getContext())
                    .load(Uri.parse(book.getCoverUrl()))
                    .placeholder(R.drawable.ic_nocover)
                    .into(ivCover);
            mll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext , BookDetailActivity.class);
                    //i.putExtra("title", book.getTitle());
                    i.putExtra("book", Parcels.wrap(book) );
                    mContext.startActivity(i);

                }
            });
        }
    }

    public BookAdapter(Context context, ArrayList<Book> aBooks) {
        mBooks = aBooks;
        mContext = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookView = inflater.inflate(R.layout.item_book, parent, false);

        // Return a new holder instance
        BookAdapter.ViewHolder viewHolder = new BookAdapter.ViewHolder(bookView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Book book = mBooks.get(position);
        viewHolder.bind(book);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
}
