package com.yascode.testapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.yascode.testapp.R
import com.yascode.testapp.data.local.Content
import com.yascode.testapp.utils.loadImg
import kotlinx.android.synthetic.main.item_content.view.*

/**
 * Created by caksono21 on 29/11/17.
 */
class ContentAdapter internal constructor(private var Contents: List<Content>?, private val listener: setOnItemClickListener) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(parent)
    }

    override fun getItemCount(): Int {
        val size = Contents?.size ?: 0
        return size
    }

    override fun onBindViewHolder(holder: ContentViewHolder?, position: Int) {
        Contents?.get(position)?.let { holder?.bind(it, listener) }
    }

    fun setContents(contents: List<Content>) {
        Contents = contents
        notifyDataSetChanged()
    }

    class ContentViewHolder internal constructor(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_content, parent, false)) {

        private val imgContent: ImageView
        private val txtSummary: TextView

        init {
            imgContent = itemView.img_content
            txtSummary = itemView.txt_summary
        }

        internal fun bind(content: Content, listener: setOnItemClickListener) {
            itemView.setTag(R.id.tag_content, content)

            txtSummary.text = content.summary
            imgContent.loadImg(content.thumbnail_url)

            itemView.setOnClickListener { view ->
                listener.onItemClick(content.id)
            }
        }
    }

    interface setOnItemClickListener {
        fun onItemClick(id: Int)
    }
}