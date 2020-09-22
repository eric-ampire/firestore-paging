package com.ericampire.android.demo.androiddemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ericampire.android.demo.androiddemo.FirestorePagingSource
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {

    val flow = Pager(PagingConfig(20)) {
        FirestorePagingSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)

}