package com.ubaya.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.advweek4.R
import com.ubaya.advweek4.databinding.FragmentStudentDetailBinding
import com.ubaya.advweek4.model.Student
import com.ubaya.advweek4.util.loadImage
import com.ubaya.advweek4.viewmodel.DetailViewModel
import com.ubaya.advweek4.viewmodel.ListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.fragment_student_list.*
import kotlinx.android.synthetic.main.student_list_item.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class StudentDetailFragment : Fragment(), StudentNotificationClickListener {
    private lateinit var viewModel :DetailViewModel
    private lateinit var dataBinding: FragmentStudentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        arguments?.let{
            viewModel.fetch(StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId)
        }

        observeViewModel()

        dataBinding.notificationListener = this
    }

    private fun observeViewModel() {
        viewModel.studentLiveData.observe(viewLifecycleOwner){
            dataBinding.student = it
            /*
            with (it){
                editID.setText(id)
                editName.setText(name)
                editDOB.setText(dob)
                editPhone.setText(phone)
                imageViewStudentPhotoDetail.loadImage(photoURL, progressBarStudentPhotoDetail)
                buttonNotif.setOnClickListener {
                    Observable.timer(5, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("mynotif", "Notification after 5 seconds")
                            MainActivity.showNotification(name!!, "Notification created", R.drawable.ic_baseline_local_fire_department_24)
                        }
                }
            }
             */
        }
    }

    override fun onNotificationClick(view: View, obj: Student) {
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("mynotif", "Notification after 5 seconds")
                MainActivity.showNotification(obj.name!!, "Notification created", R.drawable.ic_baseline_local_fire_department_24)
            }
    }
}