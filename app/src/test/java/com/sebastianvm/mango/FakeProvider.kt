package com.sebastianvm.mango

import com.sebastianvm.mango.data.FakeIncomeSourceRepository
import com.sebastianvm.mango.database.dao.FakeIncomeSourceDao
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.stream.Stream

object FakeProvider {

    val defaultIncomeSourceEntity = IncomeSourceEntity(id = 0, name = "")

    val incomeSourceDao: FakeIncomeSourceDao
        get() = FakeIncomeSourceDao(
            getIncomeSourceValue = MutableStateFlow(defaultIncomeSourceEntity),
            getAllIncomeSourcesValue = MutableStateFlow(listOf(defaultIncomeSourceEntity))
        )

    val incomeSourceRepository: FakeIncomeSourceRepository
        get() = FakeIncomeSourceRepository(
            getIncomeSourceValue = MutableStateFlow(defaultIncomeSourceEntity),
            getAllIncomeSourcesValue = MutableStateFlow(listOf(defaultIncomeSourceEntity))
        )

    @JvmStatic
    fun incomeSourceEntityProvider(): Stream<IncomeSourceEntity> {
        return Stream.of(
            IncomeSourceEntity(id = 0, name = "Mango"),
            IncomeSourceEntity(id = 1, name = "Nectarine"),
            IncomeSourceEntity(id = 2, name = "Orange")
        )
    }

    @JvmStatic
    fun incomeSourceEntityListProvider(): Stream<List<IncomeSourceEntity>> {
        return Stream.of(
            listOf(
                IncomeSourceEntity(id = 0, name = "Mango")
            ),
            listOf(
                IncomeSourceEntity(id = 0, name = "Mango"),
                IncomeSourceEntity(id = 1, name = "Nectarine")
            ),
            listOf(
                IncomeSourceEntity(id = 0, name = "Mango"),
                IncomeSourceEntity(id = 1, name = "Nectarine"),
                IncomeSourceEntity(id = 2, name = "Orange")
            )
        )
    }
}
