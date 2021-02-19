package com.example.pip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.REST_API_Client.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileEditFragment : Fragment() {
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
         var v: View= inflater.inflate(R.layout.activity_profile_edit, container, false)

         var acceptedit: ImageView = v.findViewById(com.example.pip.R.id.acceptIcon)

         var editpersonname: EditText = v.findViewById(com.example.pip.R.id.name_eigen_feld_edit)

        // HIER mein Code
        val main: MainActivity = MainActivity()
        main.loggedinUserID

        // Einen GET auf einen spezifischen Nutzer ausführen
        val getSpecificUserString = okHttpGet(ressource_users ,main.loggedinUserID) // hole den JSON String vom spezifischen User aus der Ressource Users
        val loggedinUser = gson.fromJson(getSpecificUserString, One_User::class.java) // speichere den User in eine Variable vom Typ One_User

        //Ab hier alle Profildaten den Mustereinträgen überschreiben
        val nicknameProfil : TextView =  v.findViewById(R.id.nickname_eigen_feld_edit)   // nickname Textfeld identifizieren
        nicknameProfil.setText(loggedinUser.nickname)  //Mustereintrag von Nickname zu dem Eintrag von Nickname der Get Anfrage überschreiben

        val nameProfil : TextView =  v.findViewById(R.id.name_eigen_feld_edit)
        nameProfil.setText(loggedinUser.vorname)

        val nachnameProfil : TextView =  v.findViewById(R.id.nachname_eigen_feld_edit)
        nachnameProfil.setText(loggedinUser.name)

        val beschreibungProfil : TextView =  v.findViewById(R.id.beschreibung_eigen_feld_edit)
        beschreibungProfil.setText(loggedinUser.beschreibung)

        val kommunikationProfil : TextView =  v.findViewById(R.id.kommunikation_eigen_feld_edit)
        kommunikationProfil.setText(loggedinUser.kommunikation)


        val interessenString = mutableListOf<String>()
        loggedinUser.interessen.forEach { x ->
            main.tagListe?.forEach { y -> if ( y.id == x ) interessenString.add(y.bezeichnung) }
        }

        val faehigkeitenString = mutableListOf<String>()
        loggedinUser.faehigkeiten.forEach { x ->
            main.tagListe?.forEach{ y -> if ( y.id == x) faehigkeitenString.add(y.bezeichnung)}
        }

        val interessenProfil : TextView =  v.findViewById(R.id.interessen_eigen_feld_edit)
        interessenProfil.setText(interessenString.joinToString(separator = ", "))

        val faehigkeitenProfil : TextView =  v.findViewById(R.id.fähigkeiten_eigen_feld_edit)
        faehigkeitenProfil.setText(faehigkeitenString.joinToString(separator = ", "))


         acceptedit.setOnClickListener(  View.OnClickListener {


             Log.d("Edit", nicknameProfil.text.toString())
        // PUT Request auf den eingeloggten Nutzer machen
        val updatedUser: One_User = One_User(
                nickname = nicknameProfil.text.toString(),
                vorname = nameProfil.text.toString(),
                name = nachnameProfil.text.toString(),
                beschreibung = beschreibungProfil.text.toString(),
                kommunikation = kommunikationProfil.text.toString(),
                interessen = loggedinUser.interessen,
                faehigkeiten = loggedinUser.faehigkeiten,
                email = loggedinUser.email,
                passwort = loggedinUser.email )
        val jsonUserPut = gson.toJson(updatedUser) // das One_User Objekt wird in einen JSON String geparset
        okHttpPut(ressource_users, main.loggedinUserID, jsonUserPut) // der User wird geupdated




             // wichtig zum wechseln
            var profileFragment: FiveFragment = FiveFragment()

             // wichtig zum wechseln des Fragments
            var transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(this.id, profileFragment)
            transaction.commit()





         })
         // var t2: TextView= v.findViewById (R.id.name_eigen_feld) as TextView
         return  v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}