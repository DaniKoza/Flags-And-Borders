package com.example.firstexercise.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstexercise.Activities.MainActivity;
import com.example.firstexercise.Adapters.StateAdapter;
import com.example.firstexercise.Classes.State;
import com.example.firstexercise.Listeners.RecyclerTouchListener;
import com.example.firstexercise.R;
import com.example.firstexercise.Services.CountriesDataService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FirstFragment extends Fragment {
    /**
     * Our vars
     */
    private StateAdapter stateAdapter;
    private RecyclerView recyclerView_stateList;
    private ArrayList<State> myStates;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final CountriesDataService dataService = new CountriesDataService();
        final View view = inflater.inflate(R.layout.fragment_first, container, false);

        try {
            myStates = dataService.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stateAdapter = new StateAdapter(getContext(), myStates);

        recyclerView_stateList = view.findViewById(R.id.recyclerView_states_list);
        recyclerView_stateList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_stateList.setAdapter(stateAdapter);

        EditText inputSearch = view.findViewById(R.id.editText_Search);


        recyclerView_stateList.addOnItemTouchListener(
                new RecyclerTouchListener(getContext(), recyclerView_stateList, new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        view.startAnimation(hyperspaceJumpAnimation);
                        State s = stateAdapter.getMyStates().get(position);

                        if (s.getBorders().isEmpty()) {
                            // If no borders there's no use to move to second fragment.
                            Toast.makeText(getActivity(), "Islands have no borders", Toast.LENGTH_LONG).show();
                        } else {
                            MainActivity ma = (MainActivity) getActivity();
                            ma.LoadSecFragment(s);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changes the Text
                stateAdapter = new StateAdapter(getActivity(), stateAdapter.costumeFilter(myStates, cs.toString()));
                recyclerView_stateList.setAdapter(stateAdapter);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        return view;
    }

    public interface OnFirstFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFirstFragmentInteraction(Uri uri);
    }
}
