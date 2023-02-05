package com.victorrubia.tfg.data.api

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class TFGServiceTest {

    private lateinit var service: TFGService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TFGService::class.java)
    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun newActivity_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("newactivityresponse.json")
            val responseBody = service.newActivity("2867a2b9ed8ce4672f661571d1957af7","Prueba", "2022-05-06 13:59:50").body()
            val request = server.takeRequest()
            Truth.assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/activities/?activity%5Bname%5D=Prueba&activity%5Bstart_d%5D=2022-05-06%2013%3A59%3A50")
        }
    }

    @Test
    fun endActivity_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("endactivityresponse.json")
            val responseBody = service.endActivity("2867a2b9ed8ce4672f661571d1957af7",164, "2022-05-06 14:59:50").body()
            val request = server.takeRequest()
            Truth.assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/activities/164?activity%5Bend_d%5D=2022-05-06%2014%3A59%3A50")
        }
    }

    @Test
    fun addPPGMeasure_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("addppgmeasureresponse.json")
            val responseBody = service.addPPGMeasure("2867a2b9ed8ce4672f661571d1957af7",
                "[{\\\"measure\\\":133427,\\\"date\\\":\\\"26/03/2022 10:33:02.68\\\"}]", 164).body()
            val request = server.takeRequest()
            Truth.assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/ppg_measures/")
        }
    }

    @Test
    fun addTag_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("addtagresponse.json")
            val responseBody = service.addTag("2867a2b9ed8ce4672f661571d1957af7",
                "Ruido", SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS").parse("04/05/2022 13:36:04.0")!!, 164
            ).body()
            val request = server.takeRequest()
            Truth.assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/tags/")
        }
    }


}