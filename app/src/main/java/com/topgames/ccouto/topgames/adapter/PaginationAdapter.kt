package com.topgames.ccouto.topgames.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.topgames.ccouto.topgames.R
import com.topgames.ccouto.topgames.domain.Game
import com.topgames.ccouto.topgames.domain.Top
import com.topgames.ccouto.topgames.extensions.inflate
import com.topgames.ccouto.topgames.utils.PaginationAdapterCallback
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_progress.view.*
import java.lang.Exception

/**
 * Created by ccouto on 15/11/2017.
 */
class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // View Types
    private val ITEM = 0
    private val LOADING = 1

    private var listTop: MutableList<Top>
    private val context: Context

    private var isLoadingAdded = false
    private var retryPageLoad = false

    private var errorMsg: String? = null

    private val mCallback: PaginationAdapterCallback

    constructor(context: Context) : super() {
        this.context = context
        this.mCallback = context as PaginationAdapterCallback
        this.listTop = ArrayList<Top>()
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            ITEM ->{
                return GameViewHolder(parent!!)
            }
            LOADING -> {
                return LoadingViewHolder(parent!!)
            }
            else ->
                return LoadingViewHolder(parent!!)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val top: Top = listTop.get(position)

        when(getItemViewType(position)){
            ITEM ->{
                holder as GameViewHolder
                holder.bind(top.game!!)
            }
            LOADING->{
                holder as LoadingViewHolder
                holder.bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == listTop.size-1 && isLoadingAdded)
            return LOADING

        return ITEM
    }

    override fun getItemCount(): Int = listTop.size

    private inner class GameViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_list)){

        private val mName =  itemView.name
        private val mGamePoster =  itemView.game_poster
        private val mGameProgress =  itemView.game_progress

        fun bind(item: Game) {
            mName.text = item.name
            loadImage(item.box.large).listener(object : RequestListener<String, GlideDrawable>{
                override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                    mGameProgress.visibility = View.GONE
                    return false
                }

                override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                    mGameProgress.visibility = View.GONE
                    return false
                }
            }).into(mGamePoster)

            super.itemView.setOnClickListener { Log.d("itemView", "onclick")}
        }
    }

    private inner class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_progress)), View.OnClickListener{

        val mProgressBar = itemView.loadmore_progress
        val mRetryBtn = itemView.loadmore_retry
        val mErrorTxt = itemView.loadmore_errortxt
        val mErrorLayout = itemView.loadmore_errorlayout

        init {
            mRetryBtn.setOnClickListener(this)
            mErrorLayout.setOnClickListener(this)
        }

        fun bind(){
            if(retryPageLoad){
                mErrorLayout.visibility = View.VISIBLE
                mProgressBar.visibility = View.GONE
                mErrorTxt.text = errorMsg ?: "erro nao encontrado!"
            }
            else{
                mErrorLayout.visibility = View.GONE
                mProgressBar.visibility = View.VISIBLE
            }
        }

        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.loadmore_retry, R.id.loadmore_errorlayout -> {
                    showRetry(false, null)
                    mCallback.retryPageLoad()
                }
            }
        }
    }

    fun getItem(position: Int): Top {
        return listTop.get(position)
    }

    fun showRetry(show: Boolean, error: String?) {
        retryPageLoad = show
        notifyItemChanged(listTop.size - 1)

        this.errorMsg = error ?: errorMsg
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = listTop.size - 1
        val result = getItem(position)

        if (result != null) {
            listTop.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addAll(list: List<Top>) {
        for (item in list) {
            add(item)
        }
    }

    fun add(t: Top) {
        listTop.add(t)
        notifyItemInserted(listTop.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Top(null,"",""))
    }

    private fun loadImage(posterPath: String): DrawableRequestBuilder<String> {
        return Glide
                .with(context)
                .load(posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .crossFade()
    }
}
