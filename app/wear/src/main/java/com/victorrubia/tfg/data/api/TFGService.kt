package com.victorrubia.tfg.data.api

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.tag.Tag
import retrofit2.Response
import retrofit2.http.*
import java.util.*

/**
 * Interface for the Retrofit service to connect to the backend.
 */
interface TFGService {
    /**
     * Creates an activity.
     *
     * @return The list of activities.
     */
    @POST("activities/")
    suspend fun newActivity(@Header("Authorization") apiKey : String,
                            @Query("activity[name]") name : String,
                            @Query("activity[start_d]") startTimestamp : String ) : Response<Activity>

    /**
     * Ends an activity
     *
     * @param apiKey
     * @param activityId
     * @param endTimestamp
     * @return The ended activity
     */
    @PUT("activities/{id}")
    suspend fun endActivity(@Header("Authorization") apiKey : String,
                            @Path(value = "id") activityId : Int,
                            @Query("activity[end_d]") endTimestamp : String) : Response<Activity>

    /**
     * Adds a PPG measure to an activity
     *
     * @param apiKey
     * @param measurement
     * @param activityID
     * @return any response
     */
    @FormUrlEncoded
    @POST("ppg_measures/")
    suspend fun addPPGMeasure(@Header("Authorization") apiKey : String,
                              @Field("ppg_measure[measurement]") measurement: String,
                              @Field("ppg_measure[activity_id]") activityID: Int,
    ) : Response<*>

    @FormUrlEncoded
    @POST("accelerometer_measures/")
    suspend fun addAccelerometerMeasure(@Header("Authorization") apiKey : String,
                              @Field("accelerometer_measure[measurement]") measurement: String,
                              @Field("accelerometer_measure[activity_id]") activityID: Int,
    ) : Response<*>

    @FormUrlEncoded
    @POST("gps_measures/")
    suspend fun addGPSMeasure(@Header("Authorization") apiKey : String,
                                        @Field("gps_measure[measurement]") measurement: String,
                                        @Field("gps_measure[activity_id]") activityID: Int,
    ) : Response<*>

    @FormUrlEncoded
    @POST("step_measures/")
    suspend fun addStepMeasure(@Header("Authorization") apiKey : String,
                              @Field("step_measure[measurement]") measurement: String,
                              @Field("step_measure[activity_id]") activityID: Int,
    ) : Response<*>

    @FormUrlEncoded
    @POST("significant_mov_measures/")
    suspend fun addSignificantMovMeasure(@Header("Authorization") apiKey : String,
                               @Field("significant_mov_measure[measurement]") measurement: String,
                               @Field("significant_mov_measure[activity_id]") activityID: Int,
    ) : Response<*>

    /**
     * Adds tag to an activity
     *
     * @param apiKey
     * @param tag
     * @param dateTime
     * @param activityID
     * @return The added tag
     */
    @FormUrlEncoded
    @POST("tags/")
    suspend fun addTag(@Header("Authorization") apiKey : String,
                              @Field("tag[tag]") tag: String,
                              @Field("tag[datetime]") dateTime: Date,
                              @Field("tag[activity_id]") activityID: Int,
    ) : Response<Tag>
}