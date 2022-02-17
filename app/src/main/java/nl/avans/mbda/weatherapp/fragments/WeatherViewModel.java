package nl.avans.mbda.weatherapp.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nl.avans.mbda.weatherapp.models.OneCall;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<OneCall> oneCall = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedItem = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshing = new MutableLiveData<>();

    public void setOneCall(OneCall oneCall) {
        this.oneCall.setValue(oneCall);
    }

    public LiveData<OneCall> getOneCall() {
        return oneCall;
    }

    public void setSelectedItem(Integer selectedItem) {
        this.selectedItem.setValue(selectedItem);
    }

    public LiveData<Integer> getSelectedItem() {
        return selectedItem;
    }

    public void setRefreshing(Boolean refreshing) {
        this.refreshing.setValue(refreshing);
    }

    public LiveData<Boolean> getRefreshing() {
        return refreshing;
    }
}