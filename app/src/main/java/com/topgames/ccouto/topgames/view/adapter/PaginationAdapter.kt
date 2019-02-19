package com.topgames.ccouto.topgames.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.topgames.ccouto.topgames.R
import com.topgames.ccouto.topgames.domain.Top
import com.topgames.ccouto.topgames.extensions.inflate
import com.topgames.ccouto.topgames.utils.ImageUtil
import com.topgames.ccouto.topgames.utils.PaginationAdapterCallback
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_progress.view.*
import java.lang.Exception

/**
 * Created by ccouto on 19/02/2019.
 */
class PaginationAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // View Types
    private val ITEM = 0

    private val LOADING = 1
    private var listTop: MutableList<Top> = ArrayList()

    private var isLoadingAdded = false

    private var retryPageLoad = false
    private var errorMsg: String? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        when(p1){
            ITEM ->{
                return GameViewHolder(p0)
            }
            LOADING -> {
                return LoadingViewHolder(p0)
            }
            else ->
                return LoadingViewHolder(p0)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val top: Top = listTop[position]

        when(getItemViewType(position)){
            ITEM ->{
                holder as GameViewHolder
                holder.bind(top)
            }
            LOADING->{
                holder as LoadingViewHolder
                holder.bind()
            }
        }
    }

    private val mCallback: PaginationAdapterCallback = context as PaginationAdapterCallback

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

        fun bind(top: Top) {
            top.game?.apply {
                mName.text = name
                ImageUtil.loadImage(box.large, context, true).listener(object : RequestListener<String, GlideDrawable>{
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        mGameProgress.visibility = View.GONE
                        return false
                    }

                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        mGameProgress.visibility = View.GONE
                        return false
                    }
                }).into(mGamePoster)

                super.itemView.setOnClickListener {
                    Log.d("itemView", "onclick")
                    mCallback.onClickItem(top)
                }
            }
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

    private fun getItem(position: Int): Top {
        return listTop[position]
    }

    fun showRetry(show: Boolean, error: String?) {
        retryPageLoad = show
        notifyItemChanged(listTop.size - 1)

        this.errorMsg = error ?: errorMsg
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = listTop.size - 1
        getItem(position).let {
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
}
