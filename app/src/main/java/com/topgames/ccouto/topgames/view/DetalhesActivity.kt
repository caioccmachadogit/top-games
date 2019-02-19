package com.topgames.ccouto.topgames.view

import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.topgames.ccouto.topgames.R
import com.topgames.ccouto.topgames.base.BaseActivity
import com.topgames.ccouto.topgames.base.Constants
import com.topgames.ccouto.topgames.domain.Top
import com.topgames.ccouto.topgames.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_detalhes.*
import java.lang.Exception

/**
 * Created by ccouto on 19/02/2019.
 */
class DetalhesActivity : BaseActivity() {

    private val mTop by lazy { intent.getSerializableExtra(Constants.PAR_TOP) as Top }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        loadView()
    }

    private fun loadView() {

        mTop.game?.apply {
            ImageUtil.loadImage(box.large, context, false).listener(object : RequestListener<String, GlideDrawable> {
                override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                    carregando.visibility = View.INVISIBLE
                    return false
                }

                override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                    carregando.visibility = View.INVISIBLE
                    return false
                }
            }).into(imagePoster)

            txtName.text = name
        }

        channel.text = "Canal: "+mTop.channels
        viewers.text = "Visualizações: "+mTop.viewers

    }
}