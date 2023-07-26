package com.sebastianvm.mango.data

import com.google.common.truth.Truth.assertThat
import com.sebastianvm.mango.BaseTest
import com.sebastianvm.mango.FakeProvider
import com.sebastianvm.mango.database.dao.FakeTaxDao
import com.sebastianvm.mango.model.Taxes
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TaxRepositoryImplTest : BaseTest() {

    private lateinit var taxDao: FakeTaxDao

    @BeforeEach
    fun beforeEach() {
        taxDao = FakeProvider.taxDao
    }

    @Test
    fun `populateTaxes upserts taxes entities`() = testScope.runTest {
        TaxRepositoryImpl(taxDao, dispatcher).populateTaxes()
        assertThat(taxDao.upsertTaxesInvocations).containsExactly(
            listOf(
                Taxes.values().map { it.taxEntity }
            )
        )
    }
}
