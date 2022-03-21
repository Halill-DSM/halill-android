package com.halill.data.util

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.halill.domain.exception.ReadFireBaseStoreFailException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Task<T>.createFlowToCheckTaskCallbackIsSuccess(): Flow<Boolean> =
    callbackFlow {
        this@createFlowToCheckTaskCallbackIsSuccess.addOnCompleteListener {
            trySendBlocking(it.isSuccessful)
            close()
        }
        awaitClose()
    }

@OptIn(ExperimentalCoroutinesApi::class)
fun Task<DocumentSnapshot>.dataBaseTaskToFlow(): Flow<DocumentSnapshot> =
    callbackFlow {
        this@dataBaseTaskToFlow.addOnSuccessListener {
            trySendBlocking(it)
            close()
        }.addOnFailureListener {
            throw ReadFireBaseStoreFailException()
        }
        awaitClose()
    }

@OptIn(ExperimentalCoroutinesApi::class)
fun Task<QuerySnapshot>.dataBaseQueryTaskToFlow(): Flow<QuerySnapshot> =
    callbackFlow {
        this@dataBaseQueryTaskToFlow.addOnSuccessListener {
            trySendBlocking(it)
            close()
        }.addOnFailureListener {
            Log.e("queryError", it.toString())
            throw ReadFireBaseStoreFailException()
        }
        awaitClose()
    }
