package com.example.librarybookdueremainder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textViewBookName.setText(book.getBookName());
        holder.textViewReturnDate.setText(book.getReturnDate());
    }
    @Override
    public int getItemCount() {
        return bookList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewBookName;
        private TextView textViewReturnDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBookName = itemView.findViewById(R.id.textViewBookName);
            textViewReturnDate = itemView.findViewById(R.id.textViewReturnDate);
        }
    }
}
