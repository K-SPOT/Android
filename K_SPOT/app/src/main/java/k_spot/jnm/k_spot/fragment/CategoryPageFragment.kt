package k_spot.jnm.k_spot.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.activity.DetailedEntertainerPageActivity
import kotlinx.android.synthetic.main.fragment_category_page.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class CategoryPageFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toast("카테고리 페이지")

    }
}