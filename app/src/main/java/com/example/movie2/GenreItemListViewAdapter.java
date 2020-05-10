package com.example.movie2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movie2.Model.Genre;

import java.util.ArrayList;

public class GenreItemListViewAdapter extends ArrayAdapter<Genre> {
    private static final String TAG = "GenreItemListViewAdapte";

    private TextView genreName, genreId;
    private RelativeLayout relativeLayout;

    public GenreItemListViewAdapter(Context context, ArrayList<Genre> genres) {
        super(context, 0, genres);


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        final Genre genre = getItem(position);


        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.genre_list_view_tems, parent, false);

        }

        // Lookup view for data population

        genreName = (TextView) convertView.findViewById(R.id.genreName);
        genreId = (TextView) convertView.findViewById(R.id.genreId);
        relativeLayout = (RelativeLayout) convertView.findViewById(R.id.parent);

        //populate the data into the template view using the data object

        genreName.setText(genre.getName());
        genreId.setText(String.valueOf(genre.getId()));

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), GenreActivity.class);
                intent.putExtra("incomingGenre", genre.getId());
                intent.putExtra("GenreName", genre.getName());
                getContext().startActivity(intent);
            }
        });

        // Return the complete view to render on screen
        return convertView;
    }
}
