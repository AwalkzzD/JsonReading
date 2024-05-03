package com.example.jsonreading.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonreading.R
import com.example.jsonreading.data.person.Person
import com.example.jsonreading.databinding.FragmentDatabaseBinding
import com.example.jsonreading.utils.adapter.GenericDataAdapter
import com.example.jsonreading.utils.common.toPerson
import com.example.jsonreading.utils.common.toPersonEntity
import com.example.jsonreading.utils.common.toPersonObject
import com.example.jsonreading.utils.db.realm.RealmHelper
import com.example.jsonreading.utils.db.room.PersonAppDatabase
import com.example.jsonreading.utils.db.room.dao.PersonDao
import com.example.jsonreading.utils.db.sqlite.DBHelper

class DatabaseFragment : Fragment() {

    private lateinit var binding: FragmentDatabaseBinding
    private lateinit var personDataAdapter: GenericDataAdapter<Person>

    private lateinit var sqLiteDBHelper: DBHelper
    private lateinit var personDao: PersonDao
    private lateinit var personRealm: RealmHelper

    private var personList: MutableList<Person> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDatabaseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sqLiteDBHelper = DBHelper(requireActivity(), null)
        personDao = PersonAppDatabase.getInstance(requireActivity()).personDao()
        personRealm = RealmHelper()

        initRecyclerView()

        // Room DB Data Handling
        binding.roomDb.setOnClickListener {
            if (validInput()) {
                val name = binding.nameEt.text.toString().trim()
                val age = binding.ageEt.text.toString().trim()

                personDao.insertPerson(Person(name, age).toPersonEntity())

                showPersonAddedToast()
            } else {
                showEmptyFieldToast()
            }
        }

        binding.getRoom.setOnClickListener {
            personList.clear()
            if (personDao.getPersonData().isNotEmpty()) {
                personList.addAll(personDao.getPersonData().map { personEntity ->
                    personEntity.toPerson()
                })
            } else {
                showDbEmptyToast()
            }
            personDataAdapter.notifyDataSetChanged()
        }

        binding.deleteRoomDb.setOnClickListener {
            personDao.deleteAllData()
            showDataDeletedToast()
        }

        binding.modifyRoomDb.setOnClickListener {
            if (validInput()) {
                val name = binding.nameEt.text.toString().trim()
                val age = binding.ageEt.text.toString().trim()
                personDao.updatePerson(Person(name, age).toPersonEntity())
                showPersonUpdatedToast()
            }
        }

        // SQLite DB Data Handling
        binding.sqliteDb.setOnClickListener {
            if (validInput()) {
                val name = binding.nameEt.text.toString().trim()
                val age = binding.ageEt.text.toString().trim()

                sqLiteDBHelper.addPerson(name, age)

                showPersonAddedToast()
            } else {
                showEmptyFieldToast()
            }
        }

        binding.getSqlite.setOnClickListener {
            personList.clear()
            if (sqLiteDBHelper.getPersonData().isNotEmpty()) {
                personList.addAll(sqLiteDBHelper.getPersonData())
            } else {
                showDbEmptyToast()
            }
            personDataAdapter.notifyDataSetChanged()
        }

        binding.deleteSqliteDb.setOnClickListener {
            sqLiteDBHelper.deleteAllData()
            showDataDeletedToast()
        }

        binding.modifySqliteDb.setOnClickListener {
            if (validInput()) {
                val name = binding.nameEt.text.toString().trim()
                val age = binding.ageEt.text.toString().trim()
                sqLiteDBHelper.updatePerson(name, age)
                showPersonUpdatedToast()
            }
        }

        // Realm DB Data Handling
        binding.realmDb.setOnClickListener {
            if (validInput()) {
                val name = binding.nameEt.text.toString().trim()
                val age = binding.ageEt.text.toString().trim()

                personRealm.insertData(Person(name, age))

                showPersonAddedToast()
            } else {
                showEmptyFieldToast()
            }
        }

        binding.getRealm.setOnClickListener {
            personList.clear()
            if (personRealm.getData().isNotEmpty()) {
                personList.addAll(personRealm.getData())
            } else {
                showDbEmptyToast()
            }
            personDataAdapter.notifyDataSetChanged()
        }

        binding.deleteRealmDb.setOnClickListener {
            personRealm.deleteAllData()
            showDataDeletedToast()
        }

        binding.modifyRealmDb.setOnClickListener {
            if (validInput()) {
                val name = binding.nameEt.text.toString().trim()
                val age = binding.ageEt.text.toString().trim()

                personRealm.updateData(Person(name, age).toPersonObject())
                showPersonUpdatedToast()
            }
        }
    }

    private fun initRecyclerView() {
        personDataAdapter = GenericDataAdapter(personList, R.layout.person_list_item) { person ->
            Toast.makeText(requireActivity(), person.name, Toast.LENGTH_SHORT).show()
        }

        binding.personsRv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = personDataAdapter
        }
    }

    private fun validInput(): Boolean {
        return binding.nameEt.text.toString().isNotBlank() && binding.ageEt.text.toString()
            .isNotBlank() && binding.ageEt.text.toString().toInt() in 1..100
    }

    private fun showPersonAddedToast() {
        Toast.makeText(
            requireActivity(), "Person Added to DB", Toast.LENGTH_SHORT
        ).show()
        binding.nameEt.text.clear()
        binding.ageEt.text.clear()
    }

    private fun showDataDeletedToast() {
        Toast.makeText(
            requireActivity(), "Data Deleted from DB", Toast.LENGTH_SHORT
        ).show()
    }

    private fun showPersonUpdatedToast() {
        Toast.makeText(
            requireActivity(), "Person Updated", Toast.LENGTH_SHORT
        ).show()
        binding.nameEt.text.clear()
        binding.ageEt.text.clear()
    }

    private fun showEmptyFieldToast() {
        Toast.makeText(requireActivity(), "Invalid Input", Toast.LENGTH_SHORT).show()
    }

    private fun showDbEmptyToast() {
        Toast.makeText(requireActivity(), "Database is empty!", Toast.LENGTH_SHORT).show()
    }
}