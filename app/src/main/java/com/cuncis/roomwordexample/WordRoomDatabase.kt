package com.cuncis.roomwordexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1, exportSchema = false)  // no export from outsite
abstract class WordRoomDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val wordDao = database.wordDao()

                    // delete all content here.
                    wordDao.deleteAll()

                    // add sample words.
                    var word = Word("Hello")
                    wordDao.insert(word)
                    word = Word("World!")
                    wordDao.insert(word)

                    word = Word("Bye")
                    wordDao.insert(word)
                    word = Word("Corona")
                    wordDao.insert(word)

                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java,
                        "word_db")
                        .addCallback(WordDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    instance
                }

//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    WordRoomDatabase::class.java,
//                    "word_db")
//                    .addCallback(WordDatabaseCallback(scope))
//                    .build()
//                INSTANCE = instance
//                return instance
            }
        }
    }

}