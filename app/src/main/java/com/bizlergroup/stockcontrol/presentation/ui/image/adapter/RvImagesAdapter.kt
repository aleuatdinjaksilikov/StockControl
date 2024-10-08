package com.bizlergroup.stockcontrol.presentation.ui.image.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bizlergroup.stockcontrol.data.models.ImageResponseData
import com.bizlergroup.stockcontrol.databinding.RcItemImagesBinding


class RvImagesAdapter:ListAdapter<ImageResponseData,RvImagesAdapter.RvImagesVH>(diffUtil) {

    private var onClickImageListener: ((ImageResponseData) -> Unit)? = null
    fun onClickImage(block: (ImageResponseData) -> Unit) {
        onClickImageListener = block
    }

    inner class RvImagesVH(private val binding : RcItemImagesBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(position:Int){
            val p = getItem(position)

            Log.d("asdaf",p.imageUrl)

            Glide.with(binding.root)
                .load(p.imageUrl)
                .into(binding.ivPhoto)

//            Glide.with(binding.root).load(p.imageUrl)
//                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
//                .error(R.drawable.logo_box)
//                .listener(object : RequestListener<Drawable?> {
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        resource: Drawable?,
//                        model: Any?,
//                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        Glide.with(binding.root).load(p.imageUrl).into(binding.ivPhoto)
//                        return false
//                    }
//                }).into(binding.ivPhoto)

//            Glide.with(binding.root)
//                .load("data:image/jpeg;base64,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")
//                .into(binding.ivPhoto)

//            Picasso.get().load(p.imageUrl).into(binding.ivPhoto)

            binding.root.setOnClickListener {
                onClickImageListener?.invoke(p)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvImagesVH {
        return RvImagesVH(
            RcItemImagesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RvImagesVH, position: Int) {
        holder.setData(position)
    }

    private object diffUtil : DiffUtil.ItemCallback<ImageResponseData>() {
        override fun areItemsTheSame(oldItem: ImageResponseData, newItem: ImageResponseData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ImageResponseData, newItem: ImageResponseData): Boolean {
            return oldItem.id == newItem.id
        }

    }
}