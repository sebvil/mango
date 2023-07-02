package com.sebastianvm.mango.data

import com.sebastianvm.mango.database.MangoDatabase
import com.sebastianvm.mango.database.models.UserEnt
import com.sebastianvm.mango.model.User
import com.sebastianvm.mango.util.coroutines.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserStore (
    private val db: MangoDatabase,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getUserById(id: Int): User? = withContext(ioDispatcher) { db.userDao().getUserById(id) }

}

fun User.toEnt() = UserEnt(id, name)

@Module
@InstallIn(ViewModelComponent::class)
object UserStoreModule {

    @Provides
    fun provideUserStore(
        db: MangoDatabase,
        @IODispatcher ioDispatcher: CoroutineDispatcher): UserStore {
        return UserStore(db, ioDispatcher)
    }

}