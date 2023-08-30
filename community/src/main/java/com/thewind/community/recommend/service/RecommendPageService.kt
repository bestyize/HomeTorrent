package com.thewind.community.recommend.service

import com.home.baseapp.app.config.appHost
import com.home.baseapp.app.toast.toast
import com.thewind.community.recommend.model.DeleteCommentResponse
import com.thewind.community.recommend.model.DeletePosterResponse
import com.thewind.community.recommend.model.PublishCommentResponse
import com.thewind.community.recommend.model.PublishPosterResponse
import com.thewind.community.recommend.model.RecommendComment
import com.thewind.community.recommend.model.RecommendCommentResponse
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.community.recommend.model.RecommendPosterResponse
import com.thewind.community.util.toObject
import com.thewind.network.HttpUtil.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2023/8/31 上午12:42
 * @description:
 */
object RecommendPageService {

    suspend fun requestRecommendFeeds(page: Int = 0) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/recommend/posters?page=$page").takeIf { it.isNotBlank() }
                .toObject(RecommendPosterResponse::class.java)?.let {
                    return@withContext it.data ?: emptyList<RecommendPoster>()
                }
        }
        return@withContext emptyList()
    }

    suspend fun requestComments(posterId: Long = -1) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/recommend/comments?posterId=$posterId").takeIf { it.isNotBlank() }
                .toObject(RecommendCommentResponse::class.java)?.let {
                    return@withContext it.data ?: emptyList<RecommendComment>()
                }
        }
        return@withContext emptyList()
    }


    suspend fun publishPoster(title: String = "", content: String) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/recommend/poster/publish?title=$title&content=$content").takeIf { it.isNotBlank() }
                .toObject(PublishPosterResponse::class.java)?.let {
                    return@withContext it.data
                }
        }
        return@withContext null
    }

    suspend fun publishComment(posterId: Long, content: String, parentId: Long = -1) =
        withContext(Dispatchers.IO) {
            runCatching {
                get("$appHost/recommend/comment/publish?posterId=$posterId&content=$content&parentId=$parentId").takeIf { it.isNotBlank() }
                    .toObject(PublishCommentResponse::class.java)?.let {
                        return@withContext it.data
                    }
            }
            return@withContext null
        }

    suspend fun deletePoster(posterId: Long) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/recommend/poster/delete?posterId=$posterId").takeIf { it.isNotBlank() }
                .toObject(DeletePosterResponse::class.java)?.let {
                    toast(it.message)
                    return@withContext it.code == 0
                }
        }
        return@withContext false
    }

    suspend fun deleteComment(commentId: Long) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/recommend/poster/delete?commentId=$commentId").takeIf { it.isNotBlank() }
                .toObject(DeleteCommentResponse::class.java)?.let {
                    toast(it.message)
                    return@withContext it.code == 0
                }
        }
        return@withContext false
    }


}