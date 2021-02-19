package com.example.pip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.REST_API_Client.*
import com.google.gson.GsonBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FourthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FourthFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("sdsdsd","sdfff")

        // Inflate the layout for this fragment
        var v: View= inflater.inflate(com.example.pip.R.layout.activity_teams, container, false)

        var showprojects: TextView = v.findViewById(com.example.pip.R.id.my_projects)   //Button Projekte dynamisch anzeigen lassen
        var projectslayout: LinearLayout =v.findViewById(com.example.pip.R.id.projects)  //Um Layout für Projekte anzusprechen
//      var projectmitgliederlayout: LinearLayout= v.findViewById(com.example.pip.R.id.projectmitglieder)  //Um Layout für Mitglieder anzusprechen

        showprojects.setOnClickListener(  View.OnClickListener {

            var projectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects_full,null, false)  //erstes Project erzeugen
            var mitgliederlayout: LinearLayout =projectsview.findViewById(com.example.pip.R.id.projectmitglieder)  //zum hinzufügen von Mitglieder zu Projekt 1

            var projectsview2: View= layoutInflater.inflate(com.example.pip.R.layout.projects_full,null, false)  //zweites Project erzeugen
            var mitgliederlayout2: LinearLayout =projectsview2.findViewById(com.example.pip.R.id.projectmitglieder)  //zum hinzufügen von Mitglieder zu Projekt 2

            var projectmitglied: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //ProjektMitglieder erzeugen
            var mitgliedsname: TextView= projectmitglied.findViewById(R.id.personname)  //Beispiel 1 MitgliedsName ändern
            mitgliedsname.setText("Uweeeee")

            var projectmitglied2: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //zweites ProjektMitglieder erzeugen
            var mitgliedsname2: TextView= projectmitglied2.findViewById(R.id.personname)  //Beispiel 2 MitgliedsName ändern
            mitgliedsname2.setText("Peteeeeer")

            var projectmitglied3: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //drittes ProjektMitglieder erzeugen
            var mitgliedsname3: TextView= projectmitglied2.findViewById(R.id.personname)  //Beispiel 2 MitgliedsName ändern
            mitgliedsname3.setText("Jürgeeeen")

            var projectmitglied4: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //drittes ProjektMitglieder erzeugen
            var mitgliedsname4: TextView= projectmitglied2.findViewById(R.id.personname)  //Beispiel 2 MitgliedsName ändern
            mitgliedsname4.setText("Jürgeeeen")

            var projectmitglied5: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //drittes ProjektMitglieder erzeugen
            var mitgliedsname5: TextView= projectmitglied2.findViewById(R.id.personname)  //Beispiel 2 MitgliedsName ändern
            mitgliedsname5.setText("Jürgeeeen")

            var projectmitglied6: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //drittes ProjektMitglieder erzeugen
            var mitgliedsname6: TextView= projectmitglied2.findViewById(R.id.personname)  //Beispiel 2 MitgliedsName ändern
            mitgliedsname6.setText("Jürgeeeen")



            projectslayout.addView(projectsview)  //Project1 erzeugen in diesem Bereich der LayoutListe
            mitgliederlayout.addView(projectmitglied)  //erstes Teammitglied dem Projekt 1 hinzufügen
            mitgliederlayout.addView(projectmitglied2) //zweites Teammitglied dem Projekt 1 hinzufügen
            mitgliederlayout.addView(projectmitglied3)  //erstes Teammitglied dem Projekt 1 hinzufügen
            mitgliederlayout.addView(projectmitglied4) //zweites Teammitglied dem Projekt 1 hinzufügen
            mitgliederlayout.addView(projectmitglied5)  //erstes Teammitglied dem Projekt 1 hinzufügen
            mitgliederlayout.addView(projectmitglied6) //zweites Teammitglied dem Projekt 1 hinzufügen


//            projectslayout.addView(projectsview2)  //Projects erzeugen in diesem Bereich der LayoutListe
//            mitgliederlayout2.addView(projectmitglied3)  //erstes Teammitglied dem Projekt






        })

        var showprojects2: ImageView = v.findViewById(com.example.pip.R.id.settings)   //Button Projekte dynamisch anzeigen lassen

        showprojects2.setOnClickListener(  View.OnClickListener {

            var projectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects_full,null, false)  //erstes Project erzeugen
            var mitgliederlayout: LinearLayout =projectsview.findViewById(com.example.pip.R.id.projectmitglieder)  //zum hinzufügen von Mitglieder zu Projekt 1

            var projectsview2: View= layoutInflater.inflate(com.example.pip.R.layout.projects_full,null, false)  //zweites Project erzeugen
            var mitgliederlayout2: LinearLayout =projectsview2.findViewById(com.example.pip.R.id.projectmitglieder)  //zum hinzufügen von Mitglieder zu Projekt 2

            var projectmitglied: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //ProjektMitglieder erzeugen
            var mitgliedsname: TextView= projectmitglied.findViewById(R.id.personname)  //Beispiel 1 MitgliedsName ändern
            mitgliedsname.setText("Uweeeee")

            var projectmitglied2: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //zweites ProjektMitglieder erzeugen
            var mitgliedsname2: TextView= projectmitglied2.findViewById(R.id.personname)  //Beispiel 2 MitgliedsName ändern
            mitgliedsname2.setText("Peteeeeer")

            var projectmitglied3: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //drittes ProjektMitglieder erzeugen
            var mitgliedsname3: TextView= projectmitglied2.findViewById(R.id.personname)  //Beispiel 2 MitgliedsName ändern
            mitgliedsname3.setText("Jürgeeeen")



//            projectslayout.addView(projectsview)  //Project1 erzeugen in diesem Bereich der LayoutListe
//            mitgliederlayout.addView(projectmitglied)  //erstes Teammitglied dem Projekt 1 hinzufügen
//            mitgliederlayout.addView(projectmitglied2) //zweites Teammitglied dem Projekt 1 hinzufügen


            projectslayout.addView(projectsview2)  //Projects erzeugen in diesem Bereich der LayoutListe
            mitgliederlayout2.addView(projectmitglied)  //erstes Teammitglied dem Projekt






        })



//        val loeschNutzer = "93c6863e-97d2-4783-a05a-91326f51e3b4"
//        okHttpDelete(ressource_users, loeschNutzer)
//        Log.d("HTTP-Request", "Und Weg ist der Nutzer mit der ID: $loeschNutzer")

        // Liste aller Nutzer holen und als String speichern
        val getListOfUsersString = okHttpGet(ressource_users)

        // Wert der die Liste aller User in einem Array vom Typ One_User enthält
        val userListe: List<One_User> = gson.fromJson(getListOfUsersString, Array<One_User>::class.java).toList()

        // Ausgabe aller Nutzer mit Nicknamen
        Log.d("HTTP-Request", "Liste aller Nutzer:")
        for (user in userListe) {
            Log.d("HTTP-Request","Nickname: ${user.nickname}")
        }

        // Einen GET auf einen spezifischen Nutzer ausführen und den Nicknamen ausgeben
        val getSpecificUserString = okHttpGet(ressource_users ,specificUserID) // hole den JSON String vom spezifischen User aus der Ressource Users
        val specific_user = gson.fromJson(getSpecificUserString, One_User::class.java) // speichere den User in eine Variable vom Typ One_User
        Log.d("HTTP-Request", "Der gewünschte User ist unter dem Nicknamen \"${specific_user.nickname}\" zu finden.")

        Log.d("HTTP-Request", "Inhalt des Gets: $getSpecificUserString")
        Log.d("HTTP-Request", "NACH DEM STRING")

        val zweiterTeilnehmer = "a87c4718-4004-4851-92f6-081c87aa4643"
        val ersterTeilnehmer = specificUserID
        var seifenkiste: One_Project = One_Project(
                projektleiter = specificUserID,
                name = "Grüne Lernplatform!",
                gewuenschteTeamgroesse = 2,
                gewuenschteRollen = "Informatiker, Weltenverbesserer",
                beschreibung = "Ich möchte eine grüne Lernplatform bauen auf der die Kinder sich über nachhaltige Alternativen informieren können!",
                kategorie = listOf(102, 104),
                ausfuehrungsort = "Berlin",
                zweck = listOf(2, 3, 4),
                teilnehmer = listOf(ersterTeilnehmer, zweiterTeilnehmer)
        )

        // Projekt seifenkiste wird mit Daten gefüllt und dann via POST Request versendet
//        Log.d("HTTP-wichtig-Response", "Vor dem JSON Parsen")
//        val jsonProjectObject = gson.toJson(seifenkiste) // das One_Project Objekt wird in einen JSON String geparset
//        Log.d("HTTP-wichtig-Response", jsonProjectObject)
//        okHttpPost(ressource_projects, json = jsonProjectObject) // das Projekt wird gepostet
//        Log.d("HTTP-wichtig-Response", "nach dem Post")


//        var einstein: One_User = One_User(nickname = "MC^2", vorname = "Albert", name = "Einstein" ,interessen = listOf(1,2,3), faehigkeiten = listOf(101,102,103), email = "mathe@genius.com", passwort = "relativEinfach" )
//        val jsonUserObject = gson.toJson(einstein) // das One_User Objekt wird in einen JSON String geparset
//        okHttpPost(ressource_users, json = jsonUserObject) // der User wird gepostet



        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FourthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FourthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}