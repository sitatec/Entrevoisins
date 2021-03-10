package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.utils.NeighbourListProvider;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService extends NeighbourListProvider {

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);
}
