package com.example.txl.redesign.activity

import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.txl.gankio.R
import com.example.txl.gankio.base.BaseActivity
import com.example.txl.redesign.utils.GlideUtils
import com.github.chrisbanes.photoview.OnPhotoTapListener
import com.github.chrisbanes.photoview.PhotoView

class BigImageActivity : BaseActivity(), OnPhotoTapListener, ViewPager.OnPageChangeListener {
    /**
     * 接收传过来的uri地址
     */
    private var imageList: List<String>? = null
    /**
     * 用于管理图片的滑动
     */
    private var veryImageViewpager: ViewPager? = null
    /**
     * 显示当前图片的页数
     */
    private var veryImageViewpagerText: TextView? = null

    private var page  = 0

    override fun onPhotoTap(view: ImageView?, x: Float, y: Float) {
       finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_image)
        initView()
        getIntentData()
    }

    private fun initView() {
        veryImageViewpagerText = findViewById(R.id.very_image_viewpager_text)
        veryImageViewpager = findViewById(R.id.very_image_viewpager)
    }

    private fun getIntentData() {
        val bundle = intent.extras
        if (bundle != null) {
            imageList = bundle.getStringArrayList("imageList")
        }
        val adapter = ViewPagerAdapter()
        veryImageViewpager?.adapter = adapter
        veryImageViewpager?.currentItem = 0
        veryImageViewpager?.addOnPageChangeListener(this)
        veryImageViewpager?.isEnabled = false
    }

    override fun onPageScrollStateChanged(p0: Int) {

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    override fun onPageSelected(p0: Int) {
        veryImageViewpagerText?.text = "${p0 + 1} / ${ imageList?.size}"
        page = p0
    }

    private inner class ViewPagerAdapter internal constructor() : PagerAdapter() {

        private val inflater: LayoutInflater = layoutInflater

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = inflater.inflate(R.layout.viewpager_very_image, container, false)
            val zoomImageView = view.findViewById<PhotoView>(R.id.zoom_image_view)
            val spinner = view.findViewById<ProgressBar>(R.id.loading)
            // 保存网络图片的路径
            val adapterImageEntity = getItem(position) as String
            val imageUrl: String
            imageUrl = adapterImageEntity

            spinner.visibility = View.VISIBLE
            spinner.isClickable = false
            GlideUtils.GlideUtilsBuilder()
                    .setContext(this@BigImageActivity)
                    .setUrl(imageUrl)
                    .setRequestListener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            spinner.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            val height = zoomImageView.height
                            val p = Point()
                            windowManager.defaultDisplay.getSize(p)
                            val wHeight = p.y
                                    if (height > wHeight) {
                                zoomImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            } else {
                                zoomImageView.scaleType = ImageView.ScaleType.FIT_CENTER
                            }
                            return false
                        }
                    })
                    .loadAsDrawable()
            zoomImageView.setOnPhotoTapListener(this@BigImageActivity)
            container.addView(view, 0)
            return view
        }

        override fun getCount(): Int {
            return if (imageList == null || imageList?.size == 0) {
                0
            } else imageList!!.size
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }

        private fun getItem(position: Int): Any {
            return imageList!![position]
        }
    }
}
