package diiage.potherat.demo.demoapp3.ui.gallery;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import java.util.List;

import javax.inject.Inject;

import diiage.potherat.demo.demoapp3.dal.repository.QuoteRepository;
import diiage.potherat.demo.demoapp3.dal.repository.SWRepository;
import diiage.potherat.demo.demoapp3.dal.retrofit.livedata.ApiErrorResponse;
import diiage.potherat.demo.demoapp3.dal.retrofit.livedata.ApiSuccessResponse;
import diiage.potherat.demo.demoapp3.model.Quote;
import diiage.potherat.demo.demoapp3.model.sw.People;
import diiage.potherat.demo.demoapp3.model.sw.SWModelList;
import kotlinx.coroutines.CoroutineScope;

public class GalleryViewModel extends ViewModel {

    private LiveData<PagingData<Quote>> quotes;

    private LiveData<List<People>> poeples ;

    @Inject
    @ViewModelInject
    public GalleryViewModel(QuoteRepository quoteRepository, SWRepository swRepository) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer,Quote> pager = new Pager<>(new PagingConfig(20,20,true), quoteRepository::getAll);
        quotes = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);

        poeples =
                Transformations.map(swRepository.getPeoples(),input -> {
                    if(input instanceof ApiSuccessResponse) {
                        return ((ApiSuccessResponse<SWModelList<People>>) input).getBody().results;
                    } else if (input instanceof ApiErrorResponse){
                        Log.e("debug",((ApiErrorResponse<SWModelList<People>>) input).getErrorMessage()+"");
                    }
                    return null;
                }
        );
    }

    public LiveData<PagingData<Quote>> getQuotes() {
        return quotes;
    }

    public LiveData<List<People>> getPoeples() {
        return poeples;
    }
}