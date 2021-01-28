package diiage.potherat.demo.demoapp3.ui.vehicle;

import android.util.Log;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import diiage.potherat.demo.demoapp3.dal.repository.SWRepository;
import diiage.potherat.demo.demoapp3.dal.retrofit.livedata.ApiErrorResponse;
import diiage.potherat.demo.demoapp3.dal.retrofit.livedata.ApiSuccessResponse;
import diiage.potherat.demo.demoapp3.model.sw.Vehicle;

public class VehicleViewModel extends ViewModel {

    SWRepository swRepository;
    ExecutorService executorService;

    private final MutableLiveData<String> idVehicle = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private LiveData<Vehicle> vehicle = new MutableLiveData<>();

    @Inject
    @ViewModelInject
    public VehicleViewModel(ExecutorService executorService, SWRepository swRepository) {
        this.swRepository = swRepository;
        this.executorService = executorService;
    }

    public MutableLiveData<String> getIdVehicle() {
        return idVehicle;
    }

    public LiveData<Vehicle> getVehicle() {
        return vehicle;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void searchVehicle() {
        executorService.submit(() -> {
            String idString = this.idVehicle.getValue() != null ? this.idVehicle.getValue() : "";
            int id = idString.isEmpty() ? 0 : Integer.parseInt(idString);

            vehicle = Transformations.map(swRepository.getVehicle(id), input -> {
                        if (input instanceof ApiSuccessResponse) {
                            return ((ApiSuccessResponse<Vehicle>) input).getBody();
                        } else if (input instanceof ApiErrorResponse) {
                            String message = "idVehicle : " + id + " " + ((ApiErrorResponse<Vehicle>) input).getErrorMessage();
                            Log.e("debug", message);
                            errorMessage.postValue(message);
                        }
                        return null;
                    }
            );
        });
    }


}
