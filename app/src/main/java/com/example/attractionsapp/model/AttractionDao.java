package com.example.attractionsapp.model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AttractionDao {
    @Query("select * from Attraction")
    List<Attraction> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Attraction... attractions);
    @Insert
    void insertBoth(Attraction att1, Attraction att2);
    @Delete
    void delete(Attraction attraction);
    @Query("DELETE FROM Attraction WHERE id = :id")
    void deleteById(String id);
}