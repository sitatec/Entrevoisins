package com.openclassrooms.entrevoisins.utils;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.test_utils.FavoriteNeighbourIdsTestUtil;
import com.openclassrooms.entrevoisins.test_utils.FavoriteNeighbourIdsTestUtil;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static com.openclassrooms.entrevoisins.test_utils.FavoriteNeighbourIdsTestUtil.FAKE_NEIGHBOURS;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class FavoriteNeighbourIdsTest {

    FavoriteNeighbourIds favoriteNeighbourIds;

    @Before
    public void setup(){
        favoriteNeighbourIds = DI.getFavoriteNeighbourIds();
        FavoriteNeighbourIds.setData(FavoriteNeighbourIdsTestUtil.getRandomNeighbourIds(5));
    }

    @Test
    public void putNewDataSuccessfully(){
        final long fakeData = 100000;
        Assert.assertFalse(favoriteNeighbourIds.contains(fakeData));
        favoriteNeighbourIds.put(fakeData);
        Assert.assertTrue(favoriteNeighbourIds.contains(fakeData));
    }

    @Test
    public void deleteDataSuccessfully(){
        final Neighbour neighbour = favoriteNeighbourIds.getNeighbours().get(0);
        favoriteNeighbourIds.put(neighbour.getId());
        Assert.assertTrue(favoriteNeighbourIds.contains(neighbour.getId()));
        favoriteNeighbourIds.deleteNeighbour(neighbour);
        Assert.assertFalse(favoriteNeighbourIds.contains(neighbour.getId()));
    }

    @Test
    public void checkIfContainDataCorrectly(){
        final long fakeData = 999999999; // Very big number to be sur that the favoriteNeighbourIds does not contain it.
        Assert.assertFalse(favoriteNeighbourIds.contains(fakeData));
        favoriteNeighbourIds.put(fakeData);
        Assert.assertTrue(favoriteNeighbourIds.contains(fakeData));
    }

    @Test
    public void getNeighbourSuccessfully(){
        FavoriteNeighbourIdsTestUtil.fillLocalDataWithAllNeighboursIds(favoriteNeighbourIds, FAKE_NEIGHBOURS);
        List<Neighbour> neighbours = favoriteNeighbourIds.getNeighbours();
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(FAKE_NEIGHBOURS.toArray()));
    }
}
