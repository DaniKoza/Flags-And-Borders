package com.example.firstexercise.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.firstexercise.Activities.MainActivity;
import com.example.firstexercise.Adapters.StateAdapter;
import com.example.firstexercise.Classes.State;
import com.example.firstexercise.R;
import com.example.firstexercise.Services.CountriesDataService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * My Params
     */
    private StateAdapter stateAdapter;
    private RecyclerView recyclerView_state_borders;
    private RecyclerView.LayoutManager layoutManager;
    private Button button_back;
    private ArrayList<State> bordersArray;


    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        button_back = view.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });


        Intent i = getActivity().getIntent();
        State passedState = (State) i.getSerializableExtra("StateObj");

        // If no borders there's no use to do all the other code below.
        if (passedState.getBorders().isEmpty()) {
            Toast.makeText(getActivity(), "Sorry No Borders", Toast.LENGTH_LONG).show();
            return view;
        }

        CountriesDataService countriesDataService = new CountriesDataService();
        bordersArray = new ArrayList<>();

        for (String s : passedState.getBorders()) { // find all the borders for one given state, by country code.
            bordersArray.add(countriesDataService.getStateWithStateCode(s));
        }

        stateAdapter = new StateAdapter(getContext(), bordersArray);
        recyclerView_state_borders = view.findViewById(R.id.recyclerView_state_borders);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_state_borders.setLayoutManager(layoutManager);
        recyclerView_state_borders.setAdapter(stateAdapter);
        return view;
    }


    public interface OnSecondFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSecondFragmentInteraction(Uri uri);
    }
}
