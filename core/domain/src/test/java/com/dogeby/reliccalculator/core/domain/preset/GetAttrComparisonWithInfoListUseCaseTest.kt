package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.fake.FakeGameRepository
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAttrComparisonWithInfoListUseCaseTest {

    private lateinit var getAttrComparisonWithInfoListUseCase: GetAttrComparisonWithInfoListUseCase
    private lateinit var gameRepository: FakeGameRepository

    @Before
    fun setUp() {
        gameRepository = FakeGameRepository()
        getAttrComparisonWithInfoListUseCase = GetAttrComparisonWithInfoListUseCase(
            gameRepository = gameRepository,
        )
    }

    @Test
    fun test_getAttrComparisonWithInfoListUseCase_success() = runTest {
        val propertyInfoMap = attrComparisonTypes.associateWith {
            PropertyInfo(
                type = it,
                name = "",
                field = "",
                affix = false,
                ratio = false,
                percent = false,
                order = 0,
                icon = "",
            )
        }
        gameRepository.sendPropertyInfoMap(propertyInfoMap)

        val result = getAttrComparisonWithInfoListUseCase().first()
        Assert.assertEquals(
            attrComparisonTypes,
            result.map { it.propertyInfo.type },
        )
    }
}
