package com.example.pip

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.example.REST_API_Client.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SeecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
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
        // Inflate the layout for this fragment
        var v: View= inflater.inflate(com.example.pip.R.layout.activity_search, container, false)



        var scrollbar: LinearLayout =v.findViewById(com.example.pip.R.id.suchresultat_layout) //Layout zum hinzugügen der Suchergebnisse

        var suchEingabe: EditText = v.findViewById(com.example.pip.R.id.sucheingabe)
        var sucheDefaultWert = suchEingabe.text.toString()
        var suchenachprojectButton: Button = v.findViewById(com.example.pip.R.id.projekt_button)   //ProjektButton
        var suchenachpersonButton: Button = v.findViewById(com.example.pip.R.id.person_button)   //PersonButton

        var isProjectButtonGreen = false

        suchenachprojectButton.setOnClickListener(  View.OnClickListener {   //Suche Nach Buttons Onclick

            isProjectButtonGreen = true
            Log.d("Button", "Projekt geklickt")
            Log.d("Button", isProjectButtonGreen.toString())

            suchenachprojectButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_green));
            suchenachpersonButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_grey));
        })

        suchenachpersonButton.setOnClickListener(  View.OnClickListener {   //Suche Nach Buttons Onclick

            isProjectButtonGreen = false
            Log.d("Button", "Person geklickt")
            Log.d("Button", isProjectButtonGreen.toString())

            suchenachprojectButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_grey));
            suchenachpersonButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_green));
        })


        var ortinPersonButton: Button = v.findViewById(com.example.pip.R.id.inperson_button)   //ProjektButton
        var ortinOnlineButton: Button = v.findViewById(com.example.pip.R.id.online_button)   //PersonButton

        var isInPersonButtonGreen = false

        ortinPersonButton.setOnClickListener(  View.OnClickListener {   //Suche Nach Buttons Onclick

            isInPersonButtonGreen = true
            Log.d("Button", "In Person (Ort) geklickt")
            Log.d("Button", isInPersonButtonGreen.toString())

            ortinPersonButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_green));
            ortinOnlineButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_grey));
        })

        ortinOnlineButton.setOnClickListener(  View.OnClickListener {   //Suche Nach Buttons Onclick

            isInPersonButtonGreen = false
            Log.d("Button", "Online (Ort) geklickt")
            Log.d("Button", isInPersonButtonGreen.toString())

            ortinPersonButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_grey));
            ortinOnlineButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_green));
        })



        var showprojects: Button = v.findViewById(com.example.pip.R.id.suchen_button)   //Suchbutton
        showprojects.setOnClickListener(  View.OnClickListener {   //suchbutton onclick
            scrollbar.removeAllViews()

            var suchEingabe: EditText = v.findViewById(com.example.pip.R.id.sucheingabe)
            var suche = suchEingabe.text.toString()
            Log.d("Suche", suche)

            if (isProjectButtonGreen){
                // Liste aller Projekte holen und als String speichern und ausgeben
                val getListOfProjectsString = okHttpGet(ressource_projects)

                // Wert der die Liste aller Projekte in einem Array vom Typ One_Project enthält
                var projektListe: List<One_Project> = gson.fromJson(getListOfProjectsString, Array<One_Project>::class.java).toList()

                if (suche != sucheDefaultWert) projektListe = projektListe.filterIndexed { index, i ->  i.name.contains(suche, true) }
                Log.d("Suche", suche.toString())

                projekteDynamischErzeugen(scrollbar, projektListe)
            }
            else {
                // Liste aller Projekte holen und als String speichern und ausgeben
                val getListOfUsersString = okHttpGet(ressource_users)

                // Wert der die Liste aller Projekte in einem Array vom Typ One_Project enthält
                var userListe: List<One_User> = gson.fromJson(getListOfUsersString, Array<One_User>::class.java).toList()

//                if (suche != sucheDefaultWert) userListe = userListe.filterIndexed { index, i -> i.nickname.contains(suche, true) }
                if (suche != sucheDefaultWert) userListe = userListe.filterIndexed { index, i ->
                    i.vorname.toString().contains(suche, true) ||
                    i.name.toString().contains(suche, true) ||
                    i.nickname.toString().contains(suche, true)
                }

                nutzerDynamischErzeugen(scrollbar, userListe)
            }

        })

        var clearsearch: Button = v.findViewById(com.example.pip.R.id.zurücksetzen_button)   //Button Projektesuche & Einstellungen zurücksetzen lassen
        clearsearch.setOnClickListener(  View.OnClickListener {  //Button Projektesuche & Einstellungen zurücksetzen lassen On click

            scrollbar.removeAllViews()  //Suchresultate löschen

            var suchEingabe: EditText = v.findViewById(com.example.pip.R.id.sucheingabe) //Hier z.b Fähigkeiten zurücksetzen
            suchEingabe.setText(sucheDefaultWert)

            var fähigkeiten: EditText = v.findViewById(com.example.pip.R.id.fähigkeiten_eingabe) //Hier z.b Fähigkeiten zurücksetzen
            fähigkeiten.setText("Fähigkeiten...")

            var interessen: EditText = v.findViewById(com.example.pip.R.id.interessen_eingabe) //Hier z.b Interessen zurücksetzen
            interessen.setText("Interessen...")

        })

    return v
    }

    fun tagID_inStringList(numList: List<Int>? = null): MutableList<String>{
        val stringListe = mutableListOf<String>()
        numList?.forEach { x ->
            main.tagListe?.forEach { y -> if(y.id == x) stringListe.add(y.bezeichnung) }
        }
        return stringListe
    }

    fun nutzerDynamischErzeugen(scrollbar: LinearLayout, userListe: List<One_User>){

        for(person in userListe) {


            val projektListenFaehigkeitenString = tagID_inStringList(person.faehigkeiten)
            val projektListenInteressenString = tagID_inStringList(person.interessen)

            //eine Project-Ansicht erzeugen
            var usersview: View= layoutInflater.inflate(com.example.pip.R.layout.projects,null, false)

            var vollerName = ""
            if (main.loggedinUserID == person._id) continue
            else vollerName = "${person.vorname} ${person.name}"

            var userEintragName: TextView = usersview.findViewById(R.id.projektname)
            userEintragName.setText(vollerName)

            var userEintragOrt: TextView = usersview.findViewById(R.id.projektort)
            userEintragOrt.setText(person.ort)

            var userEintragFaehigkeiten: TextView = usersview.findViewById(R.id.projektskills)
            userEintragFaehigkeiten.setText(projektListenFaehigkeitenString.joinToString(separator = ", "))

            var userEintragInteressen: TextView = usersview.findViewById(R.id.projektkategorie)
            userEintragInteressen.setText(projektListenInteressenString.joinToString(separator = ", "))


            // der ID Wert des Nutzer, den du benötigst wenn du auf den Pfeil klickst um das Profil zu öffnen
            var openUserButton : ImageView = usersview.findViewById(R.id.openproject)
            // hier wird die Detailansicht für ein Projekt dynamisch erzeugt
            openUserButton.setOnClickListener(View.OnClickListener {

                oeffneProfilDetailAnsicht(person)

            })

            scrollbar.addView(usersview)  //Projects erzeugen in diesem Bereich der LayoutListe
        }
    }

    fun oeffneProfilDetailAnsicht(person: One_User){
        if (person._id == main.loggedinUserID) {
            var profilDetailAnsicht: FiveFragment = FiveFragment()
            var transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(this.id, profilDetailAnsicht)
            transaction.commit()
        }
        else {
            main.detailProfilID = person._id.toString()
            var profilDetailAnsicht: ProfilDetailAnsichtFremdFragment = ProfilDetailAnsichtFremdFragment()
            var transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(this.id, profilDetailAnsicht)
            transaction.commit()
        }
    }

    fun projekteDynamischErzeugen(scrollbar: LinearLayout, Projektliste: List<One_Project>){

        for(projekt in Projektliste) {

            val projektListenAnforderungenString = tagID_inStringList(projekt.anforderung)
            val projektListenKategorieString = tagID_inStringList(projekt.kategorie)
            val projektListenZweckString = tagID_inStringList(projekt.zweck)

            //eine Project-Ansicht erzeugen
            var projectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects,null, false)

            var projektEintragName: TextView = projectsview.findViewById(R.id.projektname)
            projektEintragName.setText(projekt.name)

            var projektEintragOrt: TextView = projectsview.findViewById(R.id.projektort)
            projektEintragOrt.setText(projekt.ausfuehrungsort)

            var projektEintragSkills: TextView = projectsview.findViewById(R.id.projektskills)
            projektEintragSkills.setText(projektListenAnforderungenString.joinToString(separator = ", "))

            var projektEintragKategorie: TextView = projectsview.findViewById(R.id.projektkategorie)
            projektEintragKategorie.setText(projektListenKategorieString.joinToString(separator = ", "))



            // wenn eingeloggter Nutzer Projektleiter ist: zeige Admin Logo in der Projektliste
            if( main.loggedinUserID == projekt.projektleiter)
            {
                var projektEintragAdmin: ImageView = projectsview.findViewById(R.id.projectadmin)
                projektEintragAdmin.visibility = ImageView.VISIBLE
            }


            // der ID Wert des Projekts, den du benötigst wenn du auf den Pfeil klickst um das spezifische Projekt dann zu öffnen
            var openProjectButton : ImageView = projectsview.findViewById(R.id.openproject)
            // hier wird die Detailansicht für ein Projekt dynamisch erzeugt
            openProjectButton.setOnClickListener(View.OnClickListener {

                main.detailProjektID = projekt._id.toString()

                var projektDetailAnsicht: ProjektDetailAnsichtFragment = ProjektDetailAnsichtFragment()
                var transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(this.id, projektDetailAnsicht)
                transaction.commit()


            })

            scrollbar.addView(projectsview)  //Projects erzeugen in diesem Bereich der LayoutListe
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SeecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}