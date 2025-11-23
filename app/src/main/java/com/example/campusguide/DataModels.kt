package com.example.campusguide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.Context
import android.preference.PreferenceManager

// Data class for a location, using standard double for coordinates
data class CampusLocation(
    val name: String,
    val type: String, // e.g., "Class", "Hostel", "Office", or "User Added"
    val latitude: Double,
    val longitude: Double
) : java.io.Serializable

object LocationStorage {
    private const val PREFS_KEY = "CAMPUS_LOCATIONS_LIST"
    private val GSON = Gson()

    /**
     * Retrieves the saved list of locations from SharedPreferences.
     * Includes a set of default locations if none are saved.
     */
    fun getLocations(context: Context): MutableList<CampusLocation> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val json = prefs.getString(PREFS_KEY, null)

        val defaultLocations = mutableListOf(
            CampusLocation("Physics Lab", "Laboratory", -14.5472, 35.1785),
            CampusLocation("Chemistry Lab", "Laboratory", -14.5472, 35.1785),
            CampusLocation("Biology Lab", "Laboratory", -14.5474018, 35.1785381),
            CampusLocation("Library", "Library", -14.5473329, 35.1784281),
            CampusLocation("Department Of Management And Commerce", "Office", -14.5479154, 35.1786227),
            CampusLocation("Department Of CS and IT", "Office", -14.5479143, 35.1786158),
            // Add more default locations here
            CampusLocation("Computer Lab", "Laboratory", -14.5472405, 35.1784556),
            CampusLocation("Main Lecture Hall", "Lecture Hall", -14.5472, 35.1785),
            CampusLocation("Hardware Lab", "Laboratory", -14.5467, 35.1788),
            CampusLocation("Administration Office", "Office", -14.5469765, 35.1795255),
            CampusLocation("Chapel", "Other", -14.5474826, 35.1796476)
        )

        return if (json == null) {
            // Save defaults on first run
            saveLocations(context, defaultLocations)
            defaultLocations
        } else {
            val type = object : TypeToken<MutableList<CampusLocation>>() {}.type
            GSON.fromJson<MutableList<CampusLocation>>(json, type) ?: defaultLocations
        }
    }

    /**
     * Saves the current list of locations to SharedPreferences.
     */
    fun saveLocations(context: Context, locations: MutableList<CampusLocation>) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val json = GSON.toJson(locations)
        prefs.edit().putString(PREFS_KEY, json).apply()
    }
}