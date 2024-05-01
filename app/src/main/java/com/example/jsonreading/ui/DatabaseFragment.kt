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
import com.example.jsonreading.data.person.PersonObject
import com.example.jsonreading.databinding.FragmentDatabaseBinding
import com.example.jsonreading.utils.GenericDataAdapter
import com.example.jsonreading.utils.PersonAppDatabase
import com.example.jsonreading.utils.dao.PersonDao
import com.example.jsonreading.utils.sqlite.DBHelper
import com.example.jsonreading.utils.toPerson
import com.example.jsonreading.utils.toPersonEntity
import com.example.jsonreading.utils.toPersonObject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

private const val TAG = "DatabaseFragment"

class DatabaseFragment : Fragment() {

    private lateinit var binding: FragmentDatabaseBinding
    private lateinit var personDataAdapter: GenericDataAdapter<Person>

    private lateinit var sqLiteDBHelper: DBHelper
    private lateinit var personDao: PersonDao
    private lateinit var personRealm: Realm

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
        personRealm = Realm.open(RealmConfiguration.create(schema = setOf(PersonObject::class)))

        initRecyclerView()

        // Room DB Data Handling
        binding.roomDb.setOnClickListener {
            if (validInput()) {
                val name = binding.nameEt.text.toString()
                val age = binding.ageEt.text.toString()

                personDao.insertPerson(Person(name, age).toPersonEntity())

                showPersonAddedToast()

                binding.nameEt.text.clear()
                binding.ageEt.text.clear()
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

        // SQLite DB Data Handling
        binding.sqliteDb.setOnClickListener {
            if (validInput()) {
                val name = binding.nameEt.text.toString()
                val age = binding.ageEt.text.toString()

                sqLiteDBHelper.addPerson(name, age)

                showPersonAddedToast()

                binding.nameEt.text.clear()
                binding.ageEt.text.clear()
            } else {
                showEmptyFieldToast()
            }
        }

        binding.getSqlite.setOnClickListener {
            val cursor = sqLiteDBHelper.getPersonData()
            personList.clear()
            if (cursor != null) {
                if (cursor.count > 0) {
                    while (cursor.moveToNext()) {
                        personList.add(
                            Person(
                                cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL))
                            )
                        )
                    }
                } else {
                    showDbEmptyToast()
                }
                cursor.close()
            }
            personDataAdapter.notifyDataSetChanged()
        }

        // Realm DB Data Handling
        binding.realmDb.setOnClickListener {
            if (validInput()) {

                val name = binding.nameEt.text.toString()
                val age = binding.ageEt.text.toString()

                personRealm.writeBlocking {
                    copyToRealm(Person(name, age).toPersonObject())
                }

                showPersonAddedToast()

                binding.nameEt.text.clear()
                binding.ageEt.text.clear()
            } else {
                showEmptyFieldToast()
            }
        }

        binding.getRealm.setOnClickListener {
            personList.clear()
            if (personRealm.query<PersonObject>().find().isNotEmpty()) {
                personList.addAll(personRealm.query<PersonObject>().find().map { personObject ->
                    personObject.toPerson()
                })
            } else {
                showDbEmptyToast()
            }
            personDataAdapter.notifyDataSetChanged()
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
            .isNotBlank()
    }

    private fun showPersonAddedToast() {
        Toast.makeText(
            requireActivity(), "Person Added to SQLite Database", Toast.LENGTH_SHORT
        ).show()
    }

    private fun showEmptyFieldToast() {
        Toast.makeText(requireActivity(), "Fields are empty", Toast.LENGTH_SHORT).show()
    }

    private fun showDbEmptyToast() {
        Toast.makeText(requireActivity(), "Database is empty!", Toast.LENGTH_SHORT).show()
    }
}
