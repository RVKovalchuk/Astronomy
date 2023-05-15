package com.example.astronomy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdapterFor<T : Any, B : ViewBinding>(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val inflating: (LayoutInflater, ViewGroup?, Boolean) -> B,
    private val bind: Holder<T, B>.() -> Unit,
    equals: (T, T) -> Boolean
) : ListAdapter<T, AdapterFor.Holder<T, B>>(DiffCallBack(equals)) {

    private val mutableFirstVisibleItem = MutableStateFlow(0)
    val firstVisibleItem = mutableFirstVisibleItem.asStateFlow()

    private val mutableOffset = MutableStateFlow(Pair(0, 0))
    val offset = mutableOffset.asStateFlow()

    private val scrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            mutableFirstVisibleItem.value = when (val manager = recyclerView.layoutManager) {
                is LinearLayoutManager -> manager.findFirstVisibleItemPosition()
                else -> 0
            }
        }
    }

    private val mutableCurrentList: MutableStateFlow<List<T>> = MutableStateFlow(getCurrentList())
    val currentList = mutableCurrentList.asStateFlow()

    class Holder<T : Any, B : ViewBinding>(
        val binding: B,
        lifecycleScope: LifecycleCoroutineScope,
        private val bind: Holder<T, B>.() -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var job: Job? = null
        val scope = HolderScope(lifecycleScope = lifecycleScope) { job }
        private var mutableContent: T? = null
        val content: T get() = mutableContent!!

        fun bind(item: T) {
            mutableContent = item
            job?.cancel()
            job = Job()
            bind.invoke(this)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<T, B> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflating(inflater, parent, false)
        return Holder(binding, lifecycleScope, bind)
    }

    override fun onBindViewHolder(holder: Holder<T, B>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(scrollListener)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCurrentListChanged(previousList: MutableList<T>, currentList: MutableList<T>) {
        mutableCurrentList.value = currentList
        super.onCurrentListChanged(previousList, currentList)
    }

    private class DiffCallBack<T : Any>(private val equals: (T, T) -> Boolean) :
        DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return equals(oldItem, newItem)
        }
    }

    class HolderScope(
        private val lifecycleScope: LifecycleCoroutineScope,
        private val job: () -> Job?
    ) {
        fun launch(suspend: suspend HolderScope.() -> Unit) {
            val job = job.invoke() ?: return
            lifecycleScope.launch(job) {
                lifecycleScope.launchWhenStarted {
                    suspend.invoke(this@HolderScope)
                }
            }
        }

        suspend fun <T> Flow<T>.collect(collect: suspend HolderScope.(T) -> Unit) {
            val job = job.invoke() ?: return
            lifecycleScope.launch(job) {
                this@collect.collect {
                    lifecycleScope.launchWhenStarted {
                        collect.invoke(this@HolderScope, it)
                    }
                }
            }
        }
    }
}


