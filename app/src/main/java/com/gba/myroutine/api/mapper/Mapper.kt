package com.gba.myroutine.api.mapper

interface Mapper<I, O> {
    fun map(input: I): O
    fun reverseMap(input: O): I
}