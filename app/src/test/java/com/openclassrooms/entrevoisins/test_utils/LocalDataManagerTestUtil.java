package com.openclassrooms.entrevoisins.test_utils;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.utils.FavoriteNeighbourIds;


import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LocalDataManagerTestUtil {

    public static Set<Long> getRandomNeighbourIds(int count){
        final List<Neighbour> neighbours= DI.getNewInstanceApiService().getNeighbours();
        final Set<Long> neighboursIds = new HashSet<>();
        final Random randomNumber = new Random();
        Neighbour currentNeighbour;
        for (int i = 0; i < count; i++){
            currentNeighbour = neighbours.get(randomNumber.nextInt(neighbours.size()));
            neighboursIds.add(currentNeighbour.getId());
        }
        return neighboursIds;
    }

    public static void fillLocalDataWithAllNeighboursIds(FavoriteNeighbourIds favoriteNeighbourIds, List<Neighbour> neighbours){
        for (Neighbour neighbour : neighbours){
            favoriteNeighbourIds.put(neighbour.getId());
        }
    }
}
