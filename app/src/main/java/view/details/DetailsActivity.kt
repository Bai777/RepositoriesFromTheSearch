package view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.repositoriesfromthesearch.R
import com.example.repositoriesfromthesearch.databinding.DetailsActivityBinding
import kotlinx.android.synthetic.main.details_activity.*
import presenter.details.DetailsPresenter
import presenter.details.IPresenterDetailsContract
import java.util.*

class DetailsActivity : AppCompatActivity(), IViewDetailsContract {

    private val presenter: IPresenterDetailsContract = DetailsPresenter(this)
    lateinit var binding: DetailsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
    }

    private fun setUI() {
        val count = intent.getIntExtra(TOTAL_COUNT_EXTRA, 0)
        presenter.setCounter(count)
        setCountText(count)
        binding.decrementButton.setOnClickListener { presenter.onDecrement() }
        binding.incrementButton.setOnClickListener { presenter.onIncrement() }
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }

    private fun setCountText(count: Int) {
        totalCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }

    companion object {

        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        fun getIntent(context: Context, totalCount: Int): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }
        }
    }

}