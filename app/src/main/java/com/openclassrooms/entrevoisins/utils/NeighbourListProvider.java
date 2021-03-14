package com.openclassrooms.entrevoisins.utils;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * An interface that must be implemented by all Neighbour
 * data sources (e.g: Api clients, local data sources..)
 */
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
