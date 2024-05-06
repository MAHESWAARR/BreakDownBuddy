package com.example.androidproject_1

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.Calendar

class HomeScreen : AppCompatActivity(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var calen : Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent : PendingIntent



    var presentDay = 0
    var presentMonth = 0
    var presentyear = 0
    var presentMin =0
    var presentHour =0


    var selectedDay = 0
    var selectedMonth = 0
    var selectedyear = 0
    var selectedHour =0
    var selectedMin =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Initialize DrawerLayout, NavigationView, and ActionBarDrawerToggle
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, findViewById(R.id.toolBar), R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        calen = Calendar.getInstance()
        createNotificationChannel()
        // Set the NavigationView's item click listener
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profilenav-> { /* Handle home click */ }
                R.id.ratingnav -> {
                    startActivity(Intent(this,RatingBarScreen::class.java))
                }
                R.id.checkalarmnav -> {
                    getCurrentDate()
                    DatePickerDialog(this, this, presentyear,presentMonth,presentDay).show()


                }
                R.id.item4 -> {
                    startActivity(Intent(this,TabHost::class.java))
                }
                // Add more cases for other menu items
            }
            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Set up the toolbar
        val toolbar: MaterialToolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        val HomeScreenFragment = HomeScreenFragment()
        val InstructionScreenFragment = InstructionScreenFragment()
        val locationsScreenFragment = LocationsScreenFragment()

        setCurrentFragment(HomeScreenFragment)

        // Set the BottomNavigationView's item click listener
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView3)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(HomeScreenFragment())
                R.id.instruction -> setCurrentFragment(InstructionScreenFragment())
                R.id.geolocations -> setCurrentFragment(LocationFragmentScreen())
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            R.id.Profile -> {
                Toast.makeText(this, "Your Profile", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.Login -> {
                Toast.makeText(this, "Login screen", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginScreen::class.java))
                finish()
                return true
            }
            R.id.Logout -> {
                Toast.makeText(this, "Logged out Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.accswitch -> {
                Toast.makeText(this, "Account Switching", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AccountSwitching::class.java))
                return true
            }
            // Add cases for other menu items if needed
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout_container, fragment)
            commit()
        }


    fun getCurrentDate(){
        var calender = Calendar.getInstance()
        presentDay = calender.get(Calendar.DAY_OF_MONTH)
        presentMonth = calender.get(Calendar.MONTH)
        presentyear = calender.get(Calendar.YEAR)
    }
    fun getCurrentTime(){
        var calender = Calendar.getInstance()
        presentHour = calender.get(Calendar.HOUR_OF_DAY)
        presentMin = calender.get(Calendar.MINUTE)
    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        selectedyear = year
        selectedMonth = month
        selectedDay = day
        getCurrentTime()
        TimePickerDialog(this, this, presentHour, presentMin , false).show()
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, min: Int) {
        selectedHour = hour
        selectedMin = min

        calen.apply {
            set(Calendar.YEAR, selectedyear)
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, selectedDay)
            set(Calendar.HOUR_OF_DAY, selectedHour)
            set(Calendar.MINUTE, selectedMin)
            set(Calendar.SECOND, 0)
        }

        setAlarm()
    }


    private fun setAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReciver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 123, intent,
            PendingIntent.FLAG_IMMUTABLE)
        var currentMill = System.currentTimeMillis()
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calen.timeInMillis,
            pendingIntent
        )

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calen.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "checkalarm"
            val description = "Check For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("checkalarm", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }

    }
}