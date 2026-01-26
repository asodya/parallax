package com.parallax.client.dal.remote

interface RemoteStreamDataSource {
    suspend fun connect(): Result<Unit>
    suspend fun disconnect(): Result<Unit>
}
