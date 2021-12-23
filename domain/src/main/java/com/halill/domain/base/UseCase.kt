package com.halill.domain.base

abstract class UseCase<T, E> {
    abstract suspend fun execute(data: T): E
}