package com.example.pip

import android.media.Image
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
 * Use the [ProjectsEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectsEditFragment : Fragment() {
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
        var v: View= inflater.inflate(com.example.pip.R.layout.activity_project_edit, container, false)
        var scrollbar: LinearLayout =v.findViewById(com.example.pip.R.id.myprojects_Layout)

        val getSpecificProjectString = okHttpGet(ressource_projects ,main.detailProjektID) // hole den JSON String vom spezifischen User aus der Ressource Users
        val projekt = gson.fromJson(getSpecificProjectString, One_Project::class.java) // speichere den User in eine Variable vom Typ One_User

        ProjektEditAnsichtÖffnen(v, scrollbar, projekt)



        return v
    }

    fun ProjektEditAnsichtÖffnen(v: View, scrollbar: LinearLayout, projekt: One_Project) {
        var neueTeilnehmerListe = projekt.teilnehmer

        var fullprojectsview: View= layoutInflater.inflate(com.example.pip.R.layout.editprojectsfull,null, false)  //ein Detailansicht für ein Projekt erzeugen
        scrollbar.removeAllViews()

        val projektListenAnforderungenString = TagID_inStringList(projekt.anforderung)
        val projektListenKategorieString = TagID_inStringList(projekt.kategorie)
        val projektListenZweckString = TagID_inStringList(projekt.zweck)


        var projektDetailEintragName: TextView = fullprojectsview.findViewById(R.id.projektnamefulledit)
        projektDetailEintragName.setText(projekt.name)

        var projektDetailEintragBeschreibung: TextView = fullprojectsview.findViewById(R.id.projectbeschreibung_feldedit)
        projektDetailEintragBeschreibung.setText(projekt.beschreibung)

        var projektDetailEintragOrt: TextView = fullprojectsview.findViewById(R.id.projektortfulledit)
        projektDetailEintragOrt.setText(projekt.ausfuehrungsort)

        var projektDetailEintragSkills: TextView = fullprojectsview.findViewById(R.id.anforderungen_full_feldedit)
        projektDetailEintragSkills.setText(projektListenAnforderungenString.joinToString(separator = ", "))

        var projektDetailEintragKategorie: TextView = fullprojectsview.findViewById(R.id.projektkategoriefull_feldedit)
        projektDetailEintragKategorie.setText(projektListenKategorieString.joinToString(separator = ", "))

        var projektDetailEintragZweck: TextView = fullprojectsview.findViewById(R.id.projectzweck_full_feldedit)
        projektDetailEintragZweck.setText(projektListenZweckString.joinToString(separator = ", "))


        var acceptedit: ImageView = v.findViewById(com.example.pip.R.id.acceptIconprojects)
        acceptedit.setOnClickListener(  View.OnClickListener {

            var putProjekt: One_Project = One_Project(
                projektleiter = projekt.projektleiter,
                name = projektDetailEintragName.text.toString(),
                beschreibung = projektDetailEintragBeschreibung.text.toString(),
                kategorie = projekt.kategorie,
                ausfuehrungsort = projektDetailEintragOrt.text.toString(),
                zweck = projekt.zweck,
                teilnehmer = neueTeilnehmerListe )
            val jsonProjectObject = gson.toJson(putProjekt) // das One_Project Objekt wird in einen JSON String geparset
            okHttpPut(ressource_projects, projekt._id, jsonProjectObject) // das Projekt wird gepostet

            Log.d("Projekt", "erfolgreich editiert, kehre zurück zum projekt detail screen")

            var projektDetailAnsicht: ProjektDetailAnsichtFragment = ProjektDetailAnsichtFragment()
            var transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(this.id, projektDetailAnsicht)
            transaction.commit()

        })







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
            var projektfullMitglieder: LinearLayout=fullprojectsview.findViewById(com.example.pip.R.id.projectmitgliederlayoutedit)  //Layout Projektmitglieder

            var mitgliedName = ""
            if (main.loggedinUserID == person._id) mitgliedName = "Du"
            else mitgliedName = "${person.vorname} ${person.name}"
            var projektDetailEintragMitgliederName: TextView = projektMitgliedview.findViewById(R.id.personname)
            projektDetailEintragMitgliederName.setText(mitgliedName)
            projektfullMitglieder.addView(projektMitgliedview)

            if (person._id != main.loggedinUserID)
            {

                var mitgliedEntfernen: ImageView = projektMitgliedview.findViewById(R.id.project_remove_member)
                mitgliedEntfernen.visibility = ImageView.VISIBLE
                mitgliedEntfernen.setOnClickListener(View.OnClickListener {

                    // Person aus der Gruppe entfernen
                    Log.d("Projekt", "Mitglied entfernt")

                    Log.d("Projekt", neueTeilnehmerListe.toString())
                    neueTeilnehmerListe = neueTeilnehmerListe.filterIndexed { index, i ->  i != person._id }
                    Log.d("Projekt", neueTeilnehmerListe.toString())

                    projektfullMitglieder.removeView(projektMitgliedview)


                })
            }

        }


        scrollbar.addView(fullprojectsview)
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
         * @return A new instance of fragment ProjectsEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProjectsEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}