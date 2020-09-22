package com.ericampire.android.demo.androiddemo

import android.util.Log
import androidx.paging.PagingSource
import com.ericampire.android.demo.androiddemo.model.Country
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FirestorePagingSource(private val db: FirebaseFirestore) : PagingSource<QuerySnapshot, Country>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Country> {
        return try {
            val currentPage = params.key ?: db.collection("countries").limit(10).get().await()

            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]
            val nextPage = db.collection("countries").limit(10).startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(
                data = currentPage.toObjects(Country::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}