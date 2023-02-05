package com.victorrubia.tfg.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    fun getUser_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("userresponse.json")
            val responseBody = service.getUserInfo("victorjoserubia@gmail.com","4321").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/users/get_api_key/?email=victorjoserubia%40gmail.com&password_digest=4321")
        }
    }

    @Test
    fun getUser_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("userresponse.json")
            val responseBody = service.getUserInfo("victorjoserubia@gmail.com","4321").body()
            val userDetails = responseBody!!.userDetails
            val apiKey = responseBody.apiKey
            assertThat(userDetails.name).isEqualTo("PruebaNombre")
            assertThat(userDetails.email).isEqualTo("victorjoserubia@gmail.com")
            assertThat(apiKey).isEqualTo("a849134f7fedc8031baeb4fee18b33d2")
        }
    }

    @Test
    fun recoverPassword_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("recoverpasswordresponse.json")
            val responseCode = service.requestPasswordRecovery("victorjoserubia@gmail.com").code()
            assertThat(responseCode).isEqualTo(200)
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

}