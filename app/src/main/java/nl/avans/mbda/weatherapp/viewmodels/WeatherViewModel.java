package nl.avans.mbda.weatherapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nl.avans.mbda.weatherapp.models.current.Current;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<Current> current = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshing = new MutableLiveData<>();

    public void setCurrent(Current current) {
        this.current.setValue(current);
    }

    public LiveData<Current> getCurrent() {
        return current;
    }

    public void setRefreshing(Boolean refreshing) {
        this.refreshing.setValue(refreshing);
    }

    public LiveData<Boolean> getRefreshing() {
        return refreshing;
    }
}