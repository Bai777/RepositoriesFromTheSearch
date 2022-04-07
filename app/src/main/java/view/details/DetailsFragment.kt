package view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.repositoriesfromthesearch.R
import kotlinx.android.synthetic.main.fragment_details.*
import presenter.details.DetailsPresenter
import presenter.details.IPresenterDetailsContract
import java.util.*

class DetailsFragment : Fragment(), IViewDetailsContract {

    private val presenter: IPresenterDetailsContract = DetailsPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    private fun setUI() {
        arguments?.let {
            val count = it.getInt(TOTAL_COUNT_EXTRA, 0)
            presenter.setCounter(count)
            setCountText(count)
        }
        decrementButton.setOnClickListener { presenter.onDecrement() }
        incrementButton.setOnClickListener { presenter.onIncrement() }
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }

    private fun setCountText(count: Int) {
        totalCountTextViewDetails.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }

    companion object {

        private const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

       @JvmStatic
       fun newInstance(counter: Int) = DetailsFragment().apply {
           arguments = bundleOf(TOTAL_COUNT_EXTRA to counter)
       }
    }
}