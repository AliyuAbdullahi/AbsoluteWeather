package com.lek.domain.usecase

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class UseCase<in PARAM, out RESULT>(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    protected abstract suspend fun run(param: PARAM): RESULT

    suspend operator fun invoke(param: PARAM): RESULT = withContext(coroutineContext) { run(param) }
}

suspend operator fun <RESULT> UseCase<Unit, RESULT>.invoke(): RESULT = this(Unit)

abstract class StreamUseCase<in INPUT, out OUTPUT>(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    protected abstract fun run(param: INPUT): Flow<OUTPUT>

    operator fun invoke(param: INPUT): Flow<OUTPUT> = run(param).flowOn(coroutineContext)
}

operator fun <RESULT> StreamUseCase<Unit, RESULT>.invoke(): Flow<RESULT> = invoke(Unit)