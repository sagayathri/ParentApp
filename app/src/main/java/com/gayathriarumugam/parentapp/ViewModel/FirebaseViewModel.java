package com.gayathriarumugam.parentapp.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gayathriarumugam.parentapp.Model.GetAddress;
import com.gayathriarumugam.parentapp.Model.GetDistance;
import com.gayathriarumugam.parentapp.Model.Parent;
import com.gayathriarumugam.parentapp.Model.Tutor;
import com.gayathriarumugam.parentapp.Repos.FirebaseRepository;
import com.gayathriarumugam.parentapp.Utils.Constants;

import java.util.ArrayList;

public class FirebaseViewModel extends AndroidViewModel {

    public Context context;

    public MutableLiveData<ArrayList<Tutor>> tutorMutableLiveData;
    public MutableLiveData<Parent> parentMutableLiveData;

    public FirebaseViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Tutor>> getAllTutors() {
        if (tutorMutableLiveData == null) {
            tutorMutableLiveData = FirebaseRepository.getInstance(context).getAllTutors();
        }
        return tutorMutableLiveData;
    }

    public MutableLiveData<Parent> getParent() {
        if (parentMutableLiveData == null) {
            parentMutableLiveData = FirebaseRepository.getInstance(context).getParent();
        }
        return parentMutableLiveData;
    }
}
