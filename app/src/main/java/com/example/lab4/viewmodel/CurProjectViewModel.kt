package com.example.lab4.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lab4.ProjectPortalApplication
import com.example.lab4.datalayer.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
/*
Create a ViewModel class to keep track of Current Project
Use AndroidViewModel as the super class, we can pass the
application object directly to it.
 */
class CurProjectViewModel(application: Application): AndroidViewModel(application){
    private val _curProject: MutableLiveData<Project> = MutableLiveData()
    val curProject: LiveData<Project>
        get() = _curProject

    val projectportalDatabase = (application as ProjectPortalApplication).projectportalDatabase
    val projectDao = projectportalDatabase.projectDao()

    fun initCurProject(project: Project){
        if (_curProject.value == null)
            _curProject.value = project
    }
    fun setCurProject(project: Project){
        _curProject.value = project
    }
    fun isCurProject(project:Project):Boolean{
        return _curProject.value?.id == project.id
    }

    fun updateCurProject(title:String, desp:String, colab:String, fav:Boolean, colabCount:Int){
        _curProject.value = _curProject.value?.apply{
            this.title = title
            this.description = desp
            this.collaborate=colab
            this.isFavorite=fav
            this.collaborateCount=colabCount
        }

        viewModelScope.launch(Dispatchers.IO){
            projectDao.editProject(_curProject.value!!)
        }
    }

}