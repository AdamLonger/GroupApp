package data.paging

import android.arch.paging.ItemKeyedDataSource
import io.reactivex.schedulers.Schedulers

class NewsDataSource : ItemKeyedDataSource<String, News>() {

    init {
        FirebaseManager.getAnimalChangeSubject()?.observeOn(Schedulers.io())?.subscribeOn(Schedulers.computation())?.subscribe {
            invalidate()
        }
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Animal>) {
        FirebaseManager.getAnimals(params.requestedLoadSize).subscribe({
            callback.onResult(it)
        }, {})
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<News>) {
        FirebaseManager.getAnimalsAfter(params.key, params.requestedLoadSize).subscribe({
            callback.onResult(it)
        }, {})
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<News>) {
        FirebaseManager.getAnimalsBefore(params.key, params.requestedLoadSize).subscribe({
            callback.onResult(it)
        }, {})
    }

    override fun getKey(item: Animal): String {
        return item.objectKey ?: ""
    }
}
