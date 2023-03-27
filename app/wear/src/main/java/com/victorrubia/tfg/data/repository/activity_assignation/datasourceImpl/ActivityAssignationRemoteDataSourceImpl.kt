package com.victorrubia.tfg.data.repository.activity_assignation.datasourceImpl

import android.content.Context
import android.os.Environment
import android.util.Log
import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfg.data.repository.activity_assignation.datasource.ActivityAssignationRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ActivityAssignationRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource,
    private val context: Context
) : ActivityAssignationRemoteDataSource {

    override suspend fun getActivitiesAssigned() : Response<List<ActivityAssignation>> =
        tfgService.getAssignedActivities("Bearer ${user.getUserFromCache()!!.apiKey}")

    override suspend fun downloadImage(activities : List<ActivityAssignation>) {

        for (activity in activities){
            withContext(Dispatchers.IO) {
                try {
                    val response = tfgService.downloadImage(activity.activity.iconUrl).execute()
                    if (response.isSuccessful) {
                        val inputStream = response.body()?.byteStream()
                        Log.d("DOWNLOADIMAGE", "Response from API: $inputStream")
                        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS+"/activities/"), activity.activity.name + '.' + activity.activity.iconUrl.split('.').last())
                        val outputStream = FileOutputStream(file)

                        inputStream?.use { input ->
                            outputStream.use { output ->
                                input.copyTo(output)
                            }
                        }
                    } else {
                        Log.d("DOWNLOAD", "Failed to download image")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            for(tag in activity.tags){
                withContext(Dispatchers.IO) {
                    try {
                        val response = tfgService.downloadImage(tag.iconUrl).execute()
                        if (response.isSuccessful) {
                            val inputStream = response.body()?.byteStream()
                            Log.d("DOWNLOADIMAGE", "Response from API: $inputStream")
                            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS+"/tags/"), tag.name + '.' + tag.iconUrl.split('.').last())
                            val outputStream = FileOutputStream(file)

                            inputStream?.use { input ->
                                outputStream.use { output ->
                                    input.copyTo(output)
                                }
                            }
                        } else {
                            Log.d("DOWNLOAD", "Failed to download image")
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override suspend fun clearAllImages(){
        val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString())
        directory.deleteRecursively()
    }

}