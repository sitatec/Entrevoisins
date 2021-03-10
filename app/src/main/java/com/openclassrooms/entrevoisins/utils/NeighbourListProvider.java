package com.openclassrooms.entrevoisins.utils;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

public interface NeighbourListProvider {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    public List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    public void deleteNeighbour(Neighbour neighbour);
}
