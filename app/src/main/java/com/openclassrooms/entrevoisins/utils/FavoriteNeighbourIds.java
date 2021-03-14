package com.openclassrooms.entrevoisins.utils;

import android.support.annotation.VisibleForTesting;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FavoriteNeighbourIds implements NeighbourListProvider{
    private static final Set<Long> DATA_LIST = new HashSet<>();

    // Save only the neighbour id because others info might change in the future.
    public void put(Long data){
        DATA_LIST.add(data);
    }

    public boolean contains(Long data){
        return DATA_LIST.contains(data);
    }

    @VisibleForTesting
    public static void setData(Set<Long> neighbours){
        DATA_LIST.clear();
        DATA_LIST.addAll(neighbours);
    }

    @Override
    public List<Neighbour> getNeighbours() {
        final List<Neighbour> allNeighbours = DI.getNeighbourApiService().getNeighbours();
        final Set<Neighbour> favoriteNeighbours = new HashSet<>();
        for (Neighbour neighbour: allNeighbours) {
            if(DATA_LIST.contains(neighbour.getId())){
                favoriteNeighbours.add(neighbour);
            }
            if(favoriteNeighbours.size() == DATA_LIST.size()) break;
        }
        return new ArrayList<>(favoriteNeighbours);
    }

    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        DATA_LIST.remove(neighbour.getId());
    }
}
