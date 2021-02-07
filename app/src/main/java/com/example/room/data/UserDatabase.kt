package com.example.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.room.model.CrossTable
import com.example.room.model.Restaurant
import com.example.room.model.User

@Database(entities = [User::class, Restaurant::class, CrossTable::class], version = 3, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `restaurant` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `address` TEXT NOT NULL, `area` TEXT NOT NULL, " +
                        "`city` TEXT NOT NULL, `country` TEXT NOT NULL, `image_url` TEXT NOT NULL, `lat` REAL NOT NULL, `lng` REAL NOT NULL, `mobile_reserve_url` TEXT NOT NULL, " +
                "`name` TEXT NOT NULL, `phone` TEXT NOT NULL, `postal_code` TEXT NOT NULL, `price` INTEGER NOT NULL, `reserve_url` TEXT NOT NULL, `state` TEXT NOT NULL)" )
            }
        }

        private val MIGRATION_2_3: Migration = object: Migration(2,3)
        {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `favourite` (`favoriteID` INTEGER NOT NULL ,`id` INTEGER NOT NULL,`userID` INTEGER NOT NULL, PRIMARY KEY(`favoriteID`))")
            }
        }

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance =
                INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}