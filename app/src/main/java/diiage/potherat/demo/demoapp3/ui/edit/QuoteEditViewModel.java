package diiage.potherat.demo.demoapp3.ui.edit;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import diiage.potherat.demo.demoapp3.common.Event;
import diiage.potherat.demo.demoapp3.dal.repository.QuoteRepository;
import diiage.potherat.demo.demoapp3.model.Quote;

public class QuoteEditViewModel extends ViewModel {

    QuoteRepository quoteRepository;
    ExecutorService executorService;
    private final MutableLiveData<String> quote = new MutableLiveData<>();
    private final MutableLiveData<String> source = new MutableLiveData<>();

    private final MutableLiveData<Event<Boolean>> dismiss = new MutableLiveData<>();



    @Inject
    @ViewModelInject
    public QuoteEditViewModel(ExecutorService executorService, QuoteRepository quoteRepository){
        this.quoteRepository = quoteRepository;
        this.executorService = executorService;
    }

    public MutableLiveData<String> getQuote() {
        return quote;
    }

    public MutableLiveData<String> getSource() {
        return source;
    }

    public void create(){
        executorService.submit(() -> {
            String quote = this.quote.getValue() != null ? this.quote.getValue() : "";
            String source = this.source.getValue() != null ? this.source.getValue() : "";
            Quote q = new Quote();
            q.setDate(LocalDateTime.now());
            q.setQuote(quote);
            q.setSource(source);
            this.quoteRepository.create(q);

            this.dismiss.postValue(new Event<>(true));
        });
    }

    public LiveData<Event<Boolean>> getDismiss() {
        return dismiss;
    }
}
