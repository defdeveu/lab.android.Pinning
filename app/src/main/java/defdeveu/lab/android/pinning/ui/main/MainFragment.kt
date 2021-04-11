package defdeveu.lab.android.pinning.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import defdeveu.lab.android.pinning.R
import defdeveu.lab.android.pinning.service.RetrofitFactory
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            downloadTextData()
        }
    }

    private fun downloadTextData(){
        setLoading(true)

        val handler = CoroutineExceptionHandler { _, exception ->
            CoroutineScope(Dispatchers.Main).launch {
                edit_text_result.text = exception.message.toString()
                setLoading(false)
            }
        }

        CoroutineScope(Dispatchers.IO).launch(handler) {
            val response = when(radio_group.checkedRadioButtonId){
                R.id.radio_button_1 -> RetrofitFactory.createService().getText(getString(R.string.url_lestencrypt)).body()
                R.id.radio_button_2 -> RetrofitFactory.createService().getText(getString(R.string.url_defdev)).body()
                R.id.radio_button_3 -> RetrofitFactory.createService().getText(getString(R.string.url_plain)).body()
                else -> null
            }

            withContext(Dispatchers.Main) {
                setLoading(false)
                try {
                    edit_text_result.text = response
                } catch (e: Throwable) {
                    edit_text_result.text = e.message.toString()
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean){
        progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        radio_button_1.isEnabled = isLoading.not()
        radio_button_2.isEnabled = isLoading.not()
        radio_button_3.isEnabled = isLoading.not()
        button.isEnabled = isLoading.not()
    }
}