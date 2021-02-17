package com.example.pip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

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


        var showprojects: Button = v.findViewById(com.example.pip.R.id.suchen_button)   //Projekte dynamisch anzeigen lassen
        var scrollbar: LinearLayout =v.findViewById(com.example.pip.R.id.suchresultat_layout)

        showprojects.setOnClickListener(  View.OnClickListener {

            var projectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects,null, false)  //ein Project erzeugen

            scrollbar.addView(projectsview)  //Projects erzeugen in diesem Bereich der LayoutListe



        })

        var clearsearch: Button = v.findViewById(com.example.pip.R.id.zurücksetzen_button)   //Projektesuche & Einstellungen zurücksetzen lassen

        clearsearch.setOnClickListener(  View.OnClickListener {

            scrollbar.removeAllViews()  //Suchresultate löschen

            var fähigkeiten: EditText = v.findViewById(com.example.pip.R.id.fähigkeiten_eingabe) //Hier z.b Fähigkeiten zurücksetzen
            fähigkeiten.setText("Musterfähigkeiten")



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