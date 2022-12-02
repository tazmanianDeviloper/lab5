package com.example.lab4

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lab4.datalayer.Project
import com.example.lab4.datalayer.ProjectPortalDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


/*
This class defines an application class
Define the application name as ProjectPortalApplication
in the manifest file under the application tag.

 */
class ProjectPortalApplication: Application() {
    lateinit var projectportalDatabase: ProjectPortalDatabase
    override fun onCreate() {
        super.onCreate()
        projectportalDatabase =
            Room.databaseBuilder(
                applicationContext, ProjectPortalDatabase::class.java,
                "projectportal-db"
            )
                // add a callback to modify onCreate() to
                // add some initial projects.
                .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    addInitProjects()
                }
            })
                .build()
    }

    // this is only used to add some initial projects
    // when the database is first created.
    // this is done through
    fun addInitProjects(){
        // need to execute in a separate thread
        CoroutineScope(Dispatchers.IO).launch{
            projectportalDatabase.projectDao().addProject(
                Project(1, "Weather Forecast", "Weather Forcast is an app ...",false, "Taymaz",0)
            )
            projectportalDatabase.projectDao().addProject(
                Project(2, "Connect Me", "Connect Me is an app ... ",false, "Maytaz",0)
            )
            projectportalDatabase.projectDao().addProject(
                Project(3, "What to Eat", "What to Eat is an app ...",false, "Zamyat",0)
            )
        }
    }

}