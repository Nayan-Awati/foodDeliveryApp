package com.nayan.food_runner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.nayan.food_runner.R


class ProfileFragment : Fragment() {
//    lateinit var txtName: TextView
//    lateinit var txtPhone: TextView
//    lateinit var txtEmail: TextView
//    lateinit var txtDelivery: TextView

    lateinit var etName: EditText
    lateinit var etPhone: EditText
    lateinit var etEmail: EditText
    lateinit var etDelivery: EditText

//    lateinit var imgNameDone: ImageView
//    lateinit var imgNameEdit: ImageView
//    lateinit var imgPhoneDone: ImageView
//    lateinit var imgPhoneEdit: ImageView
//    lateinit var imgEmailDone: ImageView
//    lateinit var imgEmailEdit: ImageView
//    lateinit var imgAddDone: ImageView
//    lateinit var imgAddEdit: ImageView
//    var s : String =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

//        txtDelivery = view.findViewById(R.id.txtAdd)
//        txtEmail = view.findViewById(R.id.txtEmail)
//        txtName = view.findViewById(R.id.txtName)
//        txtPhone = view.findViewById(R.id.txtPhone)

        etName = view.findViewById(R.id.etName)
        etDelivery = view.findViewById(R.id.etAdd)
        etEmail = view.findViewById(R.id.etEmail)
        etPhone = view.findViewById(R.id.etPhone)

//        imgAddDone = view.findViewById(R.id.imgDoneAdd)
//        imgAddEdit = view.findViewById(R.id.imgEditAdd)
//
//        imgNameDone = view.findViewById(R.id.imgDone)
//        imgNameEdit = view.findViewById(R.id.imgEdit)
//
//        imgPhoneDone =view.findViewById(R.id.imgDonePh)
//        imgPhoneEdit = view.findViewById(R.id.imgEditPh)
//
//        imgEmailDone = view.findViewById(R.id.imgDoneEmail)
//        imgEmailEdit = view.findViewById(R.id.imgEditEmail)
//
//        imgNameEdit.setOnClickListener {
//            imgNameEdit.visibility = View.GONE
//            imgNameDone.visibility = View.VISIBLE
//            s = etName.toString()

//        }
//        imgNameDone.setOnClickListener {
//            txtName.visibility = View.VISIBLE
//            txtName.text = s
//            imgNameEdit.visibility = View.VISIBLE
//        }
//
//        imgPhoneEdit.setOnClickListener {
//            imgPhoneEdit.visibility = View.GONE
//            imgPhoneDone.visibility = View.VISIBLE
//            s = etPhone.toString()
//
//        }
//        imgPhoneDone.setOnClickListener {
//            txtPhone.visibility = View.VISIBLE
//            txtPhone.text = s
//            imgPhoneEdit.visibility = View.VISIBLE
//        }
//
//        imgEmailEdit.setOnClickListener {
//            imgEmailEdit.visibility = View.GONE
//            imgEmailDone.visibility = View.VISIBLE
//            s = etEmail.toString()
//
//        }
//        imgEmailDone.setOnClickListener {
//            txtEmail.visibility = View.VISIBLE
//            txtEmail.text = s
//            imgEmailEdit.visibility = View.VISIBLE
//        }
//
//        imgAddEdit.setOnClickListener {
//            imgAddEdit.visibility = View.GONE
//            imgAddDone.visibility = View.VISIBLE
//            s = etDelivery.toString()
//
//        }
//        imgAddDone.setOnClickListener {
//            txtDelivery.visibility = View.VISIBLE
//            txtDelivery.text = s
//            imgAddEdit.visibility = View.VISIBLE
//        }

        return view
    }




}