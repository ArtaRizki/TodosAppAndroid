package com.example.todosapp.ui.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todosapp.R
import com.example.todosapp.data.model.Todo
import com.example.todosapp.databinding.ItemTodoBinding

class TodoAdapter(
    private val onItemClick: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.apply {
                tvTitle.text = todo.title
                tvUserId.text = "User ID: ${todo.userId}"

                // Set status and color based on completion
                if (todo.completed) {
                    tvStatus.text = "Completed"
                    tvStatus.setTextColor(ContextCompat.getColor(root.context, android.R.color.holo_green_dark))
                    statusIndicator.setBackgroundColor(ContextCompat.getColor(root.context, android.R.color.holo_green_dark))
                } else {
                    tvStatus.text = "Pending"
                    tvStatus.setTextColor(ContextCompat.getColor(root.context, android.R.color.holo_orange_dark))
                    statusIndicator.setBackgroundColor(ContextCompat.getColor(root.context, android.R.color.holo_orange_dark))
                }

                root.setOnClickListener {
                    onItemClick(todo)
                }
            }
        }
    }

    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}