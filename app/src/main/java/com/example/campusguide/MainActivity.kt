package com.example.campusguide
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocationAdapter
    private lateinit var addPlaceButton: FloatingActionButton
    private var campusLocations: MutableList<CampusLocation> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.location_recycler_view)
        addPlaceButton = findViewById(R.id.add_place_fab)

        setupRecyclerView()

        addPlaceButton.setOnClickListener {
            // Launch Activity 3 (Add Location)
            val intent = Intent(this, AddLocationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload locations every time the activity resumes (after adding a new place)
        loadLocations()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LocationAdapter(campusLocations) { location ->
            // Handle item click: Launch Activity 2 (Map Tracking)
            val intent = Intent(this, MapTrackingActivity::class.java).apply {
                putExtra("DESTINATION_LAT", location.latitude)
                putExtra("DESTINATION_LNG", location.longitude)
                putExtra("DESTINATION_NAME", location.name)
            }

            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun loadLocations() {
        // Load data using the utility object
        campusLocations.clear()
        campusLocations.addAll(LocationStorage.getLocations(this))
        adapter.notifyDataSetChanged()
    }

    // --- RecyclerView Adapter and ViewHolder ---

    class LocationAdapter(
        private val locations: List<CampusLocation>,
        private val onClick: (CampusLocation) -> Unit
    ) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_location, parent, false)
            return LocationViewHolder(view)
        }

        override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
            val location = locations[position]
            holder.bind(location, onClick)
        }

        override fun getItemCount() = locations.size

        class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nameText: TextView = itemView.findViewById(R.id.location_name)
            private val typeText: TextView = itemView.findViewById(R.id.location_type)

            fun bind(location: CampusLocation, onClick: (CampusLocation) -> Unit) {
                nameText.text = location.name
                typeText.text = location.type
                itemView.setOnClickListener { onClick(location) }
            }
        }
    }
}