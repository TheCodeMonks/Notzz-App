/*
 *
 *  *
 *  *  * MIT License
 *  *  *
 *  *  * Copyright (c) 2020 Spikey Sanju
 *  *  *
 *  *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  * of this software and associated documentation files (the "Software"), to deal
 *  *  * in the Software without restriction, including without limitation the rights
 *  *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  * copies of the Software, and to permit persons to whom the Software is
 *  *  * furnished to do so, subject to the following conditions:
 *  *  *
 *  *  * The above copyright notice and this permission notice shall be included in all
 *  *  * copies or substantial portions of the Software.
 *  *  *
 *  *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  * SOFTWARE.
 *  *
 *
 *
 */

package thecodemonks.org.nottzapp.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import thecodemonks.org.nottzapp.datastore.UIModePreference
import thecodemonks.org.nottzapp.model.Notes
import thecodemonks.org.nottzapp.repo.NotesRepo
import thecodemonks.org.nottzapp.utils.NotesViewState
import javax.inject.Inject

class NotesViewModel @Inject internal constructor(
    application: Application,
    private val notesRepo: NotesRepo,
) :
    AndroidViewModel(application) {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<NotesViewState>(NotesViewState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState = _uiState.asStateFlow()

    // DataStore
    private val uiDataStore = UIModePreference(application)

    // get UI mode
    val getUIMode = uiDataStore.uiMode

    // save UI mode
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(IO) {
            uiDataStore.saveToDataStore(isNightMode)
        }
    }

    // save notes
    fun insertNotes(taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = Notes(
            title = taskName,
            description = taskDesc
        )
        notesRepo.insert(notes)
    }

    // save notes
    fun updateNotes(id: Int, taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = Notes(
            id = id,
            title = taskName,
            description = taskDesc
        )
        notesRepo.update(notes)
    }

    // get all saved notes by default
    init {
        viewModelScope.launch {
            notesRepo.getSavedNotes().distinctUntilChanged().collect { result ->
                if (result.isNullOrEmpty()) {
                    _uiState.value = NotesViewState.Empty
                } else {
                    _uiState.value = NotesViewState.Success(result)
                }
            }
        }
    }

    // delete notes
    fun deleteNotes(taskID: Int, taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = Notes(
            id = taskID,
            title = taskName,
            description = taskDesc
        )
        notesRepo.deleteNotes(notes)
    }
}
