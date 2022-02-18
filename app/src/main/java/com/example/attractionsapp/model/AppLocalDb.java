package com.example.attractionsapp.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.attractionsapp.MyApplication;

//import com.example.attractionsapp.MyApplication;

@Database(entities = {Attraction.class}, version = 3)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract AttractionDao attractionDao();
}
public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}