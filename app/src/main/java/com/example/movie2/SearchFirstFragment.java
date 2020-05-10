package com.example.movie2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class SearchFirstFragment extends Fragment {
    private static final String TAG = "SearchFirstFragment";

    private Chip comedyChip, romanceChip, horrorChip, actionChip, adventureChip, d2000_2010, d2010_2020, d1990_2000, kSuperhero, kSuicide, lHindi, lEnglish, cIndia, cUS;
    private Chip kMcu, kDc, kSpace, kMagic, kMusical, kRevenge;
    private ChipGroup genreChipGroup, chip_group_decades, chip_group_keywords, chip_group_lang, chip_group_country;
    private Button btnSeeResult;


    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_serach_fragment, container, false);

        initViews(view);

        final Intent intent = new Intent(getActivity(), SearchResultsActivity.class);


        btnSeeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> Ids = getCheckedChipIds();
                intent.putIntegerArrayListExtra("ids", Ids);
                startActivity(intent);
            }
        });

//        for (int i = 0; i < genreChipGroup.getChildCount(); i++) {
//            View child = genreChipGroup.getChildAt(i);
//            if (child instanceof Chip) {
//                ((Chip) child).setOnClickListener(new View.OnClickListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                    @Override
//                    public void onClick(View v) {
//                        changeButtonColor();
//                    }
//                });
//
//            }
//        }


        chip_group_decades.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                changeButtonColor();
            }
        });

        chip_group_keywords.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                changeButtonColor();
            }
        });
        chip_group_lang.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                changeButtonColor();
            }
        });
        chip_group_country.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                changeButtonColor();
            }
        });


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeButtonColor() {
        if (getCheckedChipIds().size() > 0) {

            btnSeeResult.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.button_state_tint));
        } else {
            btnSeeResult.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.button_state_tint2));
        }
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


    private void initViews(View view) {
        Log.d(TAG, "initViews: called");

        actionChip = (Chip) view.findViewById(R.id.chipAction);
        comedyChip = (Chip) view.findViewById(R.id.comedyChip);
        horrorChip = (Chip) view.findViewById(R.id.horrorChip);
        adventureChip = (Chip) view.findViewById(R.id.adventureChip);
        romanceChip = (Chip) view.findViewById(R.id.romanceChip);

        genreChipGroup = (ChipGroup) view.findViewById(R.id.genreChipGroup);
        chip_group_country = (ChipGroup) view.findViewById(R.id.chip_group_country);
        chip_group_decades = (ChipGroup) view.findViewById(R.id.chip_group_decades);
        chip_group_keywords = (ChipGroup) view.findViewById(R.id.chip_group_keywords);
        chip_group_lang = (ChipGroup) view.findViewById(R.id.chip_group_lang);

        d1990_2000 = (Chip) view.findViewById(R.id.d1990_2000);
        d2010_2020 = (Chip) view.findViewById(R.id.d2010_2020);
        d2000_2010 = (Chip) view.findViewById(R.id.d2000_2010);

        kSuicide = (Chip) view.findViewById(R.id.kSuicide);
        kMusical = (Chip) view.findViewById(R.id.kMusical);
        kDc = (Chip) view.findViewById(R.id.kDc);
        kMcu = (Chip) view.findViewById(R.id.kMcu);
        kMagic = (Chip) view.findViewById(R.id.kMagic);
        kRevenge = (Chip) view.findViewById(R.id.kRevenge);
        kSpace = (Chip) view.findViewById(R.id.kSpace);
        kSuperhero = (Chip) view.findViewById(R.id.kSuperhero);


        cIndia = (Chip) view.findViewById(R.id.cIndia);
        cUS = (Chip) view.findViewById(R.id.cUS);

        lEnglish = (Chip) view.findViewById(R.id.lEnglish);
        lHindi = (Chip) view.findViewById(R.id.lHindi);

        btnSeeResult = (Button) view.findViewById(R.id.btnSeeResult);
    }
}
