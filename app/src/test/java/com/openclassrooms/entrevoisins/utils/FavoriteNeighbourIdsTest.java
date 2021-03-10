package com.openclassrooms.entrevoisins.utils;


import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.test_utils.LocalDataManagerTestUtil;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class FavoriteNeighboursListTest {

    FavoriteNeighboursList favoriteNeighboursList;

    @Before
    public void setup(){
        favoriteNeighboursList = DI.getLocalDataManger();
        FavoriteNeighboursList.setData(LocalDataManagerTestUtil.getRandomNeighbourIds(5));
    }

    @Test
    public void putNewDataSuccessfully(){
        final long fakeData = 100000;
        Assert.assertFalse(favoriteNeighboursList.contains(fakeData));
        favoriteNeighboursList.put(fakeData);
        Assert.assertTrue(favoriteNeighboursList.contains(fakeData));
    }

    @Test
    public void deleteDataSuccessfully(){
        final Neighbour neighbour = favoriteNeighboursList.getNeighbours().get(0);
        favoriteNeighboursList.put(neighbour.getId());
        Assert.assertTrue(favoriteNeighboursList.contains(neighbour.getId()));
        favoriteNeighboursList.deleteNeighbour(neighbour);
        Assert.assertFalse(favoriteNeighboursList.contains(neighbour.getId()));
    }

    @Test
    public void checkIfContainDataCorrectly(){
        final long fakeData = 999999999; // Very big number to be sur that the local data manger does not contain it.
        Assert.assertFalse(favoriteNeighboursList.contains(fakeData));
        favoriteNeighboursList.put(fakeData);
        Assert.assertTrue(favoriteNeighboursList.contains(fakeData));
    }

    @Test
    public void getNeighbourSuccessfully(){
        List<Neighbour> expectedNeighbours = DI.getNewInstanceApiService().getNeighbours();
        LocalDataManagerTestUtil.fillLocalDataWithAllNeighboursIds(favoriteNeighboursList, expectedNeighbours);
        List<Neighbour> neighbours = favoriteNeighboursList.getNeighbours();
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }
}
