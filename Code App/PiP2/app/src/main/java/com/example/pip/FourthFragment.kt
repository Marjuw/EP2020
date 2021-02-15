package com.example.pip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

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