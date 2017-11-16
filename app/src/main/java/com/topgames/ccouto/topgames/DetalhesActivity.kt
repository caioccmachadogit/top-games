package com.topgames.ccouto.topgames

import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.topgames.ccouto.topgames.domain.Top
import com.topgames.ccouto.topgames.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_detalhes.*
import java.lang.Exception

/**
 * Created by ccouto on 15/11/2017.
 */
class DetalhesActivity : BaseActivity() {

    val mTop by lazy { intent.getSerializableExtra("top") as Top }

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