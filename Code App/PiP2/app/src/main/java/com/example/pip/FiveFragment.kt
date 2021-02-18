package com.example.pip


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.REST_API_Client.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FiveFragment : Fragment() {

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


        var main: MainActivity = MainActivity()
        main.loggedinUserID

        // Inflate the layout for this fragment
        var v: View= inflater.inflate(com.example.pip.R.layout.activity_profile, container, false)


        // Einen GET auf einen spezifischen Nutzer ausführen und den Nicknamen ausgeben
        val getSpecificUserString = okHttpGet(ressource_users ,main.loggedinUserID) // hole den JSON String vom spezifischen User aus der Ressource Users
        val loggedinUser = gson.fromJson(getSpecificUserString, One_User::class.java) // speichere den User in eine Variable vom Typ One_User

        //Ab hier alle Profildaten den Mustereinträgen überschreiben
        var nicknameProfil :TextView =  v.findViewById(R.id.nickname_eigen_feld)   // nickname Textfeld identifizieren
        nicknameProfil.setText(loggedinUser.nickname)  //Mustereintrag von Nickname zu dem Eintrag von Nickname der Get Anfrage überschreiben

        var nameProfil :TextView =  v.findViewById(R.id.name_eigen_feld)
        nameProfil.setText(loggedinUser.vorname)

        var nachnameProfil :TextView =  v.findViewById(R.id.nachname_eigen_feld)
        nachnameProfil.setText(loggedinUser.name)

        var beschreibungProfil :TextView =  v.findViewById(R.id.beschreibung_eigen_feld)
        beschreibungProfil.setText(loggedinUser.beschreibung)

        var kommunikationProfil :TextView =  v.findViewById(R.id.kommunikation_eigen_feld)
        kommunikationProfil.setText(loggedinUser.kommunikation)

        var interessenString = ""
        var faehigkeitenString = ""

        for (x in loggedinUser.interessen){
            Log.d("HTTP-Request", x.toString())
            for (y in main.tagListe){
                if ((y.id == x) && x == loggedinUser.interessen.last()) interessenString += y.bezeichnung
                else if (y.id == x) interessenString += "${y.bezeichnung}, "
            }
        }

        for (x in loggedinUser.faehigkeiten){
            Log.d("HTTP-Request", x.toString())
            for (y in main.tagListe){
                if ((y.id == x) && x == loggedinUser.faehigkeiten.last()) faehigkeitenString += y.bezeichnung
                else if (y.id == x) faehigkeitenString += "${y.bezeichnung}, "
            }
        }

        var interessenProfil :TextView =  v.findViewById(R.id.interessen_eigen_feld)
        interessenProfil.setText(interessenString)

        var faehigkeitenProfil :TextView =  v.findViewById(R.id.fäghigkeiten_eigen_feld)
        faehigkeitenProfil.setText(faehigkeitenString)






        var edit:ImageView= v.findViewById(com.example.pip.R.id.editicon)   //Beim klicken auf Edit wird das Layout gewechselt, damit man Text editieren kann
        edit.setOnClickListener(  View.OnClickListener {

                var profileEditFragment: ProfileEditFragment = ProfileEditFragment()
                var transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(this.id, profileEditFragment)
                transaction.commit()


        })


        var bundle=getArguments().toString();  //Ab hier werden gesendete Nachrichten von anderen Fragmenten entgen genommen
        var v1: String= getArguments()?.getString("key").toString()

        var x : TextView= v.findViewById(com.example.pip.R.id.name_eigen_feld)

        if (v1 != "null") {
            x.setText(v1)
        }


        Toast.makeText(this.context, "hallo", Toast.LENGTH_LONG).show()  //Pop Up Nachricht


     /*    var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  //Text manuell in XML Dynamisch hinzufügen
        LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(900,1900,5,6);

        val textView: TextView = TextView(this.context) as TextView
        textView.setText("textbeispiel")
        //textView.layoutParams=params
        this.activity?.addContentView(textView,params)

      */



        var showprojects:TextView= v.findViewById(com.example.pip.R.id.beteiligteprojekte_eigen)   //Projekte dynamisch anzeigen lassen
        var scrollbar: LinearLayout=v.findViewById(com.example.pip.R.id.layout_list)

        showprojects.setOnClickListener(  View.OnClickListener {

            var projectsview: View= layoutInflater.inflate(com.example.pip.R.layout.projects,null, false)  //ein Project erzeugen

            scrollbar.addView(projectsview)  //Projects erzeugen in diesem Bereich der LayoutListe



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
         * @return A new instance of fragment FiveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

}