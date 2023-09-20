package com.thewind.community.detail.service

import com.home.baseapp.app.config.appHost
import com.thewind.community.recommend.model.DeleteCommentResponse
import com.thewind.community.recommend.model.PublishCommentResponse
import com.thewind.community.recommend.model.RecommendComment
import com.thewind.community.recommend.model.RecommendCommentResponse
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.utils.toObject
import com.thewind.network.HttpUtil.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2023/9/4 上午1:18
 * @description:
 */
object DetailPageService {

    suspend fun requestPosterDetail(posterId: Long) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/recommend/poster/detail?posterId=$posterId").toObject(RecommendPoster::class.java)
                ?.let {
                    return@withContext it
                }
        }
        return@withContext null
    }

    suspend fun publishComment(posterId: Long, content: String, parentId: Long = -1) =
        withContext(Dispatchers.IO) {
            runCatching {
                val parentParam = if (parentId == -1L) "" else "&parentId=$parentId"
                get("$appHost/recommend/comment/publish?posterId=$posterId&content=$content$parentParam")
                    .toObject(PublishCommentResponse::class.java)?.let {
                        return@withContext it.data
                    }
            }
            return@withContext null
        }

    suspend fun requestComments(posterId: Long = -1) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/recommend/comments?posterId=$posterId")
                .toObject(RecommendCommentResponse::class.java)?.let {
                    return@withContext it.data ?: emptyList<RecommendComment>()
                }
        }
        return@withContext emptyList()
    }


    suspend fun deleteComment(commentId: Long) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/recommend/poster/delete?commentId=$commentId")
                .takeIf { it.isNotBlank() }
                .toObject(DeleteCommentResponse::class.java)?.let {
                    return@withContext it.code == 0
                }
        }
        return@withContext false
    }

}