package com.sebastianvm.mango

import com.sebastianvm.mango.data.FakeIncomeSourceRepositoryImpl
import com.sebastianvm.mango.database.dao.FakeIncomeSourceDaoImpl
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.stream.Stream

object FakeProvider {

    val defaultIncomeSourceEntity = IncomeSourceEntity(id = 0, name = "")

    val incomeSourceDao: FakeIncomeSourceDaoImpl
        get() = FakeIncomeSourceDaoImpl(
            getIncomeSourceValue = MutableStateFlow(defaultIncomeSourceEntity),
            getAllIncomeSourcesValue = MutableStateFlow(listOf(defaultIncomeSourceEntity))
        )

    val incomeSourceRepository: FakeIncomeSourceRepositoryImpl
        get() = FakeIncomeSourceRepositoryImpl(
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
