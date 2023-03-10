package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    // Так как используем List Adapter вместо RecyclerView то данные ниже больше не нужны!!!
//    var shopList = listOf<ShopItem>()
//        set(value) {
//            val callBack = ShopListDiffCallBack(shopList,value)
//            val diffResult = DiffUtil.calculateDiff(callBack)//Вычисляет разницу между старыми и новым списками
//            diffResult.dispatchUpdatesTo(this)
//            field = value}



    var onShopItemLongClickListener: ((ShopItem) ->Unit)? = null // лямбда функция, паринимает ShopItem и ничего не возвращает
    //изночально кладем null
    var onShopItemClickListener:((ShopItem)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when(viewType){
            VIEW_TYPE_DISABLED ->R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw java.lang.RuntimeException("Unknown view type: $viewType")
        }
        val view =
            LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem) //методом invike вызываем лямбда функцию
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
            Log.d("RV","Позиция номер: ${shopItem.id}")
        }

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
    }


//    Данный метод, больше не нужен!!!
//    override fun getItemCount(): Int {
//        return shopList.size
//    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(item.enabled){
            VIEW_TYPE_ENABLED
        }else VIEW_TYPE_DISABLED
    }

        companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 15
    }
}
