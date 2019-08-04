package com.mind.coolest.url_checker.ui.main.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.mind.coolest.url_checker.R
import com.mind.coolest.url_checker.utils.common.SORT_BY_AVAILABILITY
import com.mind.coolest.url_checker.utils.common.SORT_BY_CONNECTION_TIME
import com.mind.coolest.url_checker.utils.common.SORT_BY_NAME

const val SORT_TYPE_PARAM = "sort_type_param"

class UrlSortDialog : DialogFragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var urlSortGroup: RadioGroup
    private var sortType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sortType = arguments?.getString(SORT_TYPE_PARAM)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_sort_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        urlSortGroup = view.findViewById(R.id.rGroup_sort_picker)
        val rBSortByName = view.findViewById<RadioButton>(R.id.rBtn_sort_byName)
        val rBSortByAvailability = view.findViewById<RadioButton>(R.id.rBtn_sort_byAvailability)
        val rBSortByConnectionTime = view.findViewById<RadioButton>(R.id.rBtn_sort_byConnectionTime)

        when (sortType) {
            SORT_BY_AVAILABILITY -> rBSortByAvailability.isChecked = true
            SORT_BY_CONNECTION_TIME -> rBSortByConnectionTime.isChecked = true
            else -> rBSortByName.isChecked = true
        }

        urlSortGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rBtn_sort_byName -> listener?.onUrlSortInteraction(SORT_BY_NAME)
                R.id.rBtn_sort_byAvailability -> listener?.onUrlSortInteraction(SORT_BY_AVAILABILITY)
                R.id.rBtn_sort_byConnectionTime -> listener?.onUrlSortInteraction(SORT_BY_CONNECTION_TIME)
            }
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onUrlSortInteraction(sortType: String)
    }

    companion object {
        @JvmStatic
        fun newInstance(sortType: String?) = UrlSortDialog().apply {
            val bundle = Bundle()
            bundle.putString(SORT_TYPE_PARAM, sortType)
            arguments = bundle
        }
    }
}
