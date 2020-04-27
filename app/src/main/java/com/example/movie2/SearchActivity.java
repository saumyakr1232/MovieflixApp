package com.example.movie2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private Chip comedyChip, romanceChip, horrorChip, actionChip, adventureChip, d2000_2010, d2010_2020, d1990_2000, kSuperhero, kSuicide, lHindi, lEnglish, cIndia, cUS;
    private Chip kMcu, kDc, kSpace, kMagic, kMusical, kRevenge;
    private ChipGroup genreChipGroup, chip_group_decades, chip_group_keywords, chip_group_lang, chip_group_country;
    private Button btnSeeResult;
    private EditText searchStringEditText;
    private ImageView btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        final Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("searchQuery", searchStringEditText.getText().toString());
                startActivity(intent);

            }
        });


        btnSeeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> Ids = getCheckedChipIds();
                intent.putIntegerArrayListExtra("ids", Ids);
                intent.putExtra("searchQuery", searchStringEditText.getText());
                //Toast.makeText(SearchActivity.this, Ids.toString(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }

    /**
     * Returns the identifiers of the selected {@link Chip}s in this group. Upon empty
     * selection, the returned value is an empty list.
     *
     * @return The unique IDs of the selected {@link Chip}s in this group. When in
     * returns a list with a single ID. When no
     * {@link Chip}s are selected, returns an empty list.
     */
    @NonNull
    public ArrayList<Integer> getCheckedChipIds() {
        Log.d(TAG, "getCheckedChipIds: called ");
        ArrayList<Integer> checkedIds = new ArrayList<>();
        for (int i = 0; i < genreChipGroup.getChildCount(); i++) {
            View child = genreChipGroup.getChildAt(i);
            if (child instanceof Chip) {
                if (((Chip) child).isChecked()) {
                    checkedIds.add(child.getId());
                }
            }
        }

        for (int i = 0; i < chip_group_decades.getChildCount(); i++) {
            View child = chip_group_decades.getChildAt(i);
            if (child instanceof Chip) {
                if (((Chip) child).isChecked()) {
                    checkedIds.add(child.getId());
                }
            }
        }
        for (int i = 0; i < chip_group_lang.getChildCount(); i++) {
            View child = chip_group_lang.getChildAt(i);
            if (child instanceof Chip) {
                if (((Chip) child).isChecked()) {
                    checkedIds.add(child.getId());
                }
            }
        }
        for (int i = 0; i < chip_group_country.getChildCount(); i++) {
            View child = chip_group_country.getChildAt(i);
            if (child instanceof Chip) {
                if (((Chip) child).isChecked()) {
                    checkedIds.add(child.getId());
                }
            }
        }
        for (int i = 0; i < chip_group_keywords.getChildCount(); i++) {
            View child = chip_group_keywords.getChildAt(i);
            if (child instanceof Chip) {
                if (((Chip) child).isChecked()) {
                    checkedIds.add(child.getId());
                }
            }
        }
        Log.d(TAG, "getCheckedChipIds: ids" + checkedIds.toString());
        return checkedIds;
    }


    private void initViews() {
        Log.d(TAG, "initViews: called");
        actionChip = (Chip) findViewById(R.id.chipAction);
        comedyChip = (Chip) findViewById(R.id.comedyChip);
        horrorChip = (Chip) findViewById(R.id.horrorChip);
        adventureChip = (Chip) findViewById(R.id.adventureChip);
        romanceChip = (Chip) findViewById(R.id.romanceChip);

        genreChipGroup = (ChipGroup) findViewById(R.id.genreChipGroup);
        chip_group_country = (ChipGroup) findViewById(R.id.chip_group_country);
        chip_group_decades = (ChipGroup) findViewById(R.id.chip_group_decades);
        chip_group_keywords = (ChipGroup) findViewById(R.id.chip_group_keywords);
        chip_group_lang = (ChipGroup) findViewById(R.id.chip_group_lang);

        d1990_2000 = (Chip) findViewById(R.id.d1990_2000);
        d2010_2020 = (Chip) findViewById(R.id.d2010_2020);
        d2000_2010 = (Chip) findViewById(R.id.d2000_2010);

        kSuicide = (Chip) findViewById(R.id.kSuicide);
        kMusical = (Chip) findViewById(R.id.kMusical);
        kDc = (Chip) findViewById(R.id.kDc);
        kMcu = (Chip) findViewById(R.id.kMcu);
        kMagic = (Chip) findViewById(R.id.kMagic);
        kRevenge = (Chip) findViewById(R.id.kRevenge);
        kSpace = (Chip) findViewById(R.id.kSpace);
        kSuperhero = (Chip) findViewById(R.id.kSuperhero);


        cIndia = (Chip) findViewById(R.id.cIndia);
        cUS = (Chip) findViewById(R.id.cUS);

        lEnglish = (Chip) findViewById(R.id.lEnglish);
        lHindi = (Chip) findViewById(R.id.lHindi);

        btnSeeResult = (Button) findViewById(R.id.btnSeeResult);

        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        searchStringEditText = (EditText) findViewById(R.id.SearchString);

    }


}
