package com.example.esamefinale

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val NUM = 148

class FragmentList : Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }


    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter = StudentAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerlist)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = StudentAdapter(viewModel.list)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == NUM) {
            var stud = data?.getSerializableExtra(KEY) as User
            for (item in viewModel.list) {
                if (item.name == stud.name) {
                    item.HaveMac = stud.HaveMac
                    break
                }
            }
        }
        updateUI(viewModel.list)
    }

    private fun updateUI(user: List<User>) {
        adapter = StudentAdapter(user)
        recyclerView.adapter = adapter
    }

    private inner class StudentHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        lateinit var student: User
        private val name: TextView = itemView.findViewById(R.id.textView_name)
        private val surname: TextView = itemView.findViewById(R.id.textView_surname)
        private val id: TextView = itemView.findViewById(R.id.textView_id)
        private val img: ImageView = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(student: User) {
            this.student = student
            name.text = this.student.name
            surname.text = this.student.surname
            id.text = this.student.IDPC.toString()

            if (this.student.HaveMac) {
                img.visibility = View.VISIBLE

            } else {
                img.visibility = View.GONE
                id.visibility = View.GONE
            }
        }

        override fun onClick(view: View?) {
            Toast.makeText(context, "${student.name} pressed!", Toast.LENGTH_SHORT).show()
            context?.let {
                var intent = SingleUser.newIntent(it, student)
                startActivityForResult(intent, NUM)
            }
        }
    }

    private inner class StudentAdapter(val list: List<User>) :
        RecyclerView.Adapter<StudentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
            val view = layoutInflater.inflate(R.layout.index, parent, false)
            return StudentHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: StudentHolder, position: Int) {
            val student = list[position]
            holder.bind(student)
        }

    }
}


