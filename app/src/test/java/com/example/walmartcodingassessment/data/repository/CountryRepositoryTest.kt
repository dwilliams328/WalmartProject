package com.example.walmartcodingassessment.data.repository

import com.example.walmartcodingassessment.data.model.CountryDO
import com.example.walmartcodingassessment.data.service.CountriesService
import com.example.walmartcodingassessment.data.service.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CountryRepositoryTest {
    private lateinit var apiService: CountriesService
    private lateinit var repo: ICountryRepository
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        apiService = mockk()
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun get_List_Of_Countries_Success() = runTest(testDispatcher) {
        val fakeResponse = listOf(
            CountryDO(
                name = "Afghan afghani",
                region = "AS",
                code = "AFN",
                capital = "Kabul"
            )
        )

        val fakeSuccessResponse: Response<List<CountryDO>> = Response.success(fakeResponse)

        repo = CountryRepository(apiService)
        coEvery { apiService.getCountriesList() } returns fakeSuccessResponse

        val responseFlow = repo.getCountriesList().toList()

        assert(responseFlow[0] is ApiResult.Loading)
        assert(responseFlow[1] is ApiResult.Success)

        val successApiResult = responseFlow[1] as ApiResult.Success
        assert(successApiResult.data == fakeResponse)
    }

    @Test
    fun get_List_Of_Countries_Failure() = runTest(testDispatcher) {
        val fakeResponse: Response<List<CountryDO>> = Response.error(400, "Http Bad Request".toResponseBody())

        coEvery { apiService.getCountriesList() } returns fakeResponse
        repo = CountryRepository(apiService)

        val responseFlow = repo.getCountriesList().toList()

        assert(responseFlow[0] is ApiResult.Loading)
        assert(responseFlow[1] is ApiResult.Failure)

        val errorApiResult = responseFlow[1] as ApiResult.Failure
        assert(errorApiResult.error.message == "Network failure response code: 400")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}