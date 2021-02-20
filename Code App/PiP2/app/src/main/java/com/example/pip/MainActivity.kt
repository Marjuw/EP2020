package com.example.pip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.REST_API_Client.*



class MainActivity : AppCompatActivity() {

    val loggedinUserID: String="a87c4718-4004-4851-92f6-081c87aa4643"  //BeispielID zum durchführen der Anfragen, da wir kein Login haben. Dies ist der Login ersatz, da wir die ID ständig vergleichen

    // Liste aller Tags holen und als String speichern
    val getListOfTagsString = okHttpGet(ressource_tags)

    // Wert der die Liste aller Tags in einem Array vom Typ One_Tag enthält
    var tagListe: List<One_Tag> = gson.fromJson(getListOfTagsString, Array<One_Tag>::class.java).toList()


    override fun onCreate(savedInstanceState: Bundle?) {

        // Ausgabe aller Nutzer mit Nicknamen
        Log.d("HTTP-Request", "Liste aller Tags:")
        for (tag in tagListe) {
            Log.d("HTTP-Request","Tag: ${tag.bezeichnung}")
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationview)
        val navController = findNavController(R.id.fragment)

        // val appBarConfiguration = AppBarConfiguration(setOf(R.id.firstFragment, R.id.secondFragment, R.id.thirdFragment, R.id.fourthFragment, R.id.fithFragment))

        // appBarConfiguration wird momentan nicht gebrucht da wir nicht die von "setupActionBarWithNavController"
        // automatisch generierte Actionbar auf jedes Fragment übergeben müssen. Stattdessen haben wir nun eine Toolbar.xml implementiert
        // und diese manuell in jedes XML Fragment verknüpft.

        setSupportActionBar(findViewById(R.id.toolbar2))

        //setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)




        /*   var fiveFragment :FiveFragment= FiveFragment()
            var fm: FragmentTransaction = supportFragmentManager.beginTransaction()
            fm.add(R.id.fithFragment,fiveFragment)
            fm.commit()

        //val value = intent.getStringExtra("key")
        val s="Hiiiii"
        //  if(value != null) {
              val bundle = Bundle()
              bundle.putString("key", s)
              val ob = FiveFragment()
              ob.setArguments(bundle)
              getSupportFragmentManager().beginTransaction().replace(android.R.id.content, ob).addToBackStack(null).commit()

*/

    }
}