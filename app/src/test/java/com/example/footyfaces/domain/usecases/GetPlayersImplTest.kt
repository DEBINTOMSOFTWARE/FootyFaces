package com.example.footyfaces.domain.usecases

import com.example.footyfaces.TestConstants
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.repository.PlayerRepository
import com.example.footyfaces.domain.usecase.GetPlayersImpl
import com.example.footyfaces.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class GetPlayersImplTest {

    private lateinit var repository: PlayerRepository
    private lateinit var getPlayersImpl: GetPlayersImpl

    @Before
    fun setup() {
        repository = mockk()
        getPlayersImpl = GetPlayersImpl(repository)
    }

    @Test
    fun givenRepositoryReturnsPlayers_whenGetPlayers_thenReturnsPlayersAndPagination() = runTest {
        val mockResponse: Flow<Resource<Pair<List<PlayerEntity>, PaginationEntity>>> = flow {
            val players = listOf(
                PlayerEntity(
                    dateOfBirth = TestConstants.PLAYER_DOB,
                    displayName = TestConstants.PLAYER_NAME,
                    firstname = TestConstants.PLAYER_FIRSTNAME,
                    gender = TestConstants.PLAYER_GENDER,
                    height = TestConstants.PLAYER_HEIGHT,
                    id = TestConstants.PLAYER_ID,
                    imagePath = TestConstants.PLAYER_IMAGE_PATH,
                    lastname = TestConstants.PLAYER_LASTNAME,
                    name = TestConstants.PLAYER_NAME,
                    weight = TestConstants.PLAYER_WEIGHT
                )
            )

            val pagination = PaginationEntity(
                count = TestConstants.PAGINATION_COUNT,
                currentPage = TestConstants.PAGINATION_CURRENT_PAGE,
                hasMore = TestConstants.PAGINATION_HAS_MORE,
                nextPage = TestConstants.PAGINATION_NEXT_PAGE,
                perPage = TestConstants.PAGINATION_PER_PAGE
            )
            emit(Resource.Success(Pair(players, pagination)))
        }

        coEvery { repository.getPlayers(page = TestConstants.PAGE) } returns mockResponse
        val result = getPlayersImpl.getPlayers(page = TestConstants.PAGE)
        result.collect {
            assert(it is Resource.Success)
            assertTrue { (it as Resource.Success).data!!.first[0].displayName == TestConstants.PLAYER_NAME }
            assertTrue { (it as Resource.Success).data!!.second.hasMore == TestConstants.PAGINATION_HAS_MORE }
        }
    }

}