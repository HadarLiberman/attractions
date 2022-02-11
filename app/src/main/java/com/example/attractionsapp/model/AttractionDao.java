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
    void insertAll(Attraction... attraction);
    @Delete
    void delete(Attraction attraction);
}
