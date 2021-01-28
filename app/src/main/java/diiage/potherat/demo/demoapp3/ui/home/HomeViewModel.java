package diiage.potherat.demo.demoapp3.ui.home;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import diiage.potherat.demo.demoapp3.dal.repository.QuoteRepository;
import diiage.potherat.demo.demoapp3.model.Quote;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

/*    private LiveData<Integer> mQuotes = new MutableLiveData<>(0);
    private LiveData<Integer> mAuthors= new MutableLiveData<>(0);
    private LiveData<Quote> mLastQuote= new MutableLiveData<>(new Quote());*/
    private final QuoteRepository quoteRepository;

    @Inject
    @ViewModelInject
    public HomeViewModel(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public LiveData<Integer> getQuotes() {
        return quoteRepository.getTotalQuotes();
    }

    public LiveData<Integer> getAuthors() {
        return quoteRepository.getTotalQuotes();
    }

    public LiveData<Quote> getLastQuote() {
        return quoteRepository.getLastQuote();
    }
}