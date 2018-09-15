package com.github.yasushi.hansel

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.SystemClock
import android.net.Uri
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.UploadTask
import org.jetbrains.annotations.NotNull
import java.util.*
import kotlin.concurrent.thread

@Entity
class Trip (id: String?, userId: String?, clipUri: Uri?, start: Long?, duration: Long?) {
    @PrimaryKey
    @NotNull
    var id: String = id ?: Firebase.getNewTripId(userId)
    var userId: String? = userId
    var clipUri: Uri? = clipUri
    var start: Long = start ?: System.currentTimeMillis()

    // don't use currentTimeMillis for both start and end when delta is important
    @Ignore
    var startElapsed = SystemClock.elapsedRealtime()
    var duration = duration

    @Ignore
    constructor(userId: String) : this(null, userId, null, null, null)

    fun toMap(): Map<String, Any> {
        var trip = HashMap<String, Any>()

        trip.put("id", this.id!!)
        trip.put("userId", this.userId!!)
        trip.put("clipUri", UriToStringConverter().uriToString(this.clipUri!!))
        trip.put("start", this.start)
        trip.put("duration", this.duration!!)

        return trip
    }

    fun stop () {
        this.duration = SystemClock.elapsedRealtime() - this.startElapsed
    }

    fun addRecord(){
        Firebase.addTrip(this.userId, this.id, this.toMap())
    }

    fun upload(db: TripDatabase) {
        Firebase.uploadClip(this.id, this.clipUri).addOnSuccessListener {
            this.addRecord()
            thread {
                // deletes clip from local list
                db.tripDao().delete(this.id)
            }

            Log.d("Trip:upload", "uploaded clip: "+ this.id);

        }

    }

}