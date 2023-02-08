package com.sunny.shakelog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class LogActivity : AppCompatActivity() {
    val recyclerView: RecyclerView by lazy { findViewById(R.id.shakeLogRecyclerView) }
    private var log: ArrayList<LogItem> = arrayListOf()
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.shake_log_theme)
        setContentView(R.layout.shake_log_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Shake Log"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, android.R.color.black)))
        intent.extras?.getParcelableArrayList<LogItem>(SHAKE_LOG)?.let {
            log = it
        }
        recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter = LogAdapter(log)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    internal class LogAdapter(private val log: ArrayList<LogItem>) :
        RecyclerView.Adapter<LogAdapter.ViewHolder>() {
        var formatter: SimpleDateFormat = SimpleDateFormat(
            "h:mm a", Locale.getDefault()
        )

        internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val key: TextView
            val value: TextView
            val time: TextView
            init {
                key = view.findViewById(R.id.shakeLogKey)
                value = view.findViewById(R.id.shakeLogValue)
                time = view.findViewById(R.id.shakeLogTime)
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.shake_log_item, viewGroup, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.key.text = log[position].key
            viewHolder.value.text = log[position].value
            viewHolder.time.text = formatter.format(Date(log[position].unixTime))
        }

        override fun getItemCount() = log.size

    }

}