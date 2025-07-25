package com.example.todosapp.ui.tododetail

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.todosapp.data.model.Todo
import com.example.todosapp.databinding.ActivityTodoDetailBinding

class TodoDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TODO = "extra_todo"
    }

    private lateinit var binding: ActivityTodoDetailBinding
    private val viewModel: TodoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        val todo: Todo? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TODO, Todo::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_TODO)
        }

        todo?.let {
            viewModel.setTodo(it)
        }

        observeViewModel()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Yuliarta Rizki Nusantoko"
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    private fun observeViewModel() {
        viewModel.todo.observe(this) { todo ->
            binding.apply {
                tvTitle.text = todo.title
                tvTodoId.text = "Todo ID: ${todo.id}"
                tvUserId.text = "User ID: ${todo.userId}"

                // Set status and styling based on completion
                if (todo.completed) {
                    tvStatus.text = "Status: Completed"
                    tvStatus.setTextColor(
                        ContextCompat.getColor(
                            this@TodoDetailActivity,
                            android.R.color.holo_green_dark
                        )
                    )
                    statusCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@TodoDetailActivity,
                            android.R.color.holo_green_light
                        )
                    )
                    statusIcon.text = "✓"
                    statusIcon.setTextColor(
                        ContextCompat.getColor(
                            this@TodoDetailActivity,
                            android.R.color.holo_green_dark
                        )
                    )
                } else {
                    tvStatus.text = "Status: Pending"
                    tvStatus.setTextColor(
                        ContextCompat.getColor(
                            this@TodoDetailActivity,
                            android.R.color.holo_orange_dark
                        )
                    )
                    statusCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@TodoDetailActivity,
                            android.R.color.holo_orange_light
                        )
                    )
                    statusIcon.text = "⏳"
                    statusIcon.setTextColor(
                        ContextCompat.getColor(
                            this@TodoDetailActivity,
                            android.R.color.holo_orange_dark
                        )
                    )
                }
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}