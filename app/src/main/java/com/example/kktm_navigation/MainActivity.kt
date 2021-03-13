package com.example.kktm_navigation

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.chrisbanes.photoview.PhotoView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var spinner_from_id : String? = null
    var spinner_to_id : String? = null

    var select_from = 0
    var select_to = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner_from : Spinner = findViewById(R.id.spinner_from)
        val spinner_to : Spinner = findViewById(R.id.spinner_to)
        val layLogout: ConstraintLayout = findViewById(R.id.layLogout)
        spinner_from.onItemSelectedListener = this
        spinner_to.onItemSelectedListener = this
        spinner_from_id = spinner_from.id.toString()
        spinner_to_id = spinner_to.id.toString()
        layLogout.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }

        ArrayAdapter.createFromResource(
                this,
                R.array.from_arrays,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_from.adapter = adapter
        }

        ArrayAdapter.createFromResource(
                this,
                R.array.destination_arrays,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_to.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val photoView : PhotoView = findViewById(R.id.photo_view)
        // check which spinner selected
        if (parent!!.id.toString() == spinner_from_id) {
            select_from = position
//            d("bomoh", "${parent!!.getItemAtPosition(position)}, id: ${parent.id}, position: $position")
        } else {
            select_to = position+1
//            d("bomoh", "${parent!!.getItemAtPosition(position)}, id: ${parent.id}, position: $select_to")
        }

        if (select_from > 0 && select_to > 0) {
            d("bomoh", "selection satisfied")
            val nav = "nav_${select_from}_${select_to}"
            val id = resources.getIdentifier(nav, "drawable", packageName)

            photoView.setImageResource(id)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}