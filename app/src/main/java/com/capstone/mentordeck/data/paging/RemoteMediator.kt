package com.capstone.mentordeck.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.capstone.mentordeck.data.retrofit.ApiService
import com.capstone.mentordeck.database.MentorDatabase
import com.capstone.mentordeck.database.MentorEntity
import com.capstone.mentordeck.database.RemoteKeys

//@OptIn(ExperimentalPagingApi::class)
//class RemoteMediator(private val database: MentorDatabase, private val apiService: ApiService) : RemoteMediator<Int, MentorEntity>() {
//
//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }
//
//    override suspend fun load(loadType: LoadType, state: PagingState<Int, MentorEntity>): MediatorResult {
//        val page = when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
//            }
//
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                val prevKey = remoteKeys?.prevKey?:
//                return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                prevKey
//            }
//
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                val nextKey = remoteKeys?.nextKey?:
//                return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                nextKey
//            }
//        }
//
//        try {
//            val responseData = apiService.getAllStories("Bearer $token", page, state.config.pageSize, 0)
//
//            val responseDataList = responseData.stories
//
//            val storyData = responseDataList.map { story ->
//                StoryEntity(
//                    story.id,
//                    story.name,
//                    story.description,
//                    story.photoUrl,
//                    story.createdAt,
//                    story.lat,
//                    story.lon
//                )
//            }
//
//            val endOfPaginationReached = storyData.isEmpty()
//
//            database.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    database.remoteKeysDao().deleteRemoteKeys()
//                    database.mentorDao().deleteAll()
//                }
//
//                val prevKey = if (page == 1) null else page - 1
//
//                val nextKey = if (endOfPaginationReached) null else page + 1
//
//                val keys = storyData.map {
//                    RemoteKeys(
//                        id = it.id,
//                        prevKey = prevKey,
//                        nextKey = nextKey
//                    )
//                }
//
//                database.remoteKeysDao().insertAll(keys)
//                database.mentorDao().insertStory(storyData)
//            }
//            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//
//        } catch (exception: Exception) {
//            return  MediatorResult.Error(exception)
//        }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MentorEntity>): RemoteKeys? {
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { id ->
//                database.remoteKeysDao().getRemoteKeysId(id)
//            }
//        }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MentorEntity>): RemoteKeys? {
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
//            database.remoteKeysDao().getRemoteKeysId(data.id)
//        }
//    }
//
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MentorEntity>): RemoteKeys? {
//        return state.pages.lastOrNull{ it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
//            database.remoteKeysDao().getRemoteKeysId(data.id)
//        }
//    }
//
//    companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//}