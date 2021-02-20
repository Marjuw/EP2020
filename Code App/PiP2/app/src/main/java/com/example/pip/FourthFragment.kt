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


        val main: MainActivity = MainActivity()

        // Liste aller Projekte holen und als String speichern
        val getListOfProjectsString = okHttpGet(ressource_projects)

        // Wert der die Liste aller Projekte in einem Array vom Typ One_Project enthält
        val projectListe: List<One_Project> = gson.fromJson(getListOfProjectsString, Array<One_Project>::class.java).toList()

        var loggedinUserProjektListe = mutableListOf<One_Project>()
        for (projekt in projectListe){
            if (projekt.teilnehmer.contains(main.loggedinUserID)) loggedinUserProjektListe.add(projekt)
        }

        //Projekte dynamisch anzeigen lassen
        var scrollbar: LinearLayout=v.findViewById(com.example.pip.R.id.projects_layout)

        for(projekt in loggedinUserProjektListe) {

            var projektListenAnforderungenString = mutableListOf<String>()
            projekt.anforderung?.forEach { x ->
                main.tagListe?.forEach { y -> if (y.id == x) projektListenAnforderungenString.add(y.bezeichnung) }
            }

            var projektListenKategorieString = mutableListOf<String>()
            projekt.kategorie.forEach { x ->
                main.tagListe?.forEach { y -> if (y.id == x) projektListenKategorieString.add(y.bezeichnung) }
            }

            var projektListenZweckString = mutableListOf<String>()
            projekt.zweck?.forEach { x ->
                main.tagListe?.forEach { y -> if (y.id == x) projektListenZweckString.add(y.bezeichnung) }
            }

            var projectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects,null, false)  //ein Project erzeugen

            var projektEintragName: TextView = projectsview.findViewById(R.id.projektname)
            projektEintragName.setText(projekt.name)

            var projektEintragOrt: TextView = projectsview.findViewById(R.id.projektort)
            projektEintragOrt.setText(projekt.ausfuehrungsort)

            var projektEintragSkills: TextView = projectsview.findViewById(R.id.projektskills)
            projektEintragSkills.setText(projektListenAnforderungenString.joinToString(separator = ", "))

            var projektEintragKategorie: TextView = projectsview.findViewById(R.id.projektkategorie)
            projektEintragKategorie.setText(projektListenKategorieString.joinToString(separator = ", "))



            if( main.loggedinUserID == projekt.projektleiter)
            {
                var projektEintragAdmin: ImageView = projectsview.findViewById(R.id.projectadmin)
                projektEintragAdmin.visibility = ImageView.VISIBLE
            }


            // der ID Wert des Projekts, den du benötigst wenn du auf den Pfeil klickst um das spezifische Projekt dann zu öffnen
            var openProjectButton : ImageView = projectsview.findViewById(R.id.openproject)
            // hier wird die Detailansicht für ein Projekt dynamisch erzeugt
            openProjectButton.setOnClickListener(View.OnClickListener {

                /*    var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  //Text manuell in XML Dynamisch hinzufügen
                LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(900,1900,5,6);

                val textView: TextView = TextView(this.context) as TextView
                textView.setText("textbeispiel")
                //textView.layoutParams=params
                this.activity?.addContentView(textView,params)

              */


                var fullprojectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects_full,null, false)  //ein Detailansicht für ein Projekt erzeugen
                scrollbar.removeAllViews()

                // Wenn der eingeloggte Nutzer auch Projektleiter ist, dann erscheint der Editier-Button und erhällt seinen Code
                if( main.loggedinUserID == projekt.projektleiter)
                {
                    var projektDetailHeaderEdit: ImageView = v.findViewById(R.id.edit_project_eigen)
                    projektDetailHeaderEdit.visibility = ImageView.VISIBLE
                    projektDetailHeaderEdit.setOnClickListener(View.OnClickListener {
                        Log.d("Projekt", "I'm there !!")
                    })


                }

                if( main.loggedinUserID == projekt.projektleiter)
                {
                    var projektDetailEintragAdmin: ImageView = fullprojectsview.findViewById(R.id.projectadminfull)
                    projektDetailEintragAdmin.visibility = ImageView.VISIBLE
                }

                var projektDetailEintragName: TextView = fullprojectsview.findViewById(R.id.projektnamefull)
                projektDetailEintragName.setText(projekt.name)

                var projektDetailEintragBeschreibung: TextView = fullprojectsview.findViewById(R.id.projectbeschreibung_feld)
                projektDetailEintragBeschreibung.setText(projekt.beschreibung)

                var projektDetailEintragOrt: TextView = fullprojectsview.findViewById(R.id.projektortfull)
                projektDetailEintragOrt.setText(projekt.ausfuehrungsort)

                var projektDetailEintragSkills: TextView = fullprojectsview.findViewById(R.id.anforderungen_full_feld)
                projektDetailEintragSkills.setText(projektListenAnforderungenString.joinToString(separator = ", "))

                var projektDetailEintragKategorie: TextView = fullprojectsview.findViewById(R.id.projektkategoriefull_feld)
                projektDetailEintragKategorie.setText(projektListenKategorieString.joinToString(separator = ", "))

                var projektDetailEintragZweck: TextView = fullprojectsview.findViewById(R.id.projectzweck_full_feld)
                projektDetailEintragZweck.setText(projektListenZweckString.joinToString(separator = ", "))


                // Liste aller Nutzer holen und als String speichern
                val getListOfUsersString = okHttpGet(ressource_users)

                // Wert der die Liste aller User in einem Array vom Typ One_User enthält
                val userListe: List<One_User> = gson.fromJson(getListOfUsersString, Array<One_User>::class.java).toList()


                var listeUserImProjekt = mutableListOf<One_User>()
                for (user in userListe) {
                    if (projekt.teilnehmer.contains(user._id))listeUserImProjekt.add(user)
                }


                for (person in listeUserImProjekt){

                    var projektMitgliedview: View= layoutInflater.inflate(com.example.pip.R.layout.projectmitglied,null, false)  //ein  Mitglied erzeugen

                    //Mitglieder dynamisch anzeigen lassen
                    var projektfullMitglieder: LinearLayout=fullprojectsview.findViewById(com.example.pip.R.id.projectmitgliederlayout)  //Layout Projektmitglieder

                    var mitgliedName = ""
                    if (main.loggedinUserID == person._id) mitgliedName = "Du"
                    else mitgliedName = "${person.vorname} ${person.name}"
                    var projektDetailEintragMitgliederName: TextView = projektMitgliedview.findViewById(R.id.personname)
                    projektDetailEintragMitgliederName.setText(mitgliedName)

                    projektfullMitglieder.addView(projektMitgliedview)
                }


                scrollbar.addView(fullprojectsview)
            })







            scrollbar.addView(projectsview)  //Projects erzeugen in diesem Bereich der LayoutListe
        }




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