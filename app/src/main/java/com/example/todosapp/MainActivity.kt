package com.example.todosapp

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todosapp.data.model.Todo
import com.example.todosapp.databinding.ActivityMainBinding
import com.example.todosapp.ui.tododetail.TodoDetailActivity
import com.example.todosapp.ui.todolist.TodoAdapter
import com.example.todosapp.ui.todolist.TodoListViewModel
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TodoListViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.navigationBarColor = Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false
        setupRecyclerView()
        observeViewModel()
        setupRetryButton()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter { todo ->
            navigateToDetail(todo)
        }

        binding.recyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.todos.observe(this) { todos ->
            todoAdapter.submitList(todos)
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
                binding.btnRetry.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE
            }
        }
    }

    private fun setupRetryButton() {
        binding.btnRetry.setOnClickListener {
            viewModel.retry()
        }
    }

    private fun navigateToDetail(todo: Todo) {
        val intent = Intent(this, TodoDetailActivity::class.java).apply {
            putExtra(TodoDetailActivity.EXTRA_TODO, todo)
        }
        startActivity(intent)
    }
}