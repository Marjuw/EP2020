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
import androidx.fragment.app.FragmentTransaction
import com.example.REST_API_Client.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProjektDetailAnsicht.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjektDetailAnsichtFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v: View= inflater.inflate(com.example.pip.R.layout.activity_teams, container, false)
        var scrollbar: LinearLayout=v.findViewById(com.example.pip.R.id.projects_layout)

        val getSpecificProjectString = okHttpGet(ressource_projects ,main.detailProjektID) // hole den JSON String vom spezifischen User aus der Ressource Users
        val projekt = gson.fromJson(getSpecificProjectString, One_Project::class.java) // speichere den User in eine Variable vom Typ One_User

        ProjektDetailAnsichtÖffnen(v, scrollbar, projekt)


        return v
    }

    fun ProjektDetailAnsichtÖffnen(v: View, scrollbar: LinearLayout, projekt: One_Project) {

        var fullprojectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects_full,null, false)  //ein Detailansicht für ein Projekt erzeugen
        scrollbar.removeAllViews()

        val projektListenAnforderungenString = TagID_inStringList(projekt.anforderung)
        val projektListenKategorieString = TagID_inStringList(projekt.kategorie)
        val projektListenZweckString = TagID_inStringList(projekt.zweck)


        // Wenn der eingeloggte Nutzer auch Projektleiter ist, dann erscheint der Editier-Button und erhällt seinen Code
        if( main.loggedinUserID == projekt.projektleiter)
        {

            var projektDetailEintragAdmin: ImageView = fullprojectsview.findViewById(R.id.projectadminfull)
            projektDetailEintragAdmin.visibility = ImageView.VISIBLE

            var projektDetailHeaderEdit: ImageView = v.findViewById(R.id.edit_project_eigen)
            projektDetailHeaderEdit.visibility = ImageView.VISIBLE
            projektDetailHeaderEdit.setOnClickListener(View.OnClickListener {
                Log.d("Projekt", "I'm there !!")

                var projektEditFragment: ProjectsEditFragment = ProjectsEditFragment()
                var transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(this.id, projektEditFragment)
                transaction.commit()
            })
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




            // entfernen vom Mitglied
//                    if( main.loggedinUserID == projekt.projektleiter)
//                    {
//                        var projektDetailMitgliedEntfernen: ImageView = projektMitgliedview.findViewById(R.id.project_remove_member)
//                        projektDetailMitgliedEntfernen.visibility = ImageView.VISIBLE
//                    }

            projektfullMitglieder.addView(projektMitgliedview)
            var profilDetailAnsicht: ImageView = projektMitgliedview.findViewById(R.id.Person_eigen)
            profilDetailAnsicht.setOnClickListener(View.OnClickListener {

                OeffneProfilDetailAnsicht(person)


            })
        }


        scrollbar.addView(fullprojectsview)
    }

    fun OeffneProfilDetailAnsicht(person: One_User){
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

    fun TagID_inStringList(numList: List<Int>? = null): MutableList<String>{
        val stringListe = mutableListOf<String>()
        numList?.forEach { x ->
            main.tagListe?.forEach { y -> if(y.id == x) stringListe.add(y.bezeichnung) }
        }
        return stringListe
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProjektDetailAnsicht.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ProjektDetailAnsichtFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}