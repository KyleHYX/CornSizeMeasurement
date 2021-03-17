package com.example.cornsizemeasurement.ui.historicalData

import androidx.lifecycle.*
import com.example.cornsizemeasurement.db.CornSize
import com.example.cornsizemeasurement.db.CornSizeRepository
import kotlinx.coroutines.launch

class HistoricalDataViewModel(private val repository: CornSizeRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Here's historical Data"
    }
    val text: LiveData<String> = _text

    val AllCornSizes: LiveData<List<CornSize>> = repository.allCornSize.asLiveData()

    fun insert(cornSize: CornSize) = viewModelScope.launch {
        repository.insert(cornSize);
    }

    fun deleteAll() = viewModelScope.launch{
        repository.deleteAll();
    }

    val someList: MutableLiveData<ArrayList<Int>> = MutableLiveData(arrayListOf(1,2,3));
}

class HistoricalViewModelFactory(private val repository: CornSizeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoricalDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoricalDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}