package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.utils.NeighbourListProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.util.List;


public class NeighbourFragment extends Fragment {

    public static final String NEIGHBOUR_PAGE_ARG_KEY = "com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment.NEIGHBOUR_PAGE_ARG_KEY";
    public static final int ALL_NEIGHBOURS_PAGE = 0;
    public static final int FAVORITE_NEIGHBOURS_PAGE = 1;
    private RecyclerView mRecyclerView;
    private NeighbourListProvider mNeighbourListProvider;
    private MyNeighbourRecyclerViewAdapter adapter;

    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance() {
        NeighbourFragment fragment = new NeighbourFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int pageId =  getArguments().getInt(NEIGHBOUR_PAGE_ARG_KEY);
        mNeighbourListProvider = getNeighboursProvider(pageId);
    }


    private NeighbourListProvider getNeighboursProvider(int page){
        if (page == FAVORITE_NEIGHBOURS_PAGE){
            return DI.getFavoriteNeighbourIds();
        }
        return DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        setUpAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    /**
     * Init the List of neighbours and set adapter
     */
    private void setUpAdapter() {
        final List<Neighbour> mNeighbours = mNeighbourListProvider.getNeighbours();
        adapter = new MyNeighbourRecyclerViewAdapter(mNeighbours, this::startNeighbourDetailActivity);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("NeighbourFragment", "onStop");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("NeighbourFragment", "onPause");
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        Log.i("NeighbourFragment", "onDeleteNeighbour");
        mNeighbourListProvider.deleteNeighbour(event.neighbour);
        updateUi();
    }

    private void updateUi(){
        adapter.updateList(mNeighbourListProvider.getNeighbours());
    }

    private void startNeighbourDetailActivity(Neighbour neighbour){
        final Intent detailActivityIntent = new Intent(getContext(), NeighbourDetailActivity.class);
        detailActivityIntent.putExtra(NeighbourDetailActivity.EXTRA_NAME, neighbour);
        startActivity(detailActivityIntent);
    }
}
