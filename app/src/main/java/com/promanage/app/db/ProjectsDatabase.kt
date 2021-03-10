package com.promanage.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.promanage.app.service.Labor
import com.promanage.app.service.Material
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Project::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProjectsDatabase : RoomDatabase() {
    abstract fun projectsDao(): ProjectsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ProjectsDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ProjectsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectsDatabase::class.java,
                    "projects_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.projectsDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(projectsDao: ProjectsDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            projectsDao.deleteAll()

            var project1 = Project(
                System.currentTimeMillis(),
                projectName = "Bathroom Remodel",
                clientName = "Dave",
                startDate = Date(121, 0, 15),
                CompletionDate = Date(121, 0, 25),
                notes = "paint + change faucets + change tiles",
                address = "17744 2nd NE, Shoreline, WA 98177",
                materials = listOf(
                    Material(sku = "312528780", quantity = 34),
                    Material(sku = "204383534", quantity = 4),
                    Material(sku = "202060532", quantity = 2),
                    Material(sku = "312613927", quantity = 1)
                ),
                labor = listOf(
                    Labor(
                        name = "Salim",
                        description = "Electrician",
                        hours = 16,
                        rate = 35
                    ),
                    Labor(name = "Micheal", description = "Plumber", hours = 24, rate = 40),
                    Labor(name = "Jenny", description = "Painter", hours = 40, rate = 35)
                ),
                overheadCosts = mapOf("Permit" to 300, "Transport" to 200),
                isAccepted = true
            )
            projectsDao.insert(project1)

            var project2 = Project(
                System.currentTimeMillis() + 2,
                projectName = "Kitchen Remodel",
                clientName = "Mike",
                startDate = Date(121, 1, 15),
                CompletionDate = Date(121, 1, 25),
                notes = "paint + change faucets + change tiles",
                address = "1994 16nd NE, Seattle, WA 98177",
                materials = listOf(
                    Material(sku = "312528780", quantity = 34),
                    Material(sku = "204383534", quantity = 4),
                    Material(sku = "202060532", quantity = 2),
                    Material(sku = "312613927", quantity = 1)
                ),
                labor = listOf(
                    Labor(
                        name = "Salim",
                        description = "Electrician",
                        hours = 16,
                        rate = 35
                    ),
                    Labor(name = "Micheal", description = "Plumber", hours = 24, rate = 40),
                    Labor(name = "Jenny", description = "Painter", hours = 40, rate = 35)
                ),
                overheadCosts = mapOf("Permit" to 300, "Transport" to 200),
                isAccepted = false
            )
            projectsDao.insert(project2)
        }

    }
}